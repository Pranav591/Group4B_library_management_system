package library;

import java.awt.*;
import java.sql.*;
import java.util.Random;
import javax.swing.*;

/**
 * NewBook Class – Adds a new book to the database.
 * 
 * OOP Concepts:
 * 1. Encapsulation – private fields & methods
 * 2. Abstraction – hides database logic
 * 3. Inheritance – extends JFrame
 * 4. Polymorphism – buttons trigger different actions
 */
public class NewBook extends JFrame {

    // Encapsulation: private members
    private Connection con;
    private PreparedStatement ps;

    private JTextField txtBookID, txtName, txtPublisher, txtPrice, txtStock;
    private JComboBox<String> cmbEdition;
    private JButton btnAdd, btnBack;

    // Constructor
    public NewBook() {
        super("Add New Book");
        con = DatabaseHelper.getConnection();
        initUI();
        generateBookID();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null);
    }

    // Abstraction: UI setup
    private void initUI() {
        JLabel lbl1 = new JLabel("Book ID:");
        JLabel lbl2 = new JLabel("Name:");
        JLabel lbl3 = new JLabel("Edition:");
        JLabel lbl4 = new JLabel("Publisher:");
        JLabel lbl5 = new JLabel("Price:");
        JLabel lbl6 = new JLabel("Stock:");

        txtBookID = new JTextField(10);
        txtBookID.setEditable(false);
        txtName = new JTextField(10);
        txtPublisher = new JTextField(10);
        txtPrice = new JTextField(10);
        txtStock = new JTextField(10);
        cmbEdition = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6"});

        btnAdd = new JButton("Add");
        btnBack = new JButton("Back");

        btnAdd.addActionListener(e -> addBook());
        btnBack.addActionListener(e -> goBack());

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("New Book"));
        panel.add(lbl1); panel.add(txtBookID);
        panel.add(lbl2); panel.add(txtName);
        panel.add(lbl3); panel.add(cmbEdition);
        panel.add(lbl4); panel.add(txtPublisher);
        panel.add(lbl5); panel.add(txtPrice);
        panel.add(lbl6); panel.add(txtStock);
        panel.add(btnAdd); panel.add(btnBack);

        add(panel);
    }

    // Generate random Book ID
    private void generateBookID() {
        Random rand = new Random();
        txtBookID.setText(String.valueOf(rand.nextInt(1000)));
    }

    // Database Insertion (Abstraction)
    private void addBook() {
        if (txtName.getText().isEmpty() || txtPublisher.getText().isEmpty() || 
            txtPrice.getText().isEmpty() || txtStock.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!");
            return;
        }

        String sql = "INSERT INTO book (book_id, name, edition, publisher, price, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, txtBookID.getText());
            ps.setString(2, txtName.getText());
            ps.setString(3, (String) cmbEdition.getSelectedItem());
            ps.setString(4, txtPublisher.getText());
            ps.setInt(5, Integer.parseInt(txtPrice.getText()));
            ps.setInt(6, Integer.parseInt(txtStock.getText()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Book added successfully!");
            clearFields();
            generateBookID();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
        }
    }

    // Polymorphism: Button → action
    private void goBack() {
        new Home().setVisible(true);
        dispose();
    }

    private void clearFields() {
        txtName.setText("");
        txtPublisher.setText("");
        txtPrice.setText("");
        txtStock.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewBook().setVisible(true));
    }
}
