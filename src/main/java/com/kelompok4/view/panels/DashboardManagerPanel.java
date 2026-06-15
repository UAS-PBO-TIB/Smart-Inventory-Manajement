package com.kelompok4.view.panels;

import com.kelompok4.controller.BarangController;
import com.kelompok4.controller.BuyerController;
import com.kelompok4.controller.SupplierController;
import com.kelompok4.model.ATK;
import com.kelompok4.model.Barang;
import com.kelompok4.model.Buyer;
import com.kelompok4.model.Elektronik;
import com.kelompok4.model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class DashboardManagerPanel extends JPanel {
    private JLabel totalBarangLabel, totalKritisLabel;
    private JTable barangTable, kritisTable, topSupplierTable, topBuyerTable;
    private BarangController barangController;
    private SupplierController supplierController;
    private BuyerController buyerController;
    private JTextField searchKritisField, searchSupplierField, searchBuyerField, searchBarangField;
    private JComboBox<String> filterTipeBarang, filterTipeKritis;
    private DefaultTableModel model;
    private JLabel createStatCard(String title, String value, Color bcolor, Color fcolor) {
        JLabel label = new JLabel(
                "<html><center>"
                + "<div style='font-size:12px'>" + title + "</div>"
                + "<div style='font-size:28px'><b>" + value + "</b></div>"
                + "</center></html>"
        );

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(bcolor);
        label.setForeground(fcolor);

        label.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bcolor.darker(), 2),
                        BorderFactory.createEmptyBorder(15, 10, 15, 10)
                )
        );

        return label;
    }
    private void highlightCriticalStock() {

        barangTable.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
            ) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

                int stok = Integer.parseInt(
                        table.getValueAt(row, 3).toString()
                );

                int minimal = Integer.parseInt(
                        table.getValueAt(row, 4).toString()
                );

                if (isSelected) {

                    if (stok <= minimal) {
                        // kritis + dipilih
                        c.setBackground(new Color(150, 0, 0));
                        c.setForeground(Color.WHITE);

                    } else {
                        // normal + dipilih
                        c.setBackground(new Color(52, 73, 94));
                        c.setForeground(Color.WHITE);
                    }

                } else {

                    // odd even
                    if (row % 2 == 0) {
                        c.setBackground(new Color(245, 245, 245));
                    } else {
                        c.setBackground(Color.WHITE);
                    }

                    c.setForeground(Color.BLACK);

                    // stok kritis
                    if (stok <= minimal) {
                        c.setBackground(new Color(231, 76, 60));
                        c.setForeground(Color.WHITE);
                    }
                }

                return c;
            }
        }
        );
    }

    public DashboardManagerPanel() {
        barangController = new BarangController();
        supplierController = new SupplierController();
        buyerController = new BuyerController();
        
        setLayout(new BorderLayout());

        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        totalBarangLabel = createStatCard("Total Barang", "0", new Color(217, 249, 223), new Color(13, 12, 12));
        totalKritisLabel = createStatCard("Stok Kritis", "0", new Color(227, 86, 98), new Color(255, 242, 242));
        statsPanel.add(totalBarangLabel);
        statsPanel.add(totalKritisLabel);
        add(statsPanel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Semua Barang", createBarangPanel());
        tabs.addTab("Barang Kritis", createKritisPanel());
        tabs.addTab("Top Supplier", createTopSupplierPanel());
        tabs.addTab("Top Buyer", createTopBuyerPanel());
        add(tabs, BorderLayout.CENTER);
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
        top.add(new JLabel("Filter Tipe:"));
        filterTipeBarang = new JComboBox<>(new String[]{"Semua", "Elektronik", "ATK"});
        filterTipeBarang.addActionListener(e -> filterBarang());
        top.add(filterTipeBarang);
        panel.add(top, BorderLayout.NORTH);
        barangTable = new JTable();
        panel.add(new JScrollPane(barangTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createKritisPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        searchKritisField = new JTextField(20);
        top.add(searchKritisField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchBarangKritis());
        top.add(searchBtn);
        top.add(new JLabel("Filter Tipe:"));
        filterTipeKritis = new JComboBox<>(new String[]{"Semua", "Elektronik", "ATK"});
        filterTipeKritis.addActionListener(e -> filterBarangKritis());
        top.add(filterTipeKritis);
        panel.add(top, BorderLayout.NORTH);
        kritisTable = new JTable();
        panel.add(new JScrollPane(kritisTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTopSupplierPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        searchSupplierField = new JTextField(20);
        top.add(searchSupplierField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchSuppliers());
        top.add(searchBtn);
        panel.add(top, BorderLayout.NORTH);
        topSupplierTable = new JTable();
        panel.add(new JScrollPane(topSupplierTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTopBuyerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        searchBuyerField = new JTextField(20);
        top.add(searchBuyerField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchBuyers());
        top.add(searchBtn);
        panel.add(top, BorderLayout.NORTH);
        topBuyerTable = new JTable();
        panel.add(new JScrollPane(topBuyerTable), BorderLayout.CENTER);
        return panel;
    }
    
    private void searchSuppliers() {
        String kw = searchSupplierField.getText().trim();
        try {
            List<Supplier> list = supplierController.searchSuppliers(kw);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak"}, 0);
            for (Supplier s : list) model.addRow(new Object[]{s.getId(), s.getNama(), s.getKontak()});
            topSupplierTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    private void searchBuyers() {
        String kw = searchBuyerField.getText().trim();
        try {
            List<Buyer> list = buyerController.searchBuyers(kw);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak"}, 0);
            for (Buyer b : list) model.addRow(new Object[]{b.getId(), b.getNama(), b.getKontak()});
            topBuyerTable.setModel(model);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    private void searchBarang() {
        String kw = searchBarangField.getText().trim();
        try {
            List<Barang> list = barangController.searchBarang(kw);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Stok", "Minimal"}, 0);
            for (Barang b : list) model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getStokSaatIni(), b.getStokMinimum()});
            refreshBarangTable(list);
            highlightCriticalStock();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    private void searchBarangKritis() {
        String kw = searchKritisField.getText().trim();
        try {
            List<Barang> list = barangController.searchBarangKritis(kw);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Stok", "Minimal"}, 0);
            for (Barang b : list) model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getStokSaatIni(), b.getStokMinimum()});
            refreshKritisTable(list);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    private void filterBarang() {
        String tipe = (String) filterTipeBarang.getSelectedItem();
        if ("Semua".equals(tipe)) {
            loadAllBarang();
        } else {
            try {
                List<Barang> list = barangController.filterByTipe(tipe);
                refreshBarangTable(list);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    
    private void filterBarangKritis() {
        String tipe = (String) filterTipeKritis.getSelectedItem();
        if ("Semua".equals(tipe)) {
            loadKritisBarang();
        } else {
            try {
                List<Barang> list = barangController.filterByTipeKritis(tipe);
                refreshKritisTable(list);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    
    private void refreshBarangTable(List<Barang> list) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Stok", "Minimal"}, 0);
        for (Barang b : list) {
            model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getStokSaatIni(), b.getStokMinimum()});
        }
        barangTable.setModel(model);
        highlightCriticalStock();
    }
    
    private void refreshKritisTable(List<Barang> list) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Stok", "Minimal"}, 0);
        for (Barang b : list) {
            model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getStokSaatIni(), b.getStokMinimum()});
        }
        kritisTable.setModel(model);
    }

    private void refreshStats() {
        try {
            int total = barangController.countBarang();
            int kritis = barangController.countBarangKritis();
            totalBarangLabel.setText(
                    "<html><center>Total Barang<br><font size='6'><b>"
                    + total
                    + "</b></font></center></html>"
            );

            totalKritisLabel.setText(
                    "<html><center>Stok Kritis<br><font size='6'><b>"
                    + kritis
                    + "</b></font></center></html>"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllBarang() {
        try {
            List<Barang> list = barangController.getAllBarang();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Stok", "Minimal"}, 0);
            for (Barang b : list) {
                model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getStokSaatIni(), b.getStokMinimum()});
            }
            barangTable.setModel(model);
            highlightCriticalStock();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void loadKritisBarang() {
        try {
            List<Barang> list = barangController.getBarangKritis();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Stok", "Minimal"}, 0);
            for (Barang b : list) {
                model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getStokSaatIni(), b.getStokMinimum()});
            }
            kritisTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void loadTopSupplier() {
        try {
            com.kelompok4.controller.SupplierController sc = new com.kelompok4.controller.SupplierController();
            List<Supplier> list = sc.getTopSuppliers(5);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak"}, 0);
            for (Supplier s : list) {
                model.addRow(new Object[]{s.getId(), s.getNama(), s.getKontak()});
            }
            topSupplierTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void loadTopBuyer() {
        try {
            com.kelompok4.controller.BuyerController bc = new com.kelompok4.controller.BuyerController();
            List<Buyer> list = bc.getTopBuyers(5);
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak"}, 0);
            for (Buyer b : list) {
                model.addRow(new Object[]{b.getId(), b.getNama(), b.getKontak()});
            }
            topBuyerTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public void refreshAll() {
        refreshStats();
        loadAllBarang();
        loadKritisBarang();
        loadTopSupplier();
        loadTopBuyer();
    }
}