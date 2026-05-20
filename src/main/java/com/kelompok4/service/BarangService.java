package com.kelompok4.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author n03ll
 */
// BarangService.java
import java.sql.SQLException;
import java.util.List;
import com.kelompok4.database.BarangDAO;

public class BarangService {
    private final BarangDAO barangDAO;

    public BarangService(BarangDAO barangDAO) {
        this.barangDAO = barangDAO;
    }

    public List<Barang> getAllBarang() throws SQLException {
        return barangDAO.getAllBarang();
    }

    public Barang getBarangById(int id) throws SQLException {
        return barangDAO.getBarangById(id);
    }

    public List<Barang> cariBarang(String keyword) throws SQLException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return barangDAO.getAllBarang();
        }
        return barangDAO.cariBarangByNama(keyword.trim());
    }

    public List<Barang> getBarangByKategori(int kategoriId) throws SQLException {
        return barangDAO.getBarangByKategori(kategoriId);
    }

    public List<Barang> getBarangStokKritis() throws SQLException {
        return barangDAO.getBarangStokKritis();
    }

    public Barang tambahBarang(String kodeBarang, String namaBarang, String satuan,
                               int stokAwal, int stokMinimum, int kategoriId) throws SQLException {
        // Validasi
        if (kodeBarang == null || kodeBarang.trim().isEmpty()) throw new ServiceException("Kode barang wajib diisi!");
        if (namaBarang == null || namaBarang.trim().isEmpty()) throw new ServiceException("Nama barang wajib diisi!");
        if (stokAwal < 0) throw new ServiceException("Stok awal tidak boleh negatif!");
        if (stokMinimum < 0) throw new ServiceException("Stok minimum tidak boleh negatif!");

        return barangDAO.tambahBarang(kodeBarang, namaBarang, satuan, stokAwal, stokMinimum, kategoriId);
    }

    public Barang editBarang(int id, String namaBarang, String satuan,
                             int stokMinimum, int kategoriId) throws SQLException {
        if (namaBarang == null || namaBarang.trim().isEmpty()) throw new ServiceException("Nama barang tidak boleh kosong!");
        return barangDAO.editBarang(id, namaBarang, satuan, stokMinimum, kategoriId);
    }

    public void hapusBarang(int id) throws SQLException {
        barangDAO.hapusBarang(id);
    }
}
