/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kelompok4.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String URL      = "jdbc:postgresql://localhost:5432/smart_inventory";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "123456789";

    // Singleton: satu koneksi dibagi pakai
    private static Connection connection = null;

    // Konstruktor private biar tidak bisa di-new dari luar
    private Koneksi() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi ke database berhasil!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver PostgreSQL tidak ditemukan. Pastikan library JDBC sudah ditambahkan.", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Koneksi ditutup.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/**
 *
 * @author user
 */