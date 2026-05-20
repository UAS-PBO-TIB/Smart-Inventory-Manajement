/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kelompok4.database;
import com.kelompok4.database.Koneksi;
import java.sql.*;

public class StokKeluarDAO {

    // Catat transaksi barang keluar ke departemen
    public void tambahStokKeluar(String nomorTransaksi, int barangId, int departemenId,
                                 int dicatatOleh, int jumlah, Date tanggalKeluar,
                                 String keperluan, String catatan) throws SQLException {
        String sql = "INSERT INTO stok_keluar " +
                     "(nomor_transaksi, barang_id, departemen_id, dicatat_oleh, " +
                     "jumlah, tanggal_keluar, keperluan, catatan) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomorTransaksi);
            ps.setInt(2, barangId);
            ps.setInt(3, departemenId);
            ps.setInt(4, dicatatOleh);
            ps.setInt(5, jumlah);
            ps.setDate(6, tanggalKeluar);
            ps.setString(7, keperluan);
            ps.setString(8, catatan);
            ps.executeUpdate();
        }
    }

    // Ambil semua riwayat stok keluar — untuk laporan
    public ResultSet getAllStokKeluar() throws SQLException {
        String sql = "SELECT sk.nomor_transaksi, b.nama_barang, d.nama_departemen, " +
                     "sk.jumlah, sk.tanggal_keluar, sk.keperluan " +
                     "FROM stok_keluar sk " +
                     "JOIN barang b ON sk.barang_id = b.id " +
                     "JOIN departemen d ON sk.departemen_id = d.id " +
                     "ORDER BY sk.tanggal_keluar DESC";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
}
