/**
 * GeopistaEditorPanel.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.localgis.app.gestionciudad.dialogs.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.geopista.app.AppContext;
import com.geopista.app.gestionciudad.ConstantesEspacioPublico;
import com.geopista.app.gestionciudad.utilidades.UtilsEspacioGeopistaMap;
import com.geopista.editor.GeopistaEditor;
import com.geopista.model.GeopistaListener;
import com.geopista.server.administradorCartografia.CancelException;
import com.geopista.ui.cursortool.GeopistaMeasureTool;
import com.geopista.ui.cursortool.GeopistaSelectFeaturesTool;
import com.geopista.util.ApplicationContext;
import com.localgis.app.gestionciudad.utils.RouteNetworksUtils;
import com.vividsolutions.jump.I18N;
import com.vividsolutions.jump.util.StringUtil;
import com.vividsolutions.jump.workbench.model.FeatureEvent;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.ErrorDialog;
import com.vividsolutions.jump.workbench.ui.GUIUtil;
import com.vividsolutions.jump.workbench.ui.IAbstractSelection;
import com.vividsolutions.jump.workbench.ui.task.TaskMonitorDialog;
import com.vividsolutions.jump.workbench.ui.zoom.PanTool;
import com.vividsolutions.jump.workbench.ui.zoom.ZoomTool;

public class GeopistaEditorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static GeopistaEditor geopistaEditor=null;  

	public boolean insertarCapasGeopista=true;

	public String textoAscTemp=null;
	
	private JInternalFrame jif;

	private ApplicationContext aplicacion;
	
	private ArrayList<ActionListener> actionListeners= new ArrayList<ActionListener>();

	public GeopistaEditorPanel()
	{
		super(new GridLayout(1,0));
		aplicacion= (AppContext)AppContext.getApplicationContext();
		initialize();        
	}    

	public static GeopistaEditor getEditor(){

		return geopistaEditor;
	}

	private void initialize() {

		if (geopistaEditor == null){
			this.setLayout(new GridBagLayout());

			geopistaEditor = new GeopistaEditor("workbench-properties-gestionCiudad.xml");
			geopistaEditor.getToolBar().addCursorTool("Select tool", new GeopistaSelectFeaturesTool());
			geopistaEditor.getToolBar().addCursorTool("Zoom In/Out", new ZoomTool());
			geopistaEditor.getToolBar().addCursorTool("Measure", new GeopistaMeasureTool());
			geopistaEditor.getToolBar().addCursorTool("Pan", new PanTool());
			//geopistaEditor.getToolBar().addCursorTool("FeatureInfoTool", new FeatureInfoTool());
			

			
			geopistaEditor.addCursorTool("Dynamic clip tool", "com.vividsolutions.jump.workbench.ui.cursortool.UpdateDynamicClipTool");
			geopistaEditor.addCursorTool("Dynamic fence tool", "com.vividsolutions.jump.workbench.ui.cursortool.UpdateDynamicFenceTool");

			geopistaEditor.setVisible(true);
			geopistaEditor.showLayerName(true);
			geopistaEditor.setEnabled(true);

			this.setBorder(BorderFactory.createTitledBorder
					(null, I18N.get("LocalGISObraCivil", "localgisgestionciudad.editor.panel.graphiceditor.title"),
							TitledBorder.LEADING, TitledBorder.TOP, new java.awt.Font(null, java.awt.Font.BOLD, 13)));

			this.add(geopistaEditor, new GridBagConstraints(0, 0, 1, 1, 1, 1, 
					GridBagConstraints.CENTER,GridBagConstraints.BOTH, 
					new Insets (0,10,5,10), 0,0));
			
			
			geopistaEditor.addGeopistaListener(new GeopistaListener() {
	            public void selectionChanged(IAbstractSelection abtractSelection) {
	                fireActionPerformed();
	            }
	            public void featureAdded(FeatureEvent e) {
	            }
	            public void featureRemoved(FeatureEvent e) {
	            }
	            public void featureModified(FeatureEvent e) {
	            }
	            public void featureActioned(IAbstractSelection abtractSelection) {
	                fireActionPerformed();
	            }
	        });
			
		}
	}	
	
	private void fireActionPerformed() {
        for (Iterator<ActionListener> i = actionListeners.iterator(); i.hasNext();) {
            ActionListener l = (ActionListener) i.next();
            l.actionPerformed(new ActionEvent(this, 0, null));
        }
    }
	
	public void addActionListener(ActionListener l) {
        this.actionListeners.add(l);
    }

    public void removeActionListener(ActionListener l) {
        this.actionListeners.remove(l);
    }
    
    public void changeSelection(){
    	fireActionPerformed();
    }
	
	 public void initEditor(JInternalFrame jif) {

	    	final TaskMonitorDialog progressDialog = new TaskMonitorDialog(AppContext.getApplicationContext().getMainFrame(), null);
	    	progressDialog.setTitle("TaskMonitorDialog.Wait");
	    	progressDialog.report(I18N.get("LocalGISObraCivil","localgisgestionciudad.editorimportador.panel.LoadingEditor"));
	    	progressDialog.addComponentListener(new ComponentAdapter()
	    	{
	    		public void componentShown(ComponentEvent e)
	    		{   
	    			new Thread(new Runnable()
	    			{
	    				@SuppressWarnings("deprecation")
						public void run()
	    				{
	    					try
	    					{   
	    						progressDialog.report(I18N.get("LocalGISObraCivil","localgisgestionciudad.editorimportador.panel.LoadingEditor"));

	    						try {

	    							progressDialog.report(I18N.get("LocalGISObraCivil",
	    							"localgisgestionciudad.editorimportador.panel.LoadingEditor"));
	    							
//	    							int idMapa = new Integer(AppContext.getApplicationContext().getString("id.mapa.eiel.edicion")).intValue();
	    							int idMapa = ConstantesEspacioPublico.MAPA_GESTIONESPACIOPUBLICO;
	    							
	    							
	    							
	    							CagarRedParaRutas();
	    							
	    							if (!UtilsEspacioGeopistaMap.showGeopistaMap(AppContext.getApplicationContext().getMainFrame(), geopistaEditor, idMapa, false, null, null,false))
	    							{
	    								new JOptionPane(I18N.get("LocalGISObraCivil",
	    								"localgisgestionciudad.editorimportador.panel.ErrorLoadingMap"),
	    								JOptionPane.ERROR_MESSAGE).createDialog(GeopistaEditorPanel.this, "ERROR").show();
	    							}
	    						}
	    						/*catch (CancelException e1){// Arreglar lo del boton cancelar
	   	   							 stopApp();
	   		    					
	    						}*/ catch (Exception e) {

	    							e.printStackTrace();
	    						}

	    					} 
	    					catch (Exception e)
	    					{
	    						e.printStackTrace();
	    					} 
	    					finally
	    					{
	    						progressDialog.setVisible(false);
	    					}
	    				}
	    			}).start();
	    		}
	    	});
	    	GUIUtil.centreOnWindow(progressDialog);
	    	progressDialog.setVisible(true);
	    }
	    
	 
	 private void CagarRedParaRutas() {
			
			
			final JFrame desktop= geopistaEditor.getMainFrame();
			final TaskMonitorDialog progressDialog= new TaskMonitorDialog(desktop, null);
			progressDialog.setTitle("TaskMonitorDialog.Wait");
			progressDialog.addComponentListener(new ComponentAdapter()
			{
				public void componentShown(ComponentEvent e)
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							try
							{
								if (AppContext.getApplicationContext().isLogged() &&
										AppContext.getApplicationContext().isOnline()){
											try {
												PlugInContext pluginContext = geopistaEditor.getContext().createPlugInContext();
												
												
												RouteNetworksUtils.leerGrafodeBase(ConstantesEspacioPublico.NOMBRE_RED, progressDialog, pluginContext);
											} catch (Exception e) {
												ErrorDialog.show(desktop, "Error al carger red Callejero", "", "");		
											}
									}else{
										ErrorDialog.show(desktop, "Error al carger red Callejero", "", "");
									}
							}
							catch(Exception e)
							{
								ErrorDialog.show(desktop, "ERROR", "ERROR", StringUtil.stackTrace(e));
								return;
							}
							finally
							{
								progressDialog.setVisible(false);                                
								progressDialog.dispose();
							}
						}
					}).start();
				}
			});
			GUIUtil.centreOnWindow(progressDialog);
			progressDialog.setVisible(true);
			
			
			
			
		
		}
	// Arreglar lo del boton cancelar
	 private void  stopApp(){
		 JOptionPane.showMessageDialog(aplicacion.getMainFrame(),"Carga de mapa cancelada. Reinicie la aplicacion");
		 if (jif!=null){
			 jif.dispose();
			 jif=null;
		 }

    }
	 
}
