/**
 * ReloadLayerPlugIn.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 22-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.geopista.ui.plugin.layers;

/**
 * @author satec
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.deegree.graphics.sld.CssParameter;
import org.deegree_impl.graphics.sld.FeatureTypeStyle_Impl;
import org.deegree_impl.graphics.sld.LineSymbolizer_Impl;
import org.deegree_impl.graphics.sld.Mark_Impl;
import org.deegree_impl.graphics.sld.PointSymbolizer_Impl;
import org.deegree_impl.graphics.sld.PolygonSymbolizer_Impl;
import org.deegree_impl.graphics.sld.Stroke_Impl;
import org.deegree_impl.graphics.sld.Symbolizer_Impl;

import com.geopista.app.AppContext;
import com.geopista.app.UserPreferenceConstants;
import com.geopista.app.administrador.init.Constantes;
import com.geopista.app.catastro.model.beans.Municipio;
//import com.geopista.app.eiel.panels.GeopistaEditorPanel;
import com.geopista.io.datasource.GeopistaConnection;
import com.geopista.model.GeopistaLayer;
import com.geopista.model.GeopistaMap;
import com.geopista.protocol.control.ISesion;
import com.geopista.server.administradorCartografia.ACLayerSLD;
import com.geopista.server.administradorCartografia.Const;
import com.geopista.style.sld.model.impl.SLDStyleImpl;
import com.geopista.ui.images.IconLoader;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jump.coordsys.CoordinateSystem;
import com.vividsolutions.jump.coordsys.CoordinateSystemRegistry;
import com.vividsolutions.jump.coordsys.impl.PredefinedCoordinateSystems;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.io.DriverProperties;
import com.vividsolutions.jump.io.datasource.DataSourceQuery;
import com.vividsolutions.jump.util.Blackboard;
import com.vividsolutions.jump.util.StringUtil;
import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.model.ILayerManager;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.model.Layerable;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.EnableCheck;
import com.vividsolutions.jump.workbench.plugin.EnableCheckFactory;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.GUIUtil;
import com.vividsolutions.jump.workbench.ui.ILayerViewPanel;
import com.vividsolutions.jump.workbench.ui.LayerNamePanel;
import com.vividsolutions.jump.workbench.ui.LayerViewPanel;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;
import com.vividsolutions.jump.workbench.ui.renderer.style.BasicStyle;
import com.vividsolutions.jump.workbench.ui.task.TaskMonitorDialog;


public class ReloadLayerPlugIn extends AbstractPlugIn {

    private static AppContext aplicacion = (AppContext) AppContext.getApplicationContext();
    private static final Log	logger	= LogFactory.getLog(ReloadLayerPlugIn.class);

    GeopistaMap acMap=null;
    
    public ReloadLayerPlugIn() {
    }

    
    public boolean execute(PlugInContext context) throws Exception {
        reportNothingToUndoYet(context);
        
            
        		final JFrame desktop = (JFrame) context.getWorkbenchFrame();
        				
        		if (context.getTask() instanceof GeopistaMap)
        			acMap=(GeopistaMap)context.getTask();	
        		
        		
        		final LayerNamePanel layerNamePanel=context.getLayerNamePanel();
        		final ILayerViewPanel layerViewPanel=context.getLayerViewPanel();
				final TaskMonitorDialog progressDialog = new TaskMonitorDialog(desktop, null);
				progressDialog.setTitle("TaskMonitorDialog.Wait");
				progressDialog.addComponentListener(new ComponentAdapter() {
					public void componentShown(ComponentEvent e) {
						new Thread(new Runnable() {
							public void run() {
								try {
									 for (Iterator i = Arrays.asList(layerNamePanel.getSelectedLayers()).iterator();i.hasNext();)
										{
								        	
								        	Layer layer=null;
								        	
								        		layer = (Layer) i.next();
								        		
												if (layer instanceof GeopistaLayer){
													
													progressDialog.report("Recargando capa..."+layer.getName());
													loadFeatures(acMap,layer,layerViewPanel,false);												
												}
										}
								}
								catch (Exception e){
									
								}
								finally {
									progressDialog.setVisible(false);
									progressDialog.dispose();
								}
							}
						}).start();
					}
				});			
				//GUIUtil.centreOnWindow(progressDialog);
				progressDialog.setVisible(true);

        return true;
    }
    
    
    public void loadFeatures(final GeopistaMap acMap,final Layer layer, final LayerViewPanel layerViewPanel,JFrame frame) throws Exception{
    	
    	final JFrame desktop = frame;
    	final TaskMonitorDialog progressDialog = new TaskMonitorDialog(desktop, null);
		progressDialog.setTitle("TaskMonitorDialog.Wait");
		progressDialog.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				new Thread(new Runnable() {
					public void run() {
						try {
							 loadFeatures(acMap,layer,layerViewPanel,false);
						}
						catch (Exception e){
							
						}
						finally {
							progressDialog.setVisible(false);
							progressDialog.dispose();
						}
					}
				}).start();
			}
		});			
		//GUIUtil.centreOnWindow(progressDialog);
		progressDialog.setVisible(true);
    }
    
    public void loadFeatures(GeopistaMap acMap,Layer layer, ILayerViewPanel layerViewPanel,boolean remove) throws Exception{
    	
		GeopistaLayer geopistaLayer = null;
		ILayerManager layerManager=null;
    	boolean firingEvents=true;
		
    	try {

			   
  
			geopistaLayer=(GeopistaLayer)layer;
			
			if (geopistaLayer==null)
				return;
			
			layerManager = layer.getLayerManager();
			firingEvents = layerManager.isFiringEvents();
			
			if (remove){
				logger.info("UnLoading Layer:"+geopistaLayer.getName()+" ("+geopistaLayer.getSystemId()+")");
				layerManager.setFiringEvents(false);
				
				 //Primero borramos las que hubiera
		        List actualFeatures=layer.getFeatureCollectionWrapper().getFeatures();
				layer.getFeatureCollectionWrapper().removeAll(actualFeatures);
				layer.getFeatureCollectionWrapper().clear();
				
				
				layerViewPanel.getContext().setStatusMessage("Capa "+layer.getName()+" descargada");
				
				//TODO zoomtoSelected
				//GeopistaEditorPanel.getEditor().zoomToSelected();
				ACLayerSLD.actualizarReglaPintado(geopistaLayer);
				
			}
			else{
				
				logger.info("Loading Layer:"+geopistaLayer.getName()+" ("+geopistaLayer.getSystemId()+")");
				
				//Marcamos que no se produzcan eventos, en caso contrario las features recuperadas las intentara insertar
				//en la Base de Datos como nuevas.
				
				layerManager.setFiringEvents(false);
				
				        	
				GeopistaConnection geopistaConnection = (GeopistaConnection) layer.getDataSourceQuery().getDataSource().getConnection();
				
				//Utilizamos el SRID del mapa si existe en lugar del SRID de la Entidad
				//El SRID del Mapa se carga cuando en el BlackBoard cuando se carga el mapa
								
				String sridDefecto=null;
				if ((acMap!=null) && CoordinateSystemRegistry.instance(new Blackboard()).get(acMap.getMapProjection()) != null)
					sridDefecto = String.valueOf(Integer.valueOf(CoordinateSystemRegistry.instance(new Blackboard()).get(acMap.getMapProjection()).getEPSGCode()));

				else if (AppContext.getApplicationContext().getBlackboard().get(Const.KEY_SRID_MAPA) != null){
					 sridDefecto=(String)AppContext.getApplicationContext().getBlackboard().get(Const.KEY_SRID_MAPA);
				 }else{
					
					ISesion iSesion = (ISesion)AppContext.getApplicationContext().getBlackboard().get(UserPreferenceConstants.SESION_KEY);
					sridDefecto = geopistaConnection.getSRIDDefecto(false, Integer.parseInt(iSesion.getIdEntidad()));
				 }
				 
				CoordinateSystem outCoord = layer.getLayerManager().getCoordinateSystem();
				CoordinateSystem inCoord = PredefinedCoordinateSystems.getCoordinateSystem(Integer.parseInt(sridDefecto));
				
				Geometry geom;
				List lista=AppContext.getAlMunicipios();
				Iterator it=lista.iterator();
				while (it.hasNext()){
					Municipio municipio=(Municipio)it.next();
					if (String.valueOf(municipio.getId()).equals(String.valueOf(AppContext.getIdMunicipio()))){
						
						//geom =  geopistaConnection.obtenerGeometriaMunicipio(municipio.getId());
						//geom.setSRID(Integer.parseInt(sridDefecto));
						
						//No reproyectamos porque si no carga correctamente. Creo que hay un error
						//en este plugin
				    	//Reprojector.instance().reproject(geom,inCoord, outCoord);
				    	
				    	
				    	
				    	DriverProperties driverProperties = geopistaConnection.getDriverProperties();
				        //driverProperties.put("filtrogeometrico",geom);
				    	driverProperties.put("srid_destino",inCoord.getEPSGCode());
				        geopistaConnection = new GeopistaConnection(driverProperties);
	
				        //Creamos una coleccion para almacenar las excepciones que se producen
				        Collection exceptions = new ArrayList();
				        //Miro en qu� srid por defecto se almacenan las features en la base de datos
	
				        DataSourceQuery dataSourceQuery = layer.getDataSourceQuery();
				        if (geopistaLayer.isEditable())
				        	geopistaLayer.setRevisionActual(-1);
				        //layer.setDinamica(false);
				        
				        //Primero borramos las que hubiera
				        List actualFeatures=layer.getFeatureCollectionWrapper().getFeatures();
						layer.getFeatureCollectionWrapper().removeAll(actualFeatures);
						layer.getFeatureCollectionWrapper().clear();
						
						
						ISesion iSesion = (ISesion)AppContext.getApplicationContext().getBlackboard().get(UserPreferenceConstants.SESION_KEY);
						
						List listaMunipiosActual=iSesion.getAlMunicipios();
						
						ArrayList listaMunicipios=new ArrayList();
						listaMunicipios.add(new Municipio(String.valueOf(AppContext.getIdMunicipio()),Constantes.Entidad,sridDefecto));
						iSesion.setAlMunicipios(listaMunicipios);
										
						
					    // if (AppContext.getApplicationContext().getWorkbenchContext().getIWorkbench().getGuiComponent()  instanceof GeopistaEditor){
					         
						//GeopistaEditorPanel panel = (GeopistaEditorPanel) AppContext.getApplicationContext().getBlackboard().get("GeopistaEditor");
				    	//GeopistaEditor editor=panel.getEditor();
				    	
	
				    	
			    		layerViewPanel.getContext().setStatusMessage("Cargando la capa "+layer.getName());
	
						FeatureCollection featureCollection = geopistaConnection.executeQueryLayer(layer.getDataSourceQuery().getQuery(),exceptions,null,(GeopistaLayer)layer);
						iSesion.setAlMunicipios(listaMunipiosActual);
						
						layer.setFeatureCollection(featureCollection);
			    		layerViewPanel.getContext().setStatusMessage("Capa "+layer.getName()+" ("+geopistaLayer.getSystemId()+") cargada");
			    		
	
						
						//Es necesario aplicar el estilo a los elementos cargados porque en caso contrario salen negros
						//El estilo a aplicar es el del Mapa
						 /*GeopistaMap map = (GeopistaMap) driverProperties.get("mapadestino");
						 if (map!=null){
							 Iterator it3=map.getLayerManager().getLayers().iterator();
							 while (it3.hasNext()){
								 Layer layertemp=(Layer)it3.next();
								 if (layertemp.getName().equals(layer.getName())){
									 layer.setStyles(layertemp.getStyles());
									 break;
								 }
								 
							 }
						 }*/
						
				    	ACLayerSLD.actualizarReglaPintado(geopistaLayer);
				    	
				    	//Zoom To Selected
				    	/*if (GeopistaEditorPanel.getEditor()!=null){
				    		GeopistaEditorPanel.getEditor().zoomToSelected();
				    	}
				    	else{				    		
				    		layerViewPanel.getViewport().zoom(featureCollection.getEnvelope());
				    	}*/
				    	layerViewPanel.getViewport().zoom(featureCollection.getEnvelope());
				    	 
						//resaltarCapa(geopistaLayer);
				    	break;
					}
					
				}
			}
				
		} catch (Exception e) {
			logger.error("Error al cargar la capa",e);
			throw e;
		} 
    	finally{
    		if (layerManager!=null){
        		layerManager.setFiringEvents(firingEvents);
        		/*if (layer!=null){
	                ((SLDStyleImpl)((GeopistaLayer)layer).getStyle(SLDStyleImpl.class)).setFillColor(Color.RED);
	                ((SLDStyleImpl)((GeopistaLayer)layer).getStyle(SLDStyleImpl.class)).setLineColor(Color.RED);
	                if (layerViewPanel!=null)
	                	layerViewPanel.getViewport().zoom(EnvelopeUtil.bufferByFraction(layer.getEnvelope(), 0.03));
        		}*/
    		}
    	}
    }
    
       
    
    
    /**
     * Resaltamos la capa
     * @param layer
     */
    private void resaltarCapa(GeopistaLayer layer){
		SLDStyleImpl sldStyle = (SLDStyleImpl)layer.getStyle(SLDStyleImpl.class);
		FeatureTypeStyle_Impl featureTypeStyle = (FeatureTypeStyle_Impl)sldStyle.getDefaultStyle().getFeatureTypeStyles()[0];
		Symbolizer_Impl symbolizer = (Symbolizer_Impl)featureTypeStyle.getRules()[0].getSymbolizers()[0];
		String codigoColor = "";
		Color color = Color.WHITE;
		if (symbolizer instanceof PolygonSymbolizer_Impl){
			codigoColor = (String)((CssParameter)((PolygonSymbolizer_Impl) symbolizer).getFill().getCssParameters().get("fill")).getValue().getComponents()[0];
			color = new Color(Integer.parseInt(codigoColor.substring(1),16));
		}
		else if (symbolizer instanceof LineSymbolizer_Impl){
			codigoColor = (String)((CssParameter)((LineSymbolizer_Impl) symbolizer).getStroke().getCssParameters().get("stroke")).getValue().getComponents()[0];
			color = new Color(Integer.parseInt(codigoColor.substring(1),16));
		}
		else if (symbolizer instanceof PointSymbolizer_Impl){
			try {
				color = ((Stroke_Impl)((Mark_Impl)((PointSymbolizer_Impl) symbolizer).getGraphic().getMarksAndExtGraphics()[0]).getStroke()).getColor();
			} catch (Exception e) {
				//TODO Puede venir un ExternalGraphic_Impl en lugar de Mark_Impl (En la EIEL vienen asi)
				//color = ((Stroke_Impl)((ExternalGraphic_Impl)((PointSymbolizer_Impl) symbolizer).getGraphic().getMarksAndExtGraphics()[0]).getStroke()).getColor();			}
			
			}
		
		}
		BasicStyle style = new BasicStyle(color.darker());
		layer.addStyle(style);

    }

  

    public MultiEnableCheck createEnableCheck(
        final WorkbenchContext workbenchContext) {
        EnableCheckFactory checkFactory = new EnableCheckFactory(workbenchContext);

        return new MultiEnableCheck().add(checkFactory.createWindowWithLayerNamePanelMustBeActiveCheck())
                                     .add(checkFactory.createAtLeastNLayerablesMustBeSelectedCheck(1, Layerable.class))
									 .add(new EnableCheck() {
                public String check(JComponent component) {
                    ((JMenuItem) component).setText(aplicacion.getI18nString(getName()) +
                        StringUtil.s(
                            workbenchContext.getLayerNamePanel()
                                            .getSelectedLayers().length));

                    return null;
                }
            });
    }

    public void initialize(PlugInContext context) throws Exception {

        JPopupMenu layerNamePopupMenu = context.getWorkbenchContext().getIWorkbench()
        .getGuiComponent()
        .getLayerNamePopupMenu();

        FeatureInstaller featureInstaller = new FeatureInstaller(context.getWorkbenchContext());
        featureInstaller.addPopupMenuItem(layerNamePopupMenu,
                this, aplicacion.getI18nString(this.getName()), false, GUIUtil.toSmallIcon(this.getIcon()),
                this.createEnableCheck(context.getWorkbenchContext()));
    }
    
	public ImageIcon getIcon() {
		return IconLoader.icon("reload.gif");
	}



}
