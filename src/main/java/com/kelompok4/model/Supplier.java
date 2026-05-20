package com.kelompok4.model;

public class Supplier {
    private int id;
    private String kodeSupplier;
    private String namaSupplier;
    private String nomorTelepon;
    private String email;

    public Supplier(int id, String kodeSupplier, String namaSupplier, String nomorTelepon, String email) {
        this.id = id;
        this.kodeSupplier = kodeSupplier;
        this.namaSupplier = namaSupplier;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKodeSupplier() { return kodeSupplier; }
    public void setKodeSupplier(String kodeSupplier) { this.kodeSupplier = kodeSupplier; }
    public String getNamaSupplier() { return namaSupplier; }
    public void setNamaSupplier(String namaSupplier) { this.namaSupplier = namaSupplier; }
    public String getNomorTelepon() { return nomorTelepon; }
    public void setNomorTelepon(String nomorTelepon) { this.nomorTelepon = nomorTelepon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
