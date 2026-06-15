package com.kelompok4.view.panels;

import com.kelompok4.controller.*;
import com.kelompok4.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DashboardAdminPanel extends JPanel {
    private JLabel totalUserLabel, totalSupplierLabel, totalBuyerLabel, totalBarangLabel;
    private JTable userTable, supplierTable, buyerTable, barangTable;
    private JTextField searchUserField, searchSupplierField, searchBuyerField, searchBarangField;
    private UserController userController;
    private SupplierController supplierController;
    private BuyerController buyerController;
    private BarangController barangController;
    private JLabel createStatCard(String title, String value, Color color) {

        JLabel label = new JLabel(
                "<html><center>"
                + "<font color='white' size='4'>" + title + "</font><br>"
                + "<font color='white' size='7'><b>" + value + "</b></font>"
                + "</center></html>"
        );

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(color);

        label.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color.darker(), 2),
                        BorderFactory.createEmptyBorder(15, 10, 15, 10)
                )
        );

        return label;
    }

    public DashboardAdminPanel() {
        userController = new UserController();
        supplierController = new SupplierController();
        buyerController = new BuyerController();
        barangController = new BarangController();

        setLayout(new BorderLayout());

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        totalUserLabel = createStatCard("Users", "0", new Color(159, 161, 255));
        totalSupplierLabel = createStatCard("Supplier", "0", new Color(181, 186, 255));
        totalBuyerLabel = createStatCard("Buyer", "0", new Color(174, 226, 255));
        totalBarangLabel = createStatCard("Barang", "0", new Color(217, 249, 223));

        statsPanel.add(totalUserLabel);
        statsPanel.add(totalSupplierLabel);
        statsPanel.add(totalBuyerLabel);
        statsPanel.add(totalBarangLabel);

        add(statsPanel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Users", createUserPanel());
        tabs.addTab("Suppliers", createSupplierPanel());
        tabs.addTab("Buyers", createBuyerPanel());
        tabs.addTab("Barang", createBarangPanel());
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        searchUserField = new JTextField(20);
        top.add(searchUserField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchUsers());
        top.add(searchBtn);
        panel.add(top, BorderLayout.NORTH);
        userTable = new JTable();
        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSupplierPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        searchSupplierField = new JTextField(20);
        top.add(searchSupplierField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchSuppliers());
        top.add(searchBtn);
        panel.add(top, BorderLayout.NORTH);
        supplierTable = new JTable();
        panel.add(new JScrollPane(supplierTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBuyerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        searchBuyerField = new JTextField(20);
        top.add(searchBuyerField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchBuyers());
        top.add(searchBtn);
        panel.add(top, BorderLayout.NORTH);
        buyerTable = new JTable();
        panel.add(new JScrollPane(buyerTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBarangPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        searchBarangField = new JTextField(20);
        top.add(searchBarangField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchBarang());
        top.add(searchBtn);
        panel.add(top, BorderLayout.NORTH);
        barangTable = new JTable();
        panel.add(new JScrollPane(barangTable), BorderLayout.CENTER);
        return panel;
    }

    private void refreshStats() {
        try {
            totalUserLabel.setText(
                    "<html><center>Users<br><font size='7'><b>"
                    + userController.countUsers()
                    + "</b></font></center></html>"
            );

            totalSupplierLabel.setText(
                    "<html><center>Supplier<br><font size='7'><b>"
                    + supplierController.countSuppliers()
                    + "</b></font></center></html>"
            );

            totalBuyerLabel.setText(
                    "<html><center>Buyer<br><font size='7'><b>"
                    + buyerController.countBuyers()
                    + "</b></font></center></html>"
            );

            totalBarangLabel.setText(
                    "<html><center>Barang<br><font size='7'><b>"
                    + barangController.countBarang()
                    + "</b></font></center></html>"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try {
            List<User> list = userController.getAllUsers();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Email", "Nama", "Role"}, 0);
            for (User u : list) model.addRow(new Object[]{u.getId(), u.getEmail(), u.getNama(), u.getRole()});
            userTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void searchUsers() {
        String kw = searchUserField.getText().trim();
        try {
            List<User> list = userController.searchUsers(kw);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Email", "Nama", "Role"}, 0);
            for (User u : list) model.addRow(new Object[]{u.getId(), u.getEmail(), u.getNama(), u.getRole()});
            userTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadSuppliers() {
        try {
            List<Supplier> list = supplierController.getAllSuppliers();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak", "Alamat"}, 0);
            for (Supplier s : list) model.addRow(new Object[]{s.getId(), s.getNama(), s.getKontak(), s.getAlamat()});
            supplierTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void searchSuppliers() {
        String kw = searchSupplierField.getText().trim();
        try {
            List<Supplier> list = supplierController.searchSuppliers(kw);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak", "Alamat"}, 0);
            for (Supplier s : list) model.addRow(new Object[]{s.getId(), s.getNama(), s.getKontak(), s.getAlamat()});
            supplierTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadBuyers() {
        try {
            List<Buyer> list = buyerController.getAllBuyers();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak", "Alamat"}, 0);
            for (Buyer b : list) model.addRow(new Object[]{b.getId(), b.getNama(), b.getKontak(), b.getAlamat()});
            buyerTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void searchBuyers() {
        String kw = searchBuyerField.getText().trim();
        try {
            List<Buyer> list = buyerController.searchBuyers(kw);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak", "Alamat"}, 0);
            for (Buyer b : list) model.addRow(new Object[]{b.getId(), b.getNama(), b.getKontak(), b.getAlamat()});
            buyerTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadBarang() {
        try {
            List<Barang> list = barangController.getAllBarang();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Kategori", "Stok", "Minimal"}, 0);
            for (Barang b : list) model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getKategori(), b.getStokSaatIni(), b.getStokMinimum()});
            barangTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void searchBarang() {
        String kw = searchBarangField.getText().trim();
        try {
            List<Barang> list = barangController.searchBarang(kw);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Kategori", "Stok", "Minimal"}, 0);
            for (Barang b : list) model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getKategori(), b.getStokSaatIni(), b.getStokMinimum()});
            barangTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void refreshAll() {
        refreshStats();
        loadUsers();
        loadSuppliers();
        loadBuyers();
        loadBarang();
    }
}