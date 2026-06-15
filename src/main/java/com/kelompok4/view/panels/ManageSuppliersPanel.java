package com.kelompok4.view.panels;

import com.kelompok4.controller.SupplierController;
import com.kelompok4.model.Supplier;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ManageSuppliersPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private SupplierController supplierController;
    private JTextField searchField;

    public ManageSuppliersPanel() {
        supplierController = new SupplierController();
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchSuppliers());
        searchPanel.add(searchBtn);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadSuppliers());
        searchPanel.add(refreshBtn);
        add(searchPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak", "Alamat"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Tambah");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Hapus");
        addBtn.addActionListener(e -> tambahSupplier());
        editBtn.addActionListener(e -> editSupplier());
        deleteBtn.addActionListener(e -> hapusSupplier());
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadSuppliers();
    }

    public void loadSuppliers() {
        try {
            List<Supplier> list = supplierController.getAllSuppliers();
            model.setRowCount(0);
            for (Supplier s : list) {
                model.addRow(new Object[]{s.getId(), s.getNama(), s.getKontak(), s.getAlamat()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void searchSuppliers() {
        String keyword = searchField.getText().trim();
        try {
            List<Supplier> list = supplierController.searchSuppliers(keyword);
            model.setRowCount(0);
            for (Supplier s : list) {
                model.addRow(new Object[]{s.getId(), s.getNama(), s.getKontak(), s.getAlamat()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void tambahSupplier() {
        JTextField nama = new JTextField();
        JTextField kontak = new JTextField();
        JTextField alamat = new JTextField();
        Object[] fields = {"Nama:", nama, "Kontak:", kontak, "Alamat:", alamat};
        int result = JOptionPane.showConfirmDialog(this, fields, "Tambah Supplier", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Supplier s = new Supplier(0, nama.getText(), kontak.getText(), alamat.getText());
                supplierController.addSupplier(s);
                loadSuppliers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage());
            }
        }
    }

    private void editSupplier() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) model.getValueAt(row, 0);
        try {
            Supplier s = supplierController.getSupplierById(id);
            JTextField nama = new JTextField(s.getNama());
            JTextField kontak = new JTextField(s.getKontak());
            JTextField alamat = new JTextField(s.getAlamat());
            Object[] fields = {"Nama:", nama, "Kontak:", kontak, "Alamat:", alamat};
            int result = JOptionPane.showConfirmDialog(this, fields, "Edit Supplier", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                s.setNama(nama.getText());
                s.setKontak(kontak.getText());
                s.setAlamat(alamat.getText());
                supplierController.updateSupplier(s);
                loadSuppliers();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void hapusSupplier() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                supplierController.deleteSupplier(id);
                loadSuppliers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage());
            }
        }
    }
}