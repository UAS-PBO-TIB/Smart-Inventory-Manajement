/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kelompok4.service;

/**
 *
 * @author n03ll
 */
// AlertService.java
import java.sql.SQLException;
import java.util.List;
import com.kelompok4.database.AlertDAO;

public class AlertService {
    private final AlertDAO alertDAO;

    public AlertService(AlertDAO alertDAO) {
        this.alertDAO = alertDAO;
    }

    public List<AlertStokKritis> getAlertAktif() throws SQLException {
        return alertDAO.getAlertAktif();
    }

    public void tandaiDitangani(int alertId, int ditanganiOlehUserId) throws SQLException {
        alertDAO.tandaiDitangani(alertId, ditanganiOlehUserId);
    }
}