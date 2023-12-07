package tp.magazinchik.gui;

import tp.magazinchik.model.Product;
import tp.magazinchik.model.Sections;
import tp.magazinchik.controller.KontrollerMagazina;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class MagazinPanel extends JFrame {

    private KontrollerMagazina controller;

    public MagazinPanel(KontrollerMagazina controller) {
        this.controller = controller;

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Отделы", createSectionsPanel());
        tabbedPane.addTab("Товары", createProductsPanel());

        add(tabbedPane);
        setTitle("Магазин");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createSectionsPanel() {
        JPanel sectionsPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JButton addButton = new JButton("Добавить отдел");
        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Введите название отдела:");
            String workingHours = JOptionPane.showInputDialog("Введите часы работы отдела:");
            controller.addSection(name, workingHours);
        });

        JButton editButton = new JButton("Редактировать отдел");
        editButton.addActionListener(e -> {
            List<Sections> allSections = controller.getAllSections();
            Sections selectedSection = (Sections) JOptionPane.showInputDialog(
                    this,
                    "Выберите отдел для редактирования:",
                    "Редактировать отдел",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    allSections.toArray(),
                    allSections.get(0)
            );

            if (selectedSection != null) {
                String newName = JOptionPane.showInputDialog("Введите новое название отдела:", selectedSection.getName());
                String newWorkingHours = JOptionPane.showInputDialog("Введите новые часы работы отдела:", selectedSection.getWorkingHours());
                controller.editSection(selectedSection.getId(), newName, newWorkingHours);
            }
        });

        JButton allSectionsButton = new JButton("Все отделы");
        allSectionsButton.addActionListener(e -> {
            List<Sections> allSections = controller.getAllSections();
            StringBuilder message = new StringBuilder("Список отделов:\n");
            for (Sections section : allSections) {
                message.append(section).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Все отделы", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton removeButton = new JButton("Удалить отдел");
        removeButton.addActionListener(e -> {
            List<Sections> allSections = controller.getAllSections();
            Sections selectedSection = (Sections) JOptionPane.showInputDialog(
                    this,
                    "Выберите отдел для удаления:",
                    "Удалить отдел",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    allSections.toArray(),
                    allSections.get(0)
            );

            if (selectedSection != null) {
                controller.removeSection(selectedSection.getId());
            }
        });

        JButton productsInSectionButton = new JButton("Товары в отделе");
        productsInSectionButton.addActionListener(e -> {
            List<Sections> allSections = controller.getAllSections();
            Sections selectedSection = (Sections) JOptionPane.showInputDialog(
                    this,
                    "Выберите отдел:",
                    "Товары в отделе",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    allSections.toArray(),
                    allSections.get(0)
            );

            if (selectedSection != null) {
                List<Product> productsInSection = controller.getProductsInSection(selectedSection.getId());
                displayProducts(productsInSection, "Товары в отделе '" + selectedSection.getName() + "':");
            }
        });

        sectionsPanel.add(addButton);
        sectionsPanel.add(editButton);
        sectionsPanel.add(allSectionsButton);
        sectionsPanel.add(removeButton);
        sectionsPanel.add(productsInSectionButton);

        return sectionsPanel;

    }

    private JPanel createProductsPanel() {
        JPanel productsPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JButton addButton = new JButton("Добавить товар");
        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Введите название товара:");
            double price = Double.parseDouble(JOptionPane.showInputDialog("Введите цену товара:"));
            List<Sections> allSections = controller.getAllSections();
            Sections selectedSection = (Sections) JOptionPane.showInputDialog(
                    this,
                    "Выберите отдел для товара:",
                    "Выберите отдел",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    allSections.toArray(),
                    allSections.get(0)
            );

            if (selectedSection != null) {
                controller.addProduct(name, price, selectedSection.getId());
            }
        });

        JButton editButton = new JButton("Редактировать товар");
        editButton.addActionListener(e -> {
            List<Product> allProducts = controller.getAllProducts();
            Product selectedProduct = (Product) JOptionPane.showInputDialog(
                    this,
                    "Выберите товар для редактирования:",
                    "Редактировать товар",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    allProducts.toArray(),
                    allProducts.get(0)
            );

            if (selectedProduct != null) {
                String newName = JOptionPane.showInputDialog("Введите новое название товара:", selectedProduct.getName());
                double newPrice = Double.parseDouble(JOptionPane.showInputDialog("Введите новую цену товара:", selectedProduct.getPrice()));
                controller.editProduct(selectedProduct.getId(), newName, newPrice);
            }
        });

        JButton removeButton = new JButton("Удалить товар");
        removeButton.addActionListener(e -> {
            List<Product> allProducts = controller.getAllProducts();
            Product selectedProduct = (Product) JOptionPane.showInputDialog(
                    this,
                    "Выберите товар для удаления:",
                    "Удалить товар",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    allProducts.toArray(),
                    allProducts.get(0)
            );

            if (selectedProduct != null) {
                controller.removeProduct(selectedProduct.getId());
            }
        });

        JButton allProductsButton = new JButton("Весь список товаров");
        allProductsButton.addActionListener(e -> {
            List<Product> allProducts = controller.getAllProducts();
            StringBuilder message = new StringBuilder("Список товаров:\n");
            for (Product product : allProducts) {
                message.append(product).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Весь список товаров", JOptionPane.INFORMATION_MESSAGE);
        });

        productsPanel.add(addButton);
        productsPanel.add(editButton);
        productsPanel.add(removeButton);
        productsPanel.add(allProductsButton);

        return productsPanel;
    }

    private void displayProducts(List<Product> products, String title) {
        StringBuilder message = new StringBuilder(title + "\n");
        for (Product product : products) {
            message.append(product).append("\n");
        }
        JOptionPane.showMessageDialog(this, message.toString(), title, JOptionPane.INFORMATION_MESSAGE);
    }


}
