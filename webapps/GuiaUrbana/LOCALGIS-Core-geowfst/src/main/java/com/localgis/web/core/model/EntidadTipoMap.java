/**
 * EntidadTipoMap.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.localgis.web.core.model;

public class EntidadTipoMap {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column geowfst.entidad_tipo_map.id_entidad
     *
     * @ibatorgenerated Thu Mar 07 10:02:31 CET 2013
     */
    private Integer idEntidad;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column geowfst.entidad_tipo_map.tipo
     *
     * @ibatorgenerated Thu Mar 07 10:02:31 CET 2013
     */
    private String tipo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column geowfst.entidad_tipo_map.mapid
     *
     * @ibatorgenerated Thu Mar 07 10:02:31 CET 2013
     */
    private Integer mapid;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column geowfst.entidad_tipo_map.id_entidad
     *
     * @return the value of geowfst.entidad_tipo_map.id_entidad
     *
     * @ibatorgenerated Thu Mar 07 10:02:31 CET 2013
     */
    public Integer getIdEntidad() {
        return idEntidad;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column geowfst.entidad_tipo_map.id_entidad
     *
     * @param idEntidad the value for geowfst.entidad_tipo_map.id_entidad
     *
     * @ibatorgenerated Thu Mar 07 10:02:31 CET 2013
     */
    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column geowfst.entidad_tipo_map.tipo
     *
     * @return the value of geowfst.entidad_tipo_map.tipo
     *
     * @ibatorgenerated Thu Mar 07 10:02:31 CET 2013
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column geowfst.entidad_tipo_map.tipo
     *
     * @param tipo the value for geowfst.entidad_tipo_map.tipo
     *
     * @ibatorgenerated Thu Mar 07 10:02:31 CET 2013
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column geowfst.entidad_tipo_map.mapid
     *
     * @return the value of geowfst.entidad_tipo_map.mapid
     *
     * @ibatorgenerated Thu Mar 07 10:02:31 CET 2013
     */
    public Integer getMapid() {
        return mapid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column geowfst.entidad_tipo_map.mapid
     *
     * @param mapid the value for geowfst.entidad_tipo_map.mapid
     *
     * @ibatorgenerated Thu Mar 07 10:02:31 CET 2013
     */
    public void setMapid(Integer mapid) {
        this.mapid = mapid;
    }
}