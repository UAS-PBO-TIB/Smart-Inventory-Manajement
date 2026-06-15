package com.kelompok4.view.panels;

import com.kelompok4.controller.UserController;
import com.kelompok4.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class ManageUsersPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private UserController userController;
    private JTextField searchField;
    private int currentUserId;

    public ManageUsersPanel(int currentUserId) {
        this.currentUserId = currentUserId;
        userController = new UserController();
        setLayout(new BorderLayout());

        // Panel pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(this::searchUsers);
        searchPanel.add(searchBtn);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadUsers());
        searchPanel.add(refreshBtn);
        add(searchPanel, BorderLayout.NORTH);

        // Tabel
        model = new DefaultTableModel(new String[]{"ID", "Email", "Nama", "Role"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Tombol aksi
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Tambah");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Hapus");
        addBtn.addActionListener(e -> tambahUser());
        editBtn.addActionListener(e -> editUser());
        deleteBtn.addActionListener(e -> hapusUser());
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadUsers();
    }

    public void loadUsers() {
        try {
            List<User> users = userController.getAllUsers();
            model.setRowCount(0);
            for (User u : users) {
                model.addRow(new Object[]{u.getId(), u.getEmail(), u.getNama(), u.getRole()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat user: " + e.getMessage());
        }
    }

    private void searchUsers(ActionEvent e) {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadUsers();
            return;
        }
        try {
            List<User> users = userController.searchUsers(keyword);
            model.setRowCount(0);
            for (User u : users) {
                model.addRow(new Object[]{u.getId(), u.getEmail(), u.getNama(), u.getRole()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void tambahUser() {
        JTextField email = new JTextField();
        JTextField nama = new JTextField();
        JPasswordField pass = new JPasswordField();
        JComboBox<String> role = new JComboBox<>(new String[]{"admin", "manager", "staff"});
        Object[] fields = {"Email:", email, "Nama:", nama, "Password:", pass, "Role:", role};
        int result = JOptionPane.showConfirmDialog(this, fields, "Tambah User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                User u = new User(0, email.getText(), new String(pass.getPassword()), nama.getText(), (String) role.getSelectedItem());
                userController.addUser(u);
                loadUsers();
                JOptionPane.showMessageDialog(this, "User ditambahkan");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage());
            }
        }
    }

    private void editUser() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang akan diedit");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        try {
            User u = userController.getUserById(id);
            JTextField email = new JTextField(u.getEmail());
            JTextField nama = new JTextField(u.getNama());
            JPasswordField pass = new JPasswordField();
            JComboBox<String> role = new JComboBox<>(new String[]{"admin", "manager", "staff"});
            role.setSelectedItem(u.getRole());
            
            boolean isCurrentUser = (id == currentUserId);
            if (isCurrentUser) {
                role.setEnabled(false);
            }
            
            Object[] fields = {"Email:", email, "Nama:", nama, "Password (kosongkan jika tidak diubah):", pass, "Role:", role};
            int result = JOptionPane.showConfirmDialog(this, fields, "Edit User", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                u.setEmail(email.getText());
                u.setNama(nama.getText());
                String newPass = new String(pass.getPassword());
                
                if (!newPass.isEmpty()) u.setPassword(newPass);
                
                if (!isCurrentUser) {
                    u.setRole((String) role.getSelectedItem());
                }
                
                userController.updateUser(u);
                loadUsers();
                JOptionPane.showMessageDialog(this, "User diupdate");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void hapusUser() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) model.getValueAt(row, 0);
        
        if (id == currentUserId) {
            JOptionPane.showMessageDialog(this, "Anda tidak dapat menghapus akun Anda sendiri yang sedang aktif.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus user ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                userController.deleteUser(id);
                loadUsers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal hapus: " + ex.getMessage());
            }
        }
    }
}