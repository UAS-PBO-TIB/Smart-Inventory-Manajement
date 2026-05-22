package com.kelompok4.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author n03ll
 */
// StokService.java
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import com.kelompok4.database.*;
import com.kelompok4.model.*;

public class StokService {
    private final StokMasukDAO stokMasukDAO;
    private final StokKeluarDAO stokKeluarDAO;
    private final BarangDAO barangDAO;
    private final AlertDAO alertDAO;

    public StokService(StokMasukDAO stokMasukDAO, StokKeluarDAO stokKeluarDAO, BarangDAO barangDAO, AlertDAO alertDAO) {
        this.stokMasukDAO = stokMasukDAO;
        this.stokKeluarDAO = stokKeluarDAO;
        this.barangDAO = barangDAO;
        this.alertDAO = alertDAO;
    }

    // Logika Bisnis Stok Masuk (Thread-Safe dengan synchronized)
    public synchronized StokMasuk prosesStokMasuk(String nomorTransaksi, int barangId, int supplierId, 
                                                   int dicatatOleh, int jumlah, BigDecimal hargaSatuan, 
                                                   Date tanggalMasuk, String catatan) throws SQLException {
        // 1. Validasi Input
        if (jumlah <= 0) throw new ServiceException("Jumlah barang masuk harus lebih besar dari 0!");
        
        Barang barang = barangDAO.getBarangById(barangId);
        if (barang == null) throw new ServiceException("Barang dengan ID tersebut tidak ditemukan!");

        // 2. Simpan Dokumen Transaksi Stok Masuk
        StokMasuk sm = stokMasukDAO.tambahStokMasuk(nomorTransaksi, barangId, supplierId, dicatatOleh, jumlah, hargaSatuan, tanggalMasuk, catatan);

        // 3. Update Jumlah Stok Fisik Barang Sekarang
        int stokBaru = barang.getStokSaatIni() + jumlah;
        barangDAO.updateStok(barangId, stokBaru);

        return sm;
    }

    // Logika Bisnis Stok Keluar beserta Pengecekan Threshold Alert Otomatis
    public synchronized StokKeluar prosesStokKeluar(String nomorTransaksi, int barangId, int departemenId, 
                                                     int dicatatOleh, int jumlah, Date tanggalKeluar, 
                                                     String keperluan, String catatan) throws SQLException {
        // 1. Validasi Input & Ketersediaan barang
        if (jumlah <= 0) throw new ServiceException("Jumlah barang keluar harus lebih besar dari 0!");

        Barang barang = barangDAO.getBarangById(barangId);
        if (barang == null) throw new ServiceException("Barang tidak ditemukan!");
        
        // Aturan Bisnis: Tidak boleh membiarkan stok bernilai minus
        if (barang.getStokSaatIni() < jumlah) {
            throw new ServiceException("Gagal melakukan transaksi! Stok tidak mencukupi. Stok saat ini: " 
                                        + barang.getStokSaatIni() + " " + barang.getSatuan());
        }

        // 2. Simpan Transaksi Stok Keluar
        StokKeluar sk = stokKeluarDAO.tambahStokKeluar(nomorTransaksi, barangId, departemenId, dicatatOleh, jumlah, tanggalKeluar, keperluan, catatan);

        // 3. Kurangi Stok Fisik Barang
        int stokBaru = barang.getStokSaatIni() - jumlah;
        barangDAO.updateStok(barangId, stokBaru);

        // 4. OTOMASI FITUR C: Threshold Alert (Sesuai dokumen Breakdown Proyek)
        // Jika stok baru menyentuh/kurang dari stok minimum, sistem otomatis membuat alert AKTIF
        if (stokBaru < barang.getStokMinimum()) {
            alertDAO.buatAlert(barangId, stokBaru, barang.getStokMinimum());
        }

        return sk;
    }

    public List<StokMasuk> getAllStokMasuk() throws SQLException { return stokMasukDAO.getAllStokMasuk(); }
    public List<StokKeluar> getAllStokKeluar() throws SQLException { return stokKeluarDAO.getAllStokKeluar(); }
    public List<StokMasuk> getStokMasukByTanggal(Date dari, Date sampai) throws SQLException { return stokMasukDAO.getStokMasukByTanggal(dari, sampai); }
    public List<StokKeluar> getStokKeluarByTanggal(Date dari, Date sampai) throws SQLException { return stokKeluarDAO.getStokKeluarByTanggal(dari, sampai); }
}
