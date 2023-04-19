package edu.pwr.db.view;

import edu.pwr.db.Logger;
import edu.pwr.db.model.*;

import javax.swing.*;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// let's make this class controller also
public class AppWindow extends JFrame {
    private State state = State.NONE;
    private final JTextArea hintText;

    void resetState() {
        state = State.NONE;
    }
    State getCurrentState() {
        return state;
    }
    void nextState() {
        state = state.next();
    }

    void startInvoice() {
        state = State.INVOICE_CLIENT;
        try {
            var template = dbConnection.getClientTemplate();
            var list = template.list();
            searchResultPanel.setItems(list);
            tabbedPane.setSelectedComponent(searchResultPanel);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            // TODO: error message for user
        }
    }

    void chooseOfferToAlter() {
        state = State.OFFER_ALTER;
        var template = dbConnection.getJoinedOfferTemplate();
        Item i = Item.ANY;
        var list = template.list(i, i, i, i);
        searchResultPanel.setItems(list);
        tabbedPane.setSelectedComponent(searchInputPanel);
    }

    void showSearchPanel() {
        tabbedPane.setSelectedComponent(searchInputPanel);
    }

    void setInvoiceClient(ClientItem client) {
        invoiceGeneratorPanel.setClient(client);
        state = state.next();
        //JOptionPane.showMessageDialog(this, "select offers",
        //        "info", JOptionPane.INFORMATION_MESSAGE);
        this.showHintText("choose offer to be added to invoice");
        tabbedPane.setSelectedComponent(searchInputPanel);
    }

    public static void main(String[] args){
        new AppWindow().start();
    }

    private final SearchResultPanel searchResultPanel;
    private final SearchInputPanel searchInputPanel;
    private final InvoiceGeneratorPanel invoiceGeneratorPanel;
    private final CustomerAddPanel customerAddPanel;
    private final ProductAddPanel productAddPanel;
    private final AlterOfferPanel alterOfferPanel;
    private final AdminPanel adminPanel;
    private DBConnection dbConnection;

    private final JTabbedPane tabbedPane;

    void showHintText(String text) {
        hintText.setText(text);
    }

    public AppWindow() {
        super("shop-app");
        hintText = new JTextArea();
        hintText.setEditable(false);
        hintText.setBackground(Color.LIGHT_GRAY);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int halfInitialWidth = 360, halfInitialHeight = 280;

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (d.getWidth() / 2), y = (int) (d.getHeight() / 2);
        setBounds(x - halfInitialWidth, y - halfInitialHeight,
                2 * halfInitialWidth, 2 * halfInitialHeight);
        setResizable(true);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setUI(new MetalTabbedPaneUI() {
            @Override
            protected int calculateTabHeight(int a, int b, int c) {
                return 40; // this is pixel height of the tab
            }
        });

        searchResultPanel = new SearchResultPanel(this);
        searchInputPanel = new SearchInputPanel(this);
        invoiceGeneratorPanel = new InvoiceGeneratorPanel(this);
        customerAddPanel = new CustomerAddPanel(this);
        productAddPanel = new ProductAddPanel(this);
        alterOfferPanel = new AlterOfferPanel(this);
        adminPanel = new AdminPanel(this);

        tabbedPane.add(searchInputPanel, "Search products");
        tabbedPane.add(searchResultPanel, "Search results");
        tabbedPane.add(invoiceGeneratorPanel, "Create invoice");
        tabbedPane.add(customerAddPanel, "Add customer");
        tabbedPane.add(productAddPanel, "Add product");
        tabbedPane.add(alterOfferPanel, "Change offer");
        tabbedPane.add(adminPanel, "backups");

        add(tabbedPane, BorderLayout.CENTER);
        add(hintText, BorderLayout.NORTH);
    }

    public void start() {
        new LoginDialog(this);
        searchInputPanel.refreshContents(); // this is first load of contents
        productAddPanel.refreshContents();
        setVisible(true);
    }

    public void setDbConnection(DBConnection connection) {
        this.dbConnection = connection;
    }
    public DBConnection getDbConnection() {
        return dbConnection;
    }

    public void searchBy(Item brand, Item color, Item coverageLevel, Item type) {
        var template = dbConnection.getJoinedOfferTemplate();
        var list = template.list(brand, color, coverageLevel, type);
        var arr = new Item[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        searchResultPanel.setItems(list);
        tabbedPane.setSelectedComponent(searchResultPanel);
    }

    public void setInvoiceOffer(JoinedOfferItem result) {
        invoiceGeneratorPanel.setOffer(result);
        tabbedPane.setSelectedComponent(invoiceGeneratorPanel);
    }

    public void setAlterOffer(JoinedOfferItem item) {
        alterOfferPanel.setOffer(item);
        tabbedPane.setSelectedComponent(alterOfferPanel);
    }

    public void addOfferQuery() {
        state = State.PRODUCT_OFFER_ALTER;
        var template = dbConnection.getJoinedProductTemplate();
        Item i = Item.ANY;
        var list = template.list(i, i, i, i);
        searchResultPanel.setItems(list);
        tabbedPane.setSelectedComponent(searchInputPanel);
    }

    public void setAddProduct(JoinedProductItem result) {
        alterOfferPanel.setProduct(result);
        tabbedPane.setSelectedComponent(alterOfferPanel);
    }
}

// this enum helps to determine what is being selected in searchResult and what to do about it
enum State {
    NONE,
    INVOICE_CLIENT,
    INVOICE_LINE,
    OFFER_ALTER,
    PRODUCT_OFFER_ALTER;

    public State next() {
        Logger.debug("state changing from: " + this.toString());
        switch (this) {
            case NONE:
            case INVOICE_LINE:
            case PRODUCT_OFFER_ALTER:
            case OFFER_ALTER:
                return NONE;
            case INVOICE_CLIENT: return INVOICE_LINE;
            default: return null;
        }
    }
}