package com.kelompok4.model;

public abstract class Barang {
    private int id; 
    private String kodeBarang;
    private String namaBarang;
    private String satuan;
    private int kategoriId;
    private int stokSaatIni; 
    private int stokMinimum;

    public Barang(int id, String kodeBarang, String namaBarang, String satuan, int kategoriId, int stokSaatIni, int stokMinimum) {
        this.id = id;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.satuan = satuan;
        this.kategoriId = kategoriId;
        this.stokSaatIni = stokSaatIni;
        this.stokMinimum = stokMinimum;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKodeBarang() { return kodeBarang; }
    public void setKodeBarang(String kodeBarang) { this.kodeBarang = kodeBarang; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }

    public String getSatuan() { return satuan; }
    public void setSatuan(String satuan) { this.satuan = satuan; }

    public int getKategoriId() { return kategoriId; }
    public void setKategoriId(int kategoriId) { this.kategoriId = kategoriId; }

    public int getStokSaatIni() { return stokSaatIni; }
    public void setStokSaatIni(int stokSaatIni) { this.stokSaatIni = stokSaatIni; }

    public int getStokMinimum() { return stokMinimum; }
    public void setStokMinimum(int stokMinimum) { this.stokMinimum = stokMinimum; }

    @Override
    public String toString() {
        return this.namaBarang;
    }
}