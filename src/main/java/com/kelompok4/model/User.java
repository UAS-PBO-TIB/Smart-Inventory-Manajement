package com.kelompok4.model;

public class User {
    private int id;
    private String nik;
    private String nama;
    private String email;
    private String password;
    private String alamat;
    private String noTelepon;
    private Role role;

    public User(int id, String nik, String nama, String email, String password, String alamat, String noTelepon, Role role) {
        this.id = id;
        this.nik = nik;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}