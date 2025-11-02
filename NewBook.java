package library;

import DAO.DatabaseHelper;
import java.awt.*;
import java.sql.*;
import java.util.Random;
import javax.swing.*;


public class NewBook extends JFrame {

    // Encapsulation: variables hidden from outside
    private Connection con;
    private PreparedStatement ps;

    private JTextField txtBookID, txtName, txtPublisher, txtPrice, txtStock;
    private JComboBox<String> cmbEdition;
    private JButton btnAdd, btnBack;

    // Constructor: initializes everything
    public NewBook() {
        super("Add New Book");
        con = DatabaseHelper.getConnection();
        initUI();
        generateBookID();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null);
    }

    // Abstraction: UI creation is in a separate method
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

    // Encapsulation: generates a random Book ID
    private void generateBookID() {
        Random rand = new Random();
        txtBookID.setText(String.valueOf(rand.nextInt(1000)));
    }

    // Abstraction: handles DB logic inside a method
    private void addBook() {
        String sql = "INSERT INTO book (book_id, name, edition, publisher, price, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, txtBookID.getText());
            ps.setString(2, txtName.getText());
            ps.setString(3, (String) cmbEdition.getSelectedItem());
            ps.setString(4, txtPublisher.getText());
            ps.setString(5, txtPrice.getText());
            ps.setString(6, txtStock.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book added successfully!");
            clearFields();
            generateBookID();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // Polymorphism: different button → different action
    private void goBack() {
        JOptionPane.showMessageDialog(this, "Back to Home (placeholder)");
        dispose();
    }

    // Utility method to clear all text fields
    private void clearFields() {
        txtName.setText("");
        txtPublisher.setText("");
        txtPrice.setText("");
        txtStock.setText("");
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewBook().setVisible(true));
    }
}
