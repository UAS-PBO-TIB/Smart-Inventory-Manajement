/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kelompok4.service;

/**
 *
 * @author n03ll
 */
// UserService.java
import java.sql.SQLException;
import java.util.List;
import com.kelompok4.database.UserDAO;
import com.kelompok4.service.ServiceException;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(String email, String password) throws SQLException {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new ServiceException("Email dan password tidak boleh kosong!");
        }
        // NOTE: Jika nanti menggunakan hashing (BCrypt/SHA-256), lakukan enkripsi password di sini 
        // sebelum dicocokkan ke database melalui DAO.
        User user = userDAO.login(email, password);
        if (user == null) {
            throw new ServiceException("Email atau password salah!");
        }
        return user;
    }

    public List<User> getAllUser() throws SQLException {
        return userDAO.getAllUser();
    }

    public User getUserById(int id) throws SQLException {
        return userDAO.getUserById(id);
    }

    public List<User> getUserByRole(Role role) throws SQLException {
        return userDAO.getUserByRole(role);
    }

    public User tambahUser(String nik, String nama, String email, String password, 
                            String alamat, String noTelepon, Role role) throws SQLException {
        // Validasi Bisnis
        if (nik == null || nik.length() < 5) throw new ServiceException("NIK tidak valid!");
        if (nama == null || nama.trim().isEmpty()) throw new ServiceException("Nama tidak boleh kosong!");
        if (email == null || !email.contains("@")) throw new ServiceException("Format email salah!");
        
        return userDAO.tambahUser(nik, nama, email, password, alamat, noTelepon, role);
    }

    public User editUser(int id, String nama, String email, String alamat, 
                         String noTelepon, Role role) throws SQLException {
        if (nama == null || nama.trim().isEmpty()) throw new ServiceException("Nama tidak boleh kosong!");
        return userDAO.editUser(id, nama, email, alamat, noTelepon, role);
    }

    public void gantiPassword(int id, String passwordBaru) throws SQLException {
        if (passwordBaru == null || passwordBaru.length() < 6) {
            throw new ServiceException("Password baru minimal harus 6 karakter!");
        }
        userDAO.gantiPassword(id, passwordBaru);
    }

    public void hapusUser(int id) throws SQLException {
        userDAO.hapusUser(id);
    }
}
