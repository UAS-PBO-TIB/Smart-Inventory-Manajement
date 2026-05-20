package com.kelompok4.model;

public class Departemen {
    private int id;
    private String kodeDepartemen;
    private String namaDepartemen;
    private String deskripsi;

    public Departemen(int id, String kodeDepartemen, String namaDepartemen, String deskripsi) {
        this.id = id;
        this.kodeDepartemen = kodeDepartemen;
        this.namaDepartemen = namaDepartemen;
        this.deskripsi = deskripsi;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKodeDepartemen() { return kodeDepartemen; }
    public void setKodeDepartemen(String kodeDepartemen) { this.kodeDepartemen = kodeDepartemen; }
    public String getNamaDepartemen() { return namaDepartemen; }
    public void setNamaDepartemen(String namaDepartemen) { this.namaDepartemen = namaDepartemen; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
}