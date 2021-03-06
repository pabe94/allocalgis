/**
 * DxfBlocks.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 *
 * Este codigo se distribuye bajo licencia GPL
 * de GNU. Para obtener una c�pia integra de esta
 * licencia acude a www.gnu.org.
 * 
 * Este software se distribuye "como es". AGIL
 * solo  pretende desarrollar herramientas para
 * la promoci�n del GIS Libre.
 * AGIL no se responsabiliza de las perdidas econ�micas o de 
 * informaci�n derivadas del uso de este software.
 */


package org.agil.dxf.reader;


/** 
 *  DxfBlocks resembles the BLOCKS section of the DXF file.
 *  
 *  @version   1.00beta0 (January 1999)
 */
public class DxfBlocks extends DxfSection {
  protected DxfEntitySet    blockSet = new DxfEntitySet();   /* the BLOCKs as an entity set */

  /** 
   *  Read the BLOCKS section.
   *  @param  grp  group code reader
   *  @exception DxfException on i/o and syntax errors
   */
  public void read(DxfGroups grp) throws DxfException {
    grp.readEntities(blockSet);
  }

  /** 
   *  Find a BLOCK by name.
   *  @see    DxfEntitySet#getBlock
   *  @param  name  the name of the BLOCK
   *  @return reference to the BLOCK (when found) or <code>null</code>
   */
  public DxfEntity getBlock(String name) {
    return blockSet.getBlock(name);
  }
}

