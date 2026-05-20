package com.kelompok4.database;

import com.kelompok4.model.Kategori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {

    // ─── Helper: mapping ResultSet → Kategori ────────────────────────────────
    private Kategori mapRow(ResultSet rs) throws SQLException {
        return new Kategori(
            rs.getInt("id"),
            rs.getString("nama_kategori"),
            rs.getString("deskripsi")
        );
    }

    // ─── READ: Ambil semua kategori (dropdown & filter) ───────────────────────
    public List<Kategori> getAllKategori() throws SQLException {
        List<Kategori> list = new ArrayList<>();
        String sql = "SELECT id, nama_kategori, deskripsi "
                   + "FROM kategori ORDER BY nama_kategori";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // ─── READ: Ambil satu kategori berdasarkan ID ─────────────────────────────
    public Kategori getKategoriById(int id) throws SQLException {
        String sql = "SELECT id, nama_kategori, deskripsi "
                   + "FROM kategori WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    // ─── INSERT: Tambah kategori baru, return objek baru ─────────────────────
    public Kategori tambahKategori(String namaKategori, String deskripsi)
            throws SQLException {

        String sql = "INSERT INTO kategori (nama_kategori, deskripsi) VALUES (?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, namaKategori);
            ps.setString(2, deskripsi);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return getKategoriById(keys.getInt(1));
            }
        }
        throw new SQLException("Gagal menambah kategori: generated key tidak ditemukan.");
    }

    // ─── UPDATE: Edit kategori, return objek terupdate ───────────────────────
    public Kategori editKategori(int id, String namaKategori, String deskripsi)
            throws SQLException {

        String sql = "UPDATE kategori SET nama_kategori = ?, deskripsi = ? WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, namaKategori);
            ps.setString(2, deskripsi);
            ps.setInt(3, id);

            if (ps.executeUpdate() == 0)
                throw new SQLException("Kategori ID " + id + " tidak ditemukan.");
        }
        return getKategoriById(id);
    }

    // ─── DELETE: Hapus kategori ───────────────────────────────────────────────
    public void hapusKategori(int id) throws SQLException {
        String sql = "DELETE FROM kategori WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0)
                throw new SQLException("Kategori ID " + id + " tidak ditemukan.");
        }
    }
}