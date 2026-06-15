package com.kelompok4.view;

import com.kelompok4.controller.AuthController;
import com.kelompok4.model.User;
import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private JTextField emailField;
    private JPasswordField passwordField;
    private AuthController authController;

    public LoginDialog() {
        authController = new AuthController();
        setTitle("Login Smart Inventory");
        setModal(true);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx=0; gbc.gridy=0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx=1;
        emailField = new JTextField(15);
        panel.add(emailField, gbc);

        gbc.gridx=0; gbc.gridy=1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx=1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        JButton loginBtn = new JButton("Login");
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2;
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passwordField.getPassword());
            User user = authController.authenticateGetUser(email, pass);
            if (user != null) {
                dispose();
                new MainFrame(user.getRole(), user.getId(), user.getEmail());
            } else {
                JOptionPane.showMessageDialog(this, "Email atau password salah");
            }
        });

        add(panel);
        setVisible(true);
    }
}
