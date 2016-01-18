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
package com.vividsolutions.jump.workbench.ui.renderer.style;

import java.awt.Graphics2D;

import com.vividsolutions.jts.util.Assert;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.workbench.model.ILayer;
import com.vividsolutions.jump.workbench.ui.IViewport;

public class DummyStyle implements Style {

	public void paint(Feature f, Graphics2D g, IViewport viewport)
		throws Exception {
	}

	public void initialize(ILayer layer) {
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			Assert.shouldNeverReachHere();
			return null;
		}
	}

	public void setEnabled(boolean enabled) {
	}

	public boolean isEnabled() {
		return false;
	}

	private static DummyStyle instance = new DummyStyle();
	
	private DummyStyle() {}
	
	public static DummyStyle instance() { return instance; }

}