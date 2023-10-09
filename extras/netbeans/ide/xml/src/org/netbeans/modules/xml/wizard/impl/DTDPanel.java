/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.xml.wizard.impl;

import org.netbeans.modules.xml.wizard.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;

import org.netbeans.modules.xml.util.Util;
import org.openide.nodes.Node;
import org.openide.nodes.NodeOperation;

import org.xml.sax.*;

/**
 * Gathers data for DTD driven XML document instantionation.
 *
 * @author  Petr Kuzel
 * @version it does not work well, no PIDs no roots, ...
 */
public class DTDPanel extends AbstractPanel {
    /** Serial Version UID */
    private static final long serialVersionUID = 5310047495162425192L;    
    
    
    /** Creates new form DTDPanel */
    public DTDPanel() {
        initComponents();
        initAccessibility();
        
        // attach focus listener to editor directly
        Component editor = pidComboBox.getEditor().getEditorComponent();
        editor.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (existsPID()) updatePossibilities();
            }
        });
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        descTextArea = new javax.swing.JTextArea();
        pidLabel = new javax.swing.JLabel();
        pidComboBox = new javax.swing.JComboBox();
        catalogButton = new javax.swing.JButton();
        sidLabel = new javax.swing.JLabel();
        sidComboBox = new javax.swing.JComboBox();
        rootLabel = new javax.swing.JLabel();
        rootComboBox = new javax.swing.JComboBox();
        fillPanel = new javax.swing.JPanel();

        setName(Util.THIS.getString(DTDPanel.class, "PROP_dtd_panel_name")); // NOI18N
        setLayout(new java.awt.GridBagLayout());

        descTextArea.setEditable(false);
        descTextArea.setLineWrap(true);
        descTextArea.setText(Util.THIS.getString(DTDPanel.class, "MSG_dtd_panel_desc")); // NOI18N
        descTextArea.setWrapStyleWord(true);
        descTextArea.setDisabledTextColor(descTextArea.getForeground());
        descTextArea.setEnabled(false);
        descTextArea.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        add(descTextArea, gridBagConstraints);

        pidLabel.setLabelFor(pidComboBox);
        pidLabel.setText(Util.THIS.getString(DTDPanel.class, "PROP_dtd_pid_name")); // NOI18N
        pidLabel.setToolTipText("null");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 12);
        add(pidLabel, gridBagConstraints);

        pidComboBox.setEditable(true);
        pidComboBox.setToolTipText("null");
        pidComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pidComboBoxActionPerformed(evt);
            }
        });
        pidComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                pidComboBoxFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        add(pidComboBox, gridBagConstraints);

        catalogButton.setText(Util.THIS.getString(DTDPanel.class, "PROP_dtd_catalog_name")); // NOI18N
        catalogButton.setToolTipText("null");
        catalogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catalogButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        add(catalogButton, gridBagConstraints);

        sidLabel.setLabelFor(sidComboBox);
        sidLabel.setText(Util.THIS.getString(DTDPanel.class, "PROP_dtd_sid_name")); // NOI18N
        sidLabel.setToolTipText("null");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 12);
        add(sidLabel, gridBagConstraints);

        sidComboBox.setEditable(true);
        sidComboBox.setToolTipText("null");
        sidComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidComboBoxActionPerformed(evt);
            }
        });
        sidComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                sidComboBoxFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        add(sidComboBox, gridBagConstraints);

        rootLabel.setLabelFor(rootComboBox);
        rootLabel.setText(Util.THIS.getString(DTDPanel.class, "PROP_dtd_root_name")); // NOI18N
        rootLabel.setToolTipText("null");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 12);
        add(rootLabel, gridBagConstraints);

        rootComboBox.setEditable(true);
        rootComboBox.setToolTipText("null");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        add(rootComboBox, gridBagConstraints);

        fillPanel.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(fillPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void catalogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catalogButtonActionPerformed
        Node catalog = getCatalogNode();
        if (catalog == null) return;
        NodeOperation.getDefault().explore(catalog);
    }//GEN-LAST:event_catalogButtonActionPerformed

    private void initAccessibility() {
        
        Util util = Util.THIS;
        rootLabel.setDisplayedMnemonic(util.getChar(DTDPanel.class, "PROP_dtd_rootLabel_mne"));
        pidLabel.setDisplayedMnemonic(util.getChar(DTDPanel.class, "PROP_dtd_pidLabel_mne"));
        sidLabel.setDisplayedMnemonic(util.getChar(DTDPanel.class, "PROP_dtd_sidLabel_mne"));
        catalogButton.setMnemonic(util.getChar(DTDPanel.class, "PROP_dtd_catalog_mne"));
        getAccessibleContext().setAccessibleDescription(descTextArea.getText());
    }
    
    private void sidComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidComboBoxActionPerformed
        // ignore listing
        if (sidComboBox.isPopupVisible()) return;
        if (existsPID() == false) updatePossibilities();
    }//GEN-LAST:event_sidComboBoxActionPerformed

    private void pidComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pidComboBoxActionPerformed
        // ignore listing
        if (pidComboBox.isPopupVisible()) return;
        
        if (existsPID()) updatePossibilities();
    }//GEN-LAST:event_pidComboBoxActionPerformed

    private void pidComboBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pidComboBoxFocusLost
        if (existsPID()) updatePossibilities();
    }//GEN-LAST:event_pidComboBoxFocusLost

    private void sidComboBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sidComboBoxFocusLost
        if (existsPID() == false) updatePossibilities();
    }//GEN-LAST:event_sidComboBoxFocusLost

    /**
     * Try to access catalog management node or <code>null</code>.
     */
    private Node getCatalogNode() {

//        TopManager top = TopManager.getDefault();
//        Node runtime = top.getPlaces().nodes().environment();
//        Children children = runtime.getChildren();
//        Enumeration en = children.nodes();
//
//        while (en.hasMoreElements()) {
//            Node next = (Node) en.nextElement();
//            //!!! it is undeclared dependency on catalog module
//            if ("XML-CATALOG".equals(next.getName())) {                         // NOI18N
//                return new FilterNode(next);
//            }
//        }

        return null;
    }

    // does user entered a PID
    private boolean existsPID() {
        Object pid = pidModel.getSelectedItem();
        return (pid instanceof String)
               && (((String) pid).trim().equals("") == false);
    }

    private void updatePossibilities() {
        //??? we are in AWT parse in separate task

        Util.THIS.debug("Updating possible roots (DTD)...");

        InputSource in = new InputSource();

        Object sid = sidModel.getSelectedItem();
        String systemId = null;
        try {
            if (sid != null) {
                systemId = sid.toString();
                URL context = model.getTargetFolderURL();
                if (context != null) {
                    systemId = new URL(context, systemId).toExternalForm();
                }
            }
        } catch (MalformedURLException ex) {
            // ignore it use one passes by user
        }
        in.setSystemId(systemId);

        Object pid = pidModel.getSelectedItem();
        in.setPublicId( pid == null ? null : pid.toString() );

        Util.THIS.debug("PID: " + pid + ", SID:" + sid);
        Set roots = new DTDParser().parse(in);

        if (roots.size() > 0) {
            rootModel.removeAllElements();
            Iterator it = roots.iterator();
            while (it.hasNext()) {
                String next = (String) it.next();
                rootModel.addElement(next);
            }
        }

        // select suggested text (do not focus it)
        Component editor = rootComboBox.getEditor().getEditorComponent();
        if (editor instanceof JTextComponent) {
            ((JTextComponent)editor).selectAll();
        }
    }

    /** User just entered the panel, init view by model values
     */
    protected void initView() {
        
        // fetch catalogs etc.
        String[] pids = Util.getKnownDTDPublicIDs();
        pidModel = new DefaultComboBoxModel(pids);
        sidModel = new DefaultComboBoxModel(recentSIDs);
        rootModel = new DefaultComboBoxModel();
        
        pidComboBox.setModel(pidModel);
        pidComboBox.getEditor().selectAll();

        if (true /* #23966 */ || getCatalogNode() == null) {
            catalogButton.setVisible(false);
            // !!! gridwith mus be set somehow to remaider!
        }
        
        // set models
        rootComboBox.setModel(rootModel);
        
        sidComboBox.setModel(sidModel);       
        
    }    
    
    /** User just leaved the panel, update model
     */
    protected void updateModel() {
        Object pid = pidModel.getSelectedItem();
        model.setPublicID( pid == null ? null : pid.toString() );
        
        Object sid = sidModel.getSelectedItem();
        model.setSystemID(sid == null ? null : sid.toString());
        
        Object root = rootModel.getSelectedItem();
        model.setRoot(root == null ? null : root.toString());

        Util.THIS.debug("Model updated PID: " + pid + " SID: " + sid + " root: " + root);
        
        // update recent
        
        if (recentSIDs.contains(sid) == false) {
            recentSIDs.add(sid);
        }
    }    
    
    /** User just reentered the panel.
     */
    protected void updateView() {
    }

    private DefaultComboBoxModel rootModel;
    private DefaultComboBoxModel pidModel;
    private DefaultComboBoxModel sidModel;
    
    private static Vector recentSIDs = new Vector();
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton catalogButton;
    private javax.swing.JTextArea descTextArea;
    private javax.swing.JPanel fillPanel;
    private javax.swing.JComboBox pidComboBox;
    private javax.swing.JLabel pidLabel;
    private javax.swing.JComboBox rootComboBox;
    private javax.swing.JLabel rootLabel;
    private javax.swing.JComboBox sidComboBox;
    private javax.swing.JLabel sidLabel;
    // End of variables declaration//GEN-END:variables

}