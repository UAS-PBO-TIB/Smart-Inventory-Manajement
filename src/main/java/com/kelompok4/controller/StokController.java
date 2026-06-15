package com.kelompok4.controller;

import com.kelompok4.dao.StokTransactionDAO;
import com.kelompok4.model.StokTransaction;
import java.sql.SQLException;
import java.util.List;

public class StokController {
    private StokTransactionDAO stokDAO = new StokTransactionDAO();

    public List<StokTransaction> getAllTransactions() throws SQLException {
        return stokDAO.getAll();
    }

    public List<StokTransaction> getTransactionsByBarang(int barangId) throws SQLException {
        return stokDAO.getByBarangId(barangId);
    }

    public void tambahStokMasuk(int barangId, int jumlah, String keterangan, Integer supplierId) throws SQLException {
        stokDAO.tambahStokMasuk(barangId, jumlah, keterangan, supplierId);
    }

    public void tambahStokKeluar(int barangId, int jumlah, String keterangan, Integer buyerId) throws SQLException {
        stokDAO.tambahStokKeluar(barangId, jumlah, keterangan, buyerId);
    }

    public void hapusTransaksi(int id) throws SQLException {
        stokDAO.deleteTransaction(id);
    }

    public StokTransaction getTransactionById(int id) throws SQLException {
        return stokDAO.getById(id);
    }
}