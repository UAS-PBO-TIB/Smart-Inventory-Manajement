package com.kelompok4.model;

import java.util.Date;

public class StokMasuk {
    private int id;
    private String nomorTransaksi;
    private int barangId;
    private int supplierId;
    private int dicatatOleh;
    private int jumlah;
    private double hargaSatuan;
    private double totalHarga;
    private Date tanggalMasuk;
    private String catatan;

    public StokMasuk(int id, String nomorTransaksi, int barangId, int supplierId, int dicatatOleh, int jumlah, double hargaSatuan, double totalHarga, Date tanggalMasuk, String catatan) {
        this.id = id;
        this.nomorTransaksi = nomorTransaksi;
        this.barangId = barangId;
        this.supplierId = supplierId;
        this.dicatatOleh = dicatatOleh;
        this.jumlah = jumlah;
        this.hargaSatuan = hargaSatuan;
        this.totalHarga = totalHarga;
        this.tanggalMasuk = tanggalMasuk;
        this.catatan = catatan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomorTransaksi() { return nomorTransaksi; }
    public void setNomorTransaksi(String nomorTransaksi) { this.nomorTransaksi = nomorTransaksi; }
    public int getBarangId() { return barangId; }
    public void setBarangId(int barangId) { this.barangId = barangId; }
    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }
    public int getDicatatOleh() { return dicatatOleh; }
    public void setDicatatOleh(int dicatatOleh) { this.dicatatOleh = dicatatOleh; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public double getHargaSatuan() { return hargaSatuan; }
    public void setHargaSatuan(double hargaSatuan) { this.hargaSatuan = hargaSatuan; }
    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }
    public Date getTanggalMasuk() { return tanggalMasuk; }
    public void setTanggalMasuk(Date tanggalMasuk) { this.tanggalMasuk = tanggalMasuk; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
}