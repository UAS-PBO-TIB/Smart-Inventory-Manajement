/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kelompok4.database;
import com.kelompok4.database.Koneksi;
import java.sql.*;
import java.math.BigDecimal;

public class StokMasukDAO {

    // Catat transaksi barang masuk dari supplier
    public void tambahStokMasuk(String nomorTransaksi, int barangId, int supplierId,
                                int dicatatOleh, int jumlah, BigDecimal hargaSatuan,
                                Date tanggalMasuk, String catatan) throws SQLException {
        String sql = "INSERT INTO stok_masuk " +
                     "(nomor_transaksi, barang_id, supplier_id, dicatat_oleh, " +
                     "jumlah, harga_satuan, total_harga, tanggal_masuk, catatan) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        BigDecimal total = hargaSatuan.multiply(BigDecimal.valueOf(jumlah));
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
        }
    }

    // Ambil semua riwayat stok masuk — untuk laporan
    public ResultSet getAllStokMasuk() throws SQLException {
        String sql = "SELECT sm.nomor_transaksi, b.nama_barang, s.nama_supplier, " +
                     "sm.jumlah, sm.total_harga, sm.tanggal_masuk " +
                     "FROM stok_masuk sm " +
                     "JOIN barang b ON sm.barang_id = b.id " +
                     "JOIN supplier s ON sm.supplier_id = s.id " +
                     "ORDER BY sm.tanggal_masuk DESC";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
}

