package com.kelompok4.model;

public class Elektronik extends Barang {
    private int garansiBulan;

    public Elektronik(int id, String kodeBarang, String nama, String kategori, 
                      int stokSaatIni, int stokMinimum, String tipeBarang, int garansiBulan) {
        super(id, kodeBarang, nama, kategori, stokSaatIni, stokMinimum, tipeBarang);
        this.garansiBulan = garansiBulan;
    }

    public int getGaransiBulan() { return garansiBulan; }
    public void setGaransiBulan(int garansiBulan) { this.garansiBulan = garansiBulan; }
}