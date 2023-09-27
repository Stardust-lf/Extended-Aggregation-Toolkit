// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.sdk.toolWindow;

import com.fasterxml.aalto.util.TextUtil;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.*;
import com.intellij.execution.util.ExecUtil;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.apache.commons.compress.archivers.sevenz.CLI;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class CalendarToolWindowFactory implements ToolWindowFactory, DumbAware {

  private static CommandExcuter excuter = new CommandExcuter();

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
     CalendarToolWindowContent toolWindowContent = new CalendarToolWindowContent(project.getBasePath(), toolWindow);
    // Content content = ContentFactory.getInstance().createContent(toolWindowContent.getContentPanel(), "", false);
    String path = project.getBasePath();
    Content content = ContentFactory.getInstance().createContent(excuter.getComponent(), "", false);
    // 设置 ToolWindow 显示的内容
    toolWindow.getContentManager().addContent(content);
    toolWindow.getContentManager().addContent(content);
  }

  private static class CalendarToolWindowContent {

    private static final String CALENDAR_ICON_PATH = "/toolWindow/Calendar-icon.png";
    private static final String TIME_ZONE_ICON_PATH = "/toolWindow/Time-zone-icon.png";
    private static final String TIME_ICON_PATH = "/toolWindow/Time-icon.png";

    private final JPanel contentPanel = new JPanel();
    private final JLabel currentDate = new JLabel();
    private final JLabel timeZone = new JLabel();
    private final JLabel currentTime = new JLabel();

    public CalendarToolWindowContent(String path, ToolWindow toolWindow) {
//      contentPanel.setLayout(new BorderLayout(0, 20));
//      contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
//      contentPanel.add(createCalendarPanel(), BorderLayout.PAGE_START);
//      contentPanel.add(createControlsPanel(path, toolWindow), BorderLayout.CENTER);
//      updateCurrentDateTime("");
      excuter.setClickListener(e -> sporkMergeV3(path));
    }

    @NotNull
    private JPanel createCalendarPanel() {
      JPanel calendarPanel = new JPanel();
      setIconLabel(currentDate, CALENDAR_ICON_PATH);
      setIconLabel(timeZone, TIME_ZONE_ICON_PATH);
      setIconLabel(currentTime, TIME_ICON_PATH);
      calendarPanel.add(currentDate);
      calendarPanel.add(timeZone);
      calendarPanel.add(currentTime);
      return calendarPanel;
    }

    private void setIconLabel(JLabel label, String imagePath) {
      label.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath))));
    }

    @NotNull
    private JPanel createControlsPanel(String path, ToolWindow toolWindow) {
      JPanel controlsPanel = new JPanel();
      JButton refreshDateAndTimeButton = new JButton("merge code");
      refreshDateAndTimeButton.addActionListener(e -> updateCurrentDateTime(path));
      controlsPanel.add(refreshDateAndTimeButton);
      JButton hideToolWindowButton = new JButton("Hide");
      hideToolWindowButton.addActionListener(e -> toolWindow.hide(null));
      controlsPanel.add(hideToolWindowButton);
      return controlsPanel;
    }

    private void updateCurrentDateTime(String path) {
      Calendar calendar = Calendar.getInstance();
      currentDate.setText(getCurrentDate(calendar));
      timeZone.setText(getTimeZone(calendar));
      currentTime.setText(getCurrentTime(calendar));
      if (!StringUtils.isEmpty(path)) {
        sporkMergeV3(path);
        // sporkMerge(path);
      }
    }

    String outputInfo = "";

    private void print(String text) {
      outputInfo += text;
      excuter.setText(outputInfo);
    }

    private void sporkMergeV3(String path) {
      path += "/testcase";
      print("working derectory:" + path + "\n");
      ArrayList<String> cmds = new ArrayList<>();
      cmds.add("java -version");
      String cmd = "java -jar spork.jar Left.java Base.java Right.java";
      GeneralCommandLine generalCommandLine = new GeneralCommandLine(cmd.split(" "));
      // generalCommandLine.addParameters("-version");
      generalCommandLine.setCharset(Charset.forName("UTF-8"));
      generalCommandLine.setWorkDirectory(path);

      ProcessHandler processHandler = null;
      try {
//        ProcessOutput result1 = ExecUtil.execAndGetOutput(generalCommandLine);
//        String error = result1.getStderr();
//        String output = result1.getStdout();
//        int exitCode = result1.getExitCode();
//        System.out.println(output);
//        System.out.println("return code:" + exitCode);
        processHandler = new OSProcessHandler(generalCommandLine);
        processHandler.addProcessListener(new ProcessListener() {
          @Override
          public void startNotified(@NotNull ProcessEvent event) {
            ProcessListener.super.startNotified(event);
            // System.out.println("startNotified");
          }

          @Override
          public void processTerminated(@NotNull ProcessEvent event) {
            ProcessListener.super.processTerminated(event);
            print("运行结果:" + event.getExitCode());
            // System.out.println("process terminate");
          }

          @Override
          public void processWillTerminate(@NotNull ProcessEvent event, boolean willBeDestroyed) {
            ProcessListener.super.processWillTerminate(event, willBeDestroyed);
            // System.out.println("will terminate");
          }

          @Override
          public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) {
            ProcessListener.super.onTextAvailable(event, outputType);
            if (event.getExitCode() == 0) {
              print(event.getText());
              System.out.println(event.getText());
            } else {
              System.out.println("error:" + event.getText());
            }
          }
        });
        processHandler.startNotify();
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
    }

    private void sporkMerge(String path) {
      ArrayList<String> cmds = new ArrayList<>();
      cmds.add("java -jar spork.jar Left.java Base.java Right.java");

//      GeneralCommandLine generalCommandLine = new GeneralCommandLine(cmds);
//      generalCommandLine.setCharset(Charset.forName("UTF-8"));
//      generalCommandLine.setWorkDirectory(path);

      String base = "D:\\cityu_project\\Extended-Aggregation-Toolkit\\testcase";
      String commands = "java -jar" + base + "spork.jar Left.java Base.java Right.java";
      // String commands = "git version";
      // String commands = "java version";

      try {
        Process proc = Runtime.getRuntime().exec(commands);
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
        String line = null;
        while ((line = reader.readLine()) != null){
          System.out.println(line);
        }
        int exitVal = proc.waitFor();
        System.out.println(exitVal == 0 ? "成功" : "失败");
      } catch (IOException e) {
        System.out.println("IOException");
        throw new RuntimeException(e);
      } catch (InterruptedException e) {
        System.out.println("InterruptedException");
        throw new RuntimeException(e);
      }

      System.out.println("click end");
    }

    private String getCurrentDate(Calendar calendar) {
      return calendar.get(Calendar.DAY_OF_MONTH) + "/"
              + (calendar.get(Calendar.MONTH) + 1) + "/"
              + calendar.get(Calendar.YEAR);
    }

    private String getTimeZone(Calendar calendar) {
      long gmtOffset = calendar.get(Calendar.ZONE_OFFSET); // offset from GMT in milliseconds
      String gmtOffsetString = String.valueOf(gmtOffset / 3600000);
      return (gmtOffset > 0) ? "GMT + " + gmtOffsetString : "GMT - " + gmtOffsetString;
    }

    private String getCurrentTime(Calendar calendar) {
      return getFormattedValue(calendar, Calendar.HOUR_OF_DAY) + ":" + getFormattedValue(calendar, Calendar.MINUTE);
    }

    private String getFormattedValue(Calendar calendar, int calendarField) {
      int value = calendar.get(calendarField);
      return StringUtils.leftPad(Integer.toString(value), 2, "0");
    }

    public JPanel getContentPanel() {
      return contentPanel;
    }

  }

}
