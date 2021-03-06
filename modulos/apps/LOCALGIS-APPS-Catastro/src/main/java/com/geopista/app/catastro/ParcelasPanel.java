/**
 * ParcelasPanel.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.app.catastro;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ParcelasPanel extends JPanel {

  private JLabel lblID_Parcela = new JLabel();
  private JTextField txtID_Parcela = new JTextField();
  private JLabel lblReferencia_Catastral = new JLabel();
  private JTextField txtReferencia_Catastral = new JTextField();
  private JLabel lblID_Municipio = new JLabel();
  private JTextField txtID_Municipio = new JTextField();  
  private JLabel lblID_Distrito = new JLabel();
  private JTextField txtID_Distrito = new JTextField();
  private JLabel lblTipo = new JLabel();
  private JTextField txtTipo = new JTextField();
  private JLabel lblCodigo_Entidad_Menor = new JLabel();
  private JTextField txtCodigo_Entidad_Menor = new JTextField();
  private JLabel lblID_Via = new JLabel();
  private JTextField txtID_Via = new JTextField();
  private JLabel lblPrimer_Numero = new JLabel();
  private JTextField txtPrimer_Numero = new JTextField(); 
  private JLabel lblPrimera_Letra = new JLabel();
  private JTextField txtPrimera_Letra = new JTextField();  
  private JLabel lblSegundo_Numero = new JLabel();
  private JTextField txtSegundo_Numero = new JTextField();  
  private JLabel lblSegunda_Letra = new JLabel();
  private JTextField txtSegunda_Letra = new JTextField();  
  private JLabel lblKilometro = new JLabel();
  private JTextField txtKilometro = new JTextField();
  private JLabel lblBloque = new JLabel();
  private JTextField txtBloque = new JTextField();  
  private JLabel lblDireccion_No_Estructurada = new JLabel();
  private JTextField txtDireccion_No_Estructurada = new JTextField(); 
  private JLabel lblCodigo_Postal = new JLabel();
  private JTextField txtCodigo_Postal = new JTextField();
  private JLabel lblSuperficie_Solar = new JLabel();  
  private JTextField txtSuperficie_Solar = new JTextField();
  private JLabel lblSuperficie_Construida_Total = new JLabel();
  private JTextField txtSuperficie_Construida_Total = new JTextField();  
  private JLabel lblSuperficie_Const_SobreRasante = new JLabel();
  private JTextField txtSuperficie_Const_SobreRasante = new JTextField();  
  private JLabel lblSuperficie_Const_BajoRasante = new JLabel();
  private JTextField txtSuperficie_Const_BajoRasante = new JTextField();
  private JLabel lblSuperficie_Cubierta = new JLabel(); 
  private JTextField txtSuperficie_Cubierta = new JTextField();  
  private JLabel lblAnio_Aprobacion = new JLabel();
  private JTextField txtAnio_Aprobacion = new JTextField();  
  private JLabel lblCodigo_Calculo_Valor = new JLabel();
  private JTextField txtCodigo_Calculo_Valor = new JTextField();
  private JLabel lblModalidad_Reparto = new JLabel();
  private JComboBox cmbModalidad_Reparto = new JComboBox();
  private JLabel lblTipo_Movimiento = new JLabel();
  private JTextField txtTipo_Movimiento = new JTextField();
  private JLabel lblAnnoExpediente = new JLabel();
  private JTextField txtAnnoExpediente = new JTextField();
  private JLabel lblReferenciaExpediente = new JLabel();
  private JTextField txtReferenciaExpediente = new JTextField();
  private JLabel lblFecha_Alta = new JLabel();
  private JTextField txtFecha_Alta = new JTextField();
  private JLabel lblFecha_Baja = new JLabel();
  private JTextField txtFecha_Baja = new JTextField();  
  private JLabel lblArea = new JLabel();
  private JTextField txtArea = new JTextField();  
  private JLabel lblLength = new JLabel();
  private JTextField txtLength = new JTextField();  
  
  public ParcelasPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
    this.setName("Parcelas Catastrales");
    this.setLayout(null);
    this.setSize(new Dimension(500, 470));
    this.setBounds(new Rectangle(5, 10, 625, 470));

    lblID_Parcela.setText("ID Parcela:");
    lblID_Parcela.setBounds(new Rectangle(10, 5, 60, 15));
    txtID_Parcela.setBounds(new Rectangle(65, 5, 115, 20));
    lblReferencia_Catastral.setText("Referencia catastral:");
    lblReferencia_Catastral.setBounds(new Rectangle(10, 30, 105, 15));
    txtReferencia_Catastral.setBounds(new Rectangle(125, 30, 210, 20));
    lblID_Municipio.setText("Municipio:");
    lblID_Municipio.setBounds(new Rectangle(210, 5, 50, 15));
    txtID_Municipio.setBounds(new Rectangle(260, 5, 135, 20));
    lblID_Distrito.setText("ID Distrito:");
    lblID_Distrito.setBounds(new Rectangle(10, 55, 50, 20));
    txtID_Distrito.setBounds(new Rectangle(65, 55, 90, 20));
    lblTipo.setText("Tipo:");
    lblTipo.setBounds(new Rectangle(170, 55, 30, 20));
    txtTipo.setBounds(new Rectangle(200, 55, 25, 20));  
    lblCodigo_Entidad_Menor.setText("C�digo Entidad Menor:");
    lblCodigo_Entidad_Menor.setBounds(new Rectangle(255, 55, 110, 15));
    txtCodigo_Entidad_Menor.setBounds(new Rectangle(365, 55, 35, 20));    
    lblID_Via.setText("ID V�a:");
    lblID_Via.setBounds(new Rectangle(10, 85, 40, 15));    
    txtID_Via.setBounds(new Rectangle(50, 85, 50, 20));
    lblPrimer_Numero.setText("Primer N�mero:");
    lblPrimer_Numero.setBounds(new Rectangle(105, 85, 90, 15));
    txtPrimer_Numero.setBounds(new Rectangle(180, 85, 35, 20));
    lblPrimera_Letra.setText("Primera Letra:");
    lblPrimera_Letra.setBounds(new Rectangle(220, 85, 70, 15));
    txtPrimera_Letra.setBounds(new Rectangle(295, 85, 25, 20));    
    lblSegundo_Numero.setText("Segundo N�mero:");
    lblSegundo_Numero.setBounds(new Rectangle(325, 85, 90, 15));
    txtSegundo_Numero.setBounds(new Rectangle(415, 85, 35, 20));
    lblSegunda_Letra.setText("Segunda Letra:");
    lblSegunda_Letra.setBounds(new Rectangle(460, 85, 75, 20));    
    txtSegunda_Letra.setBounds(new Rectangle(540, 85, 25, 20));
    lblKilometro.setText("Kil�metro:");
    lblKilometro.setBounds(new Rectangle(10, 115, 55, 15));
    txtKilometro.setBounds(new Rectangle(60, 115, 80, 20));
    lblBloque.setText("Bloque:");
    lblBloque.setBounds(new Rectangle(150, 115, 40, 15));
    txtBloque.setBounds(new Rectangle(190, 115, 35, 20));
    lblDireccion_No_Estructurada.setText("Direccion No Estructurada:");
    lblDireccion_No_Estructurada.setBounds(new Rectangle(10, 145, 135, 15));
    txtDireccion_No_Estructurada.setBounds(new Rectangle(145, 145, 425, 20));
    lblCodigo_Postal.setText("C�digo Postal:");
    lblCodigo_Postal.setBounds(new Rectangle(240, 115, 90, 15));
    txtCodigo_Postal.setBounds(new Rectangle(310, 115, 55, 20));
    lblSuperficie_Solar.setText("Superficie Solar:");
    lblSuperficie_Solar.setBounds(new Rectangle(10, 175, 85, 20));
    txtSuperficie_Solar.setBounds(new Rectangle(100, 175, 65, 20));
    lblSuperficie_Construida_Total.setText("Superficie Total Construida:");
    lblSuperficie_Construida_Total.setBounds(new Rectangle(10, 205, 135, 15));
    txtSuperficie_Construida_Total.setBounds(new Rectangle(150, 205, 65, 20));
    lblSuperficie_Const_SobreRasante.setText("Superficie Construida Sobre Rasante:");
    lblSuperficie_Const_SobreRasante.setBounds(new Rectangle(10, 230, 185, 15));
    txtSuperficie_Const_SobreRasante.setBounds(new Rectangle(200, 230, 65, 20));    
    lblSuperficie_Const_BajoRasante.setText("Superficie Construida Bajo Rasante:");
    lblSuperficie_Const_BajoRasante.setBounds(new Rectangle(10, 255, 185, 15));
    txtSuperficie_Const_BajoRasante.setBounds(new Rectangle(200, 255, 65, 20));
    lblSuperficie_Cubierta.setText("Superficie Cubierta:");
    lblSuperficie_Cubierta.setBounds(new Rectangle(10, 275, 100, 20));
    txtSuperficie_Cubierta.setBounds(new Rectangle(120, 280, 65, 20));
    txtSuperficie_Cubierta.setSize(new Dimension(65, 20));    
    lblAnio_Aprobacion.setText("A�o Aprobaci�n:");
    lblAnio_Aprobacion.setBounds(new Rectangle(275, 175, 85, 15));
    txtAnio_Aprobacion.setBounds(new Rectangle(360, 175, 40, 20));
    lblCodigo_Calculo_Valor.setText("Forma C�lculo:");
    lblCodigo_Calculo_Valor.setBounds(new Rectangle(275, 225, 75, 15));
    txtCodigo_Calculo_Valor.setBounds(new Rectangle(360, 225, 30, 20));
    lblModalidad_Reparto.setText("Modalidad de reparto:");
    lblModalidad_Reparto.setBounds(new Rectangle(275, 250, 110, 15));    
    cmbModalidad_Reparto.setBounds(new Rectangle(275, 270, 155, 20));
    cmbModalidad_Reparto.addItem("1- Por partes iguales");
    cmbModalidad_Reparto.addItem("2- Por superficie");
    cmbModalidad_Reparto.addItem("3- Por coeficientes");
    cmbModalidad_Reparto.addItem("A- Se valora por repercusi�n");
    cmbModalidad_Reparto.addItem("B- Anula el vuelo indicado en ponencia");
    cmbModalidad_Reparto.addItem("C- Fincas infraedificadas o ruinosas");
    cmbModalidad_Reparto.addItem("N- Reparto efectuado seg�n forma de c�lculo");
    lblTipo_Movimiento.setText("Tipo de moviemiento:");
    lblTipo_Movimiento.setBounds(new Rectangle(430, 175, 110, 15));
    txtTipo_Movimiento.setBounds(new Rectangle(540, 175, 30, 20));    
    lblReferenciaExpediente.setText("Referencia Expediente:");
    lblReferenciaExpediente.setBounds(new Rectangle(10, 315, 115, 15));    
    txtReferenciaExpediente.setBounds(new Rectangle(125, 315, 110, 20));
    lblAnnoExpediente.setText("A�o Expediente:");
    lblAnnoExpediente.setBounds(new Rectangle(275, 200, 85, 15));
    txtAnnoExpediente.setBounds(new Rectangle(360, 200, 40, 20));
    lblFecha_Alta.setText("Fecha de alta:");
    lblFecha_Alta.setBounds(new Rectangle(430, 200, 85, 15));
    txtFecha_Alta.setBounds(new Rectangle(505, 200, 65, 20));
    lblFecha_Baja.setText("Fecha de baja:");
    lblFecha_Baja.setBounds(new Rectangle(430, 230, 85, 15));
    txtFecha_Baja.setBounds(new Rectangle(505, 230, 65, 20));    
    lblArea.setText("Area:");
    lblArea.setBounds(new Rectangle(465, 255, 35, 15));
    txtArea.setBounds(new Rectangle(500, 255, 70, 20));    
    lblLength.setText("Per�metro:");
    lblLength.setBounds(new Rectangle(445, 280, 55, 15));
    txtLength.setBounds(new Rectangle(500, 280, 70, 20));
    
    this.add(lblID_Parcela, null);
    this.add(txtID_Parcela, null);
    this.add(lblReferencia_Catastral, null);
    this.add(txtReferencia_Catastral, null);
    this.add(lblID_Municipio, null);
    this.add(txtID_Municipio, null);
    this.add(lblID_Distrito, null);
    this.add(txtID_Distrito, null);
    this.add(lblTipo, null);
    this.add(txtTipo, null);
    this.add(lblCodigo_Entidad_Menor, null);
    this.add(txtCodigo_Entidad_Menor, null);
    this.add(lblID_Via, null);
    this.add(txtID_Via, null);
    this.add(lblPrimer_Numero, null);
    this.add(txtPrimer_Numero, null);
    this.add(lblPrimera_Letra, null);
    this.add(txtPrimera_Letra, null);
    this.add(lblSegundo_Numero, null);
    this.add(txtSegundo_Numero, null);
    this.add(lblSegunda_Letra, null);
    this.add(txtSegunda_Letra, null);
    this.add(lblKilometro, null);
    this.add(txtKilometro, null);
    this.add(lblBloque, null);
    this.add(txtBloque, null);
    this.add(lblDireccion_No_Estructurada, null);
    this.add(txtDireccion_No_Estructurada, null);
    this.add(lblCodigo_Postal, null);
    this.add(txtCodigo_Postal, null);
    this.add(lblSuperficie_Solar, null);
    this.add(txtSuperficie_Solar, null);
    this.add(lblSuperficie_Construida_Total, null);
    this.add(txtSuperficie_Construida_Total, null);
    this.add(lblSuperficie_Const_SobreRasante, null);
    this.add(txtSuperficie_Const_SobreRasante, null);
    this.add(lblSuperficie_Const_BajoRasante, null);
    this.add(txtSuperficie_Const_BajoRasante, null);
    this.add(lblSuperficie_Cubierta, null);
    this.add(txtSuperficie_Cubierta, null);
    this.add(lblAnio_Aprobacion, null);
    this.add(txtAnio_Aprobacion, null);
    this.add(lblCodigo_Calculo_Valor, null);
    this.add(txtCodigo_Calculo_Valor, null);
    this.add(lblModalidad_Reparto, null);
    this.add(cmbModalidad_Reparto, null);
    this.add(lblTipo_Movimiento, null);
    this.add(txtTipo_Movimiento, null);
    this.add(lblAnnoExpediente, null);
    this.add(txtAnnoExpediente, null);
    this.add(lblReferenciaExpediente, null);
    this.add(txtReferenciaExpediente, null);
    this.add(lblFecha_Alta, null);
    this.add(txtFecha_Alta, null);
    this.add(lblFecha_Baja, null);
    this.add(txtFecha_Baja, null);
    this.add(lblArea, null);
    this.add(txtArea, null);
    this.add(lblLength, null);
    this.add(txtLength, null);

    
    
  }
}