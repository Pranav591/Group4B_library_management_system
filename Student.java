package library_management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Random;

/**
 * Simple Student Registration Form
 * ------------------------------------
 * Basic OOP concepts used:
 * - Encapsulation: data kept private inside the class
 * - Abstraction: database code hidden in helper method
 * - Object Creation: student is created and saved via method
 */
public class Student extends JFrame {

    // --- Encapsulated GUI components ---
    private JTextField txtId, txtName, txtFather, txtBranch;
    private JComboBox<String> cmbCourse, cmbYear, cmbSemester;
    private JButton btnRegister, btnBack;

    // --- Database connection ---
    private Connection con;

    // --- Constructor ---
    public Student() {
        super("Student Registration");

        // Abstraction: connect to DB (you can replace details)
        connectDatabase();

        // Initialize GUI
        initComponents();

        // Center window
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // --- Database connection (Abstraction) ---
    private void connectDatabase() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed!");
        }
    }

    // --- Generate random student ID (Abstraction) ---
    private void generateRandomID() {
        txtId.setText(String.valueOf(new Random().nextInt(1000) + 1));
    }

    // --- Register new student (Encapsulation + Abstraction) ---
    private void registerStudent() {
        String sql = "INSERT INTO student (student_id, name, fathers_name, course, branch, year, semister) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, txtId.getText());
            ps.setString(2, txtName.getText());
            ps.setString(3, txtFather.getText());
            ps.setString(4, (String) cmbCourse.getSelectedItem());
            ps.setString(5, txtBranch.getText());
            ps.setString(6, (String) cmbYear.getSelectedItem());
            ps.setString(7, (String) cmbSemester.getSelectedItem());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Registered Successfully!");
            clearFields();
            generateRandomID();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // --- Reset input fields ---
    private void clearFields() {
        txtName.setText("");
        txtFather.setText("");
        txtBranch.setText("");
        cmbCourse.setSelectedIndex(0);
        cmbYear.setSelectedIndex(0);
        cmbSemester.setSelectedIndex(0);
    }

    // --- GUI Setup (Simple and clear) ---
    private void initComponents() {
        JLabel lblId = new JLabel("Student ID:");
        JLabel lblName = new JLabel("Name:");
        JLabel lblFather = new JLabel("Father's Name:");
        JLabel lblCourse = new JLabel("Course:");
        JLabel lblBranch = new JLabel("Branch:");
        JLabel lblYear = new JLabel("Year:");
        JLabel lblSemester = new JLabel("Semester:");

        txtId = new JTextField(10);
        txtId.setEditable(false);
        txtName = new JTextField(10);
        txtFather = new JTextField(10);
        txtBranch = new JTextField(10);

        cmbCourse = new JComboBox<>(new String[]{"BSc", "BBA", "BA"});
        cmbYear = new JComboBox<>(new String[]{"1st", "2nd", "3rd"});
        cmbSemester = new JComboBox<>(new String[]{"1st", "2nd", "3rd", "4th"});

        btnRegister = new JButton("Register");
        btnBack = new JButton("Back");

        btnRegister.addActionListener(e -> registerStudent());
        btnBack.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Back to Home");
            dispose();
        });

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("New Student"));
        panel.add(lblId); panel.add(txtId);
        panel.add(lblName); panel.add(txtName);
        panel.add(lblFather); panel.add(txtFather);
        panel.add(lblCourse); panel.add(cmbCourse);
        panel.add(lblBranch); panel.add(txtBranch);
        panel.add(lblYear); panel.add(cmbYear);
        panel.add(lblSemester); panel.add(cmbSemester);
        panel.add(btnRegister); panel.add(btnBack);

        add(panel);
        pack();
        generateRandomID();
    }

    // --- Main method ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Student().setVisible(true));
    }
}
