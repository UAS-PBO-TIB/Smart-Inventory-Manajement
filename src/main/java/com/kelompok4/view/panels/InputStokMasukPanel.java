package com.kelompok4.view.panels;

import com.kelompok4.controller.*;
import com.kelompok4.model.Barang;
import com.kelompok4.model.Supplier;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class InputStokMasukPanel extends JPanel {
    private JComboBox<Barang> barangCombo;
    private JSpinner jumlahSpinner;
    private JTextArea keteranganArea;
    private JComboBox<String> supplierCombo;
    private BarangController barangController;
    private SupplierController supplierController;
    private StokController stokController;

    public InputStokMasukPanel() {
        barangController = new BarangController();
        supplierController = new SupplierController();
        stokController = new StokController();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx=0; gbc.gridy=0;
        add(new JLabel("Barang:"), gbc);
        gbc.gridx=1;
        barangCombo = new JComboBox<>();
        barangCombo.setPreferredSize(new Dimension(200,25));
        add(barangCombo, gbc);

        gbc.gridx=0; gbc.gridy=1;
        add(new JLabel("Jumlah Masuk:"), gbc);
        gbc.gridx=1;
        jumlahSpinner = new JSpinner(new SpinnerNumberModel(1,1,10000,1));
        add(jumlahSpinner, gbc);

        gbc.gridx=0; gbc.gridy=2;
        add(new JLabel("Supplier:"), gbc);
        gbc.gridx=1;
        supplierCombo = new JComboBox<>();
        add(supplierCombo, gbc);

        gbc.gridx=0; gbc.gridy=3;
        add(new JLabel("Keterangan:"), gbc);
        gbc.gridx=1;
        keteranganArea = new JTextArea(3,20);
        add(new JScrollPane(keteranganArea), gbc);

        JButton submitBtn = new JButton("Simpan Stok Masuk");
        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2;
        add(submitBtn, gbc);
        submitBtn.addActionListener(e -> simpan());

        loadBarang();
        loadSuppliers();
    }

    private void loadBarang() {
        try {
            List<Barang> list = barangController.getAllBarang();
            barangCombo.removeAllItems();
            for (Barang b : list) {
                barangCombo.addItem(b);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat barang: " + e.getMessage());
        }
    }

    private void loadSuppliers() {
        try {
            List<Supplier> list = supplierController.getAllSuppliers();
            supplierCombo.removeAllItems();
            supplierCombo.addItem("(Tanpa supplier)");
            for (Supplier s : list) {
                supplierCombo.addItem(s.getNama() + " (ID:" + s.getId() + ")");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat supplier: " + e.getMessage());
        }
    }

    private void simpan() {
        Barang selected = (Barang) barangCombo.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Pilih barang");
            return;
        }
        int jumlah = (int) jumlahSpinner.getValue();
        String keterangan = keteranganArea.getText();
        Integer supplierId = null;
        String supplierSelected = (String) supplierCombo.getSelectedItem();
        if (supplierSelected != null && !supplierSelected.equals("(Tanpa supplier)")) {
            String idPart = supplierSelected.substring(supplierSelected.indexOf("ID:")+3, supplierSelected.length()-1);
            supplierId = Integer.parseInt(idPart);
        }
        try {
            stokController.tambahStokMasuk(selected.getId(), jumlah, keterangan, supplierId);
            JOptionPane.showMessageDialog(this, "Stok masuk berhasil ditambahkan");
            // Reset
            jumlahSpinner.setValue(1);
            keteranganArea.setText("");
            loadBarang(); // refresh combobox (stok berubah)
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage());
        }
    }
    
    public void refreshData() {
        loadBarang();
        loadSuppliers();
    }
}