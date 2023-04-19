package edu.pwr.db.view;

import edu.pwr.db.model.Login;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AdminPanel extends JPanel {
    private final AppWindow appWindow;
    private final JButton doBackup, restoreFromBackup, lunchCmd;

    public AdminPanel(AppWindow appWindow) {
        this.appWindow = appWindow;
        doBackup = new JButton("make backup");
        restoreFromBackup = new JButton("restore from backup");
        lunchCmd = new JButton("lunch command line");

        setLayout(new GridBagLayout());
        var gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.CENTER;
        gc.weighty = gc.weightx = 1;

        gc.gridx = gc.gridy = 0;
        add(doBackup, gc);

        gc.gridy++;
        add(restoreFromBackup, gc);

        gc.gridy++;
        add(lunchCmd, gc);

        doBackup.addActionListener(e -> backup());
        lunchCmd.addActionListener(e -> lunchCommandLine());
        restoreFromBackup.addActionListener(e -> restore());
    }

    private void backup() {
        try {
            String dbName = "Shop";
            String dbUser = Login.getUsername();
            String dbPass = Login.getPassword();

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("select backup target directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            String path;

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                path = chooser.getCurrentDirectory().toString();
            } else {
                JOptionPane.showMessageDialog(appWindow,
                        "backup aborted", "info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String folderPath = path + "\\backup";
            File f1 = new File(folderPath);
            f1.mkdir();

            String savePath = "\"" + path + "\\backup\\" + "backup.sql\"";
            String executeCmd = "mysqldump -u" + dbUser +
                    " -p" + dbPass + " --database " + dbName + " -r " + savePath;
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                JOptionPane.showMessageDialog(appWindow,
                        "backup done", "info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(appWindow,
                        "backup failure", "info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error at backup: " + ex.getMessage());
        }
    }

    private void restore() {
        try {
            String dbName = "Shop";
            String dbUser = Login.getUsername();
            String dbPass = Login.getPassword();

            String path;

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("select backup target directory");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("sql backups", "sql");
            chooser.setFileFilter(filter);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                path = chooser.getSelectedFile().toString();
            } else {
                JOptionPane.showMessageDialog(appWindow,
                        "restore aborted", "info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[] executeCmd = new String[]{"mysql", dbName, "-u" + dbUser, "-p" + dbPass, "-e", " source " + path};
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                JOptionPane.showMessageDialog(appWindow, "Successfully restored");
            } else {
                JOptionPane.showMessageDialog(appWindow, "Error at restoring");
            }

        } catch (IOException | InterruptedException | HeadlessException ex) {
            JOptionPane.showMessageDialog(appWindow, "Error at restore: " + ex.getMessage());
        }
    }

    private void lunchCommandLine() {
        try {
            String dbName = "Shop";
            String dbUser = Login.getUsername();
            String dbPass = Login.getPassword();
            String[] executeCmd = new String[]{"cmd", "/c", "start", "cmd.exe", "/K", "mysql", dbName, "-u" + dbUser, "-p" + dbPass};
            Runtime.getRuntime().exec(executeCmd);

        } catch (IOException | HeadlessException ex) {
            JOptionPane.showMessageDialog(appWindow, "Error at restore: " + ex.getMessage());
        }
    }

}
