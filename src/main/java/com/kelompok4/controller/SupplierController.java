package com.kelompok4.controller;

import com.kelompok4.dao.SupplierDAO;
import com.kelompok4.model.Supplier;
import java.sql.SQLException;
import java.util.List;

public class SupplierController {
    private SupplierDAO supplierDAO = new SupplierDAO();

    public List<Supplier> getAllSuppliers() throws SQLException {
        return supplierDAO.getAll();
    }

    public List<Supplier> searchSuppliers(String keyword) throws SQLException {
        return supplierDAO.search(keyword);
    }

    public void addSupplier(Supplier supplier) throws SQLException {
        supplierDAO.insert(supplier);
    }

    public void updateSupplier(Supplier supplier) throws SQLException {
        supplierDAO.update(supplier);
    }

    public void deleteSupplier(int id) throws SQLException {
        supplierDAO.delete(id);
    }

    public Supplier getSupplierById(int id) throws SQLException {
        return supplierDAO.getById(id);
    }

    public int countSuppliers() throws SQLException {
        return supplierDAO.count();
    }

    public List<Supplier> getTopSuppliers(int limit) throws SQLException {
        return supplierDAO.getTopSuppliers(limit);
    }
}