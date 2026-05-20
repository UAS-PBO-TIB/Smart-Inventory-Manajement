package com.kelompok4.database;

import com.kelompok4.model.StokKeluar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StokKeluarDAO {
    private StokKeluar mapRow(ResultSet rs) throws SQLException {
        return new StokKeluar(
            rs.getInt("id"),
            rs.getString("nomor_transaksi"),
            rs.getInt("barang_id"),
            rs.getInt("departemen_id"),
            rs.getInt("dicatat_oleh"),
            rs.getInt("jumlah"),
            rs.getDate("tanggal_keluar"),
            rs.getString("keperluan"),
            rs.getString("catatan")
        );
    }

    public StokKeluar tambahStokKeluar(String nomorTransaksi, int barangId,
                                       int departemenId, int dicatatOleh, int jumlah,
                                       Date tanggalKeluar, String keperluan, String catatan)
            throws SQLException {

        String sql = "INSERT INTO stok_keluar "
                   + "(nomor_transaksi, barang_id, departemen_id, dicatat_oleh, "
                   + "jumlah, tanggal_keluar, keperluan, catatan) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nomorTransaksi);
            ps.setInt(2, barangId);
            ps.setInt(3, departemenId);
            ps.setInt(4, dicatatOleh);
            ps.setInt(5, jumlah);
            ps.setDate(6, tanggalKeluar);
            ps.setString(7, keperluan);
            ps.setString(8, catatan);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return getStokKeluarById(keys.getInt(1));
            }
        }
        throw new SQLException("Gagal mencatat stok keluar: generated key tidak ditemukan.");
    }

    public StokKeluar getStokKeluarById(int id) throws SQLException {
        String sql = "SELECT id, nomor_transaksi, barang_id, departemen_id, "
                   + "dicatat_oleh, jumlah, tanggal_keluar, keperluan, catatan "
                   + "FROM stok_keluar WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<StokKeluar> getAllStokKeluar() throws SQLException {
        List<StokKeluar> list = new ArrayList<>();
        String sql = "SELECT sk.id, sk.nomor_transaksi, sk.barang_id, sk.departemen_id, "
                   + "sk.dicatat_oleh, sk.jumlah, sk.tanggal_keluar, "
                   + "sk.keperluan, sk.catatan "
                   + "FROM stok_keluar sk "
                   + "JOIN barang b ON sk.barang_id = b.id "
                   + "JOIN departemen d ON sk.departemen_id = d.id "
                   + "ORDER BY sk.tanggal_keluar DESC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public List<StokKeluar> getStokKeluarByBarang(int barangId) throws SQLException {
        List<StokKeluar> list = new ArrayList<>();
        String sql = "SELECT id, nomor_transaksi, barang_id, departemen_id, "
                   + "dicatat_oleh, jumlah, tanggal_keluar, keperluan, catatan "
                   + "FROM stok_keluar WHERE barang_id = ? "
                   + "ORDER BY tanggal_keluar DESC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, barangId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public List<StokKeluar> getStokKeluarByTanggal(Date dari, Date sampai)
            throws SQLException {

        List<StokKeluar> list = new ArrayList<>();
        String sql = "SELECT id, nomor_transaksi, barang_id, departemen_id, "
                   + "dicatat_oleh, jumlah, tanggal_keluar, keperluan, catatan "
                   + "FROM stok_keluar "
                   + "WHERE tanggal_keluar BETWEEN ? AND ? "
                   + "ORDER BY tanggal_keluar DESC";

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