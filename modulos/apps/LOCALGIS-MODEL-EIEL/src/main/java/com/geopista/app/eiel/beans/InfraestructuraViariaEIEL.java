/**
 * InfraestructuraViariaEIEL.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.app.eiel.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;

import com.geopista.app.eiel.beans.filter.LCGCampoCapaTablaEIEL;
import com.geopista.util.EIELPanel;

public class InfraestructuraViariaEIEL extends WorkflowEIEL implements Serializable, EIELPanel{
	
	public InfraestructuraViariaEIEL(){
		
		relacionFields = new ArrayList<LCGCampoCapaTablaEIEL>();
		//relacionFields.add(new LCGCampoCapaTablaEIEL("C�digo provincia","codprov","eiel_c_redviaria_tu",""));
		//relacionFields.add(new LCGCampoCapaTablaEIEL("IdMunicip","codmunic","eiel_c_redviaria_tu",""));
		relacionFields.add(new LCGCampoCapaTablaEIEL("id","id","eiel_c_redviaria_tu",""));
		/*relacionFields.add(new LCGCampoCapaTablaEIEL("Clave","clave","eiel_c_redviaria_tu",""));
		relacionFields.add(new LCGCampoCapaTablaEIEL("C�digo INE Entidad","codentidad","eiel_c_redviaria_tu",""));
		relacionFields.add(new LCGCampoCapaTablaEIEL("C�digo INE N�cleo","codpoblamiento","eiel_c_redviaria_tu",""));
		relacionFields.add(new LCGCampoCapaTablaEIEL("C�digo tramo viario","tramo_vu","eiel_c_redviaria_tu",""));*/
		
	}	

	public Hashtable getIdentifyFields() {
		return null;
	}
	
	
}
