package com.kelompok4.database;

import com.kelompok4.model.AlertStokKritis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {

    // ─── Helper: mapping ResultSet → AlertStokKritis ───────────────────────────
    private AlertStokKritis mapRow(ResultSet rs) throws SQLException {
        return new AlertStokKritis(
            rs.getInt("id"),
            rs.getInt("barang_id"),
            rs.getInt("stok_saat_alert"),
            rs.getInt("stok_minimum"),
            rs.getString("status_alert"),
            rs.getTimestamp("tanggal_alert"),
            rs.getObject("ditangani_oleh") != null ? rs.getInt("ditangani_oleh") : null,
            rs.getTimestamp("tanggal_ditangani"),
            rs.getString("catatan")
        );
    }

    // ─── Ambil semua alert AKTIF ────────────────────────────────────────────────
    public List<AlertStokKritis> getAlertAktif() throws SQLException {
        List<AlertStokKritis> list = new ArrayList<>();

        String sql = "SELECT a.id, a.barang_id, a.stok_saat_alert, a.stok_minimum, "
                   + "a.status_alert, a.tanggal_alert, a.ditangani_oleh, "
                   + "a.tanggal_ditangani, a.catatan "
                   + "FROM alert_stok_kritis a "
                   + "JOIN barang b ON a.barang_id = b.id "
                   + "WHERE a.status_alert = 'AKTIF' "
                   + "ORDER BY a.tanggal_alert DESC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // ─── Ambil alert by ID ──────────────────────────────────────────────────────
    public AlertStokKritis getAlertById(int alertId) throws SQLException {
        String sql = "SELECT id, barang_id, stok_saat_alert, stok_minimum, "
                   + "status_alert, tanggal_alert, ditangani_oleh, "
                   + "tanggal_ditangani, catatan "
                   + "FROM alert_stok_kritis WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alertId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null; // tidak ditemukan
    }

    // ─── Buat alert baru, return objek yang baru dibuat ────────────────────────
    public AlertStokKritis buatAlert(int barangId, int stokSaatIni, int stokMinimum)
            throws SQLException {

        String sql = "INSERT INTO alert_stok_kritis "
                   + "(barang_id, stok_saat_alert, stok_minimum, status_alert) "
                   + "VALUES (?, ?, ?, 'AKTIF')";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, barangId);
            ps.setInt(2, stokSaatIni);
            ps.setInt(3, stokMinimum);
            ps.executeUpdate();

            // Ambil ID yang baru di-generate, lalu fetch objek lengkapnya
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int newId = keys.getInt(1);
                    return getAlertById(newId);
                }
            }
        }
        throw new SQLException("Gagal membuat alert: generated key tidak ditemukan.");
    }

    // ─── Tandai alert sudah ditangani, return objek terupdate ──────────────────
    public AlertStokKritis tandaiDitangani(int alertId, int ditanganiOleh)
            throws SQLException {

        String sql = "UPDATE alert_stok_kritis "
                   + "SET status_alert = 'SUDAH_DITANGANI', "
                   + "    ditangani_oleh = ?, "
                   + "    tanggal_ditangani = NOW() "
                   + "WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ditanganiOleh);
            ps.setInt(2, alertId);
            int affected = ps.executeUpdate();

            if (affected == 0) {
                throw new SQLException("Alert ID " + alertId + " tidak ditemukan.");
            }
        }
        // Return objek dengan data terbaru dari DB
        return getAlertById(alertId);
    }
}