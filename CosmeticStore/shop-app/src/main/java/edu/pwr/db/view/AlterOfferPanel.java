package edu.pwr.db.view;

import edu.pwr.db.model.DBConnection;
import edu.pwr.db.model.JoinedOfferItem;
import edu.pwr.db.model.JoinedProductItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AlterOfferPanel extends JPanel {
    private final JButton removeOffer, addOffer, updateOffer, select, cancel;
    private final JTextArea amount, price, productDescription;
    private final AppWindow appWindow;
    private JoinedOfferItem offer;
    private JoinedProductItem product;

    public AlterOfferPanel(AppWindow appWindow) {
        this.appWindow = appWindow;
        updateOffer = new JButton("update");
        select = new JButton("load offer");
        removeOffer = new JButton("remove offer");
        addOffer = new JButton("new offer");
        cancel = new JButton("cancel");
        amount = new JTextAreaWithPlaceholder("in stock");
        price = new JTextAreaWithPlaceholder("price per unit");
        productDescription = new JTextArea();
        productDescription.setEditable(false);

        select.addActionListener(e -> {
            appWindow.chooseOfferToAlter();
            updateOffer.setText("update");
            updateOffer.addActionListener(f -> updateOfferVariant());
        });

        cancel.addActionListener(e -> {
            offer = null;
            product = null;
            amount.setText("");
            price.setText("");
            productDescription.setText("");
            appWindow.resetState();
            repaint();
        });

        removeOffer.addActionListener(e -> {
            if (offer != null) {
                var template = appWindow.getDbConnection().getJoinedOfferTemplate();
                template.removeOfferById(offer.getId());
                offer = null;
                productDescription.setText("");
                price.setText("");
                amount.setText("");
                appWindow.resetState();
            }
        });

        addOffer.addActionListener(e -> {
            updateOffer.setText("create");
            updateOffer.addActionListener(f -> addOfferVariant());
            appWindow.addOfferQuery();
        });


        setLayout(new GridBagLayout());
        var gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(10, 10, 0, 0);
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridheight = 1;
        gc.gridwidth = 3;
        gc.weighty = 5;
        gc.weightx = 1;
        add(productDescription, gc);

        gc.gridy++;
        gc.weighty = 1;
        var panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 3, 10, 10));
        panel2.add(amount);
        panel2.add(price);
        JPanel holder1 = new JPanel();
        holder1.setLayout(new GridBagLayout());
        var gc2 = new GridBagConstraints();
        gc2.insets = gc.insets;
        gc2.gridx = 0;
        gc2.gridy = 0;
        gc2.gridwidth = 1;
        gc2.gridheight = 1;
        gc2.weightx = gc2.weighty = 0;
        holder1.add(updateOffer, gc2);
        gc2.gridy++;
        holder1.add(cancel, gc2);
        panel2.add(holder1);
        add(panel2, gc);

        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.CENTER;
        add(select, gc);

        gc.gridx++;
        add(removeOffer, gc);

        gc.gridx++;
        add(addOffer, gc);

        setBorder(new EmptyBorder(20, 20, 30, 30));
    }

    private void addOfferVariant() {
        try {
            var template = appWindow.getDbConnection().getJoinedOfferTemplate();
            int amount = Integer.parseInt(this.amount.getText());
            double price = Double.parseDouble(this.price.getText());
            template.insertOffer(product.getId(),price,amount);
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(appWindow,
                    "incorrect input", "error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void updateOfferVariant() {
        try {
            var template = appWindow.getDbConnection().getJoinedOfferTemplate();
            int amount = Integer.parseInt(this.amount.getText());
            double price = Double.parseDouble(this.price.getText());
            template.updateOffer(offer.getId(),price,amount);

        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(appWindow,
                    "incorrect input", "error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void setOffer(JoinedOfferItem item) {
        this.offer = item;
        amount.setText(Integer.toString(item.getUnitsInStock()));
        price.setText(Double.toString(item.getPricePerUnit()));
        // TODO perhaps prettier display here, since we have access to all fields n stuff
        productDescription.setText(item.toString());
        repaint();
    }

    public void setProduct(JoinedProductItem product) {
        this.product = product;
        amount.setText("");
        price.setText("");
        productDescription.setText(product.toString());
        repaint();
    }
}
