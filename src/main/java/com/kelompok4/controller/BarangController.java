package com.kelompok4.controller;

import com.kelompok4.dao.BarangDAO;
import com.kelompok4.model.Barang;
import java.sql.SQLException;
import java.util.List;

public class BarangController {
    private BarangDAO barangDAO = new BarangDAO();

    public List<Barang> getAllBarang() throws SQLException {
        return barangDAO.getAll();
    }

    public List<Barang> searchBarang(String keyword) throws SQLException {
        return barangDAO.search(keyword);
    }
    
    public List<Barang> searchBarangKritis(String keyword) throws SQLException {
        return barangDAO.searchKritis(keyword);
    }

    public List<Barang> filterByKategori(String kategori) throws SQLException {
        return barangDAO.filterByKategori(kategori);
    }

    public List<Barang> filterByTipe(String tipe) throws SQLException {
        return barangDAO.filterByTipe(tipe);
    }
    
    public List<Barang> filterByTipeKritis(String tipe) throws SQLException {
        return barangDAO.filterByTipeKritis(tipe);
    }

    public List<Barang> getBarangKritis() throws SQLException {
        return barangDAO.getBarangKritis();
    }

    public void addBarang(Barang barang) throws SQLException {
        barangDAO.insert(barang);
    }

    public void updateBarang(Barang barang) throws SQLException {
        barangDAO.update(barang);
    }

    public void deleteBarang(int id) throws SQLException {
        barangDAO.delete(id);
    }

    public Barang getBarangById(int id) throws SQLException {
        return barangDAO.getById(id);
    }

    public int countBarang() throws SQLException {
        return barangDAO.count();
    }

    public int countBarangKritis() throws SQLException {
        return barangDAO.countKritis();
    }

    public void updateStok(int barangId, int newStok) throws SQLException {
        barangDAO.updateStok(barangId, newStok);
    }
}