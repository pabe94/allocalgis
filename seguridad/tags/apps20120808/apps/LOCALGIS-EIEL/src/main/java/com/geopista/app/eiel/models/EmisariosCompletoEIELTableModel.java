package com.geopista.app.eiel.models;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.geopista.app.eiel.beans.CaptacionesEIEL;
import com.geopista.app.eiel.beans.EmisariosEIEL;
import com.geopista.app.eiel.utils.LocalGISEIELUtils;
import com.geopista.app.utilidades.estructuras.Estructuras;
import com.vividsolutions.jump.I18N;

/**
 * Clase que implementa el modelo de datos de la tabla del panel BienesJPanel
 */

public class EmisariosCompletoEIELTableModel  extends DefaultTableModel {
	
	private static String[] columnNames = {
			I18N.get("LocalGISEIEL","localgiseiel.tabla.generico.columna.estado"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.clave"),
//            I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.codprov"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.codmunic"),
            I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.codorden"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.titular"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.gestor"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.estado"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.material"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.sist_impulsion"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.tipo_red_interior"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.fecha_inst"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.observ"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.fecha_revision"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.estado_revision"),
		    I18N.get("LocalGISEIEL","localgiseiel.tabla.Emisarios.columna.bloqueado")};

	    
    public EmisariosCompletoEIELTableModel() {        

    }

    private ArrayList lstElementos = new ArrayList();

    /**
     * @return n�mero de columnas de la tabla
     */
    public int getColumnCount() {
    	if (columnNames != null)
    		return columnNames.length;
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
        
        switch (col)
        {
        case 0: 
            return ((EmisariosEIEL)lstElementos.get(row)).getEstadoValidacion();
        case 1:
        	return ((EmisariosEIEL)lstElementos.get(row)).getClave();
        case 2: 
            return ((EmisariosEIEL)lstElementos.get(row)).getCodINEProvincia() + ((EmisariosEIEL)lstElementos.get(row)).getCodINEMunicipio();
        case 3: 
            return ((EmisariosEIEL)lstElementos.get(row)).getCodOrden();
        case 4: 
            Estructuras.cargarEstructura("eiel_Titularidad");
            return LocalGISEIELUtils.getNameFromEstructura(((EmisariosEIEL)lstElementos.get(row)).getTitularidad());
        case 5: 
            Estructuras.cargarEstructura("eiel_Gesti�n");
            return LocalGISEIELUtils.getNameFromEstructura(((EmisariosEIEL)lstElementos.get(row)).getGestion());
        case 6: 
            Estructuras.cargarEstructura("eiel_Estado de conservaci�n");
            return LocalGISEIELUtils.getNameFromEstructura(((EmisariosEIEL)lstElementos.get(row)).getEstado());
        case 7: 
            Estructuras.cargarEstructura("eiel_material");
            return LocalGISEIELUtils.getNameFromEstructura(((EmisariosEIEL)lstElementos.get(row)).getMaterial());
        case 8: 
            Estructuras.cargarEstructura("eiel_Sistema de impulsi�n");
            return LocalGISEIELUtils.getNameFromEstructura(((EmisariosEIEL)lstElementos.get(row)).getSistema());
        case 9: 
            Estructuras.cargarEstructura("eiel_tipo_red_interior");
            return LocalGISEIELUtils.getNameFromEstructura(((EmisariosEIEL)lstElementos.get(row)).getTipo_red());
        case 10: 
            return ((EmisariosEIEL)lstElementos.get(row)).getFecha_inst();
        case 11: 
            return ((EmisariosEIEL)lstElementos.get(row)).getObservaciones();
        case 12: 
            return ((EmisariosEIEL)lstElementos.get(row)).getFechaRevision();
        case 13: 
			Estructuras.cargarEstructura("eiel_Estado de revisi�n");
            return LocalGISEIELUtils.getNameFromEstructura(((EmisariosEIEL) lstElementos.get(row)).getEstado_Revision().toString());
        
        case 14: 
            return ((EmisariosEIEL)lstElementos.get(row)).getBloqueado();        
        default: 
            return null;
        
        }        
    }
    
    /**
     * Devuelve la Depuradora1EIEL completa de la fila seleccionada
     * @param row �ndice de la fila cuyo objeto FincaCatastro se solicita
     * @return FincaCatastro completa
     */
    public EmisariosEIEL getValueAt(int row) {
        
        return (EmisariosEIEL)lstElementos.get(row);
        
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