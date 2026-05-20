package com.kelompok4.database;

import com.kelompok4.model.StokMasuk;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StokMasukDAO {

    // ─── Helper: mapping ResultSet → StokMasuk ────────────────────────────────
    private StokMasuk mapRow(ResultSet rs) throws SQLException {
        return new StokMasuk(
            rs.getInt("id"),
            rs.getString("nomor_transaksi"),
            rs.getInt("barang_id"),
            rs.getInt("supplier_id"),
            rs.getInt("dicatat_oleh"),
            rs.getInt("jumlah"),
            rs.getDouble("harga_satuan"),
            rs.getDouble("total_harga"),
            rs.getDate("tanggal_masuk"),
            rs.getString("catatan")
        );
    }

    // ─── INSERT: Catat transaksi masuk, return objek baru ─────────────────────
    public StokMasuk tambahStokMasuk(String nomorTransaksi, int barangId,
                                     int supplierId, int dicatatOleh, int jumlah,
                                     BigDecimal hargaSatuan, Date tanggalMasuk,
                                     String catatan) throws SQLException {

        String sql = "INSERT INTO stok_masuk "
                   + "(nomor_transaksi, barang_id, supplier_id, dicatat_oleh, "
                   + "jumlah, harga_satuan, total_harga, tanggal_masuk, catatan) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        BigDecimal total = hargaSatuan.multiply(BigDecimal.valueOf(jumlah));

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nomorTransaksi);
            ps.setInt(2, barangId);
            ps.setInt(3, supplierId);
            ps.setInt(4, dicatatOleh);
            ps.setInt(5, jumlah);
            ps.setBigDecimal(6, hargaSatuan);
            ps.setBigDecimal(7, total);
            ps.setDate(8, tanggalMasuk);
            ps.setString(9, catatan);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return getStokMasukById(keys.getInt(1));
            }
        }
        throw new SQLException("Gagal mencatat stok masuk: generated key tidak ditemukan.");
    }

    // ─── READ: Ambil satu transaksi berdasarkan ID ────────────────────────────
    public StokMasuk getStokMasukById(int id) throws SQLException {
        String sql = "SELECT id, nomor_transaksi, barang_id, supplier_id, "
                   + "dicatat_oleh, jumlah, harga_satuan, total_harga, "
                   + "tanggal_masuk, catatan "
                   + "FROM stok_masuk WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    // ─── READ: Ambil semua riwayat stok masuk — untuk laporan ────────────────
    public List<StokMasuk> getAllStokMasuk() throws SQLException {
        List<StokMasuk> list = new ArrayList<>();
        String sql = "SELECT sm.id, sm.nomor_transaksi, sm.barang_id, sm.supplier_id, "
                   + "sm.dicatat_oleh, sm.jumlah, sm.harga_satuan, sm.total_harga, "
                   + "sm.tanggal_masuk, sm.catatan "
                   + "FROM stok_masuk sm "
                   + "JOIN barang b ON sm.barang_id = b.id "
                   + "JOIN supplier s ON sm.supplier_id = s.id "
                   + "ORDER BY sm.tanggal_masuk DESC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // ─── READ: Filter berdasarkan barang ─────────────────────────────────────
    public List<StokMasuk> getStokMasukByBarang(int barangId) throws SQLException {
        List<StokMasuk> list = new ArrayList<>();
        String sql = "SELECT id, nomor_transaksi, barang_id, supplier_id, "
                   + "dicatat_oleh, jumlah, harga_satuan, total_harga, "
                   + "tanggal_masuk, catatan "
                   + "FROM stok_masuk WHERE barang_id = ? "
                   + "ORDER BY tanggal_masuk DESC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, barangId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    // ─── READ: Filter berdasarkan supplier ───────────────────────────────────
    public List<StokMasuk> getStokMasukBySupplier(int supplierId) throws SQLException {
        List<StokMasuk> list = new ArrayList<>();
        String sql = "SELECT id, nomor_transaksi, barang_id, supplier_id, "
                   + "dicatat_oleh, jumlah, harga_satuan, total_harga, "
                   + "tanggal_masuk, catatan "
                   + "FROM stok_masuk WHERE supplier_id = ? "
                   + "ORDER BY tanggal_masuk DESC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, supplierId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    // ─── READ: Filter berdasarkan rentang tanggal ─────────────────────────────
    public List<StokMasuk> getStokMasukByTanggal(Date dari, Date sampai)
            throws SQLException {

        List<StokMasuk> list = new ArrayList<>();
        String sql = "SELECT id, nomor_transaksi, barang_id, supplier_id, "
                   + "dicatat_oleh, jumlah, harga_satuan, total_harga, "
                   + "tanggal_masuk, catatan "
                   + "FROM stok_masuk "
                   + "WHERE tanggal_masuk BETWEEN ? AND ? "
                   + "ORDER BY tanggal_masuk DESC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, dari);
            ps.setDate(2, sampai);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }
}