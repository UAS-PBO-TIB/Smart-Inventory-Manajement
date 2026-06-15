package com.kelompok4.view.panels;

import com.kelompok4.controller.BuyerController;
import com.kelompok4.model.Buyer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ManageBuyersPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private BuyerController buyerController;
    private JTextField searchField;

    public ManageBuyersPanel() {
        buyerController = new BuyerController();
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchBuyers());
        searchPanel.add(searchBtn);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadBuyers());
        searchPanel.add(refreshBtn);
        add(searchPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nama", "Kontak", "Alamat"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Tambah");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Hapus");
        addBtn.addActionListener(e -> tambahBuyer());
        editBtn.addActionListener(e -> editBuyer());
        deleteBtn.addActionListener(e -> hapusBuyer());
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadBuyers();
    }

    public void loadBuyers() {
        try {
            List<Buyer> list = buyerController.getAllBuyers();
            model.setRowCount(0);
            for (Buyer b : list) {
                model.addRow(new Object[]{b.getId(), b.getNama(), b.getKontak(), b.getAlamat()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void searchBuyers() {
        String keyword = searchField.getText().trim();
        try {
            List<Buyer> list = buyerController.searchBuyers(keyword);
            model.setRowCount(0);
            for (Buyer b : list) {
                model.addRow(new Object[]{b.getId(), b.getNama(), b.getKontak(), b.getAlamat()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void tambahBuyer() {
        JTextField nama = new JTextField();
        JTextField kontak = new JTextField();
        JTextField alamat = new JTextField();
        Object[] fields = {"Nama:", nama, "Kontak:", kontak, "Alamat:", alamat};
        int result = JOptionPane.showConfirmDialog(this, fields, "Tambah Buyer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Buyer b = new Buyer(0, nama.getText(), kontak.getText(), alamat.getText());
                buyerController.addBuyer(b);
                loadBuyers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage());
            }
        }
    }

    private void editBuyer() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) model.getValueAt(row, 0);
        try {
            Buyer b = buyerController.getBuyerById(id);
            JTextField nama = new JTextField(b.getNama());
            JTextField kontak = new JTextField(b.getKontak());
            JTextField alamat = new JTextField(b.getAlamat());
            Object[] fields = {"Nama:", nama, "Kontak:", kontak, "Alamat:", alamat};
            int result = JOptionPane.showConfirmDialog(this, fields, "Edit Buyer", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                b.setNama(nama.getText());
                b.setKontak(kontak.getText());
                b.setAlamat(alamat.getText());
                buyerController.updateBuyer(b);
                loadBuyers();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void hapusBuyer() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                buyerController.deleteBuyer(id);
                loadBuyers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage());
            }
        }
    }
}