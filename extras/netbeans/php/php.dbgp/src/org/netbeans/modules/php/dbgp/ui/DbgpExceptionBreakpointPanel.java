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
package org.netbeans.modules.php.dbgp.ui;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.netbeans.api.debugger.DebuggerManager;
import org.netbeans.editor.Utilities;
import org.netbeans.modules.php.dbgp.breakpoints.ExceptionBreakpoint;
import org.netbeans.modules.php.dbgp.ui.completion.ExceptionClassNbDebugEditorKit;
import org.netbeans.spi.debugger.ui.Controller;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;
import org.openide.util.Pair;

@NbBundle.Messages({
    "DbgpExceptionBreakpointPanel.EmptyExceptionName=Please enter non-empty exception name.",
})
public class DbgpExceptionBreakpointPanel extends JPanel implements Controller {
    private static final long serialVersionUID = 4967178020173186468L;

    private final JEditorPane classNameEditorPane;
    private final JScrollPane classNameScrollPane;

    public DbgpExceptionBreakpointPanel() {
        initComponents();
        Pair<JScrollPane, JEditorPane> codeCompletionEditor = createCodeCompletionEditor();
        classNameScrollPane = codeCompletionEditor.first();
        classNameEditorPane = codeCompletionEditor.second();
        classNameEditorPane.getAccessibleContext().setAccessibleName("exception class name input field"); // NOI18N
        classNameEditorPane.getAccessibleContext().setAccessibleDescription("field for input of the exception class name for the exception breakpoint"); // NOI18N
        exceptionLabel.setLabelFor(classNameScrollPane);
    }

    @Override
    public boolean cancel() {
        return true;
    }

    @Override
    public boolean ok() {
        String exceptionName = classNameEditorPane.getText();
        if (exceptionName == null || exceptionName.trim().length() == 0) {
            String msg = Bundle.DbgpExceptionBreakpointPanel_EmptyExceptionName();
            NotifyDescriptor descr = new NotifyDescriptor.Message(msg);
            DialogDisplayer.getDefault().notify(descr);
            return false;
        }
        ExceptionBreakpoint breakpoint = new ExceptionBreakpoint(exceptionName);
        DebuggerManager.getDebuggerManager().addBreakpoint(breakpoint);
        return true;
    }

    private Pair<JScrollPane, JEditorPane> createCodeCompletionEditor() {
        JComponent [] editorComponents = Utilities.createSingleLineEditor(ExceptionClassNbDebugEditorKit.MIME_TYPE);
        JScrollPane scrollPane = (JScrollPane) editorComponents[0];
        JEditorPane editorPane = (JEditorPane) editorComponents[1];
        initClassNameEditorPanel(scrollPane);
        editorPane.setText(""); // NOI18N
        scrollPane.setToolTipText(""); // NOI18N
        editorPane.setToolTipText(""); // NOI18N
        return Pair.<JScrollPane, JEditorPane>of(scrollPane, editorPane);
    }

    private void initClassNameEditorPanel(JScrollPane scrollPane) {
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        classNameEditorPanel.add(scrollPane, gridBagConstraints);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        classNameEditorPanel = new javax.swing.JPanel();
        exceptionLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(DbgpExceptionBreakpointPanel.class, "LBL_Settings"))); // NOI18N

        classNameEditorPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(exceptionLabel, org.openide.util.NbBundle.getMessage(DbgpExceptionBreakpointPanel.class, "LBL_ExceptionName")); // NOI18N
        exceptionLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        classNameEditorPanel.add(exceptionLabel, gridBagConstraints);
        exceptionLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(DbgpExceptionBreakpointPanel.class, "DbgpExceptionBreakpointPanel.exceptionLabel.AccessibleContext.accessibleName")); // NOI18N
        exceptionLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(DbgpExceptionBreakpointPanel.class, "DbgpExceptionBreakpointPanel.exceptionLabel.AccessibleContext.accessibleDescription")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(classNameEditorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(classNameEditorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        classNameEditorPanel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(DbgpExceptionBreakpointPanel.class, "A11_SettingsName")); // NOI18N

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(DbgpExceptionBreakpointPanel.class, "A11_SettingsName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel classNameEditorPanel;
    private javax.swing.JLabel exceptionLabel;
    // End of variables declaration//GEN-END:variables
}