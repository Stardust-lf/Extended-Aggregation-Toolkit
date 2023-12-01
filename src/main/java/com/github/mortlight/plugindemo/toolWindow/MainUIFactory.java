package com.github.mortlight.plugindemo.toolWindow;

import com.github.mortlight.plugindemo.ui.MainUI;
import com.github.mortlight.plugindemo.ui.MainUI3;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class MainUIFactory implements ToolWindowFactory {
    private MainUI mainUI = new MainUI();
    private static final String DISPLAY_NAME = "Plugin Demo";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(mainUI.getComponent(), DISPLAY_NAME, false);
        toolWindow.getContentManager().addContent(content);
    }
}
