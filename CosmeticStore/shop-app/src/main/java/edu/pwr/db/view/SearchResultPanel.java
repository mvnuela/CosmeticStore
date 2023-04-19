package edu.pwr.db.view;

import edu.pwr.db.Logger;
import edu.pwr.db.model.ClientItem;
import edu.pwr.db.model.Item;
import edu.pwr.db.model.JoinedOfferItem;
import edu.pwr.db.model.JoinedProductItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.util.Collection;

public class SearchResultPanel extends JPanel {
    private final DefaultListModel<Item> model;
    private final JButton selectButton;
    private final AppWindow appWindow;
    public SearchResultPanel(AppWindow appWindow) {
        this.appWindow = appWindow;
        model = new DefaultListModel<>();
        JList<Item> items = new JList<>(model);
        selectButton = new JButton("select");
        setLayout(new BorderLayout());
        add(items, BorderLayout.CENTER);
        add(selectButton, BorderLayout.SOUTH);

        selectButton.addActionListener(e -> {
            Item result = items.getSelectedValue();
            if (result != null) {
                switch (appWindow.getCurrentState()) {
                    case INVOICE_CLIENT:
                        appWindow.setInvoiceClient((ClientItem) result);
                        break;
                    case INVOICE_LINE:
                        appWindow.setInvoiceOffer((JoinedOfferItem) result);
                        Logger.debug("invoice line");
                        break;
                    case OFFER_ALTER:
                        appWindow.setAlterOffer((JoinedOfferItem) result);
                        break;
                    case PRODUCT_OFFER_ALTER:
                        appWindow.setAddProduct((JoinedProductItem) result);
                        break;
                    default:

                }
            }
        });

        setBorder(new EmptyBorder(40, 40, 40, 40)); // padding
    }

    public void setItems(Collection<Item> itemCollection) {
        model.clear();
        //model.addAll(itemCollection);
        for (Item i : itemCollection) {
            model.addElement(i);
        }
        if (model.isEmpty()) {
            model.addElement(Item.NO_RESULTS);
        }
        repaint();
    }


}
