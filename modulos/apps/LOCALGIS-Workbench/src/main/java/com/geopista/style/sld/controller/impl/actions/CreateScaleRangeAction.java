/**
 * CreateScaleRangeAction.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 26-jul-2004
 *
 */
package com.geopista.style.sld.controller.impl.actions;

import java.util.List;

import com.geopista.style.sld.model.SLDFacade;
import com.geopista.style.sld.model.SLDFactory;
import com.geopista.style.sld.model.ScaleRange;

import es.enxenio.util.controller.Action;
import es.enxenio.util.controller.ActionForward;
import es.enxenio.util.controller.FrontController;
import es.enxenio.util.controller.Request;
import es.enxenio.util.controller.Session;
import es.enxenio.util.controller.impl.FrontControllerImpl;
import es.enxenio.util.controller.impl.SessionImpl;
import es.enxenio.util.exceptions.InternalErrorException;

/**
 * @author enxenio s.l.
 *
 */
public class CreateScaleRangeAction implements Action {

	/* (non-Javadoc)
	 * @see com.geopista.style.sld.controller.Action#doExecute(com.geopista.style.sld.controller.Request)
	 */
	public ActionForward doExecute(Request request) {
		
		/*Recuperamos las instancias del FrontController y de la Session*/
		FrontController frontController = FrontControllerImpl.getInstance();
		Session session = SessionImpl.getInstance();
		List pagesVisited = (List)session.getAttribute("PagesVisited");
		/*Recogemos los par�metros de la Request*/
		Double minScale = (Double) request.getAttribute("MinScale");
		Double maxScale = (Double) request.getAttribute("MaxScale");
		List inserts = (List)session.getAttribute("Insert");
		Integer insert = (Integer)inserts.get(1);
		ScaleRange scaleRange = (ScaleRange) session.getAttribute("ScaleRange");
		/*Recuperamos la lista de ScaleRanges de la Session*/
		List scaleRangeList = (List) session.getAttribute("ScaleRangeList");
		SLDFacade sldFacade = null;
		try {
			sldFacade = SLDFactory.getDelegate();
		} catch(InternalErrorException e) {
			System.err.println(e);
		}
		/*Llamamos al caso de uso del modelo*/
		scaleRange = sldFacade.updateScaleRange(minScale,maxScale,insert,scaleRange,scaleRangeList);
		session.setAttribute("ScaleRange",scaleRange);
		session.setAttribute("ScaleRangePosition",new Integer(scaleRangeList.indexOf(scaleRange)));
		/*A�adimos la lista a la Session*/
		session.setAttribute("ScaleRangeList",scaleRangeList);
		/*Actualizamos el par�metro PagesVisited en la Session*/
		int size = pagesVisited.size();
		String lastPageVisited = (String) pagesVisited.remove(size-1);
		session.setAttribute("PagesVisited",pagesVisited);
		/*Redirigimos a otra acci�n*/
		ActionForward forward = frontController.getForward(lastPageVisited);
		return forward;		
	}
}
