package com.kelompok4.view;

import javax.swing.*;
import java.awt.*;
import com.kelompok4.view.panels.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private int currentUserId;
    private DashboardAdminPanel dashboardAdminPanel;
    private ManageUsersPanel manageUsersPanel;
    private ManageSuppliersPanel manageSuppliersPanel;
    private ManageBuyersPanel manageBuyersPanel;
    // untuk manager
    private DashboardManagerPanel dashboardManagerPanel;
    private LaporanBarangPanel laporanBarangPanel;
    // untuk staff
    private DashboardStaffPanel dashboardStaffPanel;
    private ManageBarangPanel manageBarangPanel;
    private InputStokMasukPanel inputStokMasukPanel;
    private InputStokKeluarPanel inputStokKeluarPanel;

    public MainFrame(String role, int userId, String userName) {
        this.currentUserId = userId;
        
        setTitle("Smart Inventory - " + role.toUpperCase());
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = createSidebar(role);
        add(sidebar, BorderLayout.WEST);

        // Content dengan CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        add(contentPanel, BorderLayout.CENTER);

        // Register panel sesuai role
        if (role.equals("admin")) {
            dashboardAdminPanel = new DashboardAdminPanel();
            manageUsersPanel = new ManageUsersPanel(this.currentUserId);
            manageSuppliersPanel = new ManageSuppliersPanel();
            manageBuyersPanel = new ManageBuyersPanel();
            
            contentPanel.add(dashboardAdminPanel, "DashboardAdmin");
            contentPanel.add(manageUsersPanel, "ManageUsers");
            contentPanel.add(manageSuppliersPanel, "ManageSuppliers");
            contentPanel.add(manageBuyersPanel, "ManageBuyers");
            cardLayout.show(contentPanel, "DashboardAdmin");
            
            refreshPanel("DashboardAdmin");
        } else if (role.equals("manager")) {
            dashboardManagerPanel = new DashboardManagerPanel();
            laporanBarangPanel = new LaporanBarangPanel();
            
            contentPanel.add(dashboardManagerPanel, "DashboardManager");
            contentPanel.add(laporanBarangPanel, "LaporanBarang");
            cardLayout.show(contentPanel, "DashboardManager");
            
            refreshPanel("DashboardManager");
        } else if (role.equals("staff")) {
            dashboardStaffPanel = new DashboardStaffPanel();
            manageBarangPanel = new ManageBarangPanel();
            inputStokMasukPanel = new InputStokMasukPanel();
            inputStokKeluarPanel = new InputStokKeluarPanel();
            
            contentPanel.add(dashboardStaffPanel, "DashboardStaff");
            contentPanel.add(manageBarangPanel, "ManageBarang");
            contentPanel.add(inputStokMasukPanel, "InputStokMasuk");
            contentPanel.add(inputStokKeluarPanel, "InputStokKeluar");
            cardLayout.show(contentPanel, "DashboardStaff");
            
            refreshPanel("DashboardStaff");
        }

        setVisible(true);
    }

    private JPanel createSidebar(String role) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));

        // Menu berdasarkan role
        String[][] menus;
        if (role.equals("admin")) {
            menus = new String[][]{
                {"Dashboard Admin", "DashboardAdmin"},
                {"Manage Users", "ManageUsers"},
                {"Manage Suppliers", "ManageSuppliers"},
                {"Manage Buyers", "ManageBuyers"}
            };
        } else if (role.equals("manager")) {
            menus = new String[][]{
                {"Dashboard Manager", "DashboardManager"},
                {"Laporan Barang", "LaporanBarang"}
            };
        } else {
            menus = new String[][]{
                {"Dashboard Staff", "DashboardStaff"},
                {"Manage Barang", "ManageBarang"},
                {"Input Stok Masuk", "InputStokMasuk"},
                {"Input Stok Keluar", "InputStokKeluar"}
            };
        }

        for (String[] menu : menus) {
            JButton btn = new JButton(menu[0]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setBackground(new Color(236, 240, 241));
            btn.addActionListener(e -> {
                String panelName = menu[1];
                cardLayout.show(contentPanel, panelName);
                refreshPanel(panelName);
            });
            sidebar.add(Box.createVerticalStrut(20));
            sidebar.add(btn);
        }

        // Tombol Logout
        sidebar.add(Box.createVerticalGlue());
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginDialog();
        });
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(20));

        return sidebar;
    }

    private void refreshPanel(String panelName) {
        switch (panelName) {
            case "DashboardAdmin":
                if (dashboardAdminPanel != null) dashboardAdminPanel.refreshAll();
                break;
            case "ManageUsers":
                if (manageUsersPanel != null) manageUsersPanel.loadUsers();
                break;
            case "ManageSuppliers":
                if (manageSuppliersPanel != null) manageSuppliersPanel.loadSuppliers();
                break;
            case "ManageBuyers":
                if (manageBuyersPanel != null) manageBuyersPanel.loadBuyers();
                break;
            case "DashboardManager":
                if (dashboardManagerPanel != null) dashboardManagerPanel.refreshAll();
                break;
            case "LaporanBarang":
                if (laporanBarangPanel != null) laporanBarangPanel.loadData();
                break;
            case "DashboardStaff":
                if (dashboardStaffPanel != null) dashboardStaffPanel.refreshAll();
                break;
            case "ManageBarang":
                if (manageBarangPanel != null) manageBarangPanel.loadBarang();
                break;
            case "InputStokMasuk":
                if (inputStokMasukPanel != null) inputStokMasukPanel.refreshData();
                break;
            case "InputStokKeluar":
                if (inputStokKeluarPanel != null) inputStokKeluarPanel.refreshData();
                break;
        }
    }
}
