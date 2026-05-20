package com.kelompok4.database;

import com.kelompok4.model.Barang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {
    public static class BarangData extends Barang {
        private String namaKategori;

        public BarangData(int id, String kodeBarang, String namaBarang,
                          String satuan, int kategoriId, int stokSaatIni,
                          int stokMinimum, String namaKategori) {
            super(id, kodeBarang, namaBarang, satuan, kategoriId, stokSaatIni, stokMinimum);
            this.namaKategori = namaKategori;
        }

        public String getNamaKategori() { return namaKategori; }
        public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    }

    private BarangData mapRow(ResultSet rs) throws SQLException {
        return new BarangData(
            rs.getInt("id"),
            rs.getString("kode_barang"),
            rs.getString("nama_barang"),
            rs.getString("satuan"),
            rs.getInt("kategori_id"),
            rs.getInt("stok_saat_ini"),
            rs.getInt("stok_minimum"),
            rs.getString("nama_kategori")
        );
    }

    public List<Barang> getAllBarang() throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT b.id, b.kode_barang, b.nama_barang, b.satuan, "
                   + "b.stok_saat_ini, b.stok_minimum, b.kategori_id, k.nama_kategori "
                   + "FROM barang b "
                   + "JOIN kategori k ON b.kategori_id = k.id "
                   + "ORDER BY b.nama_barang";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Barang getBarangById(int id) throws SQLException {
        String sql = "SELECT b.id, b.kode_barang, b.nama_barang, b.satuan, "
                   + "b.stok_saat_ini, b.stok_minimum, b.kategori_id, k.nama_kategori "
                   + "FROM barang b "
                   + "JOIN kategori k ON b.kategori_id = k.id "
                   + "WHERE b.id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<Barang> cariBarangByNama(String keyword) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT b.id, b.kode_barang, b.nama_barang, b.satuan, "
                   + "b.stok_saat_ini, b.stok_minimum, b.kategori_id, k.nama_kategori "
                   + "FROM barang b "
                   + "JOIN kategori k ON b.kategori_id = k.id "
                   + "WHERE LOWER(b.nama_barang) LIKE LOWER(?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public List<Barang> getBarangByKategori(int kategoriId) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT b.id, b.kode_barang, b.nama_barang, b.satuan, "
                   + "b.stok_saat_ini, b.stok_minimum, b.kategori_id, k.nama_kategori "
                   + "FROM barang b "
                   + "JOIN kategori k ON b.kategori_id = k.id "
                   + "WHERE b.kategori_id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, kategoriId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public List<Barang> getBarangStokKritis() throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT b.id, b.kode_barang, b.nama_barang, b.satuan, "
                   + "b.stok_saat_ini, b.stok_minimum, b.kategori_id, k.nama_kategori "
                   + "FROM barang b "
                   + "JOIN kategori k ON b.kategori_id = k.id "
                   + "WHERE b.stok_saat_ini < b.stok_minimum";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public Barang tambahBarang(String kodeBarang, String namaBarang, String satuan,
                               int stokAwal, int stokMinimum, int kategoriId)
            throws SQLException {

        String sql = "INSERT INTO barang (kode_barang, nama_barang, satuan, "
                   + "stok_saat_ini, stok_minimum, kategori_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, kodeBarang);
            ps.setString(2, namaBarang);
            ps.setString(3, satuan);
            ps.setInt(4, stokAwal);
            ps.setInt(5, stokMinimum);
            ps.setInt(6, kategoriId);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return getBarangById(keys.getInt(1));
            }
        }
        throw new SQLException("Gagal menambah barang: generated key tidak ditemukan.");
    }

    public Barang editBarang(int id, String namaBarang, String satuan,
                             int stokMinimum, int kategoriId) throws SQLException {

        String sql = "UPDATE barang SET nama_barang = ?, satuan = ?, "
                   + "stok_minimum = ?, kategori_id = ? WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, namaBarang);
            ps.setString(2, satuan);
            ps.setInt(3, stokMinimum);
            ps.setInt(4, kategoriId);
            ps.setInt(5, id);

            if (ps.executeUpdate() == 0)
                throw new SQLException("Barang ID " + id + " tidak ditemukan.");
        }
        return getBarangById(id);
    }

    public Barang updateStok(int barangId, int stokBaru) throws SQLException {
        String sql = "UPDATE barang SET stok_saat_ini = ? WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, stokBaru);
            ps.setInt(2, barangId);

            if (ps.executeUpdate() == 0)
                throw new SQLException("Barang ID " + barangId + " tidak ditemukan.");
        }
        return getBarangById(barangId);
    }

    public void hapusBarang(int id) throws SQLException {
        String sql = "DELETE FROM barang WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0)
                throw new SQLException("Barang ID " + id + " tidak ditemukan.");
        }
    }
}