# Smart Inventory Management System

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Swing](https://img.shields.io/badge/GUI-Swing-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-336791?logo=postgresql)

> Aplikasi desktop manajemen inventaris berbasis **Java Swing** dengan arsitektur **MVC**, **DAO Pattern**, dan database **PostgreSQL**. Mendukung multi‑role (Admin, Manager, Staff), pencatatan stok masuk/keluar, alert stok kritis, serta laporan PDF.

---

## Fitur Utama

- **Autentikasi multi‑role**  
  Login dengan email & password, hak akses terpisah untuk **Admin**, **Manager**, dan **Staff Gudang**.

- **Dashboard informatif**  
  Setiap role melihat ringkasan data yang relevan (total barang, stok kritis, top supplier, top buyer).

- **Manajemen data lengkap**  
  CRUD untuk **Barang** (dengan inheritance `Elektronik` / `ATK`), **Supplier**, **Buyer**, dan **User** (khusus admin).

- **Transaksi stok masuk & keluar**  
  Pencatatan mutasi barang, otomatis update stok dan riwayat transaksi, dengan validasi stok minus.

- **Alert stok kritis**  
  Indikator visual (warna merah) pada tabel barang ketika `stok_saat_ini < stok_minimum`.

- **Pencarian & filter**  
  Cari barang berdasarkan nama/kode/kategori, filter berdasarkan tipe (Elektronik/ATK).

- **Laporan PDF**  
  Cetak laporan inventaris ke PDF (menggunakan OpenPDF) dengan dialog penyimpanan.

- **Arsitektur bersih**  
  MVC + DAO Pattern + koneksi database terpusat, memudahkan maintenance.

---

## Teknologi yang Digunakan

| Komponen         | Teknologi                          |
|-----------------|------------------------------------|
| Bahasa          | Java 17 (Standard Edition)         |
| GUI Framework   | Swing (dengan CardLayout & JTable) |
| Database        | PostgreSQL 14+                     |
| Koneksi DB      | JDBC (postgresql-42.x.x.jar)       |
| Laporan PDF     | OpenPDF 1.3.30                     |
| Build Tool      | Manual (javac) atau NetBeans/IntelliJ |

---

## Struktur Proyek
```
Smart-Inventory-Manajement/
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── kelompok4
│   │               ├── App.java
│   │               ├── controller
│   │               │   ├── AuthController.java
│   │               │   ├── BarangController.java
│   │               │   ├── BuyerController.java
│   │               │   ├── StokController.java
│   │               │   ├── SupplierController.java
│   │               │   └── UserController.java
│   │               ├── dao
│   │               │   ├── BarangDAO.java
│   │               │   ├── BuyerDAO.java
│   │               │   ├── StokTransactionDAO.java
│   │               │   ├── SupplierDAO.java
│   │               │   └── UserDAO.java
│   │               ├── database
│   │               │   └── DBConnection.java
│   │               ├── model
│   │               │   ├── ATK.java
│   │               │   ├── Barang.java
│   │               │   ├── Buyer.java
│   │               │   ├── Elektronik.java
│   │               │   ├── StokTransaction.java
│   │               │   ├── Supplier.java
│   │               │   └── User.java
│   │               └── view
│   │                   ├── LoginDialog.java
│   │                   ├── MainFrame.java
│   │                   └── panels
│   │                       ├── DashboardAdminPanel.java
│   │                       ├── DashboardManagerPanel.java
│   │                       ├── DashboardStaffPanel.java
│   │                       ├── InputStokKeluarPanel.java
│   │                       ├── InputStokMasukPanel.java
│   │                       ├── LaporanBarangPanel.java
│   │                       ├── ManageBarangPanel.java
│   │                       ├── ManageBuyersPanel.java
│   │                       ├── ManageSuppliersPanel.java
│   │                       └── ManageUsersPanel.java
```
---

## 🚀 Cara Menjalankan

### Prasyarat
- **JDK 17** atau lebih baru
- **PostgreSQL** 14+ (dengan user `postgres`)
- **IDE** (NetBeans / IntelliJ) atau command line

### Langkah‑langkah

1. **Clone repository**
   ```bash
   git clone https://github.com/username/smart-inventory.git
   cd smart-inventory
   ```
2. **Buat database & user**
   ```bash
   CREATE DATABASE smart_inventory;
   CREATE USER postgres WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE smart_inventory TO postgres;
   ```
3. **Jalankan script database**
   ```bash
   psql -U postgres -d smart_inventory -f database_setup.sql
   ```
4. **Konfigurasi koneksi**
   Edit file src/com/kelompok4/database/DBConnection.java:
   ```bash
   private static final String URL = "jdbc:postgresql://localhost:5432/smart_inventory";
   private static final String USER = "postgres";
   private static final String PASSWORD = "your_password";
   ```
5. **Tambahkan library eksternal**
   1. Unduh PostgreSQL JDBC Driver (postgresql-42.x.x.jar)
   2. Unduh OpenPDF (openpdf-1.3.30.jar)
   3. Letakkan di folder lib/ dan tambahkan ke classpath proyek.
6. **Kompilasi & jalankan**
   ```bash
   javac -cp "lib/*" -d out src/com/kelompok4/**/*.java
   java -cp "out;lib/*" com.kelompok4.view.LoginDialog
   ```
7. **Login dengan akun dummy yang tersimpan, ex:**

   | Role    | Email               | Password |
   |---------|---------------------|----------|
   | Admin   | admin@example.com   | 123456   |
   | Manager | manager@example.com | 123456   |
   | Staff   | staff@example.com   | 123456   |

---

## Screenshot
1. Login
   
   <img width="399" height="251" alt="image" src="https://github.com/user-attachments/assets/8522fe54-7a2d-4fba-87b5-19cb1b580d5a" />

**Role: Admin**

2. Dashboard Admin
   <img width="1274" height="719" alt="image" src="https://github.com/user-attachments/assets/ace385ab-0897-4aa0-9246-402a9e57d622" />
3. Manage Users
   <img width="1280" height="716" alt="image" src="https://github.com/user-attachments/assets/ffe08147-7efb-41f3-b11d-efd3efe4fafd" />
4. Manage Suppliers
   <img width="1279" height="719" alt="image" src="https://github.com/user-attachments/assets/ec43a63b-7a0d-44fa-a4e8-c6fbf8930e52" />
5. Manage Buyers
   <img width="1279" height="720" alt="image" src="https://github.com/user-attachments/assets/662cf0cb-0baf-461e-a84a-a7309b6f20ee" />

**Role: Manager**

6. Dashboard Manager
   <img width="1278" height="722" alt="image" src="https://github.com/user-attachments/assets/80de1e35-5955-412f-812e-d34cdd649bbe" />
7. Laporan Barang
   <img width="1282" height="720" alt="image" src="https://github.com/user-attachments/assets/002a3998-4826-47c9-b7d3-960372deae6f" />

**Role: Staff**

8. Dashboard Staff
   <img width="1280" height="717" alt="image" src="https://github.com/user-attachments/assets/51836818-a502-429b-a2ac-43cc96e821b4" />
9. Manage Barang
   <img width="1274" height="720" alt="image" src="https://github.com/user-attachments/assets/e712f7b1-76fd-4d2e-bb87-15b8194b74d5" />
10. Input Stok Masuk
   <img width="1274" height="722" alt="image" src="https://github.com/user-attachments/assets/4a807602-a4c3-408d-af40-ecb26310bec8" />
11. Input Stok Keluar
   <img width="1277" height="720" alt="image" src="https://github.com/user-attachments/assets/de7d66bd-4f7c-41de-b445-c40918d853fb" />

## Ucapan Terima Kasih
Dosen pembimbing mata kuliah Pemrograman Berorientasi Objek yaitu Pak Fikri.
Semua anggota kelompok 4 yang telah berkontribusi.
