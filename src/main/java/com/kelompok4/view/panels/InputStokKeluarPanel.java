package com.kelompok4.view.panels;

import com.kelompok4.controller.*;
import com.kelompok4.model.Barang;
import com.kelompok4.model.Buyer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class InputStokKeluarPanel extends JPanel {
    private JComboBox<Barang> barangCombo;
    private JSpinner jumlahSpinner;
    private JTextArea keteranganArea;
    private JComboBox<String> buyerCombo;
    private BarangController barangController;
    private BuyerController buyerController;
    private StokController stokController;

    public InputStokKeluarPanel() {
        barangController = new BarangController();
        buyerController = new BuyerController();
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
        add(new JLabel("Jumlah Keluar:"), gbc);
        gbc.gridx=1;
        jumlahSpinner = new JSpinner(new SpinnerNumberModel(1,1,10000,1));
        add(jumlahSpinner, gbc);

        gbc.gridx=0; gbc.gridy=2;
        add(new JLabel("Buyer:"), gbc);
        gbc.gridx=1;
        buyerCombo = new JComboBox<>();
        add(buyerCombo, gbc);

        gbc.gridx=0; gbc.gridy=3;
        add(new JLabel("Keterangan:"), gbc);
        gbc.gridx=1;
        keteranganArea = new JTextArea(3,20);
        add(new JScrollPane(keteranganArea), gbc);

        JButton submitBtn = new JButton("Simpan Stok Keluar");
        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2;
        add(submitBtn, gbc);
        submitBtn.addActionListener(e -> simpan());

        loadBarang();
        loadBuyers();
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

    private void loadBuyers() {
        try {
            List<Buyer> list = buyerController.getAllBuyers();
            buyerCombo.removeAllItems();
            buyerCombo.addItem("(Tanpa buyer)");
            for (Buyer b : list) {
                buyerCombo.addItem(b.getNama() + " (ID:" + b.getId() + ")");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat buyer: " + e.getMessage());
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
        Integer buyerId = null;
        String buyerSelected = (String) buyerCombo.getSelectedItem();
        if (buyerSelected != null && !buyerSelected.equals("(Tanpa buyer)")) {
            String idPart = buyerSelected.substring(buyerSelected.indexOf("ID:")+3, buyerSelected.length()-1);
            buyerId = Integer.parseInt(idPart);
        }
        try {
            stokController.tambahStokKeluar(selected.getId(), jumlah, keterangan, buyerId);
            JOptionPane.showMessageDialog(this, "Stok keluar berhasil dicatat");
            jumlahSpinner.setValue(1);
            keteranganArea.setText("");
            loadBarang();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage());
        }
    }
    
    public void refreshData() {
        loadBarang();
        loadBuyers();
    }
}