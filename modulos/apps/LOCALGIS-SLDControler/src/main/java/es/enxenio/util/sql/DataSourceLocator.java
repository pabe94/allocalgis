/**
 * DataSourceLocator.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.enxenio.util.sql;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import es.enxenio.util.exceptions.InternalErrorException;

/**
 * Caches references to <code>DataSources</code>. It allows to register
 * explicitally a <code>DataSource</code> under a name, and get a reference to
 * it by its name (using JNDI, if not already registered or cached).
 */
public class DataSourceLocator {

	/**
	 * The recommended JNDI context name for all <code>DataSources</code>:
	 * <code>java:comp/env/jdbc/</code>
	 */
	public static final String JNDI_PREFIX = "java:comp/env/jdbc/";

	private static Map dataSources = Collections.synchronizedMap(new HashMap());
	
	private DataSourceLocator() {}
	
	/**
     * Allows to register explicitally a <code>DataSource</code> with a
	 * given name. This method should only be used when 
	 * <code>DataSources</code> are not accessible through JNDI (for example 
	 * when using an application server providing only servlets and JSP).
	 */
	public static void addDataSource(String name, DataSource dataSource) {
		
		dataSources.put(name, dataSource);	
	}
	
	/**
     * Gets a reference to the <code>DataSource</code> with the given 
	 * <code>name</code>. If the reference has been explicitally registered 
	 * (with <code>addDataSource(String, DataSource)</code>) or is cached, it 
	 * is returned immediatelly. Otherwise, JNDI is used to get a reference to
	 * it (caching it for the next time) using the JNDI name:
	 * <code>JNDI_PREFIX +  name</code>. This allows an easy transition from
	 * an application server not providing <code>DataSources</code> registered
	 * under JNDI to one providing them.
     */
	public static DataSource getDataSource(String name) 
		throws InternalErrorException{
		
		DataSource dataSource = (DataSource) dataSources.get(name);
		
		if (dataSource == null) {
			try {
				InitialContext initialContext = new InitialContext();
            	dataSource = (DataSource) initialContext.lookup(
					JNDI_PREFIX + name);
				dataSources.put(name, dataSource);
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		
		}
		
		return dataSource;
	
	}

}
