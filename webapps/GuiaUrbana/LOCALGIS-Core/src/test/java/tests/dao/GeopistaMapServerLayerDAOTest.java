/**
 * GeopistaMapServerLayerDAOTest.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package tests.dao;

import com.localgis.web.core.dao.GeopistaMapServerLayerDAO;

public class GeopistaMapServerLayerDAOTest extends BaseDAOTest {

    private GeopistaMapServerLayerDAO geopistaMapServerLayerDAO = (GeopistaMapServerLayerDAO)daoManager.getDao(GeopistaMapServerLayerDAO.class);

    public void testSelectLayersByIdMapAndIdMunicipio() throws Exception {
        geopistaMapServerLayerDAO.selectLayersByIdMapAndIdEntidad(ConfigurationTests.ID_MAP_GEOPISTA, ConfigurationTests.ID_ENTIDAD);
    }

}
