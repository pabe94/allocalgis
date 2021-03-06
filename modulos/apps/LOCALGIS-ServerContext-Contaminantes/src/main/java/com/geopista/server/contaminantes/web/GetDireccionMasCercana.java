/**
 * GetDireccionMasCercana.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.server.contaminantes.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.plus.jaas.JAASUserPrincipal;

import admcarApp.PasarelaAdmcar;

import com.geopista.protocol.CResultadoOperacion;
import com.geopista.protocol.control.Sesion;
import com.geopista.protocol.net.EnviarSeguro;
import com.geopista.server.LoggerHttpServlet;
import com.geopista.server.database.COperacionesContaminantes;


/**
 * Created by IntelliJ IDEA.
 * User: angeles
 * Date: 18-nov-2004
 * Time: 15:43:30
 */
public class GetDireccionMasCercana extends LoggerHttpServlet {
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeleteActividad.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request);
		CResultadoOperacion resultado;
		try {
			JAASUserPrincipal userPrincipal = (JAASUserPrincipal) request.getUserPrincipal();
            if (userPrincipal == null) {
                            resultado = new CResultadoOperacion(false, "El usuario no ha sido autenticado por JAAS");
            } else
            {
                String sIdSesion = userPrincipal.getName();
                Sesion sSesion = PasarelaAdmcar.listaSesiones.getSesion(sIdSesion);
                String sMunicipio = request.getParameter(EnviarSeguro.idMunicipio);
                sSesion.setIdMunicipio(sMunicipio);
        		String id = request.getParameter(EnviarSeguro.mensajeXML);
			    resultado = COperacionesContaminantes.getDireccionMasCercana(id, sSesion.getIdMunicipio());
            }
		} catch (Exception e) {
			java.io.StringWriter sw = new java.io.StringWriter();
			java.io.PrintWriter pw = new java.io.PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error("Excepci�n al actualizar el vertedero:" + sw.toString());
			resultado = new CResultadoOperacion(false, "Excepci�n al borrar el vertedero:" + e.toString());
		}
		Writer writer = response.getWriter();
		writer.write(resultado.buildResponse());
		writer.flush();
		writer.close();
	}
}

