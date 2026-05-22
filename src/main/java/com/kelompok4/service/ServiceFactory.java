/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kelompok4.service;

/**
 *
 * @author n03ll
 */
import com.kelompok4.database.*;

public class ServiceFactory {
    private static ServiceFactory instance;

    // Menyimpan instansi tunggal dari tiap service
    private final UserService userService;
    private final KategoriService kategoriService;
    private final BarangService barangService;
    private final StokService stokService;
    private final AlertService alertService;

    // Constructor Private agar tidak bisa di-new secara sembarangan dari luar
    private ServiceFactory() {
        // 1. Inisialisasi semua objek DAO terlebih dahulu
        UserDAO userDAO = new UserDAO();
        KategoriDAO kategoriDAO = new KategoriDAO();
        BarangDAO barangDAO = new BarangDAO();
        StokMasukDAO stokMasukDAO = new StokMasukDAO();
        StokKeluarDAO stokKeluarDAO = new StokKeluarDAO();
        AlertDAO alertDAO = new AlertDAO();

        // 2. Suntikkan (Inject) DAO ke masing-masing Service yang membutuhkan
        this.userService = new UserService(userDAO);
        this.kategoriService = new KategoriService(kategoriDAO);
        this.barangService = new BarangService(barangDAO);
        this.alertService = new AlertService(alertDAO);
        this.stokService = new StokService(stokMasukDAO, stokKeluarDAO, barangDAO, alertDAO);
    }

    // Mengambil satu-satunya objek Factory yang ada
    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    // Getter untuk diakses oleh Anggota Tim bagian UI / Controller
    public UserService getUserService() { return userService; }
    public KategoriService getKategoriService() { return kategoriService; }
    public BarangService getBarangService() { return barangService; }
    public StokService getStokService() { return stokService; }
    public AlertService getAlertService() { return alertService; }
}
