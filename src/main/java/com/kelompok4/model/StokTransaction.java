package com.kelompok4.model;

import java.sql.Timestamp;

public class StokTransaction {
    private int id;
    private int barangId;      // foreign key ke Barang
    private String tipeTransaksi; // "MASUK" atau "KELUAR"
    private int jumlah;
    private Timestamp tanggal;
    private String keterangan;
    private Integer supplierId; // boleh null
    private Integer buyerId;    // boleh null

    // Objek relasi (opsional untuk kemudahan)
    private Barang barang;
    private Supplier supplier;
    private Buyer buyer;

    public StokTransaction() {}

    public StokTransaction(int id, int barangId, String tipeTransaksi, int jumlah, 
                           Timestamp tanggal, String keterangan, Integer supplierId, Integer buyerId) {
        this.id = id;
        this.barangId = barangId;
        this.tipeTransaksi = tipeTransaksi;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
        this.supplierId = supplierId;
        this.buyerId = buyerId;
    }

    // Getter dan Setter untuk field dasar
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBarangId() { return barangId; }
    public void setBarangId(int barangId) { this.barangId = barangId; }
    public String getTipeTransaksi() { return tipeTransaksi; }
    public void setTipeTransaksi(String tipeTransaksi) { this.tipeTransaksi = tipeTransaksi; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public Timestamp getTanggal() { return tanggal; }
    public void setTanggal(Timestamp tanggal) { this.tanggal = tanggal; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    public Integer getSupplierId() { return supplierId; }
    public void setSupplierId(Integer supplierId) { this.supplierId = supplierId; }
    public Integer getBuyerId() { return buyerId; }
    public void setBuyerId(Integer buyerId) { this.buyerId = buyerId; }

    // Getter dan Setter untuk objek relasi (diisi oleh DAO jika diperlukan)
    public Barang getBarang() { return barang; }
    public void setBarang(Barang barang) { this.barang = barang; }
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
    public Buyer getBuyer() { return buyer; }
    public void setBuyer(Buyer buyer) { this.buyer = buyer; }
}