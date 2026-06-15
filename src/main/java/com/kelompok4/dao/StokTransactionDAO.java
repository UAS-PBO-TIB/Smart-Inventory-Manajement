package com.kelompok4.dao;

import com.kelompok4.model.StokTransaction;
import com.kelompok4.model.Barang;
import com.kelompok4.database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StokTransactionDAO {

    private BarangDAO barangDAO = new BarangDAO();

    public StokTransaction getById(int id) throws SQLException {
        String sql = "SELECT * FROM stok_transactions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractTransaction(rs);
            }
        }
        return null;
    }

    public List<StokTransaction> getAll() throws SQLException {
        List<StokTransaction> list = new ArrayList<>();
        String sql = "SELECT * FROM stok_transactions ORDER BY tanggal DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractTransaction(rs));
            }
        }
        return list;
    }

    public List<StokTransaction> getByBarangId(int barangId) throws SQLException {
        List<StokTransaction> list = new ArrayList<>();
        String sql = "SELECT * FROM stok_transactions WHERE barang_id = ? ORDER BY tanggal DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, barangId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractTransaction(rs));
            }
        }
        return list;
    }

    // Method untuk menambah transaksi stok masuk
    public void tambahStokMasuk(int barangId, int jumlah, String keterangan, Integer supplierId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Insert transaksi
            String insertSql = "INSERT INTO stok_transactions (barang_id, tipe_transaksi, jumlah, keterangan, supplier_id) VALUES (?, 'MASUK', ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, barangId);
                ps.setInt(2, jumlah);
                ps.setString(3, keterangan);
                if (supplierId != null) ps.setInt(4, supplierId);
                else ps.setNull(4, Types.INTEGER);
                ps.executeUpdate();
            }

            // 2. Update stok barang
            Barang barang = barangDAO.getById(barangId);
            if (barang == null) throw new SQLException("Barang tidak ditemukan");
            int newStok = barang.getStokSaatIni() + jumlah;
            barangDAO.updateStok(barangId, newStok);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        }
    }

    // Method untuk menambah transaksi stok keluar
    public void tambahStokKeluar(int barangId, int jumlah, String keterangan, Integer buyerId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Cek stok mencukupi
            Barang barang = barangDAO.getById(barangId);
            if (barang == null) throw new SQLException("Barang tidak ditemukan");
            if (barang.getStokSaatIni() < jumlah) {
                throw new SQLException("Stok tidak mencukupi! Stok saat ini: " + barang.getStokSaatIni());
            }

            // 1. Insert transaksi
            String insertSql = "INSERT INTO stok_transactions (barang_id, tipe_transaksi, jumlah, keterangan, buyer_id) VALUES (?, 'KELUAR', ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, barangId);
                ps.setInt(2, jumlah);
                ps.setString(3, keterangan);
                if (buyerId != null) ps.setInt(4, buyerId);
                else ps.setNull(4, Types.INTEGER);
                ps.executeUpdate();
            }

            // 2. Update stok barang
            int newStok = barang.getStokSaatIni() - jumlah;
            barangDAO.updateStok(barangId, newStok);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        }
    }

    public void deleteTransaction(int id) throws SQLException {
        String sql = "DELETE FROM stok_transactions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private StokTransaction extractTransaction(ResultSet rs) throws SQLException {
        StokTransaction tr = new StokTransaction();
        tr.setId(rs.getInt("id"));
        tr.setBarangId(rs.getInt("barang_id"));
        tr.setTipeTransaksi(rs.getString("tipe_transaksi"));
        tr.setJumlah(rs.getInt("jumlah"));
        tr.setTanggal(rs.getTimestamp("tanggal"));
        tr.setKeterangan(rs.getString("keterangan"));
        int supplierId = rs.getInt("supplier_id");
        if (!rs.wasNull()) tr.setSupplierId(supplierId);
        int buyerId = rs.getInt("buyer_id");
        if (!rs.wasNull()) tr.setBuyerId(buyerId);
        return tr;
    }
}