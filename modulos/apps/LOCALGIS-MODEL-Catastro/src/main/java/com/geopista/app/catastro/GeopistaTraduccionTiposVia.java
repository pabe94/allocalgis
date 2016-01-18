/**
 * GeopistaTraduccionTiposVia.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.app.catastro;

public class GeopistaTraduccionTiposVia 
{
  private String tipoViaNormalizado;
  private String tipoVia;
  private String descripcion;

  public GeopistaTraduccionTiposVia()
  {
  }

  public String getTipoViaNormalizado()
  {
    return tipoViaNormalizado;
  }

  public void setTipoViaNormalizado(String newTipoViaNormalizado)
  {
    tipoViaNormalizado = newTipoViaNormalizado;
  }

  public String getTipoVia()
  {
    return tipoVia;
  }

  public void setTipoVia(String newTipoVia)
  {
    tipoVia = newTipoVia;
  }

  public String getDescripcion()
  {
    return descripcion;
  }

  public void setDescripcion(String newDescripcion)
  {
    descripcion = newDescripcion;
  }
}