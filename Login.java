package library;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

/**
 * Login screen for Library Management System.
 * Demonstrates OOP principles: Encapsulation and Abstraction.
 */
public class Login extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnSignup, btnForgot;
    private Connection connection;

    // Constructor
    public Login() {
        super("Library Login");
        connection = DatabaseHelper.getConnection();
        initializeUI();
    }

    // Initialize all GUI components
    private void initializeUI() {
        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);

        btnLogin = new JButton("Login");
        btnSignup = new JButton("Signup");
        btnForgot = new JButton("Forgot Password");

        btnLogin.addActionListener(e -> handleLogin());
        btnSignup.addActionListener(e -> {
            new Signup().setVisible(true);
            dispose();
        });
        btnForgot.addActionListener(e -> {
            new ForgotPassword().setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("User Login"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblUser, gbc);
        gbc.gridx = 1; panel.add(txtUsername, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblPass, gbc);
        gbc.gridx = 1; panel.add(txtPassword, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(btnLogin, gbc);
        gbc.gridx = 1; panel.add(btnSignup, gbc);
        gbc.gridwidth = 2; gbc.gridy = 3; gbc.gridx = 0;
        panel.add(btnForgot, gbc);

        add(panel);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Handle Login Logic
    private void handleLogin() {
        String sql = "SELECT * FROM account WHERE username=? AND password=?"; // ✅ fixed table name
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, txtUsername.getText().trim());
            ps.setString(2, new String(txtPassword.getPassword()).trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "✅ Login Successful!");
                dispose();
                new Home().setVisible(true); // ✅ Open Home window
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid username or password!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + e.getMessage());
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
