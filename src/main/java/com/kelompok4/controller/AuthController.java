package com.kelompok4.controller;

import com.kelompok4.dao.UserDAO;
import com.kelompok4.model.User;

public class AuthController {
    private UserDAO userDAO = new UserDAO();
    
    public User authenticateGetUser(String email, String password) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
