package com.kelompok4.view.panels;

import com.kelompok4.controller.BarangController;
import com.kelompok4.model.Barang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class DashboardStaffPanel extends JPanel {

    private JLabel totalBarangLabel, totalKritisLabel;
    private JTable barangTable, kritisTable;
    private BarangController barangController;

    private JLabel createStatCard(String title, String value, Color bcolor, Color fcolor) {
        JLabel label = new JLabel(
                "<html><center>"
                + "<div style='font-size:12px'>" + title + "</div>"
                + "<div style='font-size:28px'><b>" + value + "</b></div>"
                + "</center></html>"
        );

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(bcolor);
        label.setForeground(fcolor);

        label.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bcolor.darker(), 2),
                        BorderFactory.createEmptyBorder(15, 10, 15, 10)
                )
        );

        return label;
    }

    private void highlightCriticalStock() {

        barangTable.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
            ) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

                int stok = Integer.parseInt(
                        table.getValueAt(row, 4).toString()
                );

                int minimal = Integer.parseInt(
                        table.getValueAt(row, 5).toString()
                );

                if (isSelected) {

                    if (stok <= minimal) {
                        // kritis + dipilih
                        c.setBackground(new Color(150, 0, 0));
                        c.setForeground(Color.WHITE);

                    } else {
                        // normal + dipilih
                        c.setBackground(new Color(52, 73, 94));
                        c.setForeground(Color.WHITE);
                    }

                } else {

                    // odd even
                    if (row % 2 == 0) {
                        c.setBackground(new Color(245, 245, 245));
                    } else {
                        c.setBackground(Color.WHITE);
                    }

                    c.setForeground(Color.BLACK);

                    // stok kritis
                    if (stok <= minimal) {
                        c.setBackground(new Color(231, 76, 60));
                        c.setForeground(Color.WHITE);
                    }
                }

                return c;
            }
        }
        );
    }

    public DashboardStaffPanel() {
        barangController = new BarangController();
        setLayout(new BorderLayout());

        // Panel statistik atas
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        totalBarangLabel = createStatCard("Total Barang", "0", new Color(217, 249, 223), new Color(13, 12, 12));
        totalKritisLabel = createStatCard("Stok Kritis", "0", new Color(227, 86, 98), new Color(255, 242, 242));

        statsPanel.add(totalBarangLabel);
        statsPanel.add(totalKritisLabel);

        add(statsPanel, BorderLayout.NORTH);

        // Tabbed pane untuk tabel
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Semua Barang", createBarangPanel());
        tabs.addTab("Barang Kritis", createKritisPanel());
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createBarangPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        barangTable = new JTable();
        panel.add(new JScrollPane(barangTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createKritisPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        kritisTable = new JTable();
        panel.add(new JScrollPane(kritisTable), BorderLayout.CENTER);
        return panel;
    }

    private void refreshStats() {
        try {
            int total = barangController.countBarang();
            int kritis = barangController.countBarangKritis();
            totalBarangLabel.setText(
                    "<html><center>Total Barang<br><font size='6'><b>"
                    + total
                    + "</b></font></center></html>"
            );

            totalKritisLabel.setText(
                    "<html><center>Stok Kritis<br><font size='6'><b>"
                    + kritis
                    + "</b></font></center></html>"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllBarang() {
        try {
            List<Barang> list = barangController.getAllBarang();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Kategori", "Stok", "Minimal", "Tipe"}, 0);
            for (Barang b : list) {
                model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getKategori(), b.getStokSaatIni(), b.getStokMinimum(), b.getTipeBarang()});
            }
            barangTable.setModel(model);
            highlightCriticalStock();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat barang: " + e.getMessage());
        }
    }

    private void loadKritisBarang() {
        try {
            List<Barang> list = barangController.getBarangKritis();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Stok", "Minimal"}, 0);
            for (Barang b : list) {
                model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getStokSaatIni(), b.getStokMinimum()});
            }
            kritisTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat barang kritis: " + e.getMessage());
        }
    }

    public void refreshAll() {
        refreshStats();
        loadAllBarang();
        loadKritisBarang();
    }

}
