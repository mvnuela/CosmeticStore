package edu.pwr.db.view;

import com.mysql.jdbc.Connection;
import edu.pwr.db.model.DBConnection;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class CustomerAddPanel extends JPanel {
    protected final JButton add;
    public AppWindow appWindow;
    JTextArea inputName = new JTextAreaWithPlaceholder("Enter name");
    JTextArea inputSurname = new JTextAreaWithPlaceholder("Enter surname");
    JTextArea inputAddress = new JTextAreaWithPlaceholder("Enter address");


    public CustomerAddPanel(AppWindow appWindow) {
        this.appWindow = appWindow;
        JPanel panel = new JPanel();
        GridBagConstraints gc = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 0, 0);
        gc.anchor = GridBagConstraints.NORTHWEST;

        JLabel jlName = new JLabel("Name:");
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.weighty = 0;
        gc.weightx = 0;
        panel.add(jlName, gc);

        // JTextArea inputName = new JTextAreaWithPlaceholder("Enter name");
        gc.gridx = 1;
        gc.weightx = 5;
        panel.add(inputName, gc);

        JLabel jlSurname = new JLabel("Surname:");
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 1;
        panel.add(jlSurname, gc);

        //JTextArea inputSurname = new JTextAreaWithPlaceholder("Enter surname");
        gc.gridx = 1;
        panel.add(inputSurname, gc);

        JLabel jlAddress = new JLabel("Address:");
        gc.gridx = 0;
        gc.gridy = 2;
        panel.add(jlAddress, gc);

        //JTextArea inputAddress = new JTextAreaWithPlaceholder("Enter address");
        gc.gridx = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 10;
        panel.add(inputAddress, gc);

        add = new JButton("Add");
        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 2;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        panel.add(add, gc);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        Border padding = BorderFactory.createEmptyBorder(10, 10, 20, 20);
        setBorder(padding);
        add.addActionListener(e -> {
            try {
                addCustomer();
                inputSurname.setText("");
                inputAddress.setText("");
                inputName.setText("");

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
    }

    public void addCustomer() throws SQLException {
        CallableStatement callableStatement;
        try {
            Connection conn = (Connection) appWindow.getDbConnection().getConn();
            String SQL = "{CALL addClient(?,?,?)}";
            callableStatement = conn.prepareCall(SQL);
            callableStatement.setString(1, inputName.getText().toString());
            callableStatement.setString(2,inputSurname.getText().toString());
            callableStatement.setString(3,inputAddress.getText().toString());
            callableStatement.executeQuery();
            if (appWindow.getCurrentState().equals(State.INVOICE_CLIENT)) {
                appWindow.startInvoice();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
