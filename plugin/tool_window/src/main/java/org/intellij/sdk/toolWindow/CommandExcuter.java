package org.intellij.sdk.toolWindow;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CommandExcuter {
    private JButton button1;
    private JTextPane textPane1;
    private JPanel mainPanel;

    public CommandExcuter() {
        button1.setText("spork merge 3 files");
        textPane1.setText("output Area");
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void setClickListener(ActionListener l) {
        button1.addActionListener(l);
    }

    public void setText(String text) {
        textPane1.setText(text);
    }

}
