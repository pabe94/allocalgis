/**
 * WorldFileHandler.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 01.07.2005
 *
 * CVS information:
 *  $Author: miriamperez $
 *  $Date: 2009/07/03 12:31:31 $
 *  $ID$
 *  $Revision: 1.1 $
 *  $Source: /usr/cvslocalgis/.CVSROOT/localgisdos/core/src/pirolPlugIns/plugIns/PirolRasterImage/WorldFileHandler.java,v $
 *  $Log: WorldFileHandler.java,v $
 *  Revision 1.1  2009/07/03 12:31:31  miriamperez
 *  Rama �nica LocalGISDOS
 *
 *  Revision 1.1  2006/05/18 08:23:26  jpcastro
 *  Pirol RasterImage
 *
 *  Revision 1.2  2006/05/03 14:25:33  orahn
 *  kleine Verbesserung im Worldfilehandler
 *
 *  Revision 1.1  2006/01/04 18:11:04  orahn
 *  neues Model f�r RasterImage-Support - erfordert u.U. noch Kern-Patch
 *
 *  Revision 1.5  2005/07/13 10:11:26  orahn
 *  +HandlerToMakeYourLifeEasier
 *
 *  Revision 1.4  2005/07/05 16:35:39  orahn
 *  todo raus
 *
 *  Revision 1.3  2005/07/05 13:01:06  orahn
 *  bugfix: Fehler beim Schreiben von world files
 *
 *  Revision 1.2  2005/07/05 11:33:04  orahn
 *  warnings raus
 *
 *  Revision 1.1  2005/07/01 20:38:36  orahn
 *  verbesserte Lade-Reihenfolge und verallgemeinertes Handling vom WorldFiles
 *
 */
package pirolPlugIns.plugIns.PirolRasterImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pirolPlugIns.utilities.HandlerToMakeYourLifeEasier;
import pirolPlugIns.utilities.debugOutput.DebugUserIds;
import pirolPlugIns.utilities.debugOutput.PersonalLogger;

import com.vividsolutions.jts.geom.Envelope;

/**
 * 
 * class to create a ESRI-Worldfile for RasterImages that are exported from Jump, 
 * also for reading existent world files.
 *
 * @author Ole Rahn
 * <br>
 * <br>FH Osnabr&uuml;ck - University of Applied Sciences Osnabr&uuml;ck,
 * <br>Project: PIROL (2005),
 * <br>Subproject: Daten- und Wissensmanagement
 * 
 * @version $Revision: 1.1 $
 */
public class WorldFileHandler implements HandlerToMakeYourLifeEasier{
    protected String worldFileName = null;
    protected String imageFileName = null;
    
    private boolean allwaysLookForTFWExtension = true;
    
    protected PersonalLogger logger = new PersonalLogger(DebugUserIds.USER_Ole);
    
    /**
     * @param imageFileName name of the image file the world file is for
     * @param allwaysLookForTFWExtension if true the worldFileHandler will look for
     * world files with the extension <code>.tfw</code> even if the image file format
     * would suggest an other extension, like <code>.pgw</code> - this parameter has no influence
     * on the writing of world files!
     */
    public WorldFileHandler(String imageFileName, boolean allwaysLookForTFWExtension) {
        super();
        this.imageFileName = imageFileName;
        this.allwaysLookForTFWExtension = allwaysLookForTFWExtension;
        this.worldFileName = this.isWorldFileExistentForImage();
        
        if (this.worldFileName == null)
            this.worldFileName = (String)this.createListOfWorldFileNamesForImage().get(0);
    }
    /**
     * Method that writes a world file according to the given coordinate
     * and image information.
     * @param imageCoordinates real world coordinates of the image
     * @param imgWidth width (in pixel) of the image
     * @param imgHeight height (in pixel) of the image
     * @return true if a world file was written, else false
     */
    public boolean writeWorldFile(Envelope imageCoordinates, int imgWidth, int imgHeight){
        double faktorA, faktorB, faktorC, faktorD, CoordX, CoordY;
        double maxx = imageCoordinates.getMaxX();
        double minx = imageCoordinates.getMinX();
        double maxy = imageCoordinates.getMaxY();
        double miny = imageCoordinates.getMinY();
        try {
            FileWriter worldfileWriter = new FileWriter(this.worldFileName, false);
            faktorA = (maxx - minx) / imgWidth;
            faktorB = 0;
            faktorC = 0;
            faktorD = (miny - maxy) / imgHeight;
            CoordX = minx;
            CoordY = maxy;
            worldfileWriter.write(Double.toString(faktorA) + "\n");
            worldfileWriter.write(Double.toString(faktorB) + "\n");
            worldfileWriter.write(Double.toString(faktorC) + "\n");
            worldfileWriter.write(Double.toString(faktorD) + "\n");
            worldfileWriter.write(Double.toString(CoordX) + "\n");
            worldfileWriter.write(Double.toString(CoordY));
            worldfileWriter.close();
        } catch (IOException e) {
            this.logger.printError("Worldfile was not written: " + e.getMessage());
            return false;
        }
        this.logger.printDebug("world file " + this.worldFileName + " was written");
        return true;
    }
    
    /**
     * Retrieve the real world coordinates of the image from the world file
     * @param imgWidth width (in pixel) of the image
     * @param imgHeight height (in pixel) of the image
     * @return real world coordinates of the image
     */
    public Envelope readWorldFile(int imgWidth, int imgHeight){
        FileReader worldFileReader;
        try {
            worldFileReader = new FileReader(this.worldFileName);
        } catch (FileNotFoundException e1) {
            this.logger.printError("Worldfile not found: " + e1.getMessage());
            return null;
        }
        BufferedReader bufferedWorldFileReader = new BufferedReader(worldFileReader);
        
        double minx, maxx, miny, maxy;

        double faktorA, faktorB, faktorC, faktorD, CoordX, CoordY;
        String number = null;
        try {
            number = bufferedWorldFileReader.readLine().replaceAll("[,]",".");
            faktorA = Double.parseDouble(number);
            number = bufferedWorldFileReader.readLine().replaceAll("[,]",".");
            faktorB = Double.parseDouble(number);
            number = bufferedWorldFileReader.readLine().replaceAll("[,]",".");
            faktorC = Double.parseDouble(number);
            number = bufferedWorldFileReader.readLine().replaceAll("[,]",".");
            faktorD = Double.parseDouble(number);
            number = bufferedWorldFileReader.readLine().replaceAll("[,]",".");
            CoordX = Double.parseDouble(number);
            number = bufferedWorldFileReader.readLine().replaceAll("[,]",".");
            CoordY = Double.parseDouble(number);

        } catch (Exception e) {
            this.logger.printError("Can not read worldfile: " + e.getMessage());
            return null;
        }
        
        minx = faktorA * 0. + faktorC * 0. + CoordX;
        maxy = faktorB * 0. + faktorD * 0. + CoordY;
        maxx = faktorA * imgWidth + faktorC * imgHeight + CoordX;
        miny = faktorB * imgWidth + faktorD * imgHeight + CoordY;
        
        return new Envelope(minx, maxx, miny, maxy);
    }
    
    /**
     * creates a list of possible worldfile names for the given image
     * file name. The first one meets the naming conventions from ESRI,
     * the other names are variations that we got from ArcView, far away
     * from conventions...  
     * @return a list of possible worldfile names
     */
    protected List createListOfWorldFileNamesForImage(){
        String worldFileName = this.imageFileName.substring(0, this.imageFileName.lastIndexOf("."));
        String imageExtension = this.imageFileName.substring(this.imageFileName.lastIndexOf(".") + 1).toLowerCase();
        
        List possibleWorldFileNames = new ArrayList();
        possibleWorldFileNames.add(worldFileName + "." + imageExtension.substring(0, 1) + imageExtension.substring(imageExtension.length() - 1) + "w");
        possibleWorldFileNames.add((worldFileName + "." + imageExtension.substring(0, 1) + imageExtension.substring(imageExtension.length() - 1) + "w").toUpperCase());
        possibleWorldFileNames.add(worldFileName + "." + imageExtension + "w");
        possibleWorldFileNames.add(worldFileName + "." + (imageExtension + "w").toUpperCase());
        
        if (allwaysLookForTFWExtension) {
            possibleWorldFileNames.add(worldFileName + ".tfw");
            possibleWorldFileNames.add(worldFileName + ".tfw".toUpperCase());
        }
        
        return possibleWorldFileNames;
    }
    
    /**
     * Method that checks if a world file for the given image name can be found.
     * If so, it's name will be returned and also be stored internally.
     * @return the name of the worldfile if existent, else null
     */
    public String isWorldFileExistentForImage(){
        List possibleWorldFileNames = this.createListOfWorldFileNamesForImage();
        
        
        File worldFile = null;
        String wfName;
        for (int i = 0; i < possibleWorldFileNames.size(); i++) {
            wfName = ((String) possibleWorldFileNames.get(i));
            worldFile = new File(wfName);
            this.logger.printDebug("checking for world file named " + wfName);
            if (worldFile.exists()) {
                return (this.worldFileName = wfName);
            }
        }
        return null;
    }
    
    
    /**
     * @return Returns the allwaysLookForTFWExtension.
     */
    public boolean isAllwaysLookForTFWExtension() {
        return allwaysLookForTFWExtension;
    }
    /**
     * @param allwaysLookForTFWExtension The allwaysLookForTFWExtension to set.
     */
    public void setAllwaysLookForTFWExtension(boolean allwaysLookForTFWExtension) {
        this.allwaysLookForTFWExtension = allwaysLookForTFWExtension;
    }
    /**
     * @return Returns the imageFileName.
     */
    public String getImageFileName() {
        return imageFileName;
    }
    /**
     * @param imageFileName The imageFileName to set.
     */
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
    /**
     * @return Returns the worldFileName.
     */
    public String getWorldFileName() {
        return worldFileName;
    }
}
