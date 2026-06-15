package com.kelompok4.model;

public class Barang {
    protected int id;
    protected String kodeBarang;
    protected String nama;
    protected String kategori;
    protected int stokSaatIni;
    protected int stokMinimum;
    protected String tipeBarang;

    public Barang(int id, String kodeBarang, String nama, String kategori, int stokSaatIni, int stokMinimum, String tipeBarang) {
        this.id = id;
        this.kodeBarang = kodeBarang;
        this.nama = nama;
        this.kategori = kategori;
        this.stokSaatIni = stokSaatIni;
        this.stokMinimum = stokMinimum;
        this.tipeBarang = tipeBarang;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKodeBarang() { return kodeBarang; }
    public void setKodeBarang(String kodeBarang) { this.kodeBarang = kodeBarang; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public int getStokSaatIni() { return stokSaatIni; }
    public void setStokSaatIni(int stokSaatIni) { this.stokSaatIni = stokSaatIni; }
    public int getStokMinimum() { return stokMinimum; }
    public void setStokMinimum(int stokMinimum) { this.stokMinimum = stokMinimum; }
    public String getTipeBarang() { return tipeBarang; }
    public void setTipeBarang(String tipeBarang) { this.tipeBarang = tipeBarang; }

    public boolean isStokKritis() {
        return stokSaatIni < stokMinimum;
    }

    @Override
    public String toString() {
        return nama;
    }
}
