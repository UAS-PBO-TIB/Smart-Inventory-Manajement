package com.kelompok4.model;

import java.util.Date;

public class AlertStokKritis {
    private int id;
    private int barangId;
    private int stokSaatAlert;
    private int stokMinimum;
    private String statusAlert;
    private Date tanggalAlert;
    private Integer ditanganiOleh;
    private Date tanggalDitangani;
    private String catatan;

    public AlertStokKritis(int id, int barangId, int stokSaatAlert, int stokMinimum, String statusAlert, Date tanggalAlert, Integer ditanganiOleh, Date tanggalDitangani, String catatan) {
        this.id = id;
        this.barangId = barangId;
        this.stokSaatAlert = stokSaatAlert;
        this.stokMinimum = stokMinimum;
        this.statusAlert = statusAlert;
        this.tanggalAlert = tanggalAlert;
        this.ditanganiOleh = ditanganiOleh;
        this.tanggalDitangani = tanggalDitangani;
        this.catatan = catatan;
    }

    public int getId() { return id; }
    public int getBarangId() { return barangId; }
    public int getStokSaatAlert() { return stokSaatAlert; }
    public int getStokMinimum() { return stokMinimum; }
    public String getStatusAlert() { return statusAlert; }
    public Date getTanggalAlert() { return tanggalAlert; }
    public Integer getDitanganiOleh() { return ditanganiOleh; }
    public Date getTanggalDitangani() { return tanggalDitangani; }
    public String getCatatan() { return catatan; }
}