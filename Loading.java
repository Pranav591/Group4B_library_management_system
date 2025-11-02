package library;

import DAO.DatabaseHelper;
import java.awt.*;
import javax.swing.*;

/**
 * Simple loading screen for Library System.
 */
public class Loading extends JFrame implements Runnable {

    private final JProgressBar progressBar = new JProgressBar();
    private final Thread thread;

    public Loading() {
        super("Loading...");
        DatabaseHelper.getConnection(); // Test DB connection
        initUI();
        thread = new Thread(this);
    }

    private void initUI() {
        JLabel lbl = new JLabel("Library Management System", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));

        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(0, 153, 76));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(progressBar);

        add(panel);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startLoading() {
        setVisible(true);
        thread.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= 100; i++) {
                progressBar.setValue(i);
                Thread.sleep(40);
            }
            JOptionPane.showMessageDialog(this, "Welcome to Library Management System!");
            dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
