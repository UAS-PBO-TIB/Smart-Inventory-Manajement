/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.kelompok4.ui.staff;

import com.kelompok4.model.Barang;
import com.kelompok4.service.ServiceFactory;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 * @author kelompok4
 */
public class form_stok_masuk extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(form_stok_masuk.class.getName());
    private final ServiceFactory sf = ServiceFactory.getInstance();
    private int userId;
    private DefaultTableModel modelRiwayat;

    public form_stok_masuk(int userId) {
        this.userId = userId;
        initComponents();
        loadBarang();
        loadRiwayat();
    }

    public form_stok_masuk() {
        initComponents();
        loadBarang();
        loadRiwayat();
    }

    private void loadBarang() {
        try {
            jComboBoxBarang.removeAllItems();
            for (Barang b : sf.getBarangService().getAllBarang()) {
                jComboBoxBarang.addItem(b);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void loadRiwayat() {
        try {
            modelRiwayat = (DefaultTableModel) jTableRiwayat.getModel();
            modelRiwayat.setRowCount(0);
            sf.getStokService().getAllStokMasuk().forEach(sm ->
                modelRiwayat.addRow(new Object[]{
                    sm.getNomorTransaksi(), sm.getBarangId(), sm.getSupplierId(),
                    sm.getJumlah(), sm.getHargaSatuan(), sm.getTotalHarga(), sm.getTanggalMasuk()
                })
            );
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void simpan() {
        try {
            Barang barang = (Barang) jComboBoxBarang.getSelectedItem();
            if (barang == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Pilih barang terlebih dahulu!");
                return;
            }
            int supplierId = Integer.parseInt(jTextFieldSupplierId.getText().trim());
            int jumlah = Integer.parseInt(jTextFieldJumlah.getText().trim());
            BigDecimal harga = new BigDecimal(jTextFieldHarga.getText().trim());

            sf.getStokService().prosesStokMasuk(
                jTextFieldNoTransaksi.getText(), barang.getId(), supplierId,
                userId, jumlah, harga, Date.valueOf(LocalDate.now()), jTextFieldCatatan.getText()
            );

            javax.swing.JOptionPane.showMessageDialog(this, "Stok masuk berhasil dicatat!");
            jTextFieldNoTransaksi.setText("SM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            jTextFieldSupplierId.setText("");
            jTextFieldJumlah.setText("");
            jTextFieldHarga.setText("");
            jTextFieldCatatan.setText("");
            loadRiwayat();
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Supplier ID, Jumlah, dan Harga harus berupa angka!", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelForm = new javax.swing.JPanel();
        jLabelNoTransaksi = new javax.swing.JLabel();
        jTextFieldNoTransaksi = new javax.swing.JTextField();
        jLabelBarang = new javax.swing.JLabel();
        jComboBoxBarang = new javax.swing.JComboBox();
        jLabelSupplierId = new javax.swing.JLabel();
        jTextFieldSupplierId = new javax.swing.JTextField();
        jLabelJumlah = new javax.swing.JLabel();
        jTextFieldJumlah = new javax.swing.JTextField();
        jLabelHarga = new javax.swing.JLabel();
        jTextFieldHarga = new javax.swing.JTextField();
        jLabelCatatan = new javax.swing.JLabel();
        jTextFieldCatatan = new javax.swing.JTextField();
        jButtonSimpan = new javax.swing.JButton();
        jPanelRiwayat = new javax.swing.JPanel();
        jLabelRiwayat = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRiwayat = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Stok Masuk");
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanelForm.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Stok Masuk"));

        jLabelNoTransaksi.setText("No. Transaksi:");

        jTextFieldNoTransaksi.setEditable(false);
        jTextFieldNoTransaksi.setText("SM-");

        jLabelBarang.setText("Barang:");

        jLabelSupplierId.setText("Supplier ID:");

        jLabelJumlah.setText("Jumlah:");

        jLabelHarga.setText("Harga Satuan:");

        jLabelCatatan.setText("Catatan:");

        jButtonSimpan.setText("Simpan");
        jButtonSimpan.addActionListener(this::jButtonSimpanActionPerformed);

        javax.swing.GroupLayout jPanelFormLayout = new javax.swing.GroupLayout(jPanelForm);
        jPanelForm.setLayout(jPanelFormLayout);
        jPanelFormLayout.setHorizontalGroup(
            jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNoTransaksi)
                    .addComponent(jLabelBarang)
                    .addComponent(jLabelSupplierId)
                    .addComponent(jLabelJumlah)
                    .addComponent(jLabelHarga)
                    .addComponent(jLabelCatatan))
                .addGap(18, 18, 18)
                .addGroup(jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldNoTransaksi, 0, 300, Short.MAX_VALUE)
                    .addComponent(jComboBoxBarang, 0, 300, Short.MAX_VALUE)
                    .addComponent(jTextFieldSupplierId, 0, 300, Short.MAX_VALUE)
                    .addComponent(jTextFieldJumlah, 0, 300, Short.MAX_VALUE)
                    .addComponent(jTextFieldHarga, 0, 300, Short.MAX_VALUE)
                    .addComponent(jTextFieldCatatan, 0, 300, Short.MAX_VALUE)
                    .addComponent(jButtonSimpan, javax.swing.GroupLayout.Alignment.CENTER))
                .addContainerGap())
        );
        jPanelFormLayout.setVerticalGroup(
            jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNoTransaksi)
                    .addComponent(jTextFieldNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBarang)
                    .addComponent(jComboBoxBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSupplierId)
                    .addComponent(jTextFieldSupplierId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelJumlah)
                    .addComponent(jTextFieldJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHarga)
                    .addComponent(jTextFieldHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCatatan)
                    .addComponent(jTextFieldCatatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonSimpan)
                .addContainerGap())
        );

        jPanelRiwayat.setBorder(javax.swing.BorderFactory.createTitledBorder("Riwayat Stok Masuk"));

        jLabelRiwayat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRiwayat.setText("Riwayat Transaksi");

        jTableRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Transaksi", "Barang ID", "Supplier ID", "Jumlah", "Harga Satuan", "Total", "Tanggal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableRiwayat);

        javax.swing.GroupLayout jPanelRiwayatLayout = new javax.swing.GroupLayout(jPanelRiwayat);
        jPanelRiwayat.setLayout(jPanelRiwayatLayout);
        jPanelRiwayatLayout.setHorizontalGroup(
            jPanelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRiwayatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRiwayat)
                    .addComponent(jScrollPane1, 0, 750, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelRiwayatLayout.setVerticalGroup(
            jPanelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRiwayatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelRiwayat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, 0, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelForm, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelRiwayat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelRiwayat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimpanActionPerformed
        simpan();
    }//GEN-LAST:event_jButtonSimpanActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new form_stok_masuk().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSimpan;
    private javax.swing.JComboBox jComboBoxBarang;
    private javax.swing.JLabel jLabelBarang;
    private javax.swing.JLabel jLabelCatatan;
    private javax.swing.JLabel jLabelHarga;
    private javax.swing.JLabel jLabelJumlah;
    private javax.swing.JLabel jLabelNoTransaksi;
    private javax.swing.JLabel jLabelRiwayat;
    private javax.swing.JLabel jLabelSupplierId;
    private javax.swing.JPanel jPanelForm;
    private javax.swing.JPanel jPanelRiwayat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableRiwayat;
    private javax.swing.JTextField jTextFieldCatatan;
    private javax.swing.JTextField jTextFieldHarga;
    private javax.swing.JTextField jTextFieldJumlah;
    private javax.swing.JTextField jTextFieldNoTransaksi;
    private javax.swing.JTextField jTextFieldSupplierId;
    // End of variables declaration//GEN-END:variables
}
