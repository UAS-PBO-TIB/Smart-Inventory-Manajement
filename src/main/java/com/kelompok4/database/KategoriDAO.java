package com.kelompok4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {

    // Ambil semua kategori — untuk dropdown di form & filter
    public List<Object[]> getAllKategori() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT id, nama_kategori FROM kategori ORDER BY nama_kategori";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("nama_kategori")
            };
            list.add(row);
        }
        return list;
    }
}