package com.github.mortlight.plugindemo.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.ide.util.PackageChooserDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MainUI extends AnAction {
    private Project project;
    public JPanel contentPanel = new JBPanel<>();
    private JPanel mainPanel;
    private JPanel chooseFilePanel;
    private JTabbedPane tabbedPanel;
    private JPanel originPanel;
    private JButton originSubmitButton;
    private JButton mergeButton;
    private JButton submitButton;
    private JButton previewButton;
    private JButton applyButton;
    private EditorTextField originCodeTextArea;
    private JTextField mergeFileNameTextField;
    private JTextArea outputTextArea;
    private JTextArea conflictTextArea;
    private JLabel originChooseFilePathLabel;
    private JPanel branch1Panel;
    private JPanel branch2Panel;
    private TextFieldWithBrowseButton originTextFieldWithBrowseButton;
    private JLabel branch1ChooseFilePathLabel;
    private JLabel branch2ChooseFilePathLabel;
    private TextFieldWithBrowseButton branch1TextFieldWithBrowseButton;
    private JButton branch1SubmitButton;
    private EditorTextField branch1CodeTextArea;
    private EditorTextField branch2CodeTextArea;
    private JButton branch2SubmitButton;
    private TextFieldWithBrowseButton branch2TextFieldWithBrowseButton;
    private TextFieldWithBrowseButton mergeFilePathTextFieldWithBrowseButton;
    private TextFieldWithBrowseButton chooseOriginTextFieldWithBrowseButton;
    private File originFile;
    private File branch1File;
    private File branch2File;
    private File mergedFile;


    public MainUI(){

        originTextFieldWithBrowseButton.addBrowseFolderListener(
                "Choose Origin File", "Choose origin file", project,
                FileChooserDescriptorFactory.createSingleFileDescriptor());
        branch1TextFieldWithBrowseButton.addBrowseFolderListener(
                "Choose Branch1 File", "Choose Branch1 file", project,
                FileChooserDescriptorFactory.createSingleFileDescriptor());
        branch2TextFieldWithBrowseButton.addBrowseFolderListener(
                "Choose Branch2 File", "Choose Branch2 file", project,
                FileChooserDescriptorFactory.createSingleFileDescriptor());
        mergeFilePathTextFieldWithBrowseButton.addBrowseFolderListener(
                "Choose Merge File ", "Choose Merge File", project,
                FileChooserDescriptorFactory.createSingleFolderDescriptor());
        originSubmitButton.addActionListener(e -> {
            try {
                String originContent = new String(Files.readAllBytes(Paths.get(originTextFieldWithBrowseButton.getText())));
                originCodeTextArea.setText(originContent);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        branch1SubmitButton.addActionListener(e -> {
            try {
                String branch1Content = new String(Files.readAllBytes(Paths.get(branch1TextFieldWithBrowseButton.getText())));
                branch1CodeTextArea.setText(branch1Content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        branch2SubmitButton.addActionListener(e -> {
            try {
                String branch2Content = new String(Files.readAllBytes(Paths.get(branch2TextFieldWithBrowseButton.getText())));
                branch2CodeTextArea.setText(branch2Content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        mergeButton.addActionListener(e -> {
            String command = "java -jar " + "C:\\Users\\16682\\Downloads\\cloud-demo\\plugindemo\\src\\main\\resources\\spork-0.5.1.jar" + " " + originTextFieldWithBrowseButton.getText() + " " + branch1TextFieldWithBrowseButton.getText() + " " + branch2TextFieldWithBrowseButton.getText();
            System.out.println(command);
            try {
                Process process = Runtime.getRuntime().exec(command);

                // 读取命令输出
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
                String line;
                StringBuilder output = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("\uFFFD", "-");
                    output.append(line).append("\n");
                    System.out.println(StringEscapeUtils.escapeJava(line));
                }
                outputTextArea.setText(output.toString());
            } catch (IOException ex) {
                System.out.println("An error occurred while executing the command: " + ex.getMessage());
            }
        });

        submitButton.addActionListener(e -> {
            String fileName = mergeFileNameTextField.getText();
            String filePath = mergeFilePathTextFieldWithBrowseButton.getText();

            mergedFile = new File(filePath, fileName);

            try {
                if (mergedFile.createNewFile()) {
                    System.out.println("File created successfully: " + mergedFile.getAbsolutePath());
                } else {
                    System.out.println("File creation failed. File already exists or an error occurred.");
                }
            } catch (IOException ex) {
                System.out.println("An error occurred while creating the file: " + ex.getMessage());
            }
        });

/*        previewButton.addActionListener(e -> {
            String fileName = mergeFileNameTextField.getText();
            String filePath = mergeFilePathTextFieldWithBrowseButton.getText();

            File file = new File(filePath, fileName);

            try {
                Scanner scanner = new Scanner(file);
                StringBuilder output = new StringBuilder();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    line = line.replaceAll("\uFFFD", "-");
                    output.append(line).append("\n");
                    System.out.println(StringEscapeUtils.escapeJava(line));
                }
                conflictTextArea.setText(output.toString());
            } catch (FileNotFoundException ex) {
                System.out.println("An error occurred while reading the file: " + ex.getMessage());
            }
        });*/

        previewButton.addActionListener(e -> {
            String command = "java -jar " + "C:\\Users\\16682\\Downloads\\cloud-demo\\plugindemo\\src\\main\\resources\\spork-0.5.4-SNAPSHOT.jar" + " "
                    + originTextFieldWithBrowseButton.getText() + " "
                    + branch1TextFieldWithBrowseButton.getText() + " "
                    + branch2TextFieldWithBrowseButton.getText();
            System.out.println(command);
            try {
                Process process = Runtime.getRuntime().exec(command);

                // 读取命令输出
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
                String line;
                StringBuilder output = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("\uFFFD", "-");
                    output.append(line).append("\n");
                    System.out.println(StringEscapeUtils.escapeJava(line));
                }
                conflictTextArea.setText(output.toString());
            } catch (IOException ex) {
                System.out.println("An error occurred while executing the command: " + ex.getMessage());
            }
        });

        applyButton.addActionListener(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(mergedFile))) {
                String command = "java -jar " + "C:\\Users\\16682\\Downloads\\cloud-demo\\plugindemo\\src\\main\\resources\\spork-0.5.4-SNAPSHOT.jar" + " "
                        + originTextFieldWithBrowseButton.getText() + " "
                        + branch1TextFieldWithBrowseButton.getText() + " "
                        + branch2TextFieldWithBrowseButton.getText() + " "
                        + "-o" + " " + mergedFile.getAbsolutePath();
                Process process = Runtime.getRuntime().exec(command);

                // Read command output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
                String line;
                StringBuilder output = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                writer.write(output.toString());

                // create http connection
                String uploadUrl = "http://114.115.210.155:5000/upload";
                URL url = new URL(uploadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                // Set request headers
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + "*****");

                // request
                OutputStream outputStream = connection.getOutputStream();
                String boundary = "*****";
                String lineBreak = "\r\n";

                // Write to file field
                outputStream.write(("--" + boundary + lineBreak).getBytes());
                outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + mergedFile.getName() + "\"" + lineBreak).getBytes());
                outputStream.write(("Content-Type: " + HttpURLConnection.guessContentTypeFromName(mergedFile.getName()) + lineBreak).getBytes());
                outputStream.write(lineBreak.getBytes());

                FileInputStream fileInputStream = new FileInputStream(mergedFile);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                fileInputStream.close();

                outputStream.write(lineBreak.getBytes());
                outputStream.write(("--" + boundary + "--" + lineBreak).getBytes());
                outputStream.flush();
                outputStream.close();

                // Get server response
                int responseCode = connection.getResponseCode();
                String responseMessage = connection.getResponseMessage();
                System.out.println("Server Response: " + responseCode + " " + responseMessage);

                connection.disconnect();
            } catch (IOException ioException) {
                System.err.println("Can not write file-----" + ioException.getMessage());
            }
        });
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

    }


    /*    private void createUIComponents() {
            // TODO: place custom component creation code here
        }

        public void createUI(Project project) {
            this.project = project;
            contentPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
            this.initChooseFilePanel();
            this.initPostfixPanel();
            this.initPackagePanel();
            this.initOptionsPanel();
            this.initClearCachePanel();
            this.reset();
        }

        private void initChooseFilePanel() {

        }*/
    public JComponent getComponent() {
        return mainPanel;
    }
}
