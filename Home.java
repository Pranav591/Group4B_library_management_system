package library;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// ------------------ [ DATABASE CONNECTION CLASS ] ------------------
class DBConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/librarydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // change if different

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connected to MySQL Database: librarydb");
            } catch (Exception e) {
                System.out.println("❌ Database Connection Failed: " + e.getMessage());
            }
        }
        return connection;
    }
}

// ------------------ [ DATA ACCESS CLASS (OOP) ] ------------------
class LibraryManager {
    private final Connection conn;

    public LibraryManager() {
        conn = DBConnection.getConnection();
    }

    // ✅ Add a new book (matches `book` table)
    public void addBook(String name, String publisher, int edition, int price, int stock) {
        String query = "INSERT INTO book (name, edition, publisher, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, edition);
            ps.setString(3, publisher);
            ps.setInt(4, price);
            ps.setInt(5, stock);
            ps.executeUpdate();
            System.out.println("✅ Book Added Successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error adding book: " + e.getMessage());
        }
    }

    // ✅ Get all book names
    public List<String> getAllBooks() {
        List<String> books = new ArrayList<>();
        String query = "SELECT name FROM book";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                books.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error fetching books: " + e.getMessage());
        }
        return books;
    }

    // ✅ Validate user login (matches `account` table)
    public boolean validateUser(String username, String password) {
        String query = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error validating user: " + e.getMessage());
            return false;
        }
    }
}

// ------------------ [ MAIN GUI CLASS ] ------------------
public class Home extends javax.swing.JFrame {
    private LibraryManager manager;

    public Home() {
        initComponents();
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        manager = new LibraryManager();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnrbook = new javax.swing.JButton();
        btnibook = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnstatistics = new javax.swing.JButton();
        btnnewstudent = new javax.swing.JButton();
        btnnewbook = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        miexit = new javax.swing.JMenuItem();
        milogout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Library Management System - MySQL Integration");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Library Management System");

        // Panel 1 (Operations)
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Operations"));
        btnnewbook.setText("Add Book");
        btnnewbook.addActionListener(evt -> btnnewbookActionPerformed());
        btnstatistics.setText("View Books");
        btnstatistics.addActionListener(evt -> btnstatisticsActionPerformed());
        btnnewstudent.setText("Login Test");
        btnnewstudent.addActionListener(evt -> btnnewstudentActionPerformed());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnnewbook, 150, 150, 150)
                .addGap(18)
                .addComponent(btnstatistics, 150, 150, 150)
                .addGap(18)
                .addComponent(btnnewstudent, 150, 150, 150)
                .addContainerGap()
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup()
                    .addComponent(btnnewbook, 50, 50, 50)
                    .addComponent(btnstatistics, 50, 50, 50)
                    .addComponent(btnnewstudent, 50, 50, 50))
                .addContainerGap()
        );

        // Panel 2 (Other actions)
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));
        btnibook.setText("Issue Book");
        btnrbook.setText("Return Book");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnibook, 150, 150, 150)
                .addGap(18)
                .addComponent(btnrbook, 150, 150, 150)
                .addContainerGap()
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup()
                    .addComponent(btnibook, 50, 50, 50)
                    .addComponent(btnrbook, 50, 50, 50))
                .addContainerGap()
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1)
                .addComponent(jPanel2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1)
                .addGap(20)
                .addComponent(jPanel2)
        );

        jMenu1.setText("File");
        milogout.setText("Logout");
        milogout.addActionListener(evt -> System.out.println("Logout clicked"));
        miexit.setText("Exit");
        miexit.addActionListener(evt -> System.exit(0));
        jMenu1.add(milogout);
        jMenu1.add(miexit);
        jMenuBar1.add(jMenu1);
        setJMenuBar(jMenuBar1);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jLabel1, BorderLayout.NORTH);
        getContentPane().add(jPanel4, BorderLayout.CENTER);

        setSize(new java.awt.Dimension(600, 400));
        setLocationRelativeTo(null);
    }

    // --------- BUTTON ACTIONS ---------------
    private void btnnewbookActionPerformed() {
        try {
            String title = JOptionPane.showInputDialog(this, "Enter Book Name:");
            String publisher = JOptionPane.showInputDialog(this, "Enter Publisher:");
            int edition = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Edition:"));
            int price = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Price:"));
            int stock = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Stock:"));

            if (title != null && publisher != null && !title.isEmpty() && !publisher.isEmpty()) {
                manager.addBook(title, publisher, edition, price, stock);
                JOptionPane.showMessageDialog(this, "✅ Book Added Successfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Invalid input or database error: " + e.getMessage());
        }
    }

    private void btnstatisticsActionPerformed() {
        List<String> books = manager.getAllBooks();
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books found in database!");
        } else {
            JOptionPane.showMessageDialog(this, "Books in Library:\n" + String.join("\n", books));
        }
    }

    private void btnnewstudentActionPerformed() {
        String user = JOptionPane.showInputDialog(this, "Enter username:");
        String pass = JOptionPane.showInputDialog(this, "Enter password:");
        if (manager.validateUser(user, pass)) {
            JOptionPane.showMessageDialog(this, "✅ Login successful!");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid credentials!");
        }
    }

    // ---------- MAIN ----------
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new Home().setVisible(true));
    }

    // ---------- VARIABLES ----------
    private javax.swing.JButton btnibook;
    private javax.swing.JButton btnnewbook;
    private javax.swing.JButton btnnewstudent;
    private javax.swing.JButton btnrbook;
    private javax.swing.JButton btnstatistics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem miexit;
    private javax.swing.JMenuItem milogout;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
}
