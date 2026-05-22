package com.kelompok4.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author n03ll
 */
// KategoriService.java
import java.sql.SQLException;
import java.util.List;
import com.kelompok4.database.KategoriDAO;
import com.kelompok4.model.Kategori;

public class KategoriService {
    private final KategoriDAO kategoriDAO;

    public KategoriService(KategoriDAO kategoriDAO) {
        this.kategoriDAO = kategoriDAO;
    }

    public List<Kategori> getAllKategori() throws SQLException {
        return kategoriDAO.getAllKategori();
    }

    public Kategori getKategoriById(int id) throws SQLException {
        return kategoriDAO.getKategoriById(id);
    }

    public Kategori tambahKategori(String namaKategori, String deskripsi) throws SQLException {
        if (namaKategori == null || namaKategori.trim().isEmpty()) {
            throw new ServiceException("Nama kategori wajib diisi!");
        }
        return kategoriDAO.tambahKategori(namaKategori, deskripsi);
    }

    public Kategori editKategori(int id, String namaKategori, String deskripsi) throws SQLException {
        if (namaKategori == null || namaKategori.trim().isEmpty()) {
            throw new ServiceException("Nama kategori tidak boleh kosong saat edit!");
        }
        return kategoriDAO.editKategori(id, namaKategori, deskripsi);
    }

    public void hapusKategori(int id) throws SQLException {
        kategoriDAO.hapusKategori(id);
    }
}