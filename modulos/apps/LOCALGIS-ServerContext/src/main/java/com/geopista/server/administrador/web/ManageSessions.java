/**
 * ManageSessions.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.server.administrador.web;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import com.geopista.protocol.CResultadoOperacion;
import com.geopista.protocol.administrador.Operacion;
import com.geopista.protocol.net.EnviarSeguro;
import com.geopista.server.LoggerHttpServlet;
import com.geopista.server.database.COperacionesAdministrador;


public class ManageSessions extends LoggerHttpServlet {

	private static final long serialVersionUID = 6334011650610334866L;
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ManageSessions.class);
         
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		super.doPost(request);
		CResultadoOperacion resultado=null;
		
		try {	
			 Operacion op = (Operacion) Unmarshaller.unmarshal(Operacion.class, 
					 new StringReader(request.getParameter(EnviarSeguro.mensajeXML)));
			 
			 Marshaller.marshal(op, new OutputStreamWriter(System.out));
	         resultado = COperacionesAdministrador.ejecutarSelectDetallesOperacion(op);
	            
		}
		catch(Exception e) {

            java.io.StringWriter sw=new java.io.StringWriter();
            java.io.PrintWriter pw=new java.io.PrintWriter(sw);
            e.printStackTrace(pw);
            logger.error("[ManageSessions] Excepci�n al obtener los detalles de una operaci�n:"+sw.toString());
            resultado= new CResultadoOperacion(false, "Error al obtener los detalles de una operaci�n:"+e.toString());
        }
         
		Writer writer = response.getWriter();
         writer.write (resultado.buildResponse());
         writer.flush();
         writer.close();
       
	}
}
