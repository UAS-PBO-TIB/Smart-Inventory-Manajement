package com.kelompok4.dao;

import com.kelompok4.model.Buyer;
import com.kelompok4.database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuyerDAO {

    public Buyer getById(int id) throws SQLException {
        String sql = "SELECT * FROM buyers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractBuyer(rs);
            }
        }
        return null;
    }

    public List<Buyer> getAll() throws SQLException {
        List<Buyer> list = new ArrayList<>();
        String sql = "SELECT * FROM buyers ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractBuyer(rs));
            }
        }
        return list;
    }

    public List<Buyer> search(String keyword) throws SQLException {
        List<Buyer> list = new ArrayList<>();
        String sql = "SELECT * FROM buyers WHERE nama ILIKE ? OR kontak ILIKE ? OR alamat ILIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractBuyer(rs));
            }
        }
        return list;
    }

    public void insert(Buyer buyer) throws SQLException {
        String sql = "INSERT INTO buyers (nama, kontak, alamat) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, buyer.getNama());
            ps.setString(2, buyer.getKontak());
            ps.setString(3, buyer.getAlamat());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                buyer.setId(rs.getInt(1));
            }
        }
    }

    public void update(Buyer buyer) throws SQLException {
        String sql = "UPDATE buyers SET nama = ?, kontak = ?, alamat = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, buyer.getNama());
            ps.setString(2, buyer.getKontak());
            ps.setString(3, buyer.getAlamat());
            ps.setInt(4, buyer.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM buyers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM buyers";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<Buyer> getTopBuyers(int limit) throws SQLException {
        List<Buyer> list = new ArrayList<>();
        String sql = "SELECT b.id, b.nama, b.kontak, b.alamat, COALESCE(SUM(st.jumlah), 0) as total_pembelian " +
                     "FROM buyers b " +
                     "LEFT JOIN stok_transactions st ON b.id = st.buyer_id AND st.tipe_transaksi = 'KELUAR' " +
                     "GROUP BY b.id ORDER BY total_pembelian DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractBuyer(rs));
            }
        }
        return list;
    }

    private Buyer extractBuyer(ResultSet rs) throws SQLException {
        return new Buyer(
            rs.getInt("id"),
            rs.getString("nama"),
            rs.getString("kontak"),
            rs.getString("alamat")
        );
    }
}