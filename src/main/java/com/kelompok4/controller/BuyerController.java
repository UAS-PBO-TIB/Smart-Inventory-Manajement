package com.kelompok4.controller;

import com.kelompok4.dao.BuyerDAO;
import com.kelompok4.model.Buyer;
import java.sql.SQLException;
import java.util.List;

public class BuyerController {
    private BuyerDAO buyerDAO = new BuyerDAO();

    public List<Buyer> getAllBuyers() throws SQLException {
        return buyerDAO.getAll();
    }

    public List<Buyer> searchBuyers(String keyword) throws SQLException {
        return buyerDAO.search(keyword);
    }

    public void addBuyer(Buyer buyer) throws SQLException {
        buyerDAO.insert(buyer);
    }

    public void updateBuyer(Buyer buyer) throws SQLException {
        buyerDAO.update(buyer);
    }

    public void deleteBuyer(int id) throws SQLException {
        buyerDAO.delete(id);
    }

    public Buyer getBuyerById(int id) throws SQLException {
        return buyerDAO.getById(id);
    }

    public int countBuyers() throws SQLException {
        return buyerDAO.count();
    }

    public List<Buyer> getTopBuyers(int limit) throws SQLException {
        return buyerDAO.getTopBuyers(limit);
    }
}