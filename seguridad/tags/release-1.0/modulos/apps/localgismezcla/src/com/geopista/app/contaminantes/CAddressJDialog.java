package com.geopista.app.contaminantes;


/**
 * The GEOPISTA project is a set of tools and applications to manage
 * geographical data for local administrations.
 *
 * Copyright (C) 2004 INZAMAC-SATEC UTE
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
 USA.
 *
 * For more information, contact:
 *
 *
 * www.geopista.com
 *
 *
 */

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Hashtable;
import java.util.Vector;
import java.util.ResourceBundle;
import java.io.StringWriter;
import java.io.PrintWriter;

import com.geopista.protocol.licencias.COperacionesLicencias;
import com.geopista.protocol.licencias.CReferenciaCatastral;

/**
 * @author avivar
 */
public class CAddressJDialog extends javax.swing.JDialog {
	Logger logger = Logger.getLogger(com.geopista.app.contaminantes.CAddressJDialog.class);
    private Hashtable valoresBusqueda=new Hashtable();
    ResourceBundle messages;

	/**
	 * Creates new form JSearch
	 */
	public CAddressJDialog(java.awt.Frame parent, boolean modal, ResourceBundle messages) {
		super(parent, modal);
        this.messages = messages;

		initComponents();
		configureComponents();
	}

	private void configureComponents() {

		addressesJTableModel = new DefaultTableModel(new String[]{"ID", "Tipo v�a", "Nombre v�a", "N�", "Letra", "Bloque", "Esc.", "Planta", "Puerta"}, 0);
		addressesJTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		addressesJTable.setRowSelectionAllowed(true);
		addressesJTable.setColumnSelectionAllowed(false);
		addressesJTable.setModel(addressesJTableModel);

		renombrarComponentes();
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents() {//GEN-BEGIN:initComponents
		jPanel1 = new javax.swing.JPanel();
		aceptarJButton = new javax.swing.JButton();
		cancelarJButton = new javax.swing.JButton();
		buscarJButton = new javax.swing.JButton();
		addressesJScrollPane = new javax.swing.JScrollPane();
		addressesJTable = new javax.swing.JTable();
		nombreViaJLabel = new javax.swing.JLabel();
		numeroViaJLabel = new javax.swing.JLabel();
		numeroViaJTField = new javax.swing.JTextField();
		nombreViaJTextField = new javax.swing.JTextField();
		cPostalJLabel = new javax.swing.JLabel();
		cPostalJTField = new javax.swing.JTextField();

		getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Di\u00e1logo de busqueda");
		jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jPanel1.setBorder(new javax.swing.border.EtchedBorder());
		aceptarJButton.setText("Aceptar");
		aceptarJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aceptarJButtonActionPerformed(evt);
			}
		});

		jPanel1.add(aceptarJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, -1, -1));

		cancelarJButton.setText("Cancelar");
		cancelarJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelarJButtonActionPerformed(evt);
			}
		});

		jPanel1.add(cancelarJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 320, -1, -1));

		buscarJButton.setText("Buscar");
		buscarJButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buscarJButtonActionPerformed(evt);
			}
		});

		jPanel1.add(buscarJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, -1, -1));

		addressesJTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{

		},
				new String[]{
					"Tipo v�a", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Nombre v�a", "N�"
				}) {
			Class[] types = new Class[]{
				java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
			};
			boolean[] canEdit = new boolean[]{
				false, true, true, true, true, true, true, false, false
			};

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		addressesJTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				addressesJTableMouseClicked(evt);
			}
		});

		addressesJScrollPane.setViewportView(addressesJTable);

		jPanel1.add(addressesJScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 680, 170));

		nombreViaJLabel.setText("Nombre v\u00eda:");
		jPanel1.add(nombreViaJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 20));

		numeroViaJLabel.setText("N\u00famero:");
		jPanel1.add(numeroViaJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 200, 20));

		jPanel1.add(numeroViaJTField, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 100, -1));

		jPanel1.add(nombreViaJTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 400, -1));

		cPostalJLabel.setText("C\u00f3digo postal:");
		jPanel1.add(cPostalJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 130, 20));

		jPanel1.add(cPostalJTField, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 100, -1));

		getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 350));

		pack();
	}//GEN-END:initComponents

	private void addressesJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addressesJTableMouseClicked

		try {
		} catch (Exception ex) {

			logger.error("Exception: " + ex.toString());
		}

	}//GEN-LAST:event_addressesJTableMouseClicked

	private void buscarJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarJButtonActionPerformed


		try {

			int count = addressesJTableModel.getRowCount();
			for (int i = 0; i < count; i++) {
				addressesJTableModel.removeRow(0);
			}
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			Hashtable hash = new Hashtable();
			hash.put("b.nombrecatastro", nombreViaJTextField.getText());
			hash.put("a.rotulo", numeroViaJTField.getText());

			Vector referenciasCatastrales = COperacionesLicencias.getSearchedAddressesByNumPolicia(hash);
			logger.info("referenciasCatastrales.size(): " + referenciasCatastrales.size());

			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			for (int i = 0; i < referenciasCatastrales.size(); i++) {
				CReferenciaCatastral referenciaCatastral = (CReferenciaCatastral) referenciasCatastrales.elementAt(i);

				//"Ref.", "Tipo v�a", "Nombre v�a", "N�", "Letra","Bloque","Esc.","Planta","Puerta"
				addressesJTableModel.addRow(new String[]{referenciaCatastral.getReferenciaCatastral(),
														 referenciaCatastral.getTipoVia(),
														 referenciaCatastral.getNombreVia(),
														 referenciaCatastral.getPrimerNumero(),
														 referenciaCatastral.getPrimeraLetra(),
														 referenciaCatastral.getBloque(),
														 referenciaCatastral.getEscalera(),
														 referenciaCatastral.getPlanta(),
														 referenciaCatastral.getPuerta()});

			}
		} catch (Exception ex) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			logger.error("Exception: " + ex.toString());
		}

	}//GEN-LAST:event_buscarJButtonActionPerformed

	private void cancelarJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarJButtonActionPerformed

		dispose();

	}//GEN-LAST:event_cancelarJButtonActionPerformed

	private void aceptarJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarJButtonActionPerformed


		for (int i = 0; i < addressesJTable.getSelectedRows().length; i++) {
			int selectedRow = addressesJTable.getSelectedRows()[i];

			CReferenciaCatastral referenciaCatastral = new CReferenciaCatastral((String) addressesJTableModel.getValueAt(selectedRow, 0),
					(String) addressesJTableModel.getValueAt(selectedRow, 1),
					(String) addressesJTableModel.getValueAt(selectedRow, 2),
					(String) addressesJTableModel.getValueAt(selectedRow, 3),
					(String) addressesJTableModel.getValueAt(selectedRow, 4),
					(String) addressesJTableModel.getValueAt(selectedRow, 5),
					(String) addressesJTableModel.getValueAt(selectedRow, 6),
					(String) addressesJTableModel.getValueAt(selectedRow, 7),
					(String) addressesJTableModel.getValueAt(selectedRow, 8));


			if (valoresBusqueda==null){valoresBusqueda= new Hashtable();}
            valoresBusqueda.put(referenciaCatastral.getReferenciaCatastral(),referenciaCatastral);
			//valoresBusqueda.put(addressesJTableModel.getValueAt(selectedRow, 0),referenciaCatastral);
		}
		dispose();
	}//GEN-LAST:event_aceptarJButtonActionPerformed

    public Hashtable getValoresBusqueda() {
        return valoresBusqueda;
    }

    public void setValoresBusqueda(Hashtable valoresBusqueda) {
        this.valoresBusqueda = valoresBusqueda;
    }


	private DefaultTableModel addressesJTableModel;
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton aceptarJButton;
	private javax.swing.JScrollPane addressesJScrollPane;
	private javax.swing.JTable addressesJTable;
	private javax.swing.JButton buscarJButton;
	private javax.swing.JLabel cPostalJLabel;
	private javax.swing.JTextField cPostalJTField;
	private javax.swing.JButton cancelarJButton;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel nombreViaJLabel;
	private javax.swing.JTextField nombreViaJTextField;
	private javax.swing.JLabel numeroViaJLabel;
	private javax.swing.JTextField numeroViaJTField;
	// End of variables declaration//GEN-END:variables


	private void renombrarComponentes() {

		try {

			setTitle(messages.getString("CAddressJDialog.JInternalFrame.title"));

			nombreViaJLabel.setText(messages.getString("CAddressJDialog.nombreViaJLabel.text"));
			numeroViaJLabel.setText(messages.getString("CAddressJDialog.numeroViaJLabel.text"));
			cPostalJLabel.setText(messages.getString("CAddressJDialog.cPostalJLabel.text"));

			buscarJButton.setText(messages.getString("CAddressJDialog.buscarJButton.text"));
			cancelarJButton.setText(messages.getString("CAddressJDialog.cancelarJButton.text"));
			aceptarJButton.setText(messages.getString("CAddressJDialog.aceptarJButton.text"));


			addressesJTableModel = new DefaultTableModel
                    (new String[]{
                        messages.getString("CAddressJDialog.addressesJTableModel.text.column1"),
                        messages.getString("CAddressJDialog.addressesJTableModel.text.column2"),
                        messages.getString("CAddressJDialog.addressesJTableModel.text.column3"),
                        messages.getString("CAddressJDialog.addressesJTableModel.text.column4"),
                        messages.getString("CAddressJDialog.addressesJTableModel.text.column5"),
                        messages.getString("CAddressJDialog.addressesJTableModel.text.column6"),
                        messages.getString("CAddressJDialog.addressesJTableModel.text.column7"),
                        messages.getString("CAddressJDialog.addressesJTableModel.text.column8"),
                        messages.getString("CAddressJDialog.addressesJTableModel.text.column9")}, 0);
			addressesJTable.setModel(addressesJTableModel);


		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			logger.error("Exception: " + sw.toString());
		}
	}


}