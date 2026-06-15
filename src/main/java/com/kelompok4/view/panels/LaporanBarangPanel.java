package com.kelompok4.view.panels;

import com.kelompok4.controller.BarangController;
import com.kelompok4.model.Barang;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class LaporanBarangPanel extends JPanel {
    private BarangController barangController;
    private JTable table;
    private DefaultTableModel model;

    public LaporanBarangPanel() {
        barangController = new BarangController();
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Kode", "Nama", "Kategori", "Stok", "Minimal", "Status"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton pdfBtn = new JButton("Cetak Laporan PDF");
        pdfBtn.addActionListener(e -> cetakPDF());
        add(pdfBtn, BorderLayout.SOUTH);

        loadData();
    }

    public void loadData() {
        try {
            List<Barang> list = barangController.getAllBarang();
            model.setRowCount(0);
            for (Barang b : list) {
                String status = b.isStokKritis() ? "Kritis" : "Aman";
                model.addRow(new Object[]{b.getId(), b.getKodeBarang(), b.getNama(), b.getKategori(), b.getStokSaatIni(), b.getStokMinimum(), status});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void cetakPDF() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Simpan Laporan PDF");
        chooser.setSelectedFile(new File("laporan_barang.pdf"));
        int pilihan = chooser.showSaveDialog(this);
        if (pilihan != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".pdf")) {
            file = new File(file.getParentFile(), file.getName() + ".pdf");
        }
        
        try (FileOutputStream fos = new FileOutputStream(file)) {
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();
            document.add(new Paragraph("Laporan Inventory Barang", new Font(Font.BOLD, 16)));
            document.add(new Paragraph("Tanggal: " + new java.util.Date()));
            document.add(new Paragraph(" "));

            PdfPTable pdfTable = new PdfPTable(7);
            pdfTable.setWidthPercentage(100);
            String[] headers = {"ID", "Kode", "Nama", "Kategori", "Stok", "Minimal", "Status"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell);
            }
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    pdfTable.addCell(model.getValueAt(i, j).toString());
                }
            }
            document.add(pdfTable);
            document.close();
            JOptionPane.showMessageDialog(this, "Laporan PDF berhasil dibuat: laporan_barang.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal cetak PDF: " + e.getMessage());
        }
    }
}