package com.kelompok4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {

    // ── READ: Ambil semua barang untuk dashboard ──────────────────────
    public List<Object[]> getAllBarang() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT b.id, b.kode_barang, b.nama_barang, b.satuan, "
                + "b.stok_saat_ini, b.stok_minimum, k.nama_kategori "
                + "FROM barang b "
                + "JOIN kategori k ON b.kategori_id = k.id "
                + "ORDER BY b.nama_barang";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("satuan"),
                rs.getInt("stok_saat_ini"),
                rs.getInt("stok_minimum"),
                rs.getString("nama_kategori")
            };
            list.add(row);
        }
        return list;
    }

    // ── READ: Ambil satu barang berdasarkan ID ────────────────────────
    public Object[] getBarangById(int id) throws SQLException {
        String sql = "SELECT b.id, b.kode_barang, b.nama_barang, b.satuan, "
                + "b.stok_saat_ini, b.stok_minimum, b.kategori_id, k.nama_kategori "
                + "FROM barang b "
                + "JOIN kategori k ON b.kategori_id = k.id "
                + "WHERE b.id = ?";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Object[]{
                rs.getInt("id"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("satuan"),
                rs.getInt("stok_saat_ini"),
                rs.getInt("stok_minimum"),
                rs.getInt("kategori_id"),
                rs.getString("nama_kategori")
            };
        }
        return null;
    }

    // ── SEARCH: Cari barang berdasarkan nama (Fitur D) ────────────────
    public List<Object[]> cariBarangByNama(String keyword) throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT b.id, b.kode_barang, b.nama_barang, b.satuan, "
                + "b.stok_saat_ini, b.stok_minimum, k.nama_kategori "
                + "FROM barang b "
                + "JOIN kategori k ON b.kategori_id = k.id "
                + "WHERE LOWER(b.nama_barang) LIKE LOWER(?)";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("satuan"),
                rs.getInt("stok_saat_ini"),
                rs.getInt("stok_minimum"),
                rs.getString("nama_kategori")
            };
            list.add(row);
        }
        return list;
    }

    // ── FILTER: Filter barang berdasarkan kategori (Fitur D) ──────────
    public List<Object[]> getBarangByKategori(int kategoriId) throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT b.id, b.kode_barang, b.nama_barang, b.satuan, "
                + "b.stok_saat_ini, b.stok_minimum, k.nama_kategori "
                + "FROM barang b "
                + "JOIN kategori k ON b.kategori_id = k.id "
                + "WHERE b.kategori_id = ?";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, kategoriId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("satuan"),
                rs.getInt("stok_saat_ini"),
                rs.getInt("stok_minimum"),
                rs.getString("nama_kategori")
            };
            list.add(row);
        }
        return list;
    }

    // ── ALERT: Ambil barang stok kritis untuk warna merah di UI (Fitur C) ──
    public List<Object[]> getBarangStokKritis() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT id, kode_barang, nama_barang, stok_saat_ini, stok_minimum "
                + "FROM barang "
                + "WHERE stok_saat_ini < stok_minimum";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getInt("stok_saat_ini"),
                rs.getInt("stok_minimum")
            };
            list.add(row);
        }
        return list;
    }

    // ── INSERT: Tambah barang baru ────────────────────────────────────
    public void tambahBarang(String kodeBarang, String namaBarang, String satuan,
                             int stokAwal, int stokMinimum, int kategoriId) throws SQLException {
        String sql = "INSERT INTO barang (kode_barang, nama_barang, satuan, "
                + "stok_saat_ini, stok_minimum, kategori_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, kodeBarang);
        ps.setString(2, namaBarang);
        ps.setString(3, satuan);
        ps.setInt(4, stokAwal);
        ps.setInt(5, stokMinimum);
        ps.setInt(6, kategoriId);
        ps.executeUpdate();
    }

    // ── UPDATE: Edit data barang ──────────────────────────────────────
    public void editBarang(int id, String namaBarang, String satuan,
                           int stokMinimum, int kategoriId) throws SQLException {
        String sql = "UPDATE barang SET nama_barang = ?, satuan = ?, "
                + "stok_minimum = ?, kategori_id = ? "
                + "WHERE id = ?";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, namaBarang);
        ps.setString(2, satuan);
        ps.setInt(3, stokMinimum);
        ps.setInt(4, kategoriId);
        ps.setInt(5, id);
        ps.executeUpdate();
    }

    // ── UPDATE: Update jumlah stok setelah transaksi masuk/keluar ─────
    public void updateStok(int barangId, int stokBaru) throws SQLException {
        String sql = "UPDATE barang SET stok_saat_ini = ? WHERE id = ?";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, stokBaru);
        ps.setInt(2, barangId);
        ps.executeUpdate();
    }

    // ── DELETE: Hapus barang ──────────────────────────────────────────
    public void hapusBarang(int id) throws SQLException {
        String sql = "DELETE FROM barang WHERE id = ?";
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}