/**
 * CReferenciasCatastralesTableModel.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.app.licencias.tableModels;

import javax.swing.table.DefaultTableModel;

public class CReferenciasCatastralesTableModel extends DefaultTableModel {

        public static String[] columnNames = {"Referencia catastral","Tipo v�a","V�a p�blica","n�"};


        public CReferenciasCatastralesTableModel() {
            new DefaultTableModel(columnNames, 0);
        }

        public CReferenciasCatastralesTableModel(String[] colNames) {
            columnNames= colNames;
            new DefaultTableModel(columnNames, 0);
        }
    

        public static void setColumnNames(String[] colNames) {
            columnNames= colNames;
        }    

        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public boolean isCellEditable(int row, int col) {
            if (col == 0) return false;
            return true;
        }
}
