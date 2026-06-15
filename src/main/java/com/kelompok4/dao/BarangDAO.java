package com.kelompok4.dao;

import com.kelompok4.model.Barang;
import com.kelompok4.model.Elektronik;
import com.kelompok4.model.ATK;
import com.kelompok4.database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {

    public Barang getById(int id) throws SQLException {
        String sql = "SELECT * FROM barang WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractBarang(rs);
            }
        }
        return null;
    }

    public List<Barang> getAll() throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractBarang(rs));
            }
        }
        return list;
    }

    public List<Barang> search(String keyword) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE kode_barang ILIKE ? OR nama ILIKE ? OR kategori ILIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractBarang(rs));
            }
        }
        return list;
    }
    
    public List<Barang> searchKritis(String keyword) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE stok_saat_ini < stok_minimum AND (kode_barang ILIKE ? OR nama ILIKE ? OR kategori ILIKE ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractBarang(rs));
            }
        }
        return list;
    }

    public List<Barang> filterByKategori(String kategori) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE kategori ILIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + kategori + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractBarang(rs));
            }
        }
        return list;
    }

    public List<Barang> filterByTipe(String tipe) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE tipe_barang ILIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + tipe + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractBarang(rs));
            }
        }
        return list;
    }
    
    public List<Barang> filterByTipeKritis(String tipe) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE stok_saat_ini < stok_minimum AND tipe_barang ILIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + tipe + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractBarang(rs));
            }
        }
        return list;
    }

    public List<Barang> getBarangKritis() throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE stok_saat_ini < stok_minimum";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractBarang(rs));
            }
        }
        return list;
    }

    public void insert(Barang barang) throws SQLException {
        String sql;
        if (barang instanceof Elektronik) {
            sql = "INSERT INTO barang (kode_barang, nama, kategori, stok_saat_ini, stok_minimum, tipe_barang, garansi_bulan) VALUES (?, ?, ?, ?, ?, ?, ?)";
        } else if (barang instanceof ATK) {
            sql = "INSERT INTO barang (kode_barang, nama, kategori, stok_saat_ini, stok_minimum, tipe_barang, ukuran) VALUES (?, ?, ?, ?, ?, ?, ?)";
        } else {
            throw new IllegalArgumentException("Tipe barang tidak dikenal");
        }
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, barang.getKodeBarang());
            ps.setString(2, barang.getNama());
            ps.setString(3, barang.getKategori());
            ps.setInt(4, barang.getStokSaatIni());
            ps.setInt(5, barang.getStokMinimum());
            ps.setString(6, barang.getTipeBarang());
            if (barang instanceof Elektronik) {
                ps.setInt(7, ((Elektronik) barang).getGaransiBulan());
            } else if (barang instanceof ATK) {
                ps.setString(7, ((ATK) barang).getUkuran());
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                barang.setId(rs.getInt(1));
            }
        }
    }

    public void update(Barang barang) throws SQLException {
        String sql = "UPDATE barang SET kode_barang=?, nama=?, kategori=?, stok_minimum=?, tipe_barang=?";
        if (barang instanceof Elektronik) {
            sql += ", garansi_bulan=? WHERE id=?";
        } else if (barang instanceof ATK) {
            sql += ", ukuran=? WHERE id=?";
        } else {
            sql += " WHERE id=?";
        }
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barang.getKodeBarang());
            ps.setString(2, barang.getNama());
            ps.setString(3, barang.getKategori());
            ps.setInt(4, barang.getStokMinimum());
            ps.setString(5, barang.getTipeBarang());
            if (barang instanceof Elektronik) {
                ps.setInt(6, ((Elektronik) barang).getGaransiBulan());
                ps.setInt(7, barang.getId());
            } else if (barang instanceof ATK) {
                ps.setString(6, ((ATK) barang).getUkuran());
                ps.setInt(7, barang.getId());
            } else {
                ps.setInt(6, barang.getId());
            }
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM barang WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void updateStok(int barangId, int newStok) throws SQLException {
        String sql = "UPDATE barang SET stok_saat_ini = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStok);
            ps.setInt(2, barangId);
            ps.executeUpdate();
        }
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM barang";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int countKritis() throws SQLException {
        String sql = "SELECT COUNT(*) FROM barang WHERE stok_saat_ini < stok_minimum";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    private Barang extractBarang(ResultSet rs) throws SQLException {
        String tipe = rs.getString("tipe_barang");
        int id = rs.getInt("id");
        String kode = rs.getString("kode_barang");
        String nama = rs.getString("nama");
        String kategori = rs.getString("kategori");
        int stokSaatIni = rs.getInt("stok_saat_ini");
        int stokMinimum = rs.getInt("stok_minimum");
        if ("Elektronik".equals(tipe)) {
            int garansi = rs.getInt("garansi_bulan");
            return new Elektronik(id, kode, nama, kategori, stokSaatIni, stokMinimum, tipe, garansi);
        } else {
            String ukuran = rs.getString("ukuran");
            return new ATK(id, kode, nama, kategori, stokSaatIni, stokMinimum, tipe, ukuran);
        }
    }
}