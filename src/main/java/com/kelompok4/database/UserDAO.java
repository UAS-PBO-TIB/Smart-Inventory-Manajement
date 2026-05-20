package com.kelompok4.database;

import com.kelompok4.model.User;
import com.kelompok4.model.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("nik"),
            rs.getString("nama"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("alamat"),
            rs.getString("no_telepon"),
            Role.valueOf(rs.getString("role"))  // asumsi Role adalah enum
        );
    }

    public User login(String email, String password) throws SQLException {
        String sql = "SELECT id, nik, nama, email, password, alamat, "
                   + "no_telepon, role "
                   + "FROM users "
                   + "WHERE email = ? AND password = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password); // idealnya password di-hash dulu
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null; // null = login gagal
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT id, nik, nama, email, password, alamat, "
                   + "no_telepon, role "
                   + "FROM users WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<User> getAllUser() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, nik, nama, email, password, alamat, "
                   + "no_telepon, role "
                   + "FROM users ORDER BY nama";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public User tambahUser(String nik, String nama, String email,
                           String password, String alamat,
                           String noTelepon, Role role) throws SQLException {

        String sql = "INSERT INTO users (nik, nama, email, password, alamat, "
                   + "no_telepon, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nik);
            ps.setString(2, nama);
            ps.setString(3, email);
            ps.setString(4, password); // idealnya di-hash dulu
            ps.setString(5, alamat);
            ps.setString(6, noTelepon);
            ps.setString(7, role.name());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return getUserById(keys.getInt(1));
            }
        }
        throw new SQLException("Gagal menambah user: generated key tidak ditemukan.");
    }

    public User editUser(int id, String nama, String email,
                         String alamat, String noTelepon, Role role)
            throws SQLException {

        String sql = "UPDATE users SET nama = ?, email = ?, alamat = ?, "
                   + "no_telepon = ?, role = ? WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nama);
            ps.setString(2, email);
            ps.setString(3, alamat);
            ps.setString(4, noTelepon);
            ps.setString(5, role.name());
            ps.setInt(6, id);

            if (ps.executeUpdate() == 0)
                throw new SQLException("User ID " + id + " tidak ditemukan.");
        }
        return getUserById(id);
    }

    public void gantiPassword(int id, String passwordBaru) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, passwordBaru); // idealnya di-hash dulu
            ps.setInt(2, id);

            if (ps.executeUpdate() == 0)
                throw new SQLException("User ID " + id + " tidak ditemukan.");
        }
    }
public List<User> getUserByRole(Role role) throws SQLException {
    List<User> list = new ArrayList<>();
    String sql = "SELECT id, nik, nama, email, password, alamat, "
               + "no_telepon, role "
               + "FROM users WHERE role = ? "
               + "ORDER BY nama";

    try (Connection conn = Koneksi.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, role.name()); // enum → String untuk disimpan ke DB
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
    }
    return list;
}

    public void hapusUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0)
                throw new SQLException("User ID " + id + " tidak ditemukan.");
        }
    }
}