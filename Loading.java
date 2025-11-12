package library;

import DAO.DatabaseHelper;
import java.awt.*;
import javax.swing.*;

/**
 * Loading Screen for the Library Management System.
 * 
 * ✅ Demonstrates OOP principles:
 * - **Encapsulation:** keeps UI components private
 * - **Abstraction:** hides loading logic inside methods
 * - **Inheritance:** extends JFrame to create GUI
 * - **Polymorphism:** Thread (run method) behaves differently when started
 */
public class Loading extends JFrame implements Runnable {

    // Encapsulation: private components
    private final JProgressBar progressBar = new JProgressBar();
    private final Thread thread;

    // Constructor
    public Loading() {
        super("Library Management System - Loading");
        initUI();                           // Abstraction: build UI separately
        DatabaseHelper.getConnection();     // Test DB connection at startup
        thread = new Thread(this);          // Initialize thread for loading animation
    }

    // Abstraction: method to design GUI
    private void initUI() {
        JLabel lblTitle = new JLabel("📚 Library Management System", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 102, 204));

        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(0, 153, 76));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(Color.WHITE);

        panel.add(lblTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(progressBar);

        add(panel);
        setSize(420, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Public method to start loading animation
    public void startLoading() {
        setVisible(true);
        thread.start(); // Polymorphism: Thread calls run() automatically
    }

    // Polymorphism: "run" behaves differently based on thread execution
    @Override
    public void run() {
        try {
            for (int i = 0; i <= 100; i++) {
                progressBar.setValue(i);
                Thread.sleep(40); // Simulates loading delay
            }

            // Once loading completes, move to Home window
            SwingUtilities.invokeLater(() -> {
                new Home().setVisible(true);
                dispose();
            });

        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(this, "Loading interrupted: " + e.getMessage());
        }
    }

    // Main method to test independently
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Loading().startLoading());
    }
}
