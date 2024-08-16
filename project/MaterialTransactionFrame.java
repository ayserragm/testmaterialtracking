/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;
  

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ayseserra
 */
public class MaterialTransactionFrame extends JFrame {

    private User currentUser;
    private JPanel panel;
    private JComboBox<String> categoryComboBox;
    private JTextField quantityField;
    private JButton increaseButton;
    private JButton decreaseButton;

    public MaterialTransactionFrame(User user) {
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        panel = new JPanel();
        categoryComboBox = new JComboBox<>();
        quantityField = new JTextField();
        increaseButton = new JButton("Increase Quantity");
        decreaseButton = new JButton("Decrease Quantity");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(null);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(50, 50, 80, 20);
        panel.add(categoryLabel);

        categoryComboBox.setBounds(150, 50, 150, 20);
        panel.add(categoryComboBox);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 100, 80, 20);
        panel.add(quantityLabel);

        quantityField.setBounds(150, 100, 150, 20);
        panel.add(quantityField);

        increaseButton.setBounds(50, 150, 150, 30);
        panel.add(increaseButton);

        decreaseButton.setBounds(220, 150, 150, 30);
        panel.add(decreaseButton);

        fillCategoryComboBox();

        increaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTransaction(true);
            }
        });

        decreaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTransaction(false);
            }
        });

        add(panel);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fillCategoryComboBox() {
        ArrayList<String> materials = Database.SelectAllCategories();
        System.out.println("Categories from database: " + materials); 

        for (String material : materials) {
            categoryComboBox.addItem(material);
        }
    }

    private void performTransaction(boolean isIncrease) {
    String category = categoryComboBox.getSelectedItem().toString();
    String quantityText = quantityField.getText();

    if (quantityText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a quantity.");
        return;
    }

    int quantity;
    try {
        quantity = Integer.parseInt(quantityText);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
        return;
    }

    ResultSet materialResultSet = Database.SelectMaterialsByCategory(category);
    try {
        System.out.println("Performing transaction for category: " + category);
        System.out.println("Quantity: " + quantity);
        System.out.println("ResultSet empty: " + !materialResultSet.next());

        if (!materialResultSet.next()) {
            JOptionPane.showMessageDialog(this, "No material found for the selected category.");
        } else {
            int materialId = materialResultSet.getInt("materialId");

            if (isIncrease) {
                boolean success = Database.IncreaseMaterialQuantity(materialId, quantity);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Quantity increased successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to increase quantity.");
                }
            } else {
                boolean success = Database.DecreaseMaterialQuantity(materialId, quantity);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Quantity decreased successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to decrease quantity.");
                }
            }
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (materialResultSet != null) {
                materialResultSet.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

    public static void main(String[] args) {
        User currentUser = new User();
        new MaterialTransactionFrame(currentUser);
    }
}
