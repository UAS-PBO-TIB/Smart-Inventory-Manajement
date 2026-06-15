package com.kelompok4.model;

public class ATK extends Barang {
    private String ukuran;

    public ATK(int id, String kodeBarang, String nama, String kategori, 
               int stokSaatIni, int stokMinimum, String tipeBarang, String ukuran) {
        super(id, kodeBarang, nama, kategori, stokSaatIni, stokMinimum, tipeBarang);
        this.ukuran = ukuran;
    }

    public String getUkuran() { return ukuran; }
    public void setUkuran(String ukuran) { this.ukuran = ukuran; }
}