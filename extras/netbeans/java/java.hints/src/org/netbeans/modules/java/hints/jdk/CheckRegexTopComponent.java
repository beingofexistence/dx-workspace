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
package org.netbeans.modules.java.hints.jdk;

import java.awt.Color;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

/**
 * Top component for inspecting regular expressions.
 */
@ConvertAsProperties(
        dtd = "-//org.netbeans.modules.java.hints.jdk//CheckRegex//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "CheckRegexTopComponent",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = false, position = 13000)
@ActionID(category = "Window", id = "org.netbeans.modules.java.hints.jdk.CheckRegexTopComponent")
@ActionReferences({
    @ActionReference(name = "Check Regex", path = "Menu/Window", position = 950),
    @ActionReference(path = "Shortcuts", name = "C-8")})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_CheckRegexAction",
        preferredID = "CheckRegexTopComponent"
)
@Messages({
    "CTL_CheckRegexAction=Check Regex",
    "CTL_CheckRegexTopComponent=Check Regular Expression",
    "HINT_CheckRegexTopComponent=This is a Check Regex window"
})
public final class CheckRegexTopComponent extends TopComponent {

    private static CheckRegexTopComponent instance;
    private static final String PREFERRED_ID = "CheckRegexTopComponent";
    private static boolean isStrictMatch;
    private static int Flags = 0;
    
    public CheckRegexTopComponent() {
        initComponents();
        errorLabel.setVisible(false);
        setName(Bundle.CTL_CheckRegexTopComponent());
        setToolTipText(Bundle.HINT_CheckRegexTopComponent());
        isStrictMatch = false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        flagsPopupMenu = new javax.swing.JPopupMenu();
        multilineMenuItem = new javax.swing.JCheckBoxMenuItem();
        literalMenuItem = new javax.swing.JCheckBoxMenuItem();
        dotAllMenuItem = new javax.swing.JCheckBoxMenuItem();
        caseInsensitiveMenuItem = new javax.swing.JCheckBoxMenuItem();
        commentsMenuItem = new javax.swing.JCheckBoxMenuItem();
        unixLinesMenuItem = new javax.swing.JCheckBoxMenuItem();
        canonEqMenuItem = new javax.swing.JCheckBoxMenuItem();
        unicodeCaseMenuItem = new javax.swing.JCheckBoxMenuItem();
        unicodeCharacterClassMenuItem = new javax.swing.JCheckBoxMenuItem();
        flagsButton = new javax.swing.JButton();
        javax.swing.JButton examplesButton = new javax.swing.JButton();
        strictCheckBox = new javax.swing.JCheckBox();
        verticalSplitPane = new javax.swing.JSplitPane();
        regexPanel = new javax.swing.JPanel();
        javax.swing.JLabel regexLabel = new javax.swing.JLabel();
        regexScrollPane = new javax.swing.JScrollPane();
        regexTextArea = new javax.swing.JTextArea();
        errorLabel = new javax.swing.JLabel();
        javax.swing.JPanel examplePanel = new javax.swing.JPanel();
        javax.swing.JLabel exampleLabel = new javax.swing.JLabel();
        exampleLayeredPane = new javax.swing.JLayeredPane();
        iconLabel = new javax.swing.JLabel();
        exampleScrollPane = new javax.swing.JScrollPane();
        exampleTextArea = new javax.swing.JTextArea();

        org.openide.awt.Mnemonics.setLocalizedText(multilineMenuItem, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.multilineMenuItem.text")); // NOI18N
        multilineMenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.multilineMenuItem.toolTipText")); // NOI18N
        multilineMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multilineMenuItemActionPerformed(evt);
            }
        });
        flagsPopupMenu.add(multilineMenuItem);

        org.openide.awt.Mnemonics.setLocalizedText(literalMenuItem, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.literalMenuItem.text")); // NOI18N
        literalMenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.literalMenuItem.toolTipText")); // NOI18N
        literalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                literalMenuItemActionPerformed(evt);
            }
        });
        flagsPopupMenu.add(literalMenuItem);

        org.openide.awt.Mnemonics.setLocalizedText(dotAllMenuItem, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.dotAllMenuItem.text")); // NOI18N
        dotAllMenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.dotAllMenuItem.toolTipText")); // NOI18N
        dotAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dotAllMenuItemActionPerformed(evt);
            }
        });
        flagsPopupMenu.add(dotAllMenuItem);

        org.openide.awt.Mnemonics.setLocalizedText(caseInsensitiveMenuItem, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.caseInsensitiveMenuItem.text")); // NOI18N
        caseInsensitiveMenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.caseInsensitiveMenuItem.toolTipText")); // NOI18N
        caseInsensitiveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseInsensitiveMenuItemActionPerformed(evt);
            }
        });
        flagsPopupMenu.add(caseInsensitiveMenuItem);

        org.openide.awt.Mnemonics.setLocalizedText(commentsMenuItem, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.commentsMenuItem.text")); // NOI18N
        commentsMenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.commentsMenuItem.toolTipText")); // NOI18N
        commentsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentsMenuItemActionPerformed(evt);
            }
        });
        flagsPopupMenu.add(commentsMenuItem);

        org.openide.awt.Mnemonics.setLocalizedText(unixLinesMenuItem, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.unixLinesMenuItem.text")); // NOI18N
        unixLinesMenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.unixLinesMenuItem.toolTipText")); // NOI18N
        unixLinesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unixLinesMenuItemActionPerformed(evt);
            }
        });
        flagsPopupMenu.add(unixLinesMenuItem);

        org.openide.awt.Mnemonics.setLocalizedText(canonEqMenuItem, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.canonEqMenuItem.text")); // NOI18N
        canonEqMenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.canonEqMenuItem.toolTipText")); // NOI18N
        canonEqMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canonEqMenuItemActionPerformed(evt);
            }
        });
        flagsPopupMenu.add(canonEqMenuItem);

        org.openide.awt.Mnemonics.setLocalizedText(unicodeCaseMenuItem, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.unicodeCaseMenuItem.text")); // NOI18N
        unicodeCaseMenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.unicodeCaseMenuItem.toolTipText")); // NOI18N
        unicodeCaseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unicodeCaseMenuItemActionPerformed(evt);
            }
        });
        flagsPopupMenu.add(unicodeCaseMenuItem);

        org.openide.awt.Mnemonics.setLocalizedText(unicodeCharacterClassMenuItem, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.unicodeCharacterClassMenuItem.text")); // NOI18N
        unicodeCharacterClassMenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.unicodeCharacterClassMenuItem.toolTipText")); // NOI18N
        unicodeCharacterClassMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unicodeCharacterClassMenuItemActionPerformed(evt);
            }
        });
        flagsPopupMenu.add(unicodeCharacterClassMenuItem);

        org.openide.awt.Mnemonics.setLocalizedText(flagsButton, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.flagsButton.text")); // NOI18N
        flagsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flagsButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(examplesButton, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.examplesButton.text")); // NOI18N
        examplesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                examplesButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(strictCheckBox, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.strictCheckBox.text")); // NOI18N
        strictCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.strictCheckBox.toolTipText")); // NOI18N
        strictCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                strictCheckBoxItemStateChanged(evt);
            }
        });

        verticalSplitPane.setDividerLocation(80);
        verticalSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        regexPanel.setPreferredSize(new java.awt.Dimension(517, 80));

        org.openide.awt.Mnemonics.setLocalizedText(regexLabel, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.regexLabel.text")); // NOI18N

        regexScrollPane.setMinimumSize(new java.awt.Dimension(6, 30));
        regexScrollPane.setPreferredSize(new java.awt.Dimension(164, 74));

        regexTextArea.setColumns(20);
        regexTextArea.setRows(5);
        regexTextArea.setFocusAccelerator('g');
        regexTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                regexTextAreaKeyReleased(evt);
            }
        });
        regexScrollPane.setViewportView(regexTextArea);

        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        org.openide.awt.Mnemonics.setLocalizedText(errorLabel, "dummy error message"); // NOI18N

        javax.swing.GroupLayout regexPanelLayout = new javax.swing.GroupLayout(regexPanel);
        regexPanel.setLayout(regexPanelLayout);
        regexPanelLayout.setHorizontalGroup(
            regexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(regexPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(regexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(regexScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .addGroup(regexPanelLayout.createSequentialGroup()
                        .addComponent(regexLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        regexPanelLayout.setVerticalGroup(
            regexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(regexPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(regexLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(regexScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorLabel))
        );

        verticalSplitPane.setTopComponent(regexPanel);

        org.openide.awt.Mnemonics.setLocalizedText(exampleLabel, org.openide.util.NbBundle.getMessage(CheckRegexTopComponent.class, "CheckRegexTopComponent.exampleLabel.text")); // NOI18N

        exampleLayeredPane.setMinimumSize(new java.awt.Dimension(0, 40));

        exampleScrollPane.setOpaque(false);

        exampleTextArea.setColumns(20);
        exampleTextArea.setRows(5);
        exampleTextArea.setFocusAccelerator('x');
        exampleTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                exampleTextAreaKeyReleased(evt);
            }
        });
        exampleScrollPane.setViewportView(exampleTextArea);

        exampleLayeredPane.setLayer(iconLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        exampleLayeredPane.setLayer(exampleScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout exampleLayeredPaneLayout = new javax.swing.GroupLayout(exampleLayeredPane);
        exampleLayeredPane.setLayout(exampleLayeredPaneLayout);
        exampleLayeredPaneLayout.setHorizontalGroup(
            exampleLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exampleLayeredPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(exampleLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(exampleScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
        );
        exampleLayeredPaneLayout.setVerticalGroup(
            exampleLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exampleLayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(exampleLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(exampleLayeredPaneLayout.createSequentialGroup()
                    .addComponent(exampleScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout examplePanelLayout = new javax.swing.GroupLayout(examplePanel);
        examplePanel.setLayout(examplePanelLayout);
        examplePanelLayout.setHorizontalGroup(
            examplePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(examplePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(examplePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exampleLayeredPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(examplePanelLayout.createSequentialGroup()
                        .addComponent(exampleLabel)
                        .addGap(0, 386, Short.MAX_VALUE)))
                .addContainerGap())
        );
        examplePanelLayout.setVerticalGroup(
            examplePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(examplePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exampleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exampleLayeredPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        verticalSplitPane.setRightComponent(examplePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(flagsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(examplesButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(strictCheckBox)
                .addGap(6, 6, 6))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(verticalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(verticalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(flagsButton)
                    .addComponent(examplesButton)
                    .addComponent(strictCheckBox))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void exampleTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_exampleTextAreaKeyReleased
        if (exampleTextArea.getSelectedText() == null) {
            matchPattern();
        }
    }//GEN-LAST:event_exampleTextAreaKeyReleased

    private void regexTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_regexTextAreaKeyReleased
        matchPattern();
    }//GEN-LAST:event_regexTextAreaKeyReleased

    private void strictCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_strictCheckBoxItemStateChanged
        isStrictMatch = !isStrictMatch;
        matchPattern();
    }//GEN-LAST:event_strictCheckBoxItemStateChanged

    private void flagsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flagsButtonActionPerformed
        if(flagsPopupMenu.isVisible()){
            flagsPopupMenu.setVisible(false);
        }else{
            flagsPopupMenu.show(flagsButton, 0, (int) (0 - flagsPopupMenu.getPreferredSize().getHeight()));
        }
    }//GEN-LAST:event_flagsButtonActionPerformed

    private void caseInsensitiveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caseInsensitiveMenuItemActionPerformed
        if(caseInsensitiveMenuItem.isSelected()){
            Flags |= Pattern.CASE_INSENSITIVE;
        }else{
            Flags &= ~Pattern.CASE_INSENSITIVE;
        }
        flagsPopupMenu.show(flagsButton, 0, 0 - flagsPopupMenu.getHeight());
        matchPattern();
    }//GEN-LAST:event_caseInsensitiveMenuItemActionPerformed

    private void multilineMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multilineMenuItemActionPerformed
        if(multilineMenuItem.isSelected()){
            Flags |= Pattern.MULTILINE;
        }else{
            Flags &= ~Pattern.MULTILINE;
        }
        flagsPopupMenu.show(flagsButton, 0, 0 - flagsPopupMenu.getHeight());
        matchPattern();
    }//GEN-LAST:event_multilineMenuItemActionPerformed

    private void dotAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dotAllMenuItemActionPerformed
        if(dotAllMenuItem.isSelected()){
            Flags |= Pattern.DOTALL;
        }else{
            Flags &= ~Pattern.DOTALL;
        }
        flagsPopupMenu.show(flagsButton, 0, 0 - flagsPopupMenu.getHeight());
        matchPattern();
    }//GEN-LAST:event_dotAllMenuItemActionPerformed

    private void unicodeCaseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unicodeCaseMenuItemActionPerformed
        if(unicodeCaseMenuItem.isSelected()){
            Flags |= Pattern.UNICODE_CASE;
        }else{
            Flags &= ~Pattern.UNICODE_CASE;
        }
        flagsPopupMenu.show(flagsButton, 0, 0 - flagsPopupMenu.getHeight());
        matchPattern();
    }//GEN-LAST:event_unicodeCaseMenuItemActionPerformed

    private void unicodeCharacterClassMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unicodeCharacterClassMenuItemActionPerformed
        if(unicodeCharacterClassMenuItem.isSelected()){
            Flags |= Pattern.UNICODE_CHARACTER_CLASS;
        }else{
            Flags &= ~Pattern.UNICODE_CHARACTER_CLASS;
        }
        flagsPopupMenu.show(flagsButton, 0, 0 - flagsPopupMenu.getHeight());
        matchPattern();
    }//GEN-LAST:event_unicodeCharacterClassMenuItemActionPerformed

    private void commentsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentsMenuItemActionPerformed
        if(commentsMenuItem.isSelected()){
            Flags |= Pattern.COMMENTS;
        }else{
            Flags &= ~Pattern.COMMENTS;
        }
        flagsPopupMenu.show(flagsButton, 0, 0 - flagsPopupMenu.getHeight());
        matchPattern();
    }//GEN-LAST:event_commentsMenuItemActionPerformed

    private void literalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_literalMenuItemActionPerformed
        if(literalMenuItem.isSelected()){
            Flags |= Pattern.LITERAL;
        }else{
            Flags &= ~Pattern.LITERAL;
        }
        flagsPopupMenu.show(flagsButton, 0, 0 - flagsPopupMenu.getHeight());
        matchPattern();
    }//GEN-LAST:event_literalMenuItemActionPerformed

    private void unixLinesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unixLinesMenuItemActionPerformed
        if(unixLinesMenuItem.isSelected()){
            Flags |= Pattern.UNIX_LINES;
        }else{
            Flags &= ~Pattern.UNIX_LINES;
        }
        flagsPopupMenu.show(flagsButton, 0, 0 - flagsPopupMenu.getHeight());
        matchPattern();
    }//GEN-LAST:event_unixLinesMenuItemActionPerformed

    private void canonEqMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canonEqMenuItemActionPerformed
        if(canonEqMenuItem.isSelected()){
            Flags |= Pattern.CANON_EQ;
        }else{
            Flags &= ~Pattern.CANON_EQ;
        }
        flagsPopupMenu.show(flagsButton, 0, 0 - flagsPopupMenu.getHeight());
        matchPattern();
    }//GEN-LAST:event_canonEqMenuItemActionPerformed

    private void examplesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examplesButtonActionPerformed
        
        try {
            Pattern.compile(regexTextArea.getText());
        } catch (PatternSyntaxException pse) {
            errorLabel.setText(Bundle.CheckRegexTopComponent_label_error(pse.getDescription()));
            return;
        }
        RegexExampleAction regexExampleAction = new RegexExampleAction();
        regexExampleAction.actionPerformed(evt);
    }//GEN-LAST:event_examplesButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem canonEqMenuItem;
    private javax.swing.JCheckBoxMenuItem caseInsensitiveMenuItem;
    private javax.swing.JCheckBoxMenuItem commentsMenuItem;
    private javax.swing.JCheckBoxMenuItem dotAllMenuItem;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLayeredPane exampleLayeredPane;
    private javax.swing.JScrollPane exampleScrollPane;
    private javax.swing.JTextArea exampleTextArea;
    private javax.swing.JButton flagsButton;
    private javax.swing.JPopupMenu flagsPopupMenu;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JCheckBoxMenuItem literalMenuItem;
    private javax.swing.JCheckBoxMenuItem multilineMenuItem;
    private javax.swing.JPanel regexPanel;
    private javax.swing.JScrollPane regexScrollPane;
    private javax.swing.JTextArea regexTextArea;
    private javax.swing.JCheckBox strictCheckBox;
    private javax.swing.JCheckBoxMenuItem unicodeCaseMenuItem;
    private javax.swing.JCheckBoxMenuItem unicodeCharacterClassMenuItem;
    private javax.swing.JCheckBoxMenuItem unixLinesMenuItem;
    private javax.swing.JSplitPane verticalSplitPane;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");    // NOI18N
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");  // NOI18N
        // TODO read your settings according to their version
    }

    public static synchronized CheckRegexTopComponent getDefault() {
        if (instance == null) {
            instance = new CheckRegexTopComponent();
        }
        Mode outputMode = WindowManager.getDefault().findMode("output");    // NOI18N
        if (outputMode != null) {
            outputMode.dockInto(instance);
        }
        return instance;
    }

    public static synchronized CheckRegexTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(CheckRegexTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");   // NOI18N
            return getDefault();
        }
        if (win instanceof CheckRegexTopComponent) {
            return (CheckRegexTopComponent) win;
        }
        Logger.getLogger(CheckRegexTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");   // NOI18N
        return getDefault();
    }

    void setData(String origString) {
        regexTextArea.setText(origString);
        matchPattern();
    }

    @NbBundle.Messages({
        "CheckRegexTopComponent.tooltip.match.regex=The Example Matches the Regular Expression",
        "CheckRegexTopComponent.tooltip.need.more.input=Need more input to match",
        "CheckRegexTopComponent.tooltip.not.match=The example does not match the Regular Expression.",
        "# {0} - matchCount",
        "CheckRegexTopComponent.tooltop.sub.match={0} substring(s) match the Regular Expression",
        "# {0} - invalidRegex",
        "CheckRegexTopComponent.label.error=Invalid regular expression: {0}"
    })
    private void matchPattern() {

        Highlighter highlighter = exampleTextArea.getHighlighter();
        highlighter.removeAllHighlights();

        iconLabel.setIcon(null);

        if (regexTextArea.getText().isEmpty() || exampleTextArea.getText().isEmpty()) {
            return;
        }
        Pattern p;
        try {
            p = Pattern.compile(regexTextArea.getText(), Flags);
            errorLabel.setVisible(false);
        } catch (PatternSyntaxException pse) {
            errorLabel.setText(Bundle.CheckRegexTopComponent_label_error(pse.getDescription()));
            errorLabel.setVisible(true);
            if (regexPanel.getHeight() < regexPanel.getPreferredSize().height) {
                verticalSplitPane.resetToPreferredSizes();
            }
            return;
        }
        Matcher m = p.matcher(exampleTextArea.getText());

        if (m.matches()) {
            iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/java/hints/resources/match.png"))); // NOI18N
            exampleTextArea.setToolTipText(Bundle.CheckRegexTopComponent_tooltip_match_regex());
        } else if (m.hitEnd()) {
            iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/java/hints/resources/half-match.png"))); // NOI18N
            exampleTextArea.setToolTipText(Bundle.CheckRegexTopComponent_tooltip_need_more_input());
        } else if (isStrictMatch) {
            iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/java/hints/resources/no-match.png"))); // NOI18N
            exampleTextArea.setToolTipText(Bundle.CheckRegexTopComponent_tooltip_not_match());
        } else {
            m.reset();
            long count = 0;
            try {
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    DefaultHighlighter.DefaultHighlightPainter defaultHighlightPainter;
                    if(count%2 == 0){
                        defaultHighlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
                    }else{
                        defaultHighlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                    }
                    highlighter.addHighlight(start, end, defaultHighlightPainter);
                    count++;
                }
                if (count > 0) {
                    iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/java/hints/resources/match.png"))); // NOI18N
                    exampleTextArea.setToolTipText(Bundle.CheckRegexTopComponent_tooltop_sub_match(count));
                } else {
                    iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/java/hints/resources/no-match.png"))); // NOI18N
                    exampleTextArea.setToolTipText(Bundle.CheckRegexTopComponent_tooltip_not_match());
                }
            } catch (BadLocationException e) {
                Exceptions.printStackTrace(e);
            }
        }
    }
    
    public String getExpression(){
        return regexTextArea.getText();
    }
    
}