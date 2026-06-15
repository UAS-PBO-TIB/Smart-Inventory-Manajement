package com.kelompok4.view.panels;

import com.kelompok4.controller.BarangController;
import com.kelompok4.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class ManageBarangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private BarangController barangController;
    private JTextField searchField;
    private JComboBox<String> filterTipe;
    private void highlightCriticalStock() {

        table.setDefaultRenderer(
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
                        table.getValueAt(row, 4).toString()
                );

                int minimal = Integer.parseInt(
                        table.getValueAt(row, 5).toString()
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

    public ManageBarangPanel() {
        barangController = new BarangController();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Cari:"));
        searchField = new JTextField(15);
        topPanel.add(searchField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchBarang());
        topPanel.add(searchBtn);
        topPanel.add(new JLabel("Filter Tipe:"));
        filterTipe = new JComboBox<>(new String[]{"Semua", "Elektronik", "ATK"});
        filterTipe.addActionListener(e -> filterBarang());
        topPanel.add(filterTipe);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadBarang());
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Kategori", "Stok", "Minimal", "Tipe", "Detail"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Tambah");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Hapus");
        addBtn.addActionListener(e -> tambahBarang());
        editBtn.addActionListener(e -> editBarang());
        deleteBtn.addActionListener(e -> hapusBarang());
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadBarang();
    }

    public void loadBarang() {
        try {
            List<Barang> list = barangController.getAllBarang();
            refreshTable(list);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void searchBarang() {
        String keyword = searchField.getText().trim();
        try {
            List<Barang> list = barangController.searchBarang(keyword);
            refreshTable(list);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void filterBarang() {
        String tipe = (String) filterTipe.getSelectedItem();
        if ("Semua".equals(tipe)) {
            loadBarang();
        } else {
            try {
                List<Barang> list = barangController.filterByTipe(tipe);
                refreshTable(list);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void refreshTable(List<Barang> list) {
        model.setRowCount(0);
        for (Barang b : list) {
            String detail = "";
            if (b instanceof Elektronik) detail = "Garansi: " + ((Elektronik) b).getGaransiBulan() + " bln";
            else if (b instanceof ATK) detail = "Ukuran: " + ((ATK) b).getUkuran();
            model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getKategori(), b.getStokSaatIni(), b.getStokMinimum(), b.getTipeBarang(), detail});
        }
        
        highlightCriticalStock();
    }

    private void tambahBarang() {
        JComboBox<String> tipeCombo = new JComboBox<>(new String[]{"Elektronik", "ATK"});
        JTextField kode = new JTextField();
        JTextField nama = new JTextField();
        JTextField kategori = new JTextField();
        JTextField stok = new JTextField();
        JTextField minStok = new JTextField();
        JTextField detail1 = new JTextField(); // garansi atau ukuran
        JLabel detailLabel = new JLabel("Garansi (bulan):");
        tipeCombo.addActionListener(e -> {
            if ("Elektronik".equals(tipeCombo.getSelectedItem())) detailLabel.setText("Garansi (bulan):");
            else detailLabel.setText("Ukuran:");
        });
        Object[] fields = {"Tipe:", tipeCombo, "Kode Barang:", kode, "Nama:", nama, "Kategori:", kategori, "Stok Awal:", stok, "Stok Minimum:", minStok, detailLabel, detail1};
        int result = JOptionPane.showConfirmDialog(this, fields, "Tambah Barang", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String tipe = (String) tipeCombo.getSelectedItem();
                Barang b;
                if ("Elektronik".equals(tipe)) {
                    b = new Elektronik(0, kode.getText(), nama.getText(), kategori.getText(),
                            Integer.parseInt(stok.getText()), Integer.parseInt(minStok.getText()), tipe, Integer.parseInt(detail1.getText()));
                } else {
                    b = new ATK(0, kode.getText(), nama.getText(), kategori.getText(),
                            Integer.parseInt(stok.getText()), Integer.parseInt(minStok.getText()), tipe, detail1.getText());
                }
                barangController.addBarang(b);
                loadBarang();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid: " + ex.getMessage());
            }
        }
    }

    private void editBarang() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) model.getValueAt(row, 0);
        try {
            Barang b = barangController.getBarangById(id);
            JTextField kode = new JTextField(b.getKodeBarang());
            JTextField nama = new JTextField(b.getNama());
            JTextField kategori = new JTextField(b.getKategori());
            JTextField minStok = new JTextField(String.valueOf(b.getStokMinimum()));
            JTextField detail = new JTextField();
            String detailLabel = "";
            if (b instanceof Elektronik) {
                detailLabel = "Garansi (bulan):";
                detail.setText(String.valueOf(((Elektronik) b).getGaransiBulan()));
            } else if (b instanceof ATK) {
                detailLabel = "Ukuran:";
                detail.setText(((ATK) b).getUkuran());
            }
            Object[] fields = {"Kode:", kode, "Nama:", nama, "Kategori:", kategori, "Stok Minimum:", minStok, detailLabel, detail};
            int result = JOptionPane.showConfirmDialog(this, fields, "Edit Barang", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                b.setKodeBarang(kode.getText());
                b.setNama(nama.getText());
                b.setKategori(kategori.getText());
                b.setStokMinimum(Integer.parseInt(minStok.getText()));
                if (b instanceof Elektronik) ((Elektronik) b).setGaransiBulan(Integer.parseInt(detail.getText()));
                else if (b instanceof ATK) ((ATK) b).setUkuran(detail.getText());
                barangController.updateBarang(b);
                loadBarang();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void hapusBarang() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus barang?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                barangController.deleteBarang(id);
                loadBarang();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage());
            }
        }
    }
}