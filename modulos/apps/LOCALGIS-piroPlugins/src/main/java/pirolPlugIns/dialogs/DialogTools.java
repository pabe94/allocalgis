/**
 * DialogTools.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 17.03.2005 for PIROL
 *
 * CVS header information:
 *  $RCSfile: DialogTools.java,v $
 *  $Revision: 1.1 $
 *  $Date: 2009/07/03 12:31:46 $
 *  $Source: /usr/cvslocalgis/.CVSROOT/localgisdos/core/src/pirolPlugIns/dialogs/DialogTools.java,v $
 */
package pirolPlugIns.dialogs;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pirolPlugIns.utilities.ToolToMakeYourLifeEasier;

/**
 * @author <strong>Ole Rahn, Stefan Ostermann, Carsten Schulze</strong>
 * <br>
 * <br>FH Osnabr�ck - University of Applied Sciences Osnabr�ck
 * <br>Project PIROL 2005
 * <br>Daten- und Wissensmanagement
 * 
 * @since 1.2: changed by Stefan Ostermann at 2005-04-26: added method to return localized
 * string of a double value.
 * @since <br>1.3 changed by Carsten Schulze at 2005-05-22: added a method to 
 * center a given AWT-Window (or subclasses) on the screen.
 * @version $Revision: 1.1 $
 */
public class DialogTools extends ToolToMakeYourLifeEasier{
    
    /**
     * This method centers the window (or subclasses of it) on the screen.
     * @param window the java.awt.Window (or a subclass of it) that should be 
     * displayed in the middle of the screen. 
     */
    public static void centerOnScreen(Window window){
        Dimension screenDim = window.getToolkit().getScreenSize();
        Dimension windowDim = window.getSize();
        int x = (screenDim.width / 2) - (windowDim.width / 2);
        int y = (screenDim.height / 2) - (windowDim.height / 2);
        
        window.setLocation(x,y);
    }
    
    /**
     * This method creates a JPanel with several JLabels on it. For another 
     * method to display multiline bold text, have a look at the 
     * {javax.swing.JTextArea} and the {@link java.awt.Font} object.
     * @param text the text to split up into some JLabels.
     * @param charsPerLine the maximum number of characters per line text.
     * @return the panel with the labels on it.
     */
    public static JPanel getPanelWithLabels(String text, int charsPerLine){
        List labelTextParts = new ArrayList();
		if ( text.length() > charsPerLine ){
			int estimatedStrings = (int)Math.ceil((float)text.length() / charsPerLine);
			
			String copyLabelText = text.toString();
			for ( int i=0; i<estimatedStrings; i++ ){
				if (copyLabelText.indexOf(" ", charsPerLine)>-1)
					labelTextParts.add( copyLabelText.substring(0, copyLabelText.indexOf(" ", charsPerLine)+1) );
				else
					labelTextParts.add( copyLabelText );
				copyLabelText = copyLabelText.substring( ((String)labelTextParts.get(i)).length() );
			}
			
		} else {
			labelTextParts.add(text);
		}
		
		JPanel texts = new JPanel();
        GridLayout gl = new GridLayout(labelTextParts.size(), 1);
        gl.setHgap(0);
		texts.setLayout(gl);
		//texts.setPreferredSize( new Dimension(400, 50));
		for ( int i=0; i<labelTextParts.size(); i++ ){
			texts.add(new JLabel("  "+labelTextParts.get(i)+"  "));
		}
		texts.doLayout();
		return texts;
    }
    
    /**
     * This method replaces the localized decimal seperator with a dot.
     * @param s the String containing the double value.
     * @return the now dotted double value.
     * @see #numberStringToLocalNumberString(String)
     * @see #numberToLocalNumberString(double)
     */
    public static double localNumberStringToDouble (String s) {
    	DecimalFormatSymbols ds = new DecimalFormatSymbols();
    	s=s.replace(ds.getDecimalSeparator(),'.');
    	return Double.parseDouble(s);
    }
    
    /**
     * This method replaces the dot with the localized decimal seperator.
     * @param s the String containing the double value.
     * @return the localized String containing the double value.
     * @see #localNumberStringToDouble(String)
     * @see #numberToLocalNumberString(double)
     */
    public static String numberStringToLocalNumberString (String s) {
    	DecimalFormatSymbols ds = new DecimalFormatSymbols();
    	s=s.replace('.',ds.getDecimalSeparator());
    	return s;
    }
    
    /**
     * This method replaces the dot with the localized decimal seperator.
     * @param number the double value.
     * @return the localized String containing the double value.
     * @see #numberStringToLocalNumberString(String)
     * @see #localNumberStringToDouble(String)
     */
    public static String numberToLocalNumberString (double number) {
    	String s = new Double(number).toString();
    	return DialogTools.numberStringToLocalNumberString(s);
    }
    
    /**
     * Sets the prefered width of an JComponent and keeps it prefered height.
     * @param component the component to alter
     * @param width the new prefered width
     */
    public static void setPreferedWidth(JComponent component, int width) {
    	int preferedHeight = component.getPreferredSize().height;
    	component.setPreferredSize(new Dimension(width, preferedHeight));
    }
    
    /**
     * Sets the prefered height of an JComponent and keeps it prefered height.
     * @param component the component to alter
     * @param height the new prefered width
     */
    public static void setPreferedHeight(JComponent component, int height) {
    	int preferedWidth = component.getPreferredSize().width;
    	component.setPreferredSize(new Dimension(preferedWidth, height));
    }
    
    public static void setMaximumWidth(JComponent component, int width) {
    	int preferedHeight = component.getPreferredSize().height;
    	component.setMaximumSize(new Dimension(width, preferedHeight));
    }
    
    public static void setMaximumHeight(JComponent component, int height) {
    	int preferedWidth = component.getPreferredSize().width;
    	component.setMaximumSize(new Dimension(preferedWidth, height));
    }
}
