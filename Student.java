package library;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

/**
 * GUI-based Student Registration
 * -------------------------------
 * Demonstrates:
 *  - Encapsulation: private fields & methods
 *  - Abstraction: hides DB logic
 *  - Inheritance: extends JFrame
 */
public class Student extends JFrame {

    // --- UI Components (Encapsulation) ---
    private JTextField txtName, txtFather, txtCourse, txtBranch, txtYear, txtSemester;
    private JButton btnRegister, btnClear, btnExit;

    // --- Database Configuration ---
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // <-- change to your MySQL password

    public Student() {
        setTitle("📘 Student Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new GridLayout(8, 2, 10, 10));
        setResizable(false);

        // Center window on screen
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

        // --- UI Fields ---
        add(new JLabel("Student Name:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Father's Name:"));
        txtFather = new JTextField();
        add(txtFather);

        add(new JLabel("Course:"));
        txtCourse = new JTextField();
        add(txtCourse);

        add(new JLabel("Branch:"));
        txtBranch = new JTextField();
        add(txtBranch);

        add(new JLabel("Year:"));
        txtYear = new JTextField();
        add(txtYear);

        add(new JLabel("Semester:"));
        txtSemester = new JTextField();
        add(txtSemester);

        // --- Buttons ---
        btnRegister = new JButton("✅ Register");
        btnClear = new JButton("🧹 Clear");
        btnExit = new JButton("🚪 Exit");

        add(btnRegister);
        add(btnClear);
        add(btnExit);

        // --- Button Actions ---
        btnRegister.addActionListener(e -> registerStudent());
        btnClear.addActionListener(e -> clearFields());
        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    // ----------------- REGISTER STUDENT -----------------
    private void registerStudent() {
        String name = txtName.getText().trim();
        String father = txtFather.getText().trim();
        String course = txtCourse.getText().trim();
        String branch = txtBranch.getText().trim();
        String year = txtYear.getText().trim();
        String semester = txtSemester.getText().trim();

        // Validation
        if (name.isEmpty() || father.isEmpty() || course.isEmpty() ||
            branch.isEmpty() || year.isEmpty() || semester.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill in all fields!");
            return;
        }

        // DB Insertion
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "INSERT INTO student (name, fathers_name, course, branch, year, semister) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setString(2, father);
                    ps.setString(3, course);
                    ps.setString(4, branch);
                    ps.setString(5, year);
                    ps.setString(6, semester);
                    ps.executeUpdate();
                }
                JOptionPane.showMessageDialog(this, "✅ Student registered successfully!");
                clearFields();
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "❌ JDBC Driver not found!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Database error: " + e.getMessage());
        }
    }

    // ----------------- CLEAR FIELDS -----------------
    private void clearFields() {
        txtName.setText("");
        txtFather.setText("");
        txtCourse.setText("");
        txtBranch.setText("");
        txtYear.setText("");
        txtSemester.setText("");
    }

    // ----------------- MAIN METHOD -----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Student::new);
    }
}
