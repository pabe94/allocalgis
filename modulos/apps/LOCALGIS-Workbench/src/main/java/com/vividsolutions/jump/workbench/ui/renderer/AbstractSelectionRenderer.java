/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI 
 * for visualizing and manipulating spatial features with geometry and attributes.
 *
 * Copyright (C) 2003 Vivid Solutions
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * For more information, contact:
 *
 * Vivid Solutions
 * Suite #1A
 * 2328 Government Street
 * Victoria BC  V8T 5G5
 * Canada
 *
 * (250)385-6040
 * www.vividsolutions.com
 */

package com.vividsolutions.jump.workbench.ui.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Icon;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.util.Assert;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.util.CollectionMap;
import com.vividsolutions.jump.workbench.model.ILayer;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.ui.GUIUtil;
import com.vividsolutions.jump.workbench.ui.IViewport;
import com.vividsolutions.jump.workbench.ui.LayerViewPanel;
import com.vividsolutions.jump.workbench.ui.Viewport;
import com.vividsolutions.jump.workbench.ui.renderer.style.Style;
import com.vividsolutions.jump.workbench.ui.renderer.style.StyleUtil;

public abstract class AbstractSelectionRenderer extends FeatureCollectionRenderer implements Style {
    public final static int HANDLE_WIDTH = 5;
    private boolean enabled = true;
    private Stroke handleStroke = new BasicStroke(1);
    private Color handleFillColor;
    private Color handleLineColor = Color.black;
    private Stroke lineStroke = new BasicStroke(2);
    private Color lineColor;
    private Stroke fillStroke = new BasicStroke(1);
    private Color fillColor;
    private boolean filling = true;
	protected LayerViewPanel panel;
    
    public AbstractSelectionRenderer(Object contentID, LayerViewPanel panel, Color color, boolean paintingHandles, boolean filling) {
        super(contentID, panel);
        this.panel = panel;
        handleFillColor = color;
        lineColor = color;
        fillColor = GUIUtil.alphaColor(Color.white, 75);
        this.paintingHandles = paintingHandles;
        this.filling = filling;
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public Icon getIcon() {
        throw new UnsupportedOperationException();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Object clone() {
            Assert.shouldNeverReachHere();
            return null;
    }

    public void initialize(ILayer layer) {}

    private CollectionMap featureToSelectedItemsMap;

    private boolean paintingHandles;

    public void paint(Feature f, Graphics2D g, IViewport viewport)
        throws NoninvertibleTransformException {
        for (Iterator i = featureToSelectedItemsMap.getItems(f).iterator(); i.hasNext(); ) {
            Geometry geometry = (Geometry) i.next();
            paint(geometry, g, viewport);
        }
    }
    public void paint(Geometry geometry, Graphics2D g, IViewport viewport)
        throws NoninvertibleTransformException {
        Coordinate[] coordinates = geometry.getCoordinates();
        StyleUtil.paint(
            geometry,
            g,
            viewport,
            filling,
            fillStroke,
            fillColor,
            true,
            lineStroke,
            lineColor);
        if (paintingHandles) {            
            paintHandles(g, coordinates, handleStroke, handleFillColor, handleLineColor, panel.getViewport());
        }
    }

    protected Collection styles() {
		return Collections.singleton(this);
	}
    
    protected Map layerToFeaturesMap() {
        featureToSelectedItemsMap = new CollectionMap();
		HashMap layerToFeaturesMap = new HashMap();
        for (Iterator i = panel.getLayerManager().iterator(); i.hasNext();) {
            Layer layer = (Layer) i.next();
            Map featureToSelectedItemsMapForLayer = featureToSelectedItemsMap(layer);
            featureToSelectedItemsMap.putAll(featureToSelectedItemsMapForLayer); 
            layerToFeaturesMap.put(layer, featureToSelectedItemsMapForLayer.keySet());
        }        
        return layerToFeaturesMap;
	}

    protected abstract CollectionMap featureToSelectedItemsMap(Layer layer);

    private static Shape handle(Point2D point) {
        Rectangle2D.Double handle = new Rectangle2D.Double(0.0, 0.0, HANDLE_WIDTH, HANDLE_WIDTH);
        handle.x = point.getX() - (HANDLE_WIDTH / 2);
        handle.y = point.getY() - (HANDLE_WIDTH / 2);

        return handle;
    }

    public static void paintHandles(Graphics2D g, Coordinate[] coordinates, Stroke stroke, Color fillColor, Color lineColor, Viewport viewport)
        throws NoninvertibleTransformException {
        g.setStroke(stroke);
        g.setColor(fillColor);

        for (int i = 0; i < coordinates.length; i++) {
            if (!viewport.getEnvelopeInModelCoordinates().contains(coordinates[i])) {
                //Otherwise get "sun.dc.pr.PRException: endPath: bad path" exception [Jon Aquino 10/22/2003]
                continue;
            }
            g.fill(
                handle(
                    viewport.toViewPoint(
                        new Point2D.Double(coordinates[i].x, coordinates[i].y))));
        }

        g.setColor(lineColor);

        for (int i = 0; i < coordinates.length; i++) {
            if (!viewport.getEnvelopeInModelCoordinates().contains(coordinates[i])) {
                //Otherwise get "sun.dc.pr.PRException: endPath: bad path" exception [Jon Aquino 10/22/2003]
                continue;
            }
            g.draw(
                handle(
                    viewport.toViewPoint(
                        new Point2D.Double(coordinates[i].x, coordinates[i].y))));
        }
    }


}
