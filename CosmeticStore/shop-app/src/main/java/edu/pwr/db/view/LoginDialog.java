package edu.pwr.db.view;

import edu.pwr.db.model.DBConnection;
import edu.pwr.db.model.Login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginDialog extends JDialog {

    private final JTextField tfUsername;
    private final JPasswordField pfPassword;
    private final AppWindow parent;

    public LoginDialog(AppWindow parent) {
        super(parent, true);
        this.parent = parent;
        setUndecorated(true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(new JLabel("Username: "), cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(new JLabel("Password: "), cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);

        JButton btnLogin = new JButton("Login");

        btnLogin.addActionListener(e -> {
            DBConnection conn = Login.authenticate(getUsername(), getPassword());
            if (conn != null) {
                // TODO: see if here some notification to outside should be made
                parent.setDbConnection(conn);
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginDialog.this,
                        "Invalid username or password",
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
                pfPassword.setText("");
                tfUsername.setText("");
            }
        });
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> {
            //getOwner().dispose();
            //dispose();
            System.exit(0); // FIXME: perhaps we will want to close some connections
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnExit);

        JPanel master = new JPanel();
        master.setBorder(new EmptyBorder(10, 10, 2, 10));
        master.setLayout(new BorderLayout());
        master.add(panel, BorderLayout.CENTER);
        master.add(bp, BorderLayout.SOUTH);
        setContentPane(master);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
}
