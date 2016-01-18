package com.geopista.app.eiel.models.versionados;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.geopista.app.eiel.beans.OtrosServMunicipalesEIEL;
import com.geopista.app.eiel.beans.PadronNucleosEIEL;
import com.geopista.app.eiel.beans.VersionEiel;
import com.geopista.app.eiel.utils.LocalGISEIELUtils;
import com.geopista.app.utilidades.estructuras.Estructuras;
import com.vividsolutions.jump.I18N;

/**
 * Clase que implementa el modelo de datos de la tabla del panel BienesJPanel
 */

public class PadronNucleosVersionadoCompletoEIELTableModel  extends DefaultTableModel {
	
	private static String[] columnNames = {
			I18N.get("LocalGISEIEL","localgiseiel.version.CampoVersion"),
			I18N.get("LocalGISEIEL","localgiseiel.version.CampoAccion"),
			I18N.get("LocalGISEIEL","localgiseiel.version.CampoUsuario"),
			I18N.get("LocalGISEIEL","localgiseiel.version.CampoFecha"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.codprov"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.codmunic"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.codentidad"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.codnucleo"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.n_hombres_a1"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.n_mujeres_a1"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.total_poblacion_a1"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.n_hombres_a2"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.n_mujeres_a2"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.total_poblacion_a2"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.fecha_a1"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.fecha_a2"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.fecha_actuliza"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.observ"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.estado_revision"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.padronnucleos.columna.bloqueado")};
    
    public PadronNucleosVersionadoCompletoEIELTableModel() {        
    	
    }

    private ArrayList lstElementos = new ArrayList();

    /**
     * @return n�mero de columnas de la tabla
     */
    public int getColumnCount() {
    	if (columnNames!=null){
    		return columnNames.length;
    	}
    	else
    		return 0;
    }
    
    /**
     * @return n�mero de filas de la tabla
     */
    public int getRowCount() {
    	if (lstElementos != null)
    		return lstElementos.size();
    	else
    		return 0;
    }
    
    
    /**
     * Devuelve el nombre de la columna solicitada
     * @param col �ndice de la columna
     * @return nombre de la columna
     */
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    /**
     * Devuelve el objeto que contiene la celda en la posici�n indicada
     * @param row �ndice de la fila
     * @param col �ndice de la columna
     * 
     * @return Objeto contenido en la posici�n seleccionada
     */
    public Object getValueAt(int row, int col) {
        
        if (lstElementos.get(row)==null)
            return null;
        
        Object[] fila = (Object[]) lstElementos.get(row);
		switch (col) {
			case 0:
			case 1:
			case 2:
			case 4:
			case 5:
			case 6:
			case 7:
			case 17:
				return	 fila[col];
			case 18:
				Estructuras.cargarEstructura("eiel_Estado de revisi�n");
            	return fila[col]!=null? LocalGISEIELUtils.getNameFromEstructura(Integer.toString((Integer)fila[col])):"";
	            
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:				
			case 19:
				return fila[col] != null ? fila[col].toString() : "";
			case 3:	
        		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        		return fila[col] != null ? formatter.format(fila[col]):"";				
			default:
				return null;
		}  
		    
    }
    
    /**
     * Devuelve la Depuradora1EIEL completa de la fila seleccionada
     * @param row �ndice de la fila cuyo objeto FincaCatastro se solicita
     * @return FincaCatastro completa
     */
    public PadronNucleosEIEL getValueAt(int row) {
    	
    	Object[] fila = (Object[]) lstElementos.get(row);
    	PadronNucleosEIEL obj = new PadronNucleosEIEL();
    	 

		VersionEiel version = new VersionEiel();
		version.setIdVersion((Integer) fila[0]);
		version.setAccion((String) fila[1]);
		version.setUsuario((String) fila[2]);
		version.setFecha((Date) fila[3]); 
		
		obj.setVersion(version);
    	
		obj.setCodINEProvincia((String) fila[4]); 
		obj.setCodINEMunicipio((String) fila[5]);
		obj.setCodINEEntidad((String) fila[6]); 
		obj.setCodINEPoblamiento((String) fila[7]); 
		obj.setHombres_a1((Integer) fila[8]); 
		obj.setMujeres_a1((Integer) fila[9]);
		obj.setTotPobl_a1((Integer) fila[10]); 
		obj.setHombres_a2((Integer) fila[11]);
		obj.setMujeres_a2((Integer) fila[12]); 
		obj.setTotPobl_a2((Integer) fila[13]); 
		obj.setFecha_a1((Integer) fila[14]); 
		obj.setFecha_a2((Integer) fila[15]);
		obj.setFechaRevision((Date) fila[16]); 
		obj.setObservaciones((String) fila[17]); 
		obj.setEstadoRevision((Integer) fila[18]); 
		obj.setBloqueado((String) fila[19]);
		
        return obj;
        
    }
    /**
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  
     */
    public Class getColumnClass(int c) {
        
        if (getValueAt(0, c)!=null)        
            return getValueAt(0, c).getClass();
        else
            return String.class;
    }
    
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    
    /**
     * Establece los datos mostrados en el modelo
     * @param datos Datos a mostrar en el modelo
     */
    public void setData (ArrayList datos)
    {
        this.lstElementos = datos;
    }
    
    /**
     * Recupera los datos del modelo
     * @return Datos del modelo
     */
    public ArrayList getData (){
        return lstElementos;
    }    

}