/**
 * QuerySLDStyleAction.java
 * © MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 16-jun-2004
 *
 */
package com.geopista.style.sld.controller.impl.actions;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.deegree.graphics.sld.UserStyle;
import org.deegree_impl.graphics.sld.StyleFactory;

import com.geopista.style.sld.model.SLDStyle;
import com.vividsolutions.jump.workbench.model.Layer;

import es.enxenio.util.controller.Action;
import es.enxenio.util.controller.ActionForward;
import es.enxenio.util.controller.FrontController;
import es.enxenio.util.controller.Request;
import es.enxenio.util.controller.Session;
import es.enxenio.util.controller.impl.FrontControllerImpl;
import es.enxenio.util.controller.impl.SessionImpl;
/**
 * @author enxenio s.l.
 *
 */
public class QuerySLDStyleAction implements Action {

	/* (non-Javadoc)
	 * @see com.geopista.style.sld.controller.Action#doExecute(com.geopista.style.sld.controller.Request)
	 */
	public ActionForward doExecute(Request request) {
		
		/*Recuperamos las instancias del FrontController y de la Session*/
		FrontController frontController = FrontControllerImpl.getInstance();
		Session session = SessionImpl.getInstance();
		/*Recuperamos el parámetro de la request*/
		SLDStyle sldStyle = (SLDStyle) request.getAttribute("SLDStyle");
		String layerName = (String) request.getAttribute("LayerName");
		/*Recuperamos un objeto Layer propio de Jump y lo insertamos en la Session*/
		Layer layer = (Layer) request.getAttribute("Layer");
		session.setAttribute("Layer",layer);
		ArrayList userStyleListOriginal = (ArrayList)sldStyle.getStyles();
		ArrayList userStyleList = new ArrayList();
		/*Vamos a clonar todos los UserStyle contenidos en la lista*/
		Iterator userStyleIterator = userStyleListOriginal.iterator();
		while (userStyleIterator.hasNext()) {
			UserStyle userStyle = (UserStyle)userStyleIterator.next();
			UserStyle cloneUserStyle = clone(userStyle);
			userStyleList.add(cloneUserStyle);
		}	
		/*Creamos la lista de páginas que se vana a visitar*/
		List pagesVisited = new ArrayList();
		/*Creamos la lista que representa al flujo de datos cuando estemos manejando los simbolizadores*/
		List dataFlow = new ArrayList();
		/*Creamos la lista de en donde se almacenarán la lista de variables Insert*/
		List inserts = new ArrayList();
		/*Insertamos en la Session*/
		session.setAttribute("UserStyleList",userStyleList);
		session.setAttribute("SLDStyle",sldStyle);
		session.setAttribute("PagesVisited",pagesVisited);
		session.setAttribute("DataFlow",dataFlow);
		session.setAttribute("LayerName",layerName);
		session.setAttribute("Insert",inserts);
		/*Insertamos en la Request el nombre del estilo seleccionado*/
		session.setAttribute("CurrentStyleName", sldStyle.getCurrentStyleName());
		ActionForward forward = frontController.getForward("DisplayStyles");
		return forward;
	}
	
	private UserStyle clone(UserStyle userStyle) {
		
		UserStyle newUserStyle = (UserStyle)StyleFactory.createStyle(userStyle.getName(),userStyle.getTitle(),
			userStyle.getAbstract(),userStyle.getFeatureTypeStyles());
		newUserStyle.setDefault(userStyle.isDefault());
		return newUserStyle;
	}
}