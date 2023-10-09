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

package org.netbeans.modules.cloud.amazon.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.modules.cloud.amazon.AmazonInstance;
import org.netbeans.modules.cloud.amazon.AmazonRegion;
import org.openide.util.NbBundle;

/**
 *
 */
public class AmazonWizardComponent extends javax.swing.JPanel implements DocumentListener {

    private AmazonWizardPanel panel;
    private List<AmazonRegion> regions;
    
    /** Creates new form AmazonWizardComponent */
    public AmazonWizardComponent(AmazonWizardPanel panel, AmazonInstance ai) {
        this.panel = panel;
        initComponents();
        initRegions();
        jRegionComboBox.setModel(new DefaultComboBoxModel(regions.toArray()));
        setName(NbBundle.getBundle(AmazonWizardComponent.class).getString("LBL_Name")); // NOI18N
        if (ai != null) {
            accessKey.setText(ai.getKeyId());
            secret.setText(ai.getKey());
            accessKey.setEditable(false);
            secret.setEditable(false);
            jRegionComboBox.setEnabled(false);
            jRegionComboBox.setSelectedItem(AmazonRegion.findRegion(ai.getRegionURL()));
        }
        accessKey.getDocument().addDocumentListener(this);
        secret.getDocument().addDocumentListener(this);
    }
    
    private void initRegions() {
        regions = new ArrayList<AmazonRegion>(Arrays.asList(AmazonRegion.values()));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        accessKey = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        secret = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jRegionComboBox = new javax.swing.JComboBox();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(AmazonWizardComponent.class, "AmazonWizardComponent.jLabel1.text")); // NOI18N

        accessKey.setText(org.openide.util.NbBundle.getMessage(AmazonWizardComponent.class, "AmazonWizardComponent.accessKey.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(AmazonWizardComponent.class, "AmazonWizardComponent.jLabel2.text")); // NOI18N

        secret.setText(org.openide.util.NbBundle.getMessage(AmazonWizardComponent.class, "AmazonWizardComponent.secret.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(AmazonWizardComponent.class, "AmazonWizardComponent.jLabel3.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(secret, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(accessKey, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(jRegionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRegionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accessKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(secret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accessKey;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox jRegionComboBox;
    private javax.swing.JPasswordField secret;
    // End of variables declaration//GEN-END:variables

    public String getKeyId() {
        return accessKey.getText();
    }
    
    public String getKey() {
        return String.valueOf(secret.getPassword());
    }

    public String getRegionUrl() {
        return ((AmazonRegion)jRegionComboBox.getSelectedItem()).getUrl();
    }
    
    public String getRegionCode() {
        return ((AmazonRegion)jRegionComboBox.getSelectedItem()).getCode();
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (panel != null) {
            panel.fireChange();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (panel != null) {
            panel.fireChange();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (panel != null) {
            panel.fireChange();
        }
    }
}