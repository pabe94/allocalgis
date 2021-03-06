/**
 * RotatedLabel.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*----------------    FILE HEADER  ------------------------------------------

This file is part of deegree.
Copyright (C) 2001 by:
EXSE, Department of Geography, University of Bonn
http://www.giub.uni-bonn.de/exse/   
lat/lon Fitzke/Fretter/Poth GbR
http://www.lat-lon.de

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Contact:

Andreas Poth
lat/lon Fitzke/Fretter/Poth GbR
Meckenheimer Allee 176
53115 Bonn
Germany
E-Mail: poth@lat-lon.de

Jens Fitzke
Department of Geography
University of Bonn
Meckenheimer Allee 166
53115 Bonn
Germany
E-Mail: jens.fitzke@uni-bonn.de

---------------------------------------------------------------------------*/
package org.deegree_impl.graphics.displayelements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.deegree.graphics.displayelements.Label;
import org.deegree.graphics.sld.Fill;
import org.deegree.graphics.sld.GraphicFill;
import org.deegree.graphics.sld.Halo;
import org.deegree.model.feature.Feature;
import org.deegree.services.wfs.filterencoding.FilterEvaluationException;

/**
* This is a rotated label with style information and screen coordinates, ready
* to be rendered to the view.
* 
* @author <a href="mailto:mschneider@lat-lon.de">Markus Schneider </a>
* @version $Revision: 1.1 $ $Date: 2009/07/03 12:32:05 $
*/
class RotatedLabel implements Label {

   private String caption;

   private int[] xpoints;

   private int[] ypoints;

   private double rotation;

   private double anchorPoint[];

   // width and height of the caption
   private int w, h;

   private Color color;

   private Font font;

   private int descent, ascent;

   private Halo halo;

   private Feature feature;
   
   private String captionlines[]; //field


   RotatedLabel(String caption, Font font, Color color, LineMetrics metrics,
           Feature feature, Halo halo, int x, int y, int w, int h,
           double rotation, double anchorPoint[], double[] displacement) {

       this.caption = caption;
       this.captionlines=caption.split("\\n|\\\\n"); //JP: Allows to split multiline texts to avoid very long lines.

       this.font = font;
       this.color = color;
       this.descent = (int) metrics.getDescent();
       this.ascent = (int) metrics.getAscent();
       this.feature = feature;
       this.halo = halo;
       this.rotation = rotation;
       this.anchorPoint = anchorPoint;

       this.w = w;
       this.h = h;

       // vertices of label boundary
       int[] xpoints = new int[4];
       int[] ypoints = new int[4];
       xpoints[0] = x;
       ypoints[0] = y;
       xpoints[1] = x + w;
       ypoints[1] = y;
       xpoints[2] = x + w;
       ypoints[2] = y - h;
       xpoints[3] = x;
       ypoints[3] = y - h;

       // get rotated + translated points
       this.xpoints = new int[4];
       this.ypoints = new int[4];
       int tx = xpoints[0];
       int ty = ypoints[0];

       // transform all vertices of the boundary
       for (int i = 0; i < 4; i++) {
           int[] point = transformPoint(xpoints[i], ypoints[i], tx, ty,
                   rotation, w, h, displacement[0], displacement[1]);
           this.xpoints[i] = point[0];
           this.ypoints[i] = point[1];
       }
   }

   public String getCaption() {
       return caption;
   }

   public double getRotation() {
       return rotation;
   }

   public void paintBoundaries(Graphics2D g) {
       setColor(g, new Color(0x888888), 0.5);
       g.fillPolygon(xpoints, ypoints, xpoints.length);
       g.setColor(Color.BLACK);

       // get the current transform
       AffineTransform saveAT = g.getTransform();

       // translation parameters (rotation)
       AffineTransform transform = new AffineTransform();
       transform.concatenate(saveAT);
       
       // render the text
       transform.rotate(rotation / 180d * Math.PI, xpoints[0], ypoints[0]);
       g.setTransform(transform);
       //g.drawString( caption, xpoints [0], ypoints [0] - descent);

       // restore original transform
       g.setTransform(saveAT);
   }

   /**
    * Renders the label (including halo) to the submitted
    * <code>Graphics2D</code> context.
    * 
    * @param g
    *            <code>Graphics2D</code> context to be used
    */
   public void paint(Graphics2D g) {

       // get the current transform
       AffineTransform saveAT = g.getTransform();

       // perform transformation
       AffineTransform transform = new AffineTransform();
       transform.concatenate(saveAT);
       
       transform.rotate(rotation / 180d * Math.PI, xpoints[0], ypoints[0]);
       
       g.setTransform(transform);

       // render the halo (only if specified)
       if (halo != null) {
           try {
               paintHalo(g, halo, (int) (xpoints[0] - w * anchorPoint[0]),
                       (int) (ypoints[0] - descent + h * anchorPoint[1]));
           } catch (FilterEvaluationException e) {
               e.printStackTrace();
           }
       }

       // render the text
       setColor(g, color, 1.0);
       g.setFont(font);
       for (int i = 0; i < captionlines.length; i++)
        {
        g.drawString( captionlines[i],
                    (int) (xpoints[0] - w * anchorPoint[0]), 
                       (int)(ypoints[0] - descent + h * anchorPoint[1]+ i*h*0.5)  );
        }

       

       // restore original transform
       g.setTransform(saveAT);
   }

   /**
    * Renders the label's halo to the submitted <code>Graphics2D</code>
    * context.
    * 
    * @param g
    *            <code>Graphics2D</code> context to be used
    * @param halo
    *            <code>Halo</code> from the SLD
    * @param x
    *            x-coordinate of the label
    * @param y
    *            y-coordinate of the label
    * 
    * @throws FilterEvaluationException
    *             if the evaluation of a <code>ParameterValueType</code>
    *             fails
    */
   private void paintHalo(Graphics2D g, Halo halo, int x, int y)
           throws FilterEvaluationException {

       int radius = (int) halo.getRadius(feature);

       // only draw filled rectangle or circle, if Fill-Element is given
       Fill fill = halo.getFill();

       if (fill != null) {
           GraphicFill gFill = fill.getGraphicFill();

           if (gFill != null) {
               BufferedImage texture = gFill.getGraphic().getAsImage(feature);
               Rectangle anchor = new Rectangle(0, 0, texture.getWidth(null),
                       texture.getHeight(null));
               g.setPaint(new TexturePaint(texture, anchor));
           } else {
               double opacity = fill.getOpacity(feature);
               Color color = fill.getFill(feature);
               setColor(g, color, opacity);
           }
       } else {
           g.setColor(Color.white);
       }

       // radius specified -> draw circle
       if (radius > 0) {
           g.fillOval((x + (w >> 1)) - radius, y - (ascent >> 1) - radius,
                   radius << 1, radius << 1);
       }
       // radius unspecified -> draw rectangle
       else {
           g.fillRect(x - 1, y - ascent - 1, w + 2, h + 2);
       }

       // only stroke outline, if Stroke-Element is given
       org.deegree.graphics.sld.Stroke stroke = halo.getStroke();

       if (stroke != null) {
           double opacity = stroke.getOpacity(feature);

           if (opacity > 0.01) {
               Color color = stroke.getStroke(feature);
               int alpha = (int) Math.round(opacity * 255);
               int red = color.getRed();
               int green = color.getGreen();
               int blue = color.getBlue();
               color = new Color(red, green, blue, alpha);
               g.setColor(color);

               float[] dash = stroke.getDashArray(feature);

               // use a simple Stroke if dash == null or dash length < 2
               BasicStroke bs = null;
               float strokeWidth = (float) stroke.getWidth(feature);

               if ((dash == null) || (dash.length < 2)) {
                   bs = new BasicStroke(strokeWidth);
               } else {
                   bs = new BasicStroke(strokeWidth, stroke
                           .getLineCap(feature), stroke.getLineJoin(feature),
                           10.0f, dash, stroke.getDashOffset(feature));
                   bs = new BasicStroke(strokeWidth, stroke
                           .getLineCap(feature), stroke.getLineJoin(feature),
                           1.0f, dash, 1.0f);
               }

               g.setStroke(bs);

               // radius specified -> draw circle
               if (radius > 0) {
                   g.drawOval((x + (w >> 1)) - radius, y - (ascent >> 1)
                           - radius, radius << 1, radius << 1);
               }// radius unspecified -> draw rectangle
               else {
                   g.drawRect(x - 1, y - ascent - 1, w + 2, h + 2);
               }
           }
       }
   }

   public int getX() {
       return xpoints[0];
   }

   public int getY() {
       return ypoints[0];
   }

   public int getMaxX() {
       return xpoints[1];
   }

   public int getMaxY() {
       return ypoints[1];
   }

   public int getMinX() {
       return xpoints[3];
   }

   public int getMinY() {
       return ypoints[3];
   }

   /**
    * Determines if the label intersects with another label.
    * 
    * @param that
    *            label to test
    * @return true if the labels intersect
    */
   public boolean intersects(Label that) {
       System.out.println("Intersection test for rotated labels is "
               + "not implemented yet!");
       return false;
   }

   private int[] transformPoint(int x, int y, int tx, int ty, double rotation,
           int w, int h, double displacementX, double displacementY) {

       double cos = Math.cos(rotation);
       double sin = Math.sin(rotation);

       double m00 = cos;
       double m01 = -sin;
       double m02 = tx - tx * cos + ty * sin;
       double m10 = sin;
       double m11 = cos;
       double m12 = ty - tx * sin - ty * cos;

       int[] point2 = new int[2];
       //displacementX = displacementX + displacementY * (1+cos);        
       //displacementY = displacementY + displacementY * (1+sin);
       point2[0] = (int) (m00 * x + m01 * y + m02 + 0.5 + displacementX);
       point2[1] = (int) (m10 * x + m11 * y + m12 + 0.5 - displacementY);
       return point2;
   }

   private Graphics2D setColor(Graphics2D g2, Color color, double opacity) {
       if (opacity < 0.999) {
           final int alpha = (int) Math.round(opacity * 255);
           final int red = color.getRed();
           final int green = color.getGreen();
           final int blue = color.getBlue();
           color = new Color(red, green, blue, alpha);
       }

       g2.setColor(color);
       return g2;
   }

   public String toString() {
       return caption;
   }
}

