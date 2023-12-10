package tp.magazinchik;

import tp.magazinchik.controller.KontrollerMagazina;
import tp.magazinchik.database.ManagerDB;
import tp.magazinchik.gui.MagazinPanel;

import javax.swing.*;

public class Shop {
    public static void main(String[] args) {
        ManagerDB database = new ManagerDB();
        KontrollerMagazina controller = new KontrollerMagazina(database);

        SwingUtilities.invokeLater(() -> {
            MagazinPanel magazinPanel = new MagazinPanel(controller);
            magazinPanel.setSize(600, 300);
            magazinPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            magazinPanel.setLocationRelativeTo(null);
            magazinPanel.setVisible(true);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Подключение закрыто.");
                database.closeConnection();
            }));
        });
    }
}
