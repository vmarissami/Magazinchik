package tp.magazinchik.database;

import tp.magazinchik.model.Product;
import tp.magazinchik.model.Sections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDB {
    private static final String DATABASE_URL = "jdbc:sqlite:magazin.db";

    public ManagerDB() {
        createTables();
    }

    private void createTables() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            // Создание таблицы отделов
            statement.execute("CREATE TABLE IF NOT EXISTS sections (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "working_hours TEXT)");

            // Создание таблицы товаров
            statement.execute("CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "price REAL NOT NULL)");

            // Создание таблицы связи между отделами и товарами
            statement.execute("CREATE TABLE IF NOT EXISTS section_products (" +
                    "product_id INTEGER," +
                    "section_id INTEGER," +
                    "FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (section_id) REFERENCES sections(id) ON DELETE CASCADE)");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addSection(Sections section) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO sections (name, working_hours) VALUES (?, ?)")) {

            statement.setString(1, section.getName());
            statement.setString(2, section.getWorkingHours());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSection(int sectionId) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM sections WHERE id = ?")) {

            statement.setInt(1, sectionId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sections> getAllSections() {
        List<Sections> sections = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM sections")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String workingHours = resultSet.getString("working_hours");
                sections.add(new Sections(id, name, workingHours));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sections;
    }

    public void addProduct(Product product) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO products (name, price) VALUES (?, ?)")) {

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();

            try (Statement lastIdStatement = connection.createStatement();
                 ResultSet resultSet = lastIdStatement.executeQuery("SELECT last_insert_rowid()")) {

                if (resultSet.next()) {
                    int productId = resultSet.getInt(1);
                    product.setId(productId);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void removeProduct(int productId) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?")) {

            statement.setInt(1, productId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                products.add(new Product(id, name, price));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public void assignProductToSection(int productId, int sectionId) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO section_products (product_id, section_id) VALUES (?, ?)")) {

            statement.setInt(1, productId);
            statement.setInt(2, sectionId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProductsInSection(int sectionId) {
        List<Product> productsInSection = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT p.* FROM products p " +
                             "JOIN section_products sp ON p.id = sp.product_id " +
                             "WHERE sp.section_id = ?")) {

            statement.setInt(1, sectionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    productsInSection.add(new Product(id, name, price));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productsInSection;
    }


    public void editSection(int sectionId, String newName, String newWorkingHours) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("UPDATE sections SET name = ?, working_hours = ? WHERE id = ?")) {

            statement.setString(1, newName);
            statement.setString(2, newWorkingHours);
            statement.setInt(3, sectionId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editProduct(int productId, String newName, double newPrice) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("UPDATE products SET name = ?, price = ? WHERE id = ?")) {

            statement.setString(1, newName);
            statement.setDouble(2, newPrice);
            statement.setInt(3, productId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection() {
        try {
            DriverManager.getConnection(DATABASE_URL).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
