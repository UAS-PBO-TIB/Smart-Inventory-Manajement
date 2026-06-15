package com.kelompok4.model;

public class User {
    private int id;
    private String email;
    private String password;
    private String nama;
    private String role; // admin, manager, staff

    public User() {}

    public User(int id, String email, String password, String nama, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.role = role;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}