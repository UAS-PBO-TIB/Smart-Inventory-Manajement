package com.kelompok4.dao;

import com.kelompok4.model.Supplier;
import com.kelompok4.database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public Supplier getById(int id) throws SQLException {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractSupplier(rs);
            }
        }
        return null;
    }

    public List<Supplier> getAll() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractSupplier(rs));
            }
        }
        return list;
    }

    public List<Supplier> search(String keyword) throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM suppliers WHERE nama ILIKE ? OR kontak ILIKE ? OR alamat ILIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractSupplier(rs));
            }
        }
        return list;
    }

    public void insert(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO suppliers (nama, kontak, alamat) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, supplier.getNama());
            ps.setString(2, supplier.getKontak());
            ps.setString(3, supplier.getAlamat());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                supplier.setId(rs.getInt(1));
            }
        }
    }

    public void update(Supplier supplier) throws SQLException {
        String sql = "UPDATE suppliers SET nama = ?, kontak = ?, alamat = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplier.getNama());
            ps.setString(2, supplier.getKontak());
            ps.setString(3, supplier.getAlamat());
            ps.setInt(4, supplier.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM suppliers";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<Supplier> getTopSuppliers(int limit) throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT s.id, s.nama, s.kontak, s.alamat, COALESCE(SUM(st.jumlah), 0) as total_pengiriman " +
                     "FROM suppliers s " +
                     "LEFT JOIN stok_transactions st ON s.id = st.supplier_id AND st.tipe_transaksi = 'MASUK' " +
                     "GROUP BY s.id ORDER BY total_pengiriman DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractSupplier(rs));
            }
        }
        return list;
    }

    private Supplier extractSupplier(ResultSet rs) throws SQLException {
        return new Supplier(
            rs.getInt("id"),
            rs.getString("nama"),
            rs.getString("kontak"),
            rs.getString("alamat")
        );
    }
}