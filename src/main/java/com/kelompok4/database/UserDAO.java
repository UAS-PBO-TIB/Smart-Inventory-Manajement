package com.kelompok4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Login: cek NIK dan password
    public Object[] login(String nik, String password) throws SQLException {
        String sql = "SELECT id, nik, nama, role FROM users "
                + "WHERE nik = ? AND password = ?";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nik);
        ps.setString(2, password); // idealnya password di-hash dulu
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Object[]{
                rs.getInt("id"),
                rs.getString("nik"),
                rs.getString("nama"),
                rs.getString("role")
            };
        }
        return null; // null = login gagal
    }
}