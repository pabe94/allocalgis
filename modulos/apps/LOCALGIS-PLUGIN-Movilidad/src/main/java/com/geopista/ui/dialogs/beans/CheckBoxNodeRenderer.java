/**
 * CheckBoxNodeRenderer.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.ui.dialogs.beans;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxNodeRenderer implements TreeCellRenderer {
	  private JCheckBox leafRenderer = new JCheckBox();

	  private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();

	  private Color selectionBorderColor, selectionForeground, selectionBackground,
	      textForeground, textBackground;

	  public Color getSelectionBorderColor() {
		return selectionBorderColor;
	}

	public void setSelectionBorderColor(Color selectionBorderColor) {
		this.selectionBorderColor = selectionBorderColor;
	}

	public Color getSelectionForeground() {
		return selectionForeground;
	}

	public void setSelectionForeground(Color selectionForeground) {
		this.selectionForeground = selectionForeground;
	}

	public Color getSelectionBackground() {
		return selectionBackground;
	}

	public void setSelectionBackground(Color selectionBackground) {
		this.selectionBackground = selectionBackground;
	}

	public Color getTextForeground() {
		return textForeground;
	}

	public void setTextForeground(Color textForeground) {
		this.textForeground = textForeground;
	}

	public Color getTextBackground() {
		return textBackground;
	}

	public void setTextBackground(Color textBackground) {
		this.textBackground = textBackground;
	}

	protected JCheckBox getLeafRenderer() {
	    return leafRenderer;
	  }

	  public CheckBoxNodeRenderer() {
	    Font fontValue;
	    fontValue = UIManager.getFont("Tree.font");
	    if (fontValue != null) {
	      leafRenderer.setFont(fontValue);
	    }
	    Boolean booleanValue = (Boolean) UIManager
	        .get("Tree.drawsFocusBorderAroundIcon");
	    leafRenderer.setFocusPainted((booleanValue != null)
	        && (booleanValue.booleanValue()));

	    selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
	    selectionForeground = UIManager.getColor("Tree.selectionForeground");
	    selectionBackground = UIManager.getColor("Tree.selectionBackground");
	    textForeground = UIManager.getColor("Tree.textForeground");
	    textBackground = UIManager.getColor("Tree.textBackground");
	  }
	  
	  public CheckBoxNodeRenderer(Color textForeground){
		  this();
		  this.textForeground = textForeground;
	  }

	  public Component getTreeCellRendererComponent(JTree tree, Object value,
		      boolean selected, boolean expanded, boolean leaf, int row,
		      boolean hasFocus) {

		    Component returnValue;
		    if (leaf) {

		      String stringValue = tree.convertValueToText(value, selected,
		          expanded, leaf, row, false);
		      leafRenderer.setText(stringValue);
		      leafRenderer.setSelected(false);

		      leafRenderer.setEnabled(tree.isEnabled());

		      if (selected) {
		        leafRenderer.setForeground(selectionForeground);
		        leafRenderer.setBackground(selectionBackground);
		      } else {
		        leafRenderer.setForeground(textForeground);
		        leafRenderer.setBackground(textBackground);
		      }

		      if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
		        Object userObject = ((DefaultMutableTreeNode) value)
		            .getUserObject();
		        if (userObject instanceof CheckBoxNode) {
		          CheckBoxNode node = (CheckBoxNode) userObject;
		          leafRenderer.setText(node.getText());
		          leafRenderer.setSelected(node.isSelected());
		        }
		      }
		      returnValue = leafRenderer;
		    } else {
		      returnValue = nonLeafRenderer.getTreeCellRendererComponent(tree,
		          value, selected, expanded, leaf, row, hasFocus);
		    }
		    return returnValue;
		  }
}