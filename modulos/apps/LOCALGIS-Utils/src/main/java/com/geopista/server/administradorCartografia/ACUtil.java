/**
 * ACUtil.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.server.administradorCartografia;

public class ACUtil {

	public static void initSizeOf() {
		
		String mostrarSizeOf=System.getProperty("mostrarSizeOf");
		
		if ((mostrarSizeOf!=null) && (mostrarSizeOf.equals("true"))){
			net.sourceforge.sizeof.SizeOf.skipStaticField(true);
			net.sourceforge.sizeof.SizeOf.setMinSizeToLog(0); //0 turn off logging
		}
	}

	public static long deepSizeOf(Object object) {

		String mostrarSizeOf=System.getProperty("mostrarSizeOf");
		
		if ((mostrarSizeOf!=null) && (mostrarSizeOf.equals("true"))){
			return net.sourceforge.sizeof.SizeOf.deepSizeOf(object);
		}
		else
			return 0;
	}
}
