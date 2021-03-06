/**
 * JPanelVertedero.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * JPanelVertedero.java
 *
 * Created on 18 de octubre de 2004, 18:20
 */

package com.geopista.app.contaminantes.panel;

import com.geopista.protocol.contaminantes.Vertedero;
import com.geopista.app.utilidades.TextPane;
import com.geopista.app.utilidades.JNumberTextField;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 *
 * @author  angeles
 */
public class JPanelVertedero extends javax.swing.JPanel {
    public static Logger logger= Logger.getLogger(JPanelVertedero.class);
   private ResourceBundle messages;
    private Vertedero vertedero;
    private Frame parent;
    private boolean modoEdicion=false;
    /** Creates new form JPanelVertedero */
    public JPanelVertedero(Frame parent, ResourceBundle messages)  {
        this.parent=parent;
        this.messages=messages;
        initComponents();
        changeScreenLang(messages);
    }

    public Vertedero getVertedero() {
        return vertedero;
    }

    public void setVertedero(Vertedero vertedero) {
        this.vertedero = vertedero;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jLabelTipo = new javax.swing.JLabel();
        jTextFieldTipo = new com.geopista.app.utilidades.TextField(50);
        jLabelTitularidad = new javax.swing.JLabel();
        jTextFieldTitularidad = new com.geopista.app.utilidades.TextField(50);
        jLabelGestion = new javax.swing.JLabel();
        jTextFieldGestion = new com.geopista.app.utilidades.TextField(50);
        jLabelProblemas = new javax.swing.JLabel();
        jScrollPaneProblemas = new javax.swing.JScrollPane();
        jTextPaneProblemas = new com.geopista.app.utilidades.TextPane(1000);
        jLabelCapacidad = new javax.swing.JLabel();
        jTextFieldCapacidad = new JNumberTextField(JNumberTextField.NUMBER, new Integer(999999999));
        jLabelOcupacion = new javax.swing.JLabel();
        jTextFieldOcupacion= new JNumberTextField(JNumberTextField.REAL, new Float(999999));
        jLabelAmpliacion = new javax.swing.JLabel();
        jTextFieldAmpliacion = new com.geopista.app.utilidades.TextField(50);
        jLabelConservacion = new javax.swing.JLabel();
        jTextFieldConservacion = new com.geopista.app.utilidades.TextField(50);
        jLabelVidaUtil = new javax.swing.JLabel();
        jTextFieldVidaUtil = new JNumberTextField(JNumberTextField.NUMBER, new Integer(9999));
        jButtonResiduos = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        add(jLabelTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, -1));
        add(jTextFieldTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 320, -1));
        add(jLabelTitularidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, -1));
        add(jTextFieldTitularidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 320, -1));
        add(jLabelGestion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 130, -1));
        add(jTextFieldGestion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 260, -1));
        add(jLabelProblemas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 270, -1));
        jScrollPaneProblemas.setViewportView(jTextPaneProblemas);
        add(jScrollPaneProblemas, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 260, 70));
        add(jLabelCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 90, -1));
        add(jTextFieldCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 80, -1));
        add(jLabelOcupacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 120, -1));
        add(jTextFieldOcupacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 100, -1));
        add(jLabelAmpliacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 140, -1));
        add(jTextFieldAmpliacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 260, -1));
        add(jLabelConservacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 140, -1));
        add(jTextFieldConservacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 260, -1));
        add(jLabelVidaUtil, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 140, -1));
        add(jTextFieldVidaUtil, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 60, -1));
        add(jButtonResiduos, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 270, 100, -1));
        jButtonResiduos.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        mostrarResiduos();
                                    }
                                });
        setEnabled(false);

    }//GEN-END:initComponents

    public boolean isModoEdicion() {
        return modoEdicion;
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }



    public void changeScreenLang(ResourceBundle messages) {
        try
        {
            this.messages=messages;
            jButtonResiduos.setText(messages.getString("JPanelVertedero.jButtonResiduos"));//"Residuos");
            jLabelVidaUtil.setText(messages.getString("JPanelVertedero.jLabelVidaUtil"));//"Vida util:");
            jLabelConservacion.setText(messages.getString("JPanelVertedero.jLabelConservacion"));//"Estado de conservaci\u00f3n:");
            jLabelTipo.setText(messages.getString("JPanelVertedero.jLabelTipo"));//"Tipo:");
            jLabelTitularidad.setText(messages.getString("JPanelVertedero.jLabelTitularidad"));//"Titularidad:");
            jLabelGestion.setText(messages.getString("JPanelVertedero.jLabelGestion"));//"Gesti\u00f3n administrativa:");
            jLabelProblemas.setText(messages.getString("JPanelVertedero.jLabelProblemas"));//"Problemas existentes:");
            jLabelCapacidad.setText(messages.getString("JPanelVertedero.jLabelCapacidad"));//"Capacidad:");
            jLabelOcupacion.setText(messages.getString("JPanelVertedero.jLabelOcupacion"));//"Grado de ocupaci\u00f3n:");
            jLabelAmpliacion.setText(messages.getString("JPanelVertedero.jLabelAmpliacion"));//"Posibilidad de ampliaci\u00f3n:");

            jButtonResiduos.setToolTipText(messages.getString("JPanelVertedero.jButtonResiduos"));
        }catch(Exception e)
        {
            logger.error("Error al cargar las etiquetas: ",e);
        }
   }
    public void load(Vertedero verte)
   {
        if (verte==null) verte=new Vertedero();
        if(verte.getCapacidad()!=null)
            jTextFieldCapacidad.setNumber(verte.getCapacidad());
        else
            jTextFieldCapacidad.setText("");

        if(verte.getgOcupacion()!=null)
                jTextFieldOcupacion.setNumber(verte.getgOcupacion());
        else
                jTextFieldOcupacion.setText("");
        jTextFieldConservacion.setText(verte.getEstado()==null?"":verte.getEstado());
        if(verte.getVidaUtil()!=null)
              jTextFieldVidaUtil.setNumber(verte.getVidaUtil());
        else
            jTextFieldVidaUtil.setText("");
        jTextFieldTipo.setText(verte.getTipo()==null?"":verte.getTipo());
        jTextFieldTitularidad.setText(verte.getTitularidad()==null?"":verte.getTitularidad());
        jTextFieldGestion.setText(verte.getgAdm()==null?"":verte.getgAdm());
        jTextFieldAmpliacion.setText(verte.getPosiAmplia()==null?"":verte.getPosiAmplia());
        jTextPaneProblemas.setText(verte.getpExistentes());
        vertedero=(Vertedero)verte.clone();
    }
     public void guardarCambios()
   {
       try
       {
        if (vertedero==null) vertedero=new Vertedero();
        if(jTextFieldCapacidad.getText().length()<=0)
             vertedero.setCapacidad(null);
        else
             vertedero.setCapacidad(jTextFieldCapacidad.getNumber()==null?null:new Long(jTextFieldCapacidad.getNumber().longValue()));
        if (jTextFieldOcupacion.getText().length()<=0)
            vertedero.setgOcupacion(null);
        else
            vertedero.setgOcupacion(jTextFieldOcupacion.getNumber()==null?null:new Float(jTextFieldOcupacion.getNumber().floatValue()));
        vertedero.setEstado(jTextFieldConservacion.getText());
        if (jTextFieldVidaUtil.getText().length()<=0)
            vertedero.setVidaUtil(null);
        else
            vertedero.setVidaUtil(jTextFieldVidaUtil.getNumber()==null?null: new Integer(jTextFieldVidaUtil.getNumber().intValue()));
        vertedero.setTipo(jTextFieldTipo.getText());
        vertedero.setTitularidad(jTextFieldTitularidad.getText());
        vertedero.setgAdm(jTextFieldGestion.getText());
        vertedero.setpExistentes(jTextPaneProblemas.getText());
        vertedero.setPosiAmplia(jTextFieldAmpliacion.getText());
       }
        catch(Exception e)
       {
           logger.error("Error al guardar los cambios del vertedero");
       }

   }
    public boolean validar()
    {
        return true;
    }
    private void mostrarResiduos()
    {
         if (vertedero==null)
        {
              JOptionPane optionPane= new JOptionPane(messages.getString("JFrameVertedero.mensaje.vernoseleccionado"),JOptionPane.INFORMATION_MESSAGE);
              JDialog dialog =optionPane.createDialog(this,"INFO");
              dialog.show();
              return;
        }

        JDialogResiduos dialogResiduos = new JDialogResiduos(this.parent, true, messages,vertedero.getResiduos(),modoEdicion);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        dialogResiduos.setSize(475,525);
        dialogResiduos.setLocation(d.width/2 - dialogResiduos.getSize().width/2, d.height/2 - dialogResiduos.getSize().height/2);
        dialogResiduos.setResizable(false);
        dialogResiduos.show();
        dialogResiduos=null;
      }


    public void setEnabled(boolean bValor)
    {
        modoEdicion=bValor;
        jTextFieldCapacidad.setEditable(bValor);
        jTextFieldOcupacion.setEditable(bValor);
        jTextFieldConservacion.setEditable(bValor);
        jTextFieldVidaUtil.setEditable(bValor);
        jTextFieldTipo.setEditable(bValor);
        jTextFieldTitularidad.setEditable(bValor);
        jTextFieldGestion.setEditable(bValor);
        jTextFieldAmpliacion.setEditable(bValor);
        jTextPaneProblemas.setEnabled(bValor);
        jTextPaneProblemas.setBackground(!bValor?Color.LIGHT_GRAY:Color.WHITE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonResiduos;
    private javax.swing.JLabel jLabelAmpliacion;
    private javax.swing.JLabel jLabelCapacidad;
    private javax.swing.JLabel jLabelConservacion;
    private javax.swing.JLabel jLabelGestion;
    private javax.swing.JLabel jLabelOcupacion;
    private javax.swing.JLabel jLabelProblemas;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JLabel jLabelTitularidad;
    private javax.swing.JLabel jLabelVidaUtil;
    private javax.swing.JScrollPane jScrollPaneProblemas;
    private JNumberTextField jTextFieldCapacidad;
    private JNumberTextField jTextFieldOcupacion;
    private com.geopista.app.utilidades.TextField jTextFieldConservacion;
    private JNumberTextField jTextFieldVidaUtil;
    private com.geopista.app.utilidades.TextField jTextFieldTipo;
    private com.geopista.app.utilidades.TextField jTextFieldTitularidad;
    private com.geopista.app.utilidades.TextField jTextFieldGestion;
    private com.geopista.app.utilidades.TextField jTextFieldAmpliacion;
    private com.geopista.app.utilidades.TextPane jTextPaneProblemas;
    // End of variables declaration//GEN-END:variables
    
}
