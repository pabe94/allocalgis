/**
 * JUMPFeatureFactory.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * (c) 2007 by lat/lon GmbH
 *
 * @author Ugo Taddei (taddei@latlon.de)
 *
 * This program is free software under the GPL (v2.0)
 * Read the file LICENSE.txt coming with the sources for details.
 */
package de.latlon.deejump.wfs.data;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.deegreewfs.datatypes.QualifiedName;
import org.deegreewfs.datatypes.Types;
import org.deegreewfs.datatypes.UnknownTypeException;
import org.deegreewfs.framework.util.IDGenerator;
import org.deegreewfs.framework.xml.XMLParsingException;
import org.deegreewfs.framework.xml.schema.XMLSchemaException;
import org.deegreewfs.model.crs.CoordinateSystem;
import org.deegreewfs.model.crs.UnknownCRSException;
import org.deegreewfs.model.feature.FeatureFactory;
import org.deegreewfs.model.feature.FeatureProperty;
import org.deegreewfs.model.feature.GMLFeatureCollectionDocument;
import org.deegreewfs.model.feature.schema.AbstractPropertyType;
import org.deegreewfs.model.feature.schema.FeatureType;
import org.deegreewfs.model.feature.schema.GMLSchema;
import org.deegreewfs.model.feature.schema.GMLSchemaDocument;
import org.deegreewfs.model.feature.schema.PropertyType;
import org.deegreewfs.model.filterencoding.ComplexFilter;
import org.deegreewfs.model.filterencoding.Filter;
import org.deegreewfs.model.filterencoding.Operation;
import org.deegreewfs.model.filterencoding.OperationDefines;
import org.deegreewfs.model.filterencoding.PropertyName;
import org.deegreewfs.model.filterencoding.SpatialOperation;
import org.deegreewfs.model.spatialschema.GeometryException;
import org.deegreewfs.model.spatialschema.GeometryFactory;
import org.deegreewfs.model.spatialschema.JTSAdapter;
import org.deegreewfs.ogcbase.CommonNamespaces;
import org.deegreewfs.ogcwebservices.wfs.XMLFactory;
import org.deegreewfs.ogcwebservices.wfs.operation.GetFeature;
import org.deegreewfs.ogcwebservices.wfs.operation.GetFeature.RESULT_TYPE;
import org.deegreewfs.ogcwebservices.wfs.operation.Query;
import org.xml.sax.SAXException;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;

import de.latlon.deejump.wfs.DeeJUMPException;
import de.latlon.deejump.wfs.client.AbstractWFSWrapper;
import de.latlon.deejump.wfs.client.WFSClientHelper;

/**
 * Utility functions to create different kinds of FeatureDatasets. <br/>Further methods provided for
 * JUMPlon implemented by UT
 * 
 * @author <a href="mailto:taddei@lat-lon.de">Ugo Taddei </a>
 */
public class JUMPFeatureFactory {

    private static Logger LOG = Logger.getLogger( JUMPFeatureFactory.class );

    private static int maxFeatures = 100;

    // Integer.parseInt( DeeJUMPProperties.getString("maxFeatures") );

    /**
     * Creates a JUMP FeatureCollection from a deegree FeatureCollection [UT]
     * 
     * @author <a href="mailto:taddei@lat-lon.de">Ugo Taddei </a>
     * @param deegreeFeatCollec
     *            the deegree FeatureCollection
     * @return the new JUMP FeatureCollection
     * @throws DeeJUMPException
     */
    public static FeatureCollection createFromDeegreeFC( org.deegreewfs.model.feature.FeatureCollection deegreeFeatCollec )
                            throws DeeJUMPException {

        return createFromDeegreeFC( deegreeFeatCollec, null );
    }

    /**
     * Creates a deegree <code>WFSGetFeatureRequest</code> based on the WFS version, the feature
     * name (<code>typeName</code>) and the <code>envelope</code>. <br/>This method was
     * adapted from <code>DownloadListener</code>.
     * 
     * @author <a href="mailto:taddei@lat-lon.de">Ugo Taddei </a>
     * @param version
     *            the WFS version
     * @param qualName
     *            the feature (type) name
     * @param envelope
     *            the box inside of which data has been requested
     * @return a wfs GetFeature request
     */
    public static GetFeature createFeatureRequest( String version, QualifiedName qualName,
                                                   org.deegreewfs.model.spatialschema.Envelope envelope ) {

        CoordinateSystem cs = null;

        Filter filter = null;
        if ( envelope != null ) {
            org.deegreewfs.model.spatialschema.Geometry boxGeom = null;
            try {
                boxGeom = GeometryFactory.createSurface( envelope, cs );
            } catch ( GeometryException e ) {
                e.printStackTrace();
                throw new RuntimeException( "Cannot create surface from bbox." + e.getMessage() );
            }

            Operation op = new SpatialOperation( OperationDefines.BBOX,
                                                 new PropertyName( new QualifiedName( "GEOM" ) ), boxGeom ); //$NON-NLS-1$

            filter = new ComplexFilter( op );
        }

        Query query = Query.create( null, null, null, null, version, new QualifiedName[] { qualName }, null, null,
                                    filter, maxFeatures, 0, RESULT_TYPE.RESULTS );
        IDGenerator idg = IDGenerator.getInstance();

        int maxDepth = 100;
        int traverseExpiry = -999;
        GetFeature gfr = GetFeature.create( version, "" + idg.generateUniqueID(), RESULT_TYPE.RESULTS,
                                            "text/xml; subtype=gml/3.1.1", null, maxFeatures, 0, maxDepth,
                                            traverseExpiry, new Query[] { query } );

        return gfr;
    }

    /**
     * Creates a deegree <code>FeatureCollection</code> from a given GetFeature request to a
     * server.
     * 
     * @author <a href="mailto:taddei@lat-lon.de">Ugo Taddei </a>
     * @param serverUrl
     *            the URL of the WFS server
     * @param request
     *            the GetFeature request
     * @return a deegree FeatureCollection
     * @throws XMLParsingException
     * @throws IOException
     * @throws DeeJUMPException
     */
    public static org.deegreewfs.model.feature.FeatureCollection createDeegreeFCfromWFS( AbstractWFSWrapper serverUrl,
                                                                                      GetFeature request )
                            throws DeeJUMPException, IOException, XMLParsingException {

        return createDeegreeFCfromWFS( serverUrl, XMLFactory.export( request ).getAsString(), null );
    }

    /**
     * Creates a deegree <code>FeatureCollection</code> from a given GetFeature request to a
     * server.
     * 
     * @author <a href="mailto:taddei@lat-lon.de">Ugo Taddei </a>
     * @param server
     *            the URL of the WFS server
     * @param request
     *            the GetFeature request
     * @param featureType
     *            if non null, a DescribeFeatureType will be performed to get the correct schema
     * @return a deegree FeatureCollection
     * @throws DeeJUMPException
     */
    public static org.deegreewfs.model.feature.FeatureCollection createDeegreeFCfromWFS( AbstractWFSWrapper server,
                                                                                      String request,
                                                                                      QualifiedName featureType )
                            throws DeeJUMPException {

        String s = WFSClientHelper.createResponsefromWFS( server.getGetFeatureURL(), request );

        if ( s.indexOf( "<Exception>" ) >= 0 || s.indexOf( "<ServiceExceptionReport" ) >= 0 ) { //$NON-NLS-1$ //$NON-NLS-2$
            RuntimeException re = new RuntimeException( "Couldn't get data from WFS:\n" //$NON-NLS-1$
                                                        + s );
            LOG.debug( "Couldn't get data from WFS.", re );
            throw re;
        }

        LOG.debug( "WFS FC: " + s ); //$NON-NLS-1$ //$NON-NLS-2$

        StringReader sr = new StringReader( s );

        GMLFeatureCollectionDocument gfDoc = new GMLFeatureCollectionDocument();

        // get schema from server
        if ( featureType != null ) {
            Map<URI, GMLSchema> schemaMap = new HashMap<URI, GMLSchema>();
            String dft = server.getDescribeTypeURL( featureType );
            GMLSchemaDocument doc = new GMLSchemaDocument();
            try {
                doc.load( new URL( dft ) );
                LOG.debug( "Feature type schema:\n" + doc.getAsPrettyString() );
                GMLSchema schema = doc.parseGMLSchema();
                schemaMap.put( featureType.getNamespace(), schema );
                gfDoc.setSchemas( schemaMap );
            } catch ( XMLSchemaException e ) {
                LOG.debug( "DescribeFeatureType did not work." );
            } catch ( UnknownCRSException e ) {
                LOG.debug( "DescribeFeatureType did not work." );
            } catch ( XMLParsingException e ) {
                LOG.debug( "DescribeFeatureType did not work." );
            } catch ( MalformedURLException e ) {
                LOG.debug( "DescribeFeatureType did not work." );
            } catch ( IOException e ) {
                LOG.debug( "DescribeFeatureType did not work." );
            } catch ( SAXException e ) {
                LOG.debug( "DescribeFeatureType did not work." );
            } finally {
                LOG.debug( "Trying to use base url of server for DescribeFeatureType..." );
                try {
                    String baseURL = server.getBaseWfsURL();
                    baseURL += baseURL.endsWith( "?" ) ? "" : "?";
                    doc.load( new URL( server.getDescribeTypeURL( baseURL, featureType ) ) );

                    LOG.debug( "Feature type schema:\n" + doc.getAsPrettyString() );

                    GMLSchema schema = doc.parseGMLSchema();
                    schemaMap.put( featureType.getNamespace(), schema );
                    gfDoc.setSchemas( schemaMap );
                } catch ( XMLSchemaException e ) {
                    LOG.debug( "DescribeFeatureType did not work." );
                } catch ( UnknownCRSException e ) {
                    LOG.debug( "DescribeFeatureType did not work." );
                } catch ( XMLParsingException e ) {
                    LOG.debug( "DescribeFeatureType did not work." );
                } catch ( MalformedURLException e ) {
                    LOG.debug( "DescribeFeatureType did not work." );
                } catch ( IOException e ) {
                    LOG.debug( "DescribeFeatureType did not work." );
                } catch ( SAXException e ) {
                    LOG.debug( "DescribeFeatureType did not work." );
                }
            }
        }

        org.deegreewfs.model.feature.FeatureCollection newFeatCollec = null;
        try {
            gfDoc.load( sr, "http://dummySysId" );

            newFeatCollec = gfDoc.parse();
        } catch ( SAXException e ) {
            String mesg = "Error parsing response.";
            LOG.error( mesg, e );
            throw new DeeJUMPException( mesg, e );
        } catch ( IOException e ) {
            String mesg = "Error parsing response.";
            LOG.error( mesg, e );
            throw new DeeJUMPException( mesg, e );
        } catch ( XMLParsingException e ) {
            String mesg = "Error parsing response.";
            LOG.error( mesg, e );
            try {
                LOG.error( "Schema could not be used to validate FeatureCollection." );
                LOG.error( "Trying once again with crude guessing method." );
                gfDoc = new GMLFeatureCollectionDocument( true );
                gfDoc.load( sr, "http://www.systemid.org" );
                newFeatCollec = gfDoc.parse();
            } catch ( SAXException e1 ) {
                LOG.error( mesg, e );
                throw new DeeJUMPException( mesg, e );
            } catch ( IOException e1 ) {
                LOG.error( mesg, e );
                throw new DeeJUMPException( mesg, e );
            } catch ( XMLParsingException e1 ) {
                LOG.error( mesg, e );
                throw new DeeJUMPException( mesg, e );
            }
            throw new DeeJUMPException( mesg, e );
        }

        return newFeatCollec;
    }

    /**
     * @param fc
     * @param geom
     * @return a JUMP feature collection
     * @throws DeeJUMPException
     */
    public static FeatureCollection createFromDeegreeFC( org.deegreewfs.model.feature.FeatureCollection fc, Geometry geom )
                            throws DeeJUMPException {
        return createFromDeegreeFC( fc, geom, null, null, false );
    }

    /**
     * Creates a JUMP FeatureCollection from a deegree FeatureCollection [UT] and a specified
     * JUMP/JTS Geometry object. The new JUMP FeatureCollection returned will have the
     * <code>defaultGeometry</code> as its <code>GEOM</code> attribute
     * 
     * @author <a href="mailto:taddei@lat-lon.de">Ugo Taddei </a>
     * @param deegreeFeatCollec
     *            the deegree FeatureCollection
     * @param defaultGeometry
     *            the geometry of the returned FeatureCollection
     * @param wfs
     *            if the data came from a wfs, this can be used to determine the feature type even
     *            without any features
     * @param ftName
     *            the requested feature type from the above wfs
     * @param addids
     *            whether to add the GML ids as field
     * @return the new JUMP FeatureCollection
     * @throws DeeJUMPException
     */
    public static FeatureCollection createFromDeegreeFC( org.deegreewfs.model.feature.FeatureCollection deegreeFeatCollec,
                                                         Geometry defaultGeometry, AbstractWFSWrapper wfs,
                                                         QualifiedName ftName, boolean addids )
                            throws DeeJUMPException

    {

        FeatureSchema fs = new FeatureSchema();

        com.vividsolutions.jump.feature.FeatureCollection jumpFC = new FeatureDataset( fs );

        org.deegreewfs.model.feature.Feature[] feats = deegreeFeatCollec.toArray();

        if ( wfs == null && ( feats == null || feats.length < 1 ) ) {
            throw new DeeJUMPException( "No data found" );
        }

        FeatureType ft = null;
        if ( wfs != null && ftName != null ) {
            ft = wfs.getSchemaForFeatureType( ftName.getLocalName() ).getFeatureType( ftName );
        } else {
            if ( feats.length > 0 ) {
                ft = feats[0].getFeatureType();
            }
        }

        AbstractPropertyType[] geoTypeProps = ft.getGeometryProperties();

        String geoProName = null;

        if ( geoTypeProps.length > 1 ) {
            LOG.warn( "This feature type has more than one geometry property. Only the first one will be used." );
        }

        if ( geoTypeProps == null || geoTypeProps.length == 0 ) {
            LOG.debug( "Guessing geometry property name." );
            geoProName = "GEOMETRY"; //$NON-NLS-1$
        } else {
            geoProName = geoTypeProps[0].getName().getLocalName();
            LOG.debug( "Geometry property name: " + geoProName );
        }

        PropertyType[] featTypeProps = ft.getProperties();

        boolean addedGeometry = false;

        if ( addids ) {
            fs.addAttribute( "Internal ID", AttributeType.STRING );
        }

        // populate JUMP schema
        for ( int j = 0; j < featTypeProps.length; j++ ) {
            String name = featTypeProps[j].getName().getLocalName();
            AttributeType type = findType( featTypeProps[j].getType() );
            if ( type == AttributeType.GEOMETRY ) {
                if ( !addedGeometry ) {
                    addedGeometry = true;
                } else {
                    continue;
                }
            }
            fs.addAttribute( name, type );
        }

        if ( defaultGeometry == null && fs.getGeometryIndex() == -1 ) {
            throw new RuntimeException( "No geometry property found!" );
        } else if ( defaultGeometry != null && fs.getGeometryIndex() == -1 ) {
            fs.addAttribute( "FAKE_GEOMETRY", AttributeType.GEOMETRY ); //$NON-NLS-1$
        }

        // populate FC with data
        for ( int i = 0; i < feats.length; i++ ) {

            com.vividsolutions.jump.feature.Feature jf = new BasicFeature( fs );
            org.deegreewfs.model.spatialschema.Geometry geoObject = feats[i].getDefaultGeometryPropertyValue();

            if ( addedGeometry ) {
                try {
                    Geometry geom = JTSAdapter.export( geoObject );
                    jf.setGeometry( geom );

                } catch ( Exception e ) {
                    throw new RuntimeException( e );
                }
            } else {
                jf.setGeometry( defaultGeometry );
            }

            int geoIndex = fs.getGeometryIndex();

            if ( addids ) {
                jf.setAttribute( 0, feats[i].getId() );
            }

            for ( int j = addids ? 1 : 0; j < fs.getAttributeCount(); j++ ) {
                if ( j != geoIndex ) {
                    QualifiedName qn = new QualifiedName( fs.getAttributeName( j ),
                                                          featTypeProps[addids ? j - 1 : j].getName().getNamespace() );

                    FeatureProperty fp = feats[i].getDefaultProperty( qn );
                    Object value = null;
                    if ( fp != null ) {
                        value = fp.getValue();
                    }
                    // OpenJUMP only knows int values
                    if ( value instanceof Long ) {
                        jf.setAttribute( j, (int) ( (Long) value ).longValue() );
                    } else {
                        jf.setAttribute( j, value );
                    }
                }
            }
            jumpFC.add( jf );
        }

        return jumpFC;
    }

    /**
     * @param type
     *            an SQL type code as in deegree Types class
     * @return the JUMP type
     */
    public static AttributeType findType( int type ) {
        // assumes integer for SQL's NUMERIC
        String xsd = Types.getXSDTypeForSQLType( type, 0 );

        if ( xsd.equals( "dateTime" ) ) {
            return AttributeType.DATE;
        }

        if ( xsd.equals( "gml:GeometryPropertyType" ) ) {
            return AttributeType.GEOMETRY;
        }

        if ( xsd.equals( "integer" ) ) {
            return AttributeType.INTEGER;
        }

        if ( xsd.equals( "double" ) || xsd.equals( "decimal" ) || xsd.equals( "float" ) ) {
            return AttributeType.DOUBLE;
        }

        if ( xsd.equals( "gml:FeaturePropertyType" ) ) {
            return AttributeType.OBJECT; // unknown what happens in this case
        }

        // default is string, should work for booleans as well
        return AttributeType.STRING;
    }

    /**
     * @param jumpFeatureCollection
     * @return a deegree FC
     * @throws UnknownTypeException
     * @throws GeometryException
     */
    public static org.deegreewfs.model.feature.FeatureCollection createFromJUMPFeatureCollection(
                                                                                               com.vividsolutions.jump.feature.FeatureCollection jumpFeatureCollection )
                            throws UnknownTypeException, GeometryException {

        if ( jumpFeatureCollection.size() == 0 || jumpFeatureCollection == null ) {
            throw new IllegalArgumentException( "FeatureCollection cannot be null and must have at least one feature" );
        }
        org.deegreewfs.model.feature.FeatureCollection fc = FeatureFactory.createFeatureCollection(
                                                                                                 "id",
                                                                                                 jumpFeatureCollection.size() );

        FeatureSchema schema = jumpFeatureCollection.getFeatureSchema();
        // for (int i = 0; i < schema.getAttributeCount(); i++) {
        // System.out.println(schema.getAttributeName(i) + " "
        // + schema.getAttributeType(i));
        // }
        int count = 0;

        final URI GMLNS = CommonNamespaces.GMLNS;
        final URI XSNS = CommonNamespaces.XSNS;

        for ( Iterator<?> iter = jumpFeatureCollection.iterator(); iter.hasNext(); ) {
            com.vividsolutions.jump.feature.Feature feature = (com.vividsolutions.jump.feature.Feature) iter.next();

            // FeatureProperty[] ftp = new FeatureProperty[ schema.getAttributeCount() ];
            PropertyType[] propType = new PropertyType[schema.getAttributeCount()];

            int geoIx = schema.getGeometryIndex();

            for ( int i = 0; i < schema.getAttributeCount(); i++ ) {

                if ( i != geoIx ) {
                    String type = toXSDName( schema.getAttributeType( i ) );

                    propType[i] = FeatureFactory.createPropertyType( new QualifiedName( schema.getAttributeName( i ) ),
                                                                     new QualifiedName( type, XSNS ), true );

                    /*
                     * ftp[i] = FeatureFactory .createFeatureProperty( propType[i].getName(),
                     * feature.getAttribute( schema.getAttributeName(i) ) );
                     */

                } else {

                    String type = "org.deegree.model.geometry.Geometry";
                    propType[i] = FeatureFactory.createPropertyType(
                                                                     new QualifiedName( schema.getAttributeName( geoIx ) ),
                                                                     new QualifiedName( type, GMLNS ), true );

                    /*
                     * ftp[i] = FeatureFactory .createFeatureProperty( propType[0].getName() ,
                     * feature.getAttribute( schema.getAttributeName(i) ));
                     */
                }
            }

            // 2.
            FeatureType ft = FeatureFactory.createFeatureType( new QualifiedName( "featuretypename" ), false, propType );
            // 3.
            FeatureProperty[] fp = new FeatureProperty[schema.getAttributeCount()];

            for ( int i = 0; i < schema.getAttributeCount(); i++ ) {
                if ( i != geoIx ) {
                    fp[i] = FeatureFactory.createFeatureProperty( new QualifiedName( schema.getAttributeName( i ) ),
                                                                  feature.getAttribute( i ) );
                } else {
                    fp[i] = FeatureFactory.createFeatureProperty(
                                                                  new QualifiedName( schema.getAttributeName( geoIx ) ),
                                                                  JTSAdapter.wrap( feature.getGeometry() ) );

                }
            }
            // 4.
            org.deegreewfs.model.feature.Feature fe = FeatureFactory.createFeature( "fid_" + count++, ft, fp );

            fc.add( fe );
        }

        return fc;
    }

    /**
     * @param i
     *            max number of features. Values below 1 are ignored
     */
    public static void setMaxFeatures( int i ) {
        if ( i > 0 ) {
            maxFeatures = i;
        }
    }

    /**
     * @return the maxFeatures setting
     */
    public static int getMaxFeatures() {
        return maxFeatures;
    }

    /**
     * @param type
     * @return converts type to xsd typename
     */
    public static String toXSDName( AttributeType type ) {
        String t = null;
        if ( type == AttributeType.DATE ) {
            t = "date";
        } else if ( type == AttributeType.INTEGER ) {
            t = "integer";
        } else if ( type == AttributeType.STRING ) {
            t = "string";
        } else if ( type == AttributeType.DOUBLE ) {
            t = "double";
        } else if ( type == AttributeType.OBJECT ) {
            t = "object";
        } else {
            throw new RuntimeException( "no xsd type found for: " + type );
        }

        return t;
    }

    /**
     * @param type
     * @return an SQL type code approximation of the type
     */
    public static int toSQLTypeCode( AttributeType type ) {
        if ( type == AttributeType.DATE ) {
            return Types.DATE;
        } else if ( type == AttributeType.INTEGER ) {
            return Types.INTEGER;
        } else if ( type == AttributeType.STRING ) {
            return Types.VARCHAR;
        } else if ( type == AttributeType.DOUBLE ) {
            return Types.DOUBLE;
        } else if ( type == AttributeType.OBJECT ) {
            return Types.VARBINARY;
        } else if ( type == AttributeType.GEOMETRY ) {
            return Types.GEOMETRY;
        } else {
            return Types.VARBINARY;
        }
    }

}
