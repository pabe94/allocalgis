
/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI
 * for visualizing and manipulating spatial features with geometry and attributes.
 *
 * Copyright (C) 2003 Vivid Solutions
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * For more information, contact:
 *
 * Vivid Solutions
 * Suite #1A
 * 2328 Government Street
 * Victoria BC  V8T 5G5
 * Canada
 *
 * (250)385-6040
 * www.vividsolutions.com
 */

package com.vividsolutions.jump.workbench.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vividsolutions.jts.util.Assert;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.CategoryEvent;
import com.vividsolutions.jump.workbench.model.FeatureEvent;
import com.vividsolutions.jump.workbench.model.FeatureEventType;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.model.LayerEvent;
import com.vividsolutions.jump.workbench.model.LayerEventType;
import com.vividsolutions.jump.workbench.model.LayerListener;
import com.vividsolutions.jump.workbench.model.UndoableCommand;

public class LayerTableModel extends ColumnBasedTableModel implements ILayerTableModel {
    private Layer layer;
    private ArrayList features = new ArrayList();
    private String sortedColumnName = null;
    private boolean sortAscending = false;
    
	private static final Log	logger	= LogFactory.getLog(LayerTableModel.class);

    private abstract class MyColumn extends Column implements ILayerTableModel {
        public MyColumn(String name, Class dataClass) {
            super(name, dataClass);
        }

        /* (non-Javadoc)
		 * @see com.vividsolutions.jump.workbench.ui.ILayerTableModel#getValueAt(int)
		 */
        @Override
		public Object getValueAt(int rowIndex) {
            return getValue(getFeature(rowIndex));
        }

        /* (non-Javadoc)
		 * @see com.vividsolutions.jump.workbench.ui.ILayerTableModel#setValueAt(java.lang.Object, int)
		 */
        @Override
		public void setValueAt(Object value, int rowIndex) {
            setValue(value, getFeature(rowIndex));
        }

        protected abstract Object getValue(Feature feature);

        protected abstract void setValue(Object value, Feature feature);
    }

    private Column fidColumn = new MyColumn("FID", Integer.class) {
        protected Object getValue(Feature feature) {
            return new Integer(feature.getID());
        }

        protected void setValue(Object value, Feature feature) {
            Assert.shouldNeverReachHere();
        }
    };

        private Column buttonColumn = new MyColumn(" ", String.class) {//button column [Jon Aquino]
    protected Object getValue(Feature feature) {
            return null;
        }

        protected void setValue(Object value, Feature feature) {
            Assert.shouldNeverReachHere();
        }
    };

    private FeatureSchema schema;

    public LayerTableModel(final Layer layer) {
        this.layer = layer;

        layer.getLayerManager().addLayerListener(layerListener);
        initColumns(layer);
    }

    private LayerListener layerListener = new LayerListener() {
        public void categoryChanged(CategoryEvent e) {}
        public void featuresChanged(FeatureEvent e) {
            if (e.getLayer() != getLayer()) {
                return;
            }
            if (e.getType() == FeatureEventType.DELETED) {
                removeAll(e.getFeatures());
            }
            if (e.getType() == FeatureEventType.ATTRIBUTES_MODIFIED) {
                for (Iterator i = e.getFeatures().iterator(); i.hasNext();) {
                    Feature feature = (Feature) i.next();
                    int row = getFeatures().indexOf(feature);
                    if (row != -1) {
                        fireTableChanged(new TableModelEvent(LayerTableModel.this, row, row));
                    }
                }
            }
        }
        public void layerChanged(LayerEvent e) {
            if (e.getLayerable() != getLayer()) {
                return;
            }

            if (e.getType() == LayerEventType.METADATA_CHANGED) {
                //User may have changed the schema. [Jon Aquino]
                if (!schema.equals(layer.getFeatureCollectionWrapper().getFeatureSchema(), true)) {
                    initColumns(layer);
                    fireTableChanged(
                        new TableModelEvent(LayerTableModel.this, TableModelEvent.HEADER_ROW));
                }
            }

        }
    };

    private void initColumns(final Layer layer) {
        schema = layer.getFeatureCollectionWrapper().getFeatureSchema();
        ArrayList columns = new ArrayList();
        columns.add(buttonColumn);
        columns.add(fidColumn);

        for (int i = 0; i < schema.getAttributeCount(); i++) {
            if (schema.getAttributeType(i) == AttributeType.GEOMETRY) {
                continue;
            }

            final int j = i;
            columns
                .add(new MyColumn(schema.getAttributeName(i), schema.getAttributeType(i).toJavaClass()) {
                protected Object getValue(Feature feature) {
                  // MD - trapping bad index value here, since at this point it's too late to do anything about it
                  Object value = null;
                  try {
                    value = feature.getAttribute(j);
                  }
                  catch (ArrayIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                  }
                  return value;
                }

                protected void setValue(Object value, final Feature feature) {
                    final Feature oldAttributes = (Feature) feature.clone();
                    final Feature newAttributes = (Feature) feature.clone();
                    if (!verificarCambioRealizado(oldAttributes,value,j))
                    	return;
                    newAttributes.setAttribute(j, value);
                    layer.getLayerManager().getUndoableEditReceiver().startReceiving();
                    try {
                        UndoableCommand command =
                            new UndoableCommand("Edit " + schema.getAttributeName(j)) {
                            public void execute() {
                                setAttributesOf(feature, newAttributes);
                            }
                            public void unexecute() {
                                setAttributesOf(feature, oldAttributes);
                            }
                        };
                        command.execute();
                        layer.getLayerManager().getUndoableEditReceiver().receive(
                            command.toUndoableEdit());
                    } finally {
                        layer.getLayerManager().getUndoableEditReceiver().stopReceiving();
                    }
                }
            });
        }
        setColumns(columns);
    }

private boolean verificarCambioRealizado(Feature oldAttributes, Object value,int j){
    	
    	boolean cambiado=true;
    	try {
			//System.out.println(oldAttributes.getAttribute(j)+" vs "+value);
			if (oldAttributes.getAttribute(j) instanceof Integer){
				Integer valorNuevo=Integer.parseInt((String)value);
				Integer valorActual=(Integer)oldAttributes.getAttribute(j);
				if (valorActual.intValue()==valorNuevo.intValue()){
			     	logger.info("No se ha cambiado el valor del Atributo Integer:"+valorActual.intValue()+" vs "+valorNuevo.intValue());
			     	cambiado=false;
				}
			}
			if (oldAttributes.getAttribute(j) instanceof Long){
				Long valorNuevo=Long.parseLong((String)value);
				Long valorActual=(Long)oldAttributes.getAttribute(j);
				if (valorActual.longValue()==valorNuevo.longValue()){
			     	logger.info("No se ha cambiado el valor del Atributo Long:"+valorActual.longValue()+" vs "+valorNuevo.longValue());
			     	cambiado=false;
				}
			}
			else if (oldAttributes.getAttribute(j) instanceof BigDecimal){
				BigDecimal valorNuevo=BigDecimal.valueOf(Long.parseLong((String)value));
				BigDecimal valorActual=(BigDecimal)oldAttributes.getAttribute(j);
				if (valorActual.longValue()==valorNuevo.longValue()){
			     	logger.info("No se ha cambiado el valor del Atributo BigDecimal:"+valorActual.longValue()+" vs "+valorNuevo.longValue());
			    	cambiado=false;
				}
			}
			else if (oldAttributes.getAttribute(j) instanceof String){
				String valorNuevo=(String)value;
				String valorActual=(String)oldAttributes.getAttribute(j);
			  	 if (((String)oldAttributes.getAttribute(j)).equals((String)value)){
			  		logger.info("No se ha cambiado el valor del Atributo String: "+valorActual+" vs "+valorNuevo);
			       	cambiado=false;
			  	 }
			  }
		} catch (Throwable e) {
			
		}
    	return cambiado;
    }

    private void setAttributesOf(Feature feature, Feature attributes) {
        Feature oldClone = (Feature) feature.clone();
        
        boolean fireEvents=true;
        
        if (feature instanceof com.geopista.feature.GeopistaFeature){
        	fireEvents=((com.geopista.feature.GeopistaFeature)feature).isFireDirtyEvents();
        	((com.geopista.feature.GeopistaFeature)feature).setFireDirtyEvents(false);
        }
        for (int i = 0; i < feature.getSchema().getAttributeCount(); i++) {
        	//logger.info("Cambiando atributo:"+attributes.getAttribute(i));
            feature.setAttribute(i, attributes.getAttribute(i));
        }
        if (feature instanceof com.geopista.feature.GeopistaFeature)
        	((com.geopista.feature.GeopistaFeature)feature).setFireDirtyEvents(fireEvents);

        layer.getLayerManager().fireFeaturesChanged(
            Arrays.asList(new Feature[] { feature }),
            FeatureEventType.ATTRIBUTES_MODIFIED,
            layer);
    }

    public Layer getLayer() {
        return layer;
    }

    public Feature getFeature(int row) {
        return (Feature) features.get(row);
    }

    public int getRowCount() {
        return features.size();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (!layer.isEditable()) {
            return false;
        }

        if (getColumn(columnIndex) == fidColumn) {
            return false;
        }

        if (getColumn(columnIndex) == buttonColumn) {
            return false;
        }

        return true;
    }

    public void clear() {
        features.clear();
        fireTableChanged(new TableModelEvent(this));
    }

    public void removeAll(Collection featuresToRemove) {
        for (Iterator i = featuresToRemove.iterator(); i.hasNext();) {
            Feature feature = (Feature) i.next();
            int row = features.indexOf(feature);
            if (row == -1) {
                //A LayerTableModel might not have all the features in a layer
                //i.e. a FeatureInfo window, as opposed to a complete Attributes window. [Jon Aquino]
                continue;
            }
            features.remove(row);
            fireTableChanged(
                new TableModelEvent(
                    this,
                    row,
                    row,
                    TableModelEvent.ALL_COLUMNS,
                    TableModelEvent.DELETE));
        }
    }

    public void addAll(Collection newFeatures) {
        int originalFeaturesSize = features.size();
        features.addAll(newFeatures);

        if (sortedColumnName != null) {
            sort(sortedColumnName, sortAscending);
        }

        fireTableChanged(
            new TableModelEvent(
                this,
                originalFeaturesSize,
                features.size() - 1,
                TableModelEvent.ALL_COLUMNS,
                TableModelEvent.INSERT));
    }

    /**
     * Facilitate garbage collection by releasing references.
     */
    public void dispose() {
        layer.getLayerManager().removeLayerListener(layerListener);
        features.clear();
    }

    public List getFeatures() {
        return Collections.unmodifiableList(features);
    }

    /**
     * @return null if the table has not yet been sorted
     */
    public String getSortedColumnName() {
        return sortedColumnName;
    }

    public boolean isSortAscending() {
        return sortAscending;
    }

    public void sort(String columnName) {
        sort(columnName, columnName.equals(sortedColumnName) ? (!sortAscending) : true);
    }

    public void sort(final String columnName, final boolean ascending) {
        this.sortAscending = ascending;
        this.sortedColumnName = columnName;

        final int column = indexOfColumn(columnName);
        Collections.sort(features, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ascendingCompare(o1, o2) * (ascending ? 1 : (-1));
            }

            private int ascendingCompare(Object o1, Object o2) {
                Feature f1 = (Feature) o1;
                Feature f2 = (Feature) o2;
                Comparable attribute1 = (Comparable) ((MyColumn) getColumn(column)).getValue(f1);
                Comparable attribute2 = (Comparable) ((MyColumn) getColumn(column)).getValue(f2);

                if (attribute1 == null) {
                    return -1;
                }

                if (attribute2 == null) {
                    return 1;
                }

                return attribute1.compareTo(attribute2);
            }
        });
    }

    public String getType(int column) {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new JTable().getDefaultEditor(Date.class));
    }

	@Override
	public Object getValueAt(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValueAt(Object value, int rowIndex) {
		// TODO Auto-generated method stub
		
	}

}
