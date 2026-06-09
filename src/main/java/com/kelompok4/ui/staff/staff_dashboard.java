/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.kelompok4.ui.staff;

import com.kelompok4.model.AlertStokKritis;
import com.kelompok4.model.Barang;
import com.kelompok4.service.ServiceFactory;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 * @author kelompok4
 */
public class staff_dashboard extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(staff_dashboard.class.getName());
    private final ServiceFactory sf = ServiceFactory.getInstance();
    private int userId;
    private DefaultTableModel modelBarang;
    private DefaultTableModel modelAlert;

    public staff_dashboard(int userId, String namaUser) {
        this.userId = userId;
        initComponents();
        jLabelNamaUser.setText("Halo, " + namaUser);
        loadData();
    }

    public staff_dashboard() {
        initComponents();
        loadData();
    }

    private void loadData() {
        try {
            List<Barang> barangs = sf.getBarangService().getAllBarang();
            modelBarang = (DefaultTableModel) jTableBarang.getModel();
            modelBarang.setRowCount(0);
            for (Barang b : barangs) {
                modelBarang.addRow(new Object[]{
                    b.getKodeBarang(), b.getNamaBarang(), b.getKategoriId(),
                    b.getStokSaatIni(), b.getStokMinimum(), b.getSatuan()
                });
            }
            jLabelTotalBarang.setText(String.valueOf(barangs.size()));

            List<AlertStokKritis> alerts = sf.getAlertService().getAlertAktif();
            modelAlert = (DefaultTableModel) jTableAlert.getModel();
            modelAlert.setRowCount(0);
            for (AlertStokKritis a : alerts) {
                modelAlert.addRow(new Object[]{
                    a.getId(), a.getBarangId(), a.getStokSaatAlert(),
                    a.getStokMinimum(), a.getTanggalAlert()
                });
            }
            jLabelTotalAlert.setText(String.valueOf(alerts.size()));
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cariBarang() {
        try {
            String keyword = jTextFieldCari.getText().trim();
            List<Barang> hasil = sf.getBarangService().cariBarang(keyword);
            modelBarang.setRowCount(0);
            for (Barang b : hasil) {
                modelBarang.addRow(new Object[]{
                    b.getKodeBarang(), b.getNamaBarang(), b.getKategoriId(),
                    b.getStokSaatIni(), b.getStokMinimum(), b.getSatuan()
                });
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSidebar = new javax.swing.JPanel();
        jLabelNamaUser = new javax.swing.JLabel();
        jButtonDashboard = new javax.swing.JButton();
        jButtonStokMasuk = new javax.swing.JButton();
        jButtonStokKeluar = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jPanelMain = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanelKartuBarang = new javax.swing.JPanel();
        jLabelKeteranganBarang = new javax.swing.JLabel();
        jLabelTotalBarang = new javax.swing.JLabel();
        jPanelKartuAlert = new javax.swing.JPanel();
        jLabelKeteranganAlert = new javax.swing.JLabel();
        jLabelTotalAlert = new javax.swing.JLabel();
        jPanelTabelBarang = new javax.swing.JPanel();
        jLabelDaftarBarang = new javax.swing.JLabel();
        jTextFieldCari = new javax.swing.JTextField();
        jButtonCari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBarang = new javax.swing.JTable();
        jPanelTabelAlert = new javax.swing.JPanel();
        jLabelAlertKritis = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAlert = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Staff Dashboard - Smart Inventory");
        setMinimumSize(new java.awt.Dimension(1080, 700));
        setPreferredSize(new java.awt.Dimension(1080, 700));

        jPanelSidebar.setBackground(new java.awt.Color(30, 58, 95));
        jPanelSidebar.setPreferredSize(new java.awt.Dimension(180, 700));

        jLabelNamaUser.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNamaUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNamaUser.setText("Staff");

        jButtonDashboard.setText("Dashboard");
        jButtonDashboard.addActionListener(this::jButtonDashboardActionPerformed);

        jButtonStokMasuk.setText("Stok Masuk");
        jButtonStokMasuk.addActionListener(this::jButtonStokMasukActionPerformed);

        jButtonStokKeluar.setText("Stok Keluar");
        jButtonStokKeluar.addActionListener(this::jButtonStokKeluarActionPerformed);

        jButtonLogout.setBackground(new java.awt.Color(180, 50, 50));
        jButtonLogout.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(this::jButtonLogoutActionPerformed);

        javax.swing.GroupLayout jPanelSidebarLayout = new javax.swing.GroupLayout(jPanelSidebar);
        jPanelSidebar.setLayout(jPanelSidebarLayout);
        jPanelSidebarLayout.setHorizontalGroup(
            jPanelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNamaUser, 0, 160, Short.MAX_VALUE)
                    .addComponent(jButtonDashboard, 0, 160, Short.MAX_VALUE)
                    .addComponent(jButtonStokMasuk, 0, 168, Short.MAX_VALUE)
                    .addComponent(jButtonStokKeluar, 0, 160, Short.MAX_VALUE)
                    .addComponent(jButtonLogout, 0, 160, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelSidebarLayout.setVerticalGroup(
            jPanelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSidebarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelNamaUser)
                .addGap(30, 30, 30)
                .addComponent(jButtonDashboard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonStokMasuk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonStokKeluar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLogout)
                .addContainerGap())
        );

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabelTitle.setText("Dashboard Staff");

        jPanelKartuBarang.setBackground(new java.awt.Color(52, 152, 219));
        jPanelKartuBarang.setPreferredSize(new java.awt.Dimension(250, 90));

        jLabelKeteranganBarang.setForeground(new java.awt.Color(255, 255, 255));
        jLabelKeteranganBarang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelKeteranganBarang.setText("Total Barang");

        jLabelTotalBarang.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabelTotalBarang.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalBarang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTotalBarang.setText("0");

        javax.swing.GroupLayout jPanelKartuBarangLayout = new javax.swing.GroupLayout(jPanelKartuBarang);
        jPanelKartuBarang.setLayout(jPanelKartuBarangLayout);
        jPanelKartuBarangLayout.setHorizontalGroup(
            jPanelKartuBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKartuBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelKartuBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelKeteranganBarang, 0, 238, Short.MAX_VALUE)
                    .addComponent(jLabelTotalBarang, 0, 230, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelKartuBarangLayout.setVerticalGroup(
            jPanelKartuBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKartuBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelKeteranganBarang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotalBarang)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanelKartuAlert.setBackground(new java.awt.Color(231, 76, 60));
        jPanelKartuAlert.setPreferredSize(new java.awt.Dimension(250, 90));

        jLabelKeteranganAlert.setForeground(new java.awt.Color(255, 255, 255));
        jLabelKeteranganAlert.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelKeteranganAlert.setText("Alert Stok Kritis");

        jLabelTotalAlert.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabelTotalAlert.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalAlert.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTotalAlert.setText("0");

        javax.swing.GroupLayout jPanelKartuAlertLayout = new javax.swing.GroupLayout(jPanelKartuAlert);
        jPanelKartuAlert.setLayout(jPanelKartuAlertLayout);
        jPanelKartuAlertLayout.setHorizontalGroup(
            jPanelKartuAlertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKartuAlertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelKartuAlertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelKeteranganAlert, 0, 238, Short.MAX_VALUE)
                    .addComponent(jLabelTotalAlert, 0, 230, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelKartuAlertLayout.setVerticalGroup(
            jPanelKartuAlertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKartuAlertLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelKeteranganAlert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotalAlert)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabelDaftarBarang.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabelDaftarBarang.setText("Daftar Barang");

        jTextFieldCari.setColumns(20);

        jButtonCari.setText("Cari");
        jButtonCari.addActionListener(this::jButtonCariActionPerformed);

        jTableBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Nama Barang", "Kategori ID", "Stok", "Min. Stok", "Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableBarang);

        javax.swing.GroupLayout jPanelTabelBarangLayout = new javax.swing.GroupLayout(jPanelTabelBarang);
        jPanelTabelBarang.setLayout(jPanelTabelBarangLayout);
        jPanelTabelBarangLayout.setHorizontalGroup(
            jPanelTabelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabelBarangLayout.createSequentialGroup()
                .addComponent(jLabelDaftarBarang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldCari, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCari))
            .addComponent(jScrollPane1, 0, 860, Short.MAX_VALUE)
        );
        jPanelTabelBarangLayout.setVerticalGroup(
            jPanelTabelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabelBarangLayout.createSequentialGroup()
                .addGroup(jPanelTabelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDaftarBarang)
                    .addComponent(jTextFieldCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, 0, 263, Short.MAX_VALUE))
        );

        jLabelAlertKritis.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabelAlertKritis.setForeground(new java.awt.Color(192, 0, 0));
        jLabelAlertKritis.setText("Alert Stok Kritis");

        jTableAlert.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Alert", "Barang ID", "Stok Saat Alert", "Stok Minimum", "Tanggal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableAlert);

        javax.swing.GroupLayout jPanelTabelAlertLayout = new javax.swing.GroupLayout(jPanelTabelAlert);
        jPanelTabelAlert.setLayout(jPanelTabelAlertLayout);
        jPanelTabelAlertLayout.setHorizontalGroup(
            jPanelTabelAlertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelAlertKritis)
            .addComponent(jScrollPane2, 0, 860, Short.MAX_VALUE)
        );
        jPanelTabelAlertLayout.setVerticalGroup(
            jPanelTabelAlertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabelAlertLayout.createSequentialGroup()
                .addComponent(jLabelAlertKritis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, 0, 214, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitle)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jPanelKartuBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelKartuAlert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelTabelBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTabelAlert, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelKartuBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelKartuAlert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelTabelBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelTabelAlert, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMain, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelSidebar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelMain, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDashboardActionPerformed
        loadData();
    }//GEN-LAST:event_jButtonDashboardActionPerformed

    private void jButtonStokMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStokMasukActionPerformed
        new form_stok_masuk(userId).setVisible(true);
    }//GEN-LAST:event_jButtonStokMasukActionPerformed

    private void jButtonStokKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStokKeluarActionPerformed
        new form_stok_keluar(userId).setVisible(true);
    }//GEN-LAST:event_jButtonStokKeluarActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Logout", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
            new com.kelompok4.ui.login().setVisible(true);
        }
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCariActionPerformed
        cariBarang();
    }//GEN-LAST:event_jButtonCariActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new staff_dashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCari;
    private javax.swing.JButton jButtonDashboard;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonStokKeluar;
    private javax.swing.JButton jButtonStokMasuk;
    private javax.swing.JLabel jLabelAlertKritis;
    private javax.swing.JLabel jLabelDaftarBarang;
    private javax.swing.JLabel jLabelKeteranganAlert;
    private javax.swing.JLabel jLabelKeteranganBarang;
    private javax.swing.JLabel jLabelNamaUser;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelTotalAlert;
    private javax.swing.JLabel jLabelTotalBarang;
    private javax.swing.JPanel jPanelKartuAlert;
    private javax.swing.JPanel jPanelKartuBarang;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JPanel jPanelTabelAlert;
    private javax.swing.JPanel jPanelTabelBarang;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableAlert;
    private javax.swing.JTable jTableBarang;
    private javax.swing.JTextField jTextFieldCari;
    // End of variables declaration//GEN-END:variables
}
