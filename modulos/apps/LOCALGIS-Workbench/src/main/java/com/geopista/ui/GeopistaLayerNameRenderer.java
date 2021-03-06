package com.geopista.ui;

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


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;

import org.agil.core.jump.coverage.CoverageLayer;

import pirolPlugIns.utilities.RasterImageSupport.IRasterImageLayer;

import com.geopista.model.DynamicLayer;
import com.geopista.model.GeopistaLayer;
import com.geopista.style.sld.model.SLDStyle;
import com.geopista.ui.images.IconLoader;
import com.geopista.ui.legend.SymbolizerPanel;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.model.Layerable;
import com.vividsolutions.jump.workbench.model.WMSLayer;
import com.vividsolutions.jump.workbench.ui.GUIUtil;
import com.vividsolutions.jump.workbench.ui.LayerColorPanel;
import com.vividsolutions.jump.workbench.ui.plugin.wms.MapLayerPanel;
import com.vividsolutions.jump.workbench.ui.renderer.IRenderingManager;


public class GeopistaLayerNameRenderer extends JPanel implements ListCellRenderer,
    TreeCellRenderer {
    
    //<<TODO>> See how the colour looks with other L&F's. [Jon Aquino]
    private final static Color UNSELECTED_EDITABLE_FONT_COLOR = Color.red;
    private final static Color SELECTED_EDITABLE_FONT_COLOR = Color.yellow;
    protected JCheckBox checkBox = new JCheckBox();
    private LayerColorPanel colorPanel = new LayerColorPanel();
    GridBagLayout gridBagLayout = new GridBagLayout();
    protected JLabel label = new JLabel();
    private boolean indicatingEditability = false;
    private boolean indicatingProgress = false;
    private int progressIconSize = 13;
    private Icon[] progressIcons = null;
    private Icon clearProgressIcon = GUIUtil.resize(IconLoader.icon("Clear.gif"),
            progressIconSize);
    private String PROGRESS_ICON_KEY = "PROGRESS_ICON";
    private DefaultListCellRenderer defaultListCellRenderer = new DefaultListCellRenderer();
    private IRenderingManager renderingManager;
    private JLabel progressIconLabel = new JLabel();
    private Font font = new JLabel().getFont();
    private Font editableFont = font.deriveFont(Font.BOLD);
    private JLabel wmsIconLabel = new JLabel(MapLayerPanel.ICON);
	private SymbolizerPanel symbolPanel=new SymbolizerPanel();

    public GeopistaLayerNameRenderer() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setIndicatingEditability(boolean indicatingEditability) {
        this.indicatingEditability = indicatingEditability;
    }

    public void setIndicatingProgress(boolean indicatingProgress,
        IRenderingManager renderingManager) {
        this.indicatingProgress = indicatingProgress;
        this.renderingManager = renderingManager;
    }
    private Icon[] getProgressIcons() {
	//Create lazily -- OptimizeIt tells me creating these images takes 20
	//seconds [Jon Aquino 2004-05-14]
	if (progressIcons == null) {
		progressIcons = new Icon[] {
				GUIUtil.resize(IconLoader.icon("ClockN.gif"),
						progressIconSize),
				GUIUtil.resize(IconLoader.icon("ClockNE.gif"),
						progressIconSize),
				GUIUtil.resize(IconLoader.icon("ClockE.gif"),
						progressIconSize),
				GUIUtil.resize(IconLoader.icon("ClockSE.gif"),
						progressIconSize),
				GUIUtil.resize(IconLoader.icon("ClockS.gif"),
						progressIconSize),
				GUIUtil.resize(IconLoader.icon("ClockSW.gif"),
						progressIconSize),
				GUIUtil.resize(IconLoader.icon("ClockW.gif"),
						progressIconSize),
				GUIUtil.resize(IconLoader.icon("ClockNW.gif"),
						progressIconSize) };
	}
	return progressIcons;
}
    public JLabel getLabel() {
        return label;
    }

    /**
     * @return relative to this panel
     */
    public Rectangle getCheckBoxBounds() {
        int i = gridBagLayout.getConstraints(checkBox).gridx;
        int x = 0;

        for (int j = 0; j < i; j++) {
            x += getColumnWidth(j);
        }

        return new Rectangle(x, 0, getColumnWidth(i), getRowHeight());
    }

    /**
     * @param i zero-based
     */
    protected int getColumnWidth(int i) {
    	//Esto antes funcionaba bien Java 1.6
        //validateTree();
        synchronized(getTreeLock()) {
            validateTree();
       }

        return gridBagLayout.getLayoutDimensions()[0][i];
    }

    protected int getRowHeight() {
    	//Esto antes funcionaba bien Java 1.6
        //validateTree();
        synchronized(getTreeLock()) {
            validateTree();
       }


        return gridBagLayout.getLayoutDimensions()[1][0];
    }

    public void setCheckBoxVisible(boolean checkBoxVisible) {
        checkBox.setVisible(checkBoxVisible);
    }

    /**
     * Workaround for bug 4238829 in the Java bug database:
     * "JComboBox containing JPanel fails to display selected item at creation time"
     */
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        validate();
    }

    public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus) {
        if (value == null) {
            return defaultListCellRenderer.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);
        }

        final Layerable layerable = (Layerable) value;

        ///
        label.setText(layerable.getName());
        setToolTipText(layerable.getName() +
            ((layerable instanceof Layer &&
            (((Layer) layerable).getDescription() != null) &&
            (((Layer) layerable).getDescription().trim().length() > 0))
            ? (": " + ((Layer) layerable).getDescription()) : ""));

        if (isSelected) {
            label.setForeground(list.getSelectionForeground());
            label.setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            setBackground(list.getSelectionBackground());
        } else {
            label.setForeground(list.getForeground());
            label.setBackground(list.getBackground());
            setForeground(list.getForeground());
            setBackground(list.getBackground());
        }
        if (layerable instanceof Layer)
        {
        colorPanel.setVisible(((Layer)layerable).getStyle(SLDStyle.class)==null);
        symbolPanel.setVisible(((Layer)layerable).getStyle(SLDStyle.class)!=null);
        }
        else
        {
        colorPanel.setVisible(false);
        symbolPanel.setVisible(false);
        }
        
        checkBox.setSelected(layerable.isVisible());
        checkBox.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				layerable.setVisible(!layerable.isVisible());
				
			}});

        if (indicatingEditability && layerable instanceof Layer)
        	{
        	if(((Layer) layerable).isEditable()) 
        		{
	            label.setFont(editableFont);
	            label.setForeground(isSelected ? SELECTED_EDITABLE_FONT_COLOR
	                                           : UNSELECTED_EDITABLE_FONT_COLOR);
	        	} 
        	}
        else
        	 label.setFont(font);
        
        if (layerable instanceof GeopistaLayer)
		{
		if (!((GeopistaLayer) layerable).isActiva())
			{
			label.setForeground(Color.GRAY);
			}
		}
        wmsIconLabel.setVisible(layerable instanceof WMSLayer || layerable instanceof CoverageLayer|| layerable instanceof IRasterImageLayer|| layerable instanceof DynamicLayer);

      
        		  //Only show the progress icon (clocks) for WMSLayers, not Layers.
                //Otherwise it's too busy. [Jon Aquino]
                if (
                		(layerable instanceof WMSLayer || layerable instanceof CoverageLayer || layerable instanceof IRasterImageLayer|| layerable instanceof DynamicLayer)
                	//	&&	layerable.getBlackboard().get(LayerNameRenderer.USE_CLOCK_ANIMATION_KEY, false)
        				&& indicatingProgress
        				&& (renderingManager.getRenderer(layerable) != null)
        				&& renderingManager.getRenderer(layerable).isRendering()
        				) 
                	{
        			layerable.getBlackboard().put(PROGRESS_ICON_KEY,
        					layerable.getBlackboard().get(PROGRESS_ICON_KEY, 0) + 1);
        			if (layerable.getBlackboard().getInt(PROGRESS_ICON_KEY) > (getProgressIcons().length - 1)) {
        				layerable.getBlackboard().put(PROGRESS_ICON_KEY, 0);
        			}
        			progressIconLabel.setIcon(getProgressIcons()[layerable
        					.getBlackboard().getInt(PROGRESS_ICON_KEY)]);
        		} else {
        			progressIconLabel.setIcon(clearProgressIcon);
        			layerable.getBlackboard().put(PROGRESS_ICON_KEY, null);
        		}
                   
        Color backgroundColor = list.getBackground();
        Color selectionBackgroundColor = list.getSelectionBackground();

        if (layerable instanceof Layer)
        if (((Layer)layerable).getStyle(SLDStyle.class)==null) // JUMP implementation
        {
        Layer layer = (Layer) layerable;
        colorPanel.init(layer, isSelected, backgroundColor,
        		selectionBackgroundColor);
        }else { // Geopista implementation
        Layer layer = (Layer) layerable;
        symbolPanel.init(layer);
        
        }

        return this;
    }

    private JList list(JTree tree) {
        JList list = new JList();
        list.setForeground(tree.getForeground());
        list.setBackground(tree.getBackground());
        list.setSelectionForeground(UIManager.getColor(
                "Tree.selectionForeground"));
        list.setSelectionBackground(UIManager.getColor(
                "Tree.selectionBackground"));

        return list;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
        boolean selected, boolean expanded, boolean leaf, int row,
        boolean hasFocus) {
        Layerable layerable = (Layerable) value;
        getListCellRendererComponent(list(tree), layerable, -1, selected,
            hasFocus);

        if (selected) {
            label.setForeground(UIManager.getColor("Tree.selectionForeground"));
            label.setBackground(UIManager.getColor("Tree.selectionBackground"));
            setForeground(UIManager.getColor("Tree.selectionForeground"));
            setBackground(UIManager.getColor("Tree.selectionBackground"));
        } else {
            label.setForeground(tree.getForeground());
            label.setBackground(tree.getBackground());
            setForeground(tree.getForeground());
            setBackground(tree.getBackground());
        }

        if (indicatingEditability && layerable instanceof Layer) {
            if (((Layer) layerable).isEditable()) {
                label.setForeground(selected ? SELECTED_EDITABLE_FONT_COLOR
                                             : UNSELECTED_EDITABLE_FONT_COLOR);
            }
        }

        return this;
    }

    void jbInit() throws Exception {
        checkBox.setVisible(true);
        this.setLayout(gridBagLayout);
        label.setOpaque(false);
        label.setText("Layer Name Goes Here");
        checkBox.setOpaque(false);
        symbolPanel.setMinimumSize(new Dimension(20,20));
        symbolPanel.setPreferredSize(new Dimension(20,20));
        symbolPanel.setSize(new Dimension(20,20));
        this.add(progressIconLabel,
            new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 2), 0, 0));
        this.add(wmsIconLabel,
            new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 2), 0, 0));
        this.add(colorPanel,
            new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 5), 0, 0));
        this.add(symbolPanel,
              new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                  GridBagConstraints.WEST, GridBagConstraints.NONE,
                  new Insets(0, 0, 0, 5), 0, 0));
        this.add(checkBox,
            new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        this.add(label,
            new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
    
    }
}
