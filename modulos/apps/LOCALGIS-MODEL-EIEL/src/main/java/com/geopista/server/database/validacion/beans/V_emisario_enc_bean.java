/**
 * V_emisario_enc_bean.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.server.database.validacion.beans;


public class V_emisario_enc_bean {

	    String clave="-";
	    String provincia="-";
	    String municipio="-";
	    String orden_emis="-";
	    String tipo_vert="-";
	    String zona_vert="-";
	    int distancia;
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getOrden_emis() {
		return orden_emis;
	}
	public void setOrden_emis(String orden_emis) {
		this.orden_emis = orden_emis;
	}
	public String getTipo_vert() {
		return tipo_vert;
	}
	public void setTipo_vert(String tipo_vert) {
		this.tipo_vert = tipo_vert;
	}
	public String getZona_vert() {
		return zona_vert;
	}
	public void setZona_vert(String zona_vert) {
		this.zona_vert = zona_vert;
	}
	public int getDistancia() {
		return distancia;
	}
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	 
}
