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

package com.vividsolutions.jump.workbench.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.geopista.app.AppContext;
import com.geopista.editor.WorkBench;
import com.geopista.editor.WorkbenchGuiComponent;
import com.vividsolutions.jts.util.Assert;
import com.vividsolutions.jump.util.Blackboard;

public class OptionsDialog extends JDialog {

    static AppContext appContext = (AppContext) AppContext.getApplicationContext();
    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private OKCancelPanel okCancelPanel = new OKCancelPanel();
    private JTabbedPane tabbedPane = new JTabbedPane();

    private OptionsDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
        } catch (Exception ex) {
            Assert.shouldNeverReachHere(ex.getMessage());
        }

        addComponentListener(new ComponentAdapter() {            
            public void componentShown(ComponentEvent e) {
                fireInit();
            }
        });

        okCancelPanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (okCancelPanel.wasOKPressed()) {
                    String errorMessage = validateInput();

                    if (errorMessage != null) {
                        JOptionPane.showMessageDialog(
                            OptionsDialog.this,
                            errorMessage,
                            AppContext.getMessage("GeopistaName"),
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        fireOKPressed();
                        setVisible(false);
                        return;
                    }
                }
                setVisible(false);
            }
        });
    }

    public static OptionsDialog instance(WorkBench workbench) {
        return instance(workbench.getBlackboard(), (WorkbenchGuiComponent) workbench.getGuiComponent());
    }

    public static OptionsDialog instance(Blackboard blackboard, WorkbenchGuiComponent frame) {
        if (blackboard.get(OptionsDialog.class +" - INSTANCE") == null) {
            return (OptionsDialog) blackboard.get(
                        OptionsDialog.class +" - INSTANCE",
                        new OptionsDialog(AppContext.getApplicationContext().getMainFrame(), appContext.getI18nString("Options"), true));    
        }
        return (OptionsDialog) blackboard.get(OptionsDialog.class +" - INSTANCE");        
    }

    private void fireOKPressed() {
        for (Iterator i = optionsPanels().iterator(); i.hasNext();) {
            OptionsPanel panel = (OptionsPanel) i.next();
            panel.okPressed();
        }
    }

    private void fireInit() {
        for (Iterator i = optionsPanels().iterator(); i.hasNext();) {
            OptionsPanel panel = (OptionsPanel) i.next();
            panel.init();
        }
    }

    private Collection optionsPanels() {
        ArrayList optionsPanels = new ArrayList();

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            optionsPanels.add(tabbedPane.getComponentAt(i));
        }

        return optionsPanels;
    }

    private String validateInput() {
        for (Iterator i = optionsPanels().iterator(); i.hasNext();) {
            OptionsPanel panel = (OptionsPanel) i.next();
            String errorMessage = panel.validateInput();

            if (errorMessage != null) {
                return errorMessage;
            }
        }

        return null;
    }

    public void addTab(String title, OptionsPanel panel) {
        addTab(title, null, panel);
    }    
    
    public void addTab(String title, Icon icon, OptionsPanel panel) {
        tabbedPane.addTab(title, icon, (Component) panel);
        pack();
    }

    private void jbInit() throws Exception {
        //Ensure dialog is wide enough to prevent a second row of tabs [Jon Aquino 11/6/2003]
        JPanel strut = new JPanel();
        strut.setPreferredSize(new Dimension(580, 0));
        getContentPane().add(strut, BorderLayout.NORTH);
        panel1.setLayout(borderLayout1);        
        this.setModal(true);
        this.setTitle(appContext.getI18nString("Options"));
        getContentPane().add(panel1);
        panel1.add(tabbedPane, BorderLayout.CENTER);
        this.getContentPane().add(okCancelPanel, BorderLayout.SOUTH);
    }
    public boolean wasOKPressed() {
        return okCancelPanel.wasOKPressed();
    }

}
