/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kelompok4.service;

import com.kelompok4.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author n03ll
 */
public class Userservice {
    private final Connection conn;
 
    public Userservice(Connection conn) {
        this.conn = conn;
    }
    
    public User login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
 
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email.trim().toLowerCase());
            ResultSet rs = ps.executeQuery();
 
            if (rs.next()) {
                String storedHash = rs.getString("password");
                // TODO: ganti dengan BCrypt.checkpw(password, storedHash) atau equiv.
                if (verifikasiPassword(password, storedHash)) {
                    return mapRowToUser(rs);
                }
            }
        }
        return null;
    }
    
    public User tambahUser(User user, String passwordPlain, Role pelakuRole)
            throws SQLException, SecurityException {
 
        if (pelakuRole != Role.ADMIN) {
            throw new SecurityException("Hanya ADMIN yang dapat menambahkan user baru.");
        }
 
        String sql = "INSERT INTO users (nik, nama, email, password, alamat, no_telepon, role) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id, dibuat_pada";
 
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getNik().toUpperCase());
            ps.setString(2, user.getNama());
            ps.setString(3, user.getEmail().toLowerCase());
            ps.setString(4, hashPassword(passwordPlain));
            ps.setString(5, user.getAlamat());
            ps.setString(6, user.getNoTelepon());
            ps.setString(7, user.getRole().name());
 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
            }
        }
        return user;
    }
    
    public List<User> getAllUser() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY nama";
        List<User> list = new ArrayList<>();
 
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRowToUser(rs));
        }
        return list;
    }
    
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRowToUser(rs);
        }
        return null;
    }
    
    public List<User> getUserByRole(Role role) throws SQLException {
        String sql = "SELECT * FROM users WHERE role = ? ORDER BY nama";
        List<User> list = new ArrayList<>();
 
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, role.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRowToUser(rs));
        }
        return list;
    }
    
    public boolean ubahRole(int targetUserId, Role roleBaru, int adminId)
            throws SQLException, SecurityException {
 
        if (targetUserId == adminId) {
            throw new SecurityException("Admin tidak dapat mengubah role dirinya sendiri.");
        }
 
        String sql = "UPDATE users SET role = ?, diperbarui_pada = now() WHERE id = ?";
 
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roleBaru.name());
            ps.setInt(2, targetUserId);
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean hapusUser(int targetUserId, int adminId) throws SQLException, SecurityException {
        if (targetUserId == adminId) {
            throw new SecurityException("Admin tidak dapat menghapus akunnya sendiri.");
        }
 
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, targetUserId);
            return ps.executeUpdate() > 0;
        }
    }
    
    private boolean verifikasiPassword(String plain, String hash) {
        return BCrypt.checkpw(plain, hash);
    }
    
    private String hashPassword(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }
    
    private User mapRowToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("nik"),
            rs.getString("nama"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("alamat"),
            rs.getString("no_telepon"),
            Role.valueOf(rs.getString("role"))
        );
    }
}
