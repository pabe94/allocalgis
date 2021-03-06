/**
 * InventarioTextField.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.app.inventario.component;

import java.awt.Font;

public class InventarioTextField extends com.geopista.app.utilidades.TextField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InventarioTextField(){
		  super();
		  setFont(new Font("arial", Font.PLAIN, 10));
	}

	public InventarioTextField(int maxLength){
		  super(maxLength);
		  setFont(new Font("arial", Font.PLAIN, 10));
	}

}
