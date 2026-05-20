package com.kelompok4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {

    // Ambil semua alert yang masih AKTIF
    public List<Object[]> getAlertAktif() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT a.id, b.kode_barang, b.nama_barang, "
                + "a.stok_saat_alert, a.stok_minimum, a.tanggal_alert "
                + "FROM alert_stok_kritis a "
                + "JOIN barang b ON a.barang_id = b.id "
                + "WHERE a.status_alert = 'AKTIF' "
                + "ORDER BY a.tanggal_alert DESC";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getInt("stok_saat_alert"),
                rs.getInt("stok_minimum"),
                rs.getTimestamp("tanggal_alert")
            };
            list.add(row);
        }
        return list;
    }

    // Buat alert baru ketika stok < minimum
    public void buatAlert(int barangId, int stokSaatIni, int stokMinimum) throws SQLException {
        String sql = "INSERT INTO alert_stok_kritis "
                + "(barang_id, stok_saat_alert, stok_minimum, status_alert) "
                + "VALUES (?, ?, ?, 'AKTIF')";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, barangId);
        ps.setInt(2, stokSaatIni);
        ps.setInt(3, stokMinimum);
        ps.executeUpdate();
    }

    // Tandai alert sudah ditangani
    public void tandaiDitangani(int alertId, int ditanganiOleh) throws SQLException {
        String sql = "UPDATE alert_stok_kritis "
                + "SET status_alert = 'SUDAH_DITANGANI', "
                + "ditangani_oleh = ?, tanggal_ditangani = NOW() "
                + "WHERE id = ?";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, ditanganiOleh);
        ps.setInt(2, alertId);
        ps.executeUpdate();
    }
}