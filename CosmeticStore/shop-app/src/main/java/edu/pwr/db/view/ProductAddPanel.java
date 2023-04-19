package edu.pwr.db.view;

import com.mysql.jdbc.Connection;
import edu.pwr.db.Logger;
import edu.pwr.db.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductAddPanel extends JPanel {
    protected final JComboBox<Item> brands, colors, coverageLevels, types;
    protected final JTextArea inputName, newBrand, newColor, newCoverageLevelName, newCoverageLevelValue, newType;
    protected final AppWindow appWindow;

    protected final JButton add;
    public ProductAddPanel(AppWindow appWindow) {
        this.appWindow = appWindow;
        JPanel panel = new JPanel();
        GridBagConstraints gc = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 0, 0);

        JLabel jlBrand = new JLabel("brand:");
        gc.anchor = GridBagConstraints.WEST;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 2;
        gc.weighty = 1;
        gc.weightx = 1;
        panel.add(jlBrand, gc);

        newBrand = new JTextAreaWithPlaceholder("new brand");
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridheight = 1;
        gc.gridwidth = 2;
        gc.gridx++;
        gc.weightx = 0;
        gc.weighty = 1;
        panel.add(newBrand, gc);

        brands = new JComboBox<>();
        brands.addItem(Item.NOT_SELECTED);
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy++;
        gc.weightx = 5;
        panel.add(brands, gc);

        gc.weightx = 0;

        JLabel jlColor = new JLabel("color:");
        gc.anchor = GridBagConstraints.WEST;
        gc.gridx = 0;
        gc.gridy++;
        gc.gridwidth = 1;
        gc.gridheight = 2;
        panel.add(jlColor, gc);

        newColor = new JTextAreaWithPlaceholder("new color");
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        gc.gridx++;
        panel.add(newColor, gc);

        colors = new JComboBox<>();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy++;
        colors.addItem(Item.NOT_SELECTED);
        panel.add(colors, gc);

        JLabel jlLevel = new JLabel("coverage level: ");
        gc.anchor = GridBagConstraints.WEST;
        gc.gridwidth = 1;
        gc.gridheight = 2;
        gc.gridx = 0;
        gc.gridy++;
        panel.add(jlLevel, gc);

        newCoverageLevelName = new JTextAreaWithPlaceholder("new coverage level name");
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridheight = 1;
        gc.gridx++;
        gc.weightx = 0.5;
        panel.add(newCoverageLevelName, gc);

        newCoverageLevelValue = new JTextAreaWithPlaceholder("new coverage level value");
        gc.gridx++;
        panel.add(newCoverageLevelValue, gc);

        coverageLevels = new JComboBox<>();
        coverageLevels.addItem(Item.NOT_SELECTED);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx--;
        gc.gridy++;
        gc.gridwidth = 2;
        panel.add(coverageLevels, gc);

        JLabel jlTypes = new JLabel("type:");
        gc.anchor = GridBagConstraints.WEST;
        gc.gridx = 0;
        gc.gridy++;
        gc.gridwidth = 1;
        gc.gridheight = 2;
        panel.add(jlTypes, gc);

        newType = new JTextAreaWithPlaceholder("new type");
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx++;
        gc.gridheight = 1;
        gc.gridwidth = 2;
        panel.add(newType, gc);

        types = new JComboBox<>();
        types.addItem(Item.NOT_SELECTED);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy++;
        panel.add(types, gc);

        JLabel jlName = new JLabel("name:");
        gc.gridx = 0;
        gc.gridy++;
        gc.gridwidth = 1;
        panel.add(jlName,gc);

        inputName = new JTextAreaWithPlaceholder("Enter product name");
        gc.gridx++;
        gc.gridwidth = 2;
        //gc.weighty = 2;
        gc.fill = GridBagConstraints.BOTH;
        panel.add(inputName,gc);

        add = new JButton("Add");
        gc.gridx = 0;
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weighty = 0.7;

        gc.gridwidth = 3;
        panel.add(add, gc);

        add.addActionListener(e -> {
            try {
                String brand, color, coverageName, type, name;
                int coverageValue;
                Item selected = (Item) brands.getSelectedItem();
                if (selected instanceof SmallItem) {
                    brand = ((SmallItem) selected).getName();
                }
                else {
                    brand = newBrand.getText();
                }

                selected = (Item) colors.getSelectedItem();
                if (selected instanceof SmallItem) {
                    color = ((SmallItem) selected).getName();
                }
                else {
                    color = newColor.getText();
                }

                selected = (Item) coverageLevels.getSelectedItem();
                if (selected instanceof CoverageLevelItem) {
                    coverageName = ((CoverageLevelItem) selected).getName();
                    coverageValue = ((CoverageLevelItem) selected).getNumericValue();
                }
                else {
                    coverageName = newCoverageLevelName.getText();
                    coverageValue = Integer.parseInt(newCoverageLevelValue.getText());
                }

                selected = (Item) types.getSelectedItem();
                if (selected instanceof SmallItem) {
                    type = ((SmallItem) selected).getName();
                }
                else {
                    type = newType.getText();
                }

                name = inputName.getText();
                addProduct(brand,color,coverageName,coverageValue,type,name);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(appWindow, "Wrong input", "Warning", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setBorder(new EmptyBorder(20, 20, 30, 30));
    }

    public void refreshContents() {
        try {
            DBConnection connection = appWindow.getDbConnection();
            SmallItemJdbcTemplate template;
            List<Item> list;

            template = connection.getSmallItemTemplate("colors");
            list = template.list();
            colors.removeAllItems();
            colors.addItem(Item.NOT_SELECTED);
            for (Item item : list) {
                colors.addItem(item);
            }
            template = connection.getSmallItemTemplate("brands");
            list = template.list();
            brands.removeAllItems();
            brands.addItem(Item.NOT_SELECTED);
            for (Item item : list) {
                brands.addItem(item);
            }
            template = connection.getSmallItemTemplate("types");
            list = template.list();
            types.removeAllItems();
            types.addItem(Item.NOT_SELECTED);
            for (Item item : list) {
                types.addItem(item);
            }
            var coverageTemplate = connection.getCoverageLevelTemplate();
            list = coverageTemplate.list();
            coverageLevels.removeAllItems();
            coverageLevels.addItem(Item.NOT_SELECTED);
            for (Item item : list) {
                coverageLevels.addItem(item);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addProduct(String brand, String color, String coverageName, int coverageValue, String type, String name){
        CallableStatement callableStatement;
        try {Connection conn = (Connection) appWindow.getDbConnection().getConn();
        String SQL = "{CALL addProduct(?,?,?,?,?,?)}";
            callableStatement = conn.prepareCall(SQL);
            callableStatement.setString(1, brand);
            callableStatement.setString(2,color);
            callableStatement.setString(3,coverageName);
            callableStatement.setInt(4,coverageValue);
            callableStatement.setString(5,type);
            callableStatement.setString(6,name);
            callableStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
