package com.kelompok4.controller;

import com.kelompok4.dao.UserDAO;
import com.kelompok4.model.User;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    private UserDAO userDAO = new UserDAO();

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    public List<User> searchUsers(String keyword) throws SQLException {
        return userDAO.searchUsers(keyword);
    }

    public void addUser(User user) throws SQLException {
        userDAO.insertUser(user);
    }

    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);
    }

    public void deleteUser(int id) throws SQLException {
        userDAO.deleteUser(id);
    }

    public User getUserById(int id) throws SQLException {
        return userDAO.getUserById(id);
    }

    public int countUsers() throws SQLException {
        return userDAO.countUsers();
    }
}