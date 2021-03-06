/**
 * NewContact.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.server.metadatos.web;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.plus.jaas.JAASUserPrincipal;
import org.exolab.castor.xml.Unmarshaller;

import admcarApp.PasarelaAdmcar;

import com.geopista.protocol.CResultadoOperacion;
import com.geopista.protocol.control.Sesion;
import com.geopista.protocol.metadatos.CI_ResponsibleParty;
import com.geopista.protocol.net.EnviarSeguro;
import com.geopista.server.database.COperacionesMetadatos;


/**
 * Created by IntelliJ IDEA.
 * User: angeles
 * Date: 27-jul-2004
 * Time: 13:50:40
 */
public class NewContact  extends HttpServlet {
       private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NewContact.class);
            public void doPost (HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException
            {
               CResultadoOperacion resultado=null;
               try
               {
                   //El identificador de sesi�n debe venir como el nombre del user principal
                     String sIdMunicipio=null;
                     JAASUserPrincipal userPrincipal = (JAASUserPrincipal)request.getUserPrincipal();
                     if (userPrincipal!=null)
                     {
                          Sesion sSesion=PasarelaAdmcar.listaSesiones.getSesion(userPrincipal.getName());
                          if (sSesion!=null) sIdMunicipio=sSesion.getIdMunicipio();
                          sIdMunicipio = request.getParameter(EnviarSeguro.idMunicipio);
                          sSesion.setIdMunicipio(sIdMunicipio);
                     }
                     
                     CI_ResponsibleParty contact=(CI_ResponsibleParty)Unmarshaller.unmarshal(CI_ResponsibleParty.class,new StringReader(request.getParameter(EnviarSeguro.mensajeXML)));
                     //Marshaller.marshal(user,new OutputStreamWriter(System.out));
                     resultado=COperacionesMetadatos.ejecutarNewContact(contact,sIdMunicipio);
                     Vector vResultados = new Vector();
                     vResultados.add(contact);
                     resultado.setVector(vResultados);


               }catch(Exception e)
               {
                   java.io.StringWriter sw=new java.io.StringWriter();
                   java.io.PrintWriter pw=new java.io.PrintWriter(sw);
                   e.printStackTrace(pw);
                   logger.error("Excepci�n al crear el nuevo USUARIO:"+sw.toString());
                   resultado= new CResultadoOperacion(false, "Excepci�n al crear un usuario:"+e.toString());
               }
                Writer writer = response.getWriter();
                writer.write (resultado.buildResponse());
                writer.flush();
                writer.close();
              }
}
