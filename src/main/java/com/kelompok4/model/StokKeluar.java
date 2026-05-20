package com.kelompok4.model;

import java.util.Date;

public class StokKeluar {
    private int id;
    private String nomorTransaksi;
    private int barangId;
    private int departemenId;
    private int dicatatOleh;
    private int jumlah;
    private Date tanggalKeluar;
    private String keperluan;
    private String catatan;

    public StokKeluar(int id, String nomorTransaksi, int barangId, int departemenId, int dicatatOleh, int jumlah, Date tanggalKeluar, String keperluan, String catatan) {
        this.id = id;
        this.nomorTransaksi = nomorTransaksi;
        this.barangId = barangId;
        this.departemenId = departemenId;
        this.dicatatOleh = dicatatOleh;
        this.jumlah = jumlah;
        this.tanggalKeluar = tanggalKeluar;
        this.keperluan = keperluan;
        this.catatan = catatan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomorTransaksi() { return nomorTransaksi; }
    public void setNomorTransaksi(String nomorTransaksi) { this.nomorTransaksi = nomorTransaksi; }
    public int getBarangId() { return barangId; }
    public void setBarangId(int barangId) { this.barangId = barangId; }
    public int getDepartemenId() { return departemenId; }
    public void setDepartemenId(int departemenId) { this.departemenId = departemenId; }
    public int getDicatatOleh() { return dicatatOleh; }
    public void setDicatatOleh(int dicatatOleh) { this.dicatatOleh = dicatatOleh; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public Date getTanggalKeluar() { return tanggalKeluar; }
    public void setTanggalKeluar(Date tanggalKeluar) { this.tanggalKeluar = tanggalKeluar; }
    public String getKeperluan() { return keperluan; }
    public void setKeperluan(String keperluan) { this.keperluan = keperluan; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
}