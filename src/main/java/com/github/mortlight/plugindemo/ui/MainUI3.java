package com.github.mortlight.plugindemo.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MainUI3 extends AnAction {
    private JPanel mainPanel;
    private JButton button1;

    public JComponent getComponent() {
        return mainPanel;
    }
    public MainUI3() {
        $$$setupUI$$$();
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Messages.showMessageDialog("Hello World!", "Information", Messages.getInformationIcon());
    }

    private void $$$setupUI$$$() {
    }
}
