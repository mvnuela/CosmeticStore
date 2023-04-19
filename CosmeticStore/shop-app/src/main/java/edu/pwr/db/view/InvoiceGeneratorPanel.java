package edu.pwr.db.view;

import com.mysql.jdbc.Connection;
import edu.pwr.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class InvoiceGeneratorPanel extends JPanel {
    private final JButton newInvoice;
    private final JButton addOffer;
    private final JButton endCreation;
    private final JButton cancelCreation;
    private final JButton okButton;
    private final JTextArea unitsCountInput;
    private final JTextArea alreadyCreatedView;
    private final JTextArea selectedOfferInfo;
    private final AppWindow appWindow;


    /**
     * -1 means that we do not have invoice selected (created)
     */
    private int invoiceID = -1;
    private JoinedOfferItem currentOffer;

    public InvoiceGeneratorPanel(AppWindow appWindow) {
        this.appWindow = appWindow;

        newInvoice = new JButton("create new");
        addOffer = new JButton("add entry");
        endCreation = new JButton("confirm");
        cancelCreation = new JButton("cancel");
        okButton = new JButton("OK");
        unitsCountInput = new JTextAreaWithPlaceholder("units");
        alreadyCreatedView = new JTextArea();
        selectedOfferInfo = new JTextArea();
        selectedOfferInfo.setEditable(false);
        alreadyCreatedView.setEditable(false);
        addOffer.setEnabled(false);
        endCreation.setEnabled(false);
        cancelCreation.setEnabled(false);


        newInvoice.addActionListener(e -> {
            newInvoice.setEnabled(false);
            addOffer.setEnabled(true);
            endCreation.setEnabled(true);
            cancelCreation.setEnabled(true);

            JOptionPane.showMessageDialog(appWindow, "choose client",
                    "info", JOptionPane.INFORMATION_MESSAGE);
            appWindow.startInvoice();
        });

        addOffer.addActionListener(e -> {
            appWindow.showSearchPanel();
        });

        okButton.addActionListener(e -> {
            if (currentOffer != null) {

                try {
                    int amount = Integer.parseInt(unitsCountInput.getText());
                    int productID=getToKnowId(currentOffer.getId());
                    addLine(invoiceID,productID,amount);
                   // String SQL = "SELECT product FROM offer WHERE id=" + String.valueOf(currentOffer.getId());

                    // TODO: SQL stuff [adding entry to invoiceLine], delete line 'throw new .."
                    // TODO: disable & enable buttons (also in latter listeners)

                    // TODO: make prettier display of added line
                    alreadyCreatedView.setText(
                            alreadyCreatedView.getText() + "\n" + currentOffer);
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(appWindow, "bad input","error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        endCreation.addActionListener(e -> {
            newInvoice.setEnabled(true);
            addOffer.setEnabled(false);
            endCreation.setEnabled(false);
            cancelCreation.setEnabled(false);
            confirm(invoiceID);
        });

        cancelCreation.addActionListener(e -> {
            newInvoice.setEnabled(true);
            addOffer.setEnabled(false);
            endCreation.setEnabled(false);
            cancelCreation.setEnabled(false);
            removeUnfinished(invoiceID);
        });

        // display
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 0, 0);
        gc.fill = GridBagConstraints.BOTH;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 4;
        gc.weighty = 8;
        gc.weightx = 0;
        panel.add(alreadyCreatedView, gc);

        gc.gridy++;
        gc.gridwidth = 2;
        gc.weighty = 0.5;
        panel.add(selectedOfferInfo, gc);

        gc.gridwidth = 1;
        gc.gridx += 2;
        gc.weightx = 1;
        panel.add(unitsCountInput, gc);

        gc.fill = GridBagConstraints.NONE;
        gc.gridx++;
        panel.add(okButton, gc);

        gc.weighty = 1;

        gc.gridx = 0;
        gc.gridy++;
        panel.add(newInvoice, gc);

        gc.gridx++;
        panel.add(addOffer, gc);

        gc.gridx++;
        panel.add(cancelCreation, gc);

        gc.gridx++;
        panel.add(endCreation, gc);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        Border padding = BorderFactory.createEmptyBorder(10, 10, 20, 20);
        setBorder(padding);
    }

    public void setOffer(JoinedOfferItem offer) {
        unitsCountInput.setText("");
        selectedOfferInfo.setText(offer.toString());
        currentOffer = offer;
    }

    public void setClient(ClientItem client) { //TWORZYMY INVOICE DLA KLIENTA
        int clientID=client.getId();
        CallableStatement callableStatement;
        try{
            Connection conn = (Connection) appWindow.getDbConnection().getConn();
            String SQL = "{CALL addInvoice(?,?)}";
            callableStatement=conn.prepareCall(SQL);
            callableStatement.setInt(1,clientID);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.executeQuery();
            invoiceID=callableStatement.getInt(2); // TUTAJ POBIERAMY WYNIK
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addLine(int inv,int product,int units){
        CallableStatement callableStatement;
        try{
            Connection conn = (Connection) appWindow.getDbConnection().getConn();
            String SQL = "{CALL addLine(?,?,?)}";
            callableStatement=conn.prepareCall(SQL);
            callableStatement.setInt(1,inv);
            callableStatement.setInt(2,product);
            callableStatement.setInt(3,units);
            callableStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public int getToKnowId(int id){
        String SQL = "select product from offer where id = " + id;
        int productID = appWindow.getDbConnection().getJDBCTemplate().queryForObject(SQL, Integer.class);
        return productID;
    }

    public void confirm(int inv){
        CallableStatement callableStatement;
        try{
            Connection conn = (Connection) appWindow.getDbConnection().getConn();
            String SQL = "{CALL confirm(?)}";
            callableStatement=conn.prepareCall(SQL);
            callableStatement.setInt(1,inv);
            callableStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void removeUnfinished(int inv){
        CallableStatement callableStatement;
        try{
            Connection conn = (Connection) appWindow.getDbConnection().getConn();
            String SQL = "{CALL removeUnfinished(?)}";
            callableStatement=conn.prepareCall(SQL);
            callableStatement.setInt(1,inv);
            callableStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
