package tp.magazinchik.controller;

import tp.magazinchik.database.ManagerDB;
import tp.magazinchik.model.Product;
import tp.magazinchik.model.Sections;

import java.util.List;

public class KontrollerMagazina {
    private final ManagerDB database;

    public KontrollerMagazina(ManagerDB database) {
        this.database = database;
    }

    public void addSection(String name, String workingHours) {
        Sections section = new Sections(0, name, workingHours);
        database.addSection(section);
    }

    public void removeSection(int sectionId) {
        database.removeSection(sectionId);
    }

    public List<Sections> getAllSections() {
        return database.getAllSections();
    }

    public void addProduct(String name, double price, int sectionId) {
        Product product = new Product(0, name, price);
        database.addProduct(product);

        if (sectionId != -1) {
            database.assignProductToSection(product.getId(), sectionId);
        }
    }


    public void removeProduct(int productId) {
        database.removeProduct(productId);
    }

    public List<Product> getAllProducts() {
        return database.getAllProducts();
    }


    public List<Product> getProductsInSection(int sectionId) {
        return database.getProductsInSection(sectionId);
    }

    public void editSection(int sectionId, String newName, String newWorkingHours) {
        database.editSection(sectionId, newName, newWorkingHours);
    }

    public void editProduct(int productId, String newName, double newPrice) {
        database.editProduct(productId, newName, newPrice);
    }

}