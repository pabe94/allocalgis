/**
 * DiagramItem.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 14.12.2004
 *
 * CVS header information:
 *  $RCSfile: DiagramItem.java,v $
 *  $Revision: 1.1 $
 *  $Date: 2009/07/03 12:31:56 $
 *  $Source: /usr/cvslocalgis/.CVSROOT/localgisdos/core/src/pirolPlugIns/cursorTools/DiagramItem.java,v $s
 */
package pirolPlugIns.cursorTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ole Rahn
 * <br>
 * <br>FH Osnabr&uuml;ck - University of Applied Sciences Osnabr&uuml;ck,
 * <br>Project: PIROL (2005),
 * <br>Subproject: Daten- und Wissensmanagement
 * 
 * @version $Revision: 1.1 $
 */
public abstract class DiagramItem {
	protected List FIDs = new ArrayList();
	protected int index;
	protected String rangeString;
	
	public DiagramItem(String rangeString, int index) {
		this.rangeString = rangeString;
		this.index = index;
	}
	
	public String getRangeString() {
		return rangeString;
	}
	
	public boolean addFID(Integer FID){
		return this.FIDs.add(FID);
	}
    
    public boolean addFID(int FID){
        return this.addFID(new Integer(FID));
    }

	public List getFIDs() {
		return FIDs;
	}

	public int getNumItems(){
		return this.FIDs.size();
	}

	public int getIndex() {
		return index;
	}
}
