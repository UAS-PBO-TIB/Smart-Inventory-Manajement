package com.kelompok4.model;

import java.util.Date;

public class RiwayatStok {
    private int id;
    private int barangId;
    private int dilakukanOleh;
    private String jenisPerubahan;
    private int jumlahPerubahan;
    private int stokSebelum;
    private int stokSesudah;
    private int referensiId;
    private String referensiTabel;
    private Date tanggalPerubahan;
    private String keterangan;

    public RiwayatStok(int id, int barangId, int dilakukanOleh, String jenisPerubahan, int jumlahPerubahan, int stokSebelum, int stokSesudah, int referensiId, String referensiTabel, Date tanggalPerubahan, String keterangan) {
        this.id = id;
        this.barangId = barangId;
        this.dilakukanOleh = dilakukanOleh;
        this.jenisPerubahan = jenisPerubahan;
        this.jumlahPerubahan = jumlahPerubahan;
        this.stokSebelum = stokSebelum;
        this.stokSesudah = stokSesudah;
        this.referensiId = referensiId;
        this.referensiTabel = referensiTabel;
        this.tanggalPerubahan = tanggalPerubahan;
        this.keterangan = keterangan;
    }

    public int getId() { return id; }
    public int getBarangId() { return barangId; }
    public int getDilakukanOleh() { return dilakukanOleh; }
    public String getJenisPerubahan() { return jenisPerubahan; }
    public int getJumlahPerubahan() { return jumlahPerubahan; }
    public int getStokSebelum() { return stokSebelum; }
    public int getStokSesudah() { return stokSesudah; }
    public int getReferensiId() { return referensiId; }
    public String getReferensiTabel() { return referensiTabel; }
    public Date getTanggalPerubahan() { return tanggalPerubahan; }
    public String getKeterangan() { return keterangan; }
}