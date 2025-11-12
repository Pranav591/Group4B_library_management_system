package library;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;

/**
 * Issue Book Window
 * ------------------------------
 * Matches your MySQL schema (librarydb)
 * Demonstrates:
 * - Encapsulation (private model classes)
 * - Abstraction (database logic hidden)
 * - Inheritance (extends JFrame)
 */
public class Issue extends JFrame {

    // ------------------------------
    // Database Configuration
    // ------------------------------
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // <-- change if needed

    // ------------------------------
    // Model Classes (Encapsulation)
    // ------------------------------
    static class Book {
        private int id, edition, price, stock;
        private String name, publisher;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getPublisher() { return publisher; }
        public int getEdition() { return edition; }
        public int getPrice() { return price; }
        public int getStock() { return stock; }

        public void setId(int id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setPublisher(String publisher) { this.publisher = publisher; }
        public void setEdition(int edition) { this.edition = edition; }
        public void setPrice(int price) { this.price = price; }
        public void setStock(int stock) { this.stock = stock; }
    }

    static class Student {
        private int id;
        private String name, father, course, branch, year, semister;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getFather() { return father; }
        public String getCourse() { return course; }
        public String getBranch() { return branch; }
        public String getYear() { return year; }
        public String getSemister() { return semister; }

        public void setId(int id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setFather(String father) { this.father = father; }
        public void setCourse(String course) { this.course = course; }
        public void setBranch(String branch) { this.branch = branch; }
        public void setYear(String year) { this.year = year; }
        public void setSemister(String semister) { this.semister = semister; }
    }

    // ------------------------------
    // Database Connection
    // ------------------------------
    private Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "❌ MySQL JDBC Driver not found!");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ------------------------------
    // DAO Methods
    // ------------------------------
    private Book getBook(int id) {
        String sql = "SELECT * FROM book WHERE book_id=?";
        try (Connection conn = connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("book_id"));
                b.setName(rs.getString("name"));
                b.setEdition(rs.getInt("edition"));
                b.setPublisher(rs.getString("publisher"));
                b.setPrice(rs.getInt("price"));
                b.setStock(rs.getInt("stock"));
                return b;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error fetching book: " + e.getMessage());
        }
        return null;
    }

    private Student getStudent(int id) {
        String sql = "SELECT * FROM student WHERE student_id=?";
        try (Connection conn = connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("student_id"));
                s.setName(rs.getString("name"));
                s.setFather(rs.getString("fathers_name"));
                s.setCourse(rs.getString("course"));
                s.setBranch(rs.getString("branch"));
                s.setYear(rs.getString("year"));
                s.setSemister(rs.getString("semister"));
                return s;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error fetching student: " + e.getMessage());
        }
        return null;
    }

    // ------------------------------
    // Issue Book Logic
    // ------------------------------
    private void issueBook(Book b, Student s, LocalDate date) {
        if (b == null || s == null) {
            JOptionPane.showMessageDialog(this, "❌ Invalid Book or Student!");
            return;
        }
        if (b.getStock() <= 0) {
            JOptionPane.showMessageDialog(this, "⚠️ Book out of stock!");
            return;
        }

        String issueSql = """
            INSERT INTO issue (book_id, b_name, edition, publisher, price, stock,
                               stu_id, s_name, f_name, course, branch, year, semister, doi)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        String updateSql = "UPDATE book SET stock = stock - 1 WHERE book_id = ?";

        try (Connection conn = connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pst1 = conn.prepareStatement(issueSql);
                 PreparedStatement pst2 = conn.prepareStatement(updateSql)) {

                pst1.setInt(1, b.getId());
                pst1.setString(2, b.getName());
                pst1.setInt(3, b.getEdition());
                pst1.setString(4, b.getPublisher());
                pst1.setInt(5, b.getPrice());
                pst1.setInt(6, b.getStock());
                pst1.setInt(7, s.getId());
                pst1.setString(8, s.getName());
                pst1.setString(9, s.getFather());
                pst1.setString(10, s.getCourse());
                pst1.setString(11, s.getBranch());
                pst1.setString(12, s.getYear());
                pst1.setString(13, s.getSemister());
                pst1.setDate(14, Date.valueOf(date));
                pst1.executeUpdate();

                pst2.setInt(1, b.getId());
                pst2.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(this, "✅ Book Issued Successfully!");
            } catch (SQLException e) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "❌ Error issuing book: " + e.getMessage());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Database error: " + e.getMessage());
        }
    }

    // ------------------------------
    // GUI Components
    // ------------------------------
    private JTextField txtBookId, txtBookName, txtPublisher, txtEdition, txtPrice, txtStock;
    private JTextField txtStudentId, txtStudentName, txtFather, txtCourse, txtBranch, txtYear, txtSemister;

    private void initComponents() {
        setTitle("📚 Issue Book");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(15, 2, 5, 5));

        JLabel lblBookId = new JLabel("Book ID:");
        JLabel lblBookName = new JLabel("Book Name:");
        JLabel lblEdition = new JLabel("Edition:");
        JLabel lblPublisher = new JLabel("Publisher:");
        JLabel lblPrice = new JLabel("Price:");
        JLabel lblStock = new JLabel("Stock:");
        JLabel lblStudentId = new JLabel("Student ID:");
        JLabel lblStudentName = new JLabel("Student Name:");
        JLabel lblFather = new JLabel("Father's Name:");
        JLabel lblCourse = new JLabel("Course:");
        JLabel lblBranch = new JLabel("Branch:");
        JLabel lblYear = new JLabel("Year:");
        JLabel lblSemister = new JLabel("Semister:");

        txtBookId = new JTextField();
        txtBookName = new JTextField();
        txtEdition = new JTextField();
        txtPublisher = new JTextField();
        txtPrice = new JTextField();
        txtStock = new JTextField();

        txtStudentId = new JTextField();
        txtStudentName = new JTextField();
        txtFather = new JTextField();
        txtCourse = new JTextField();
        txtBranch = new JTextField();
        txtYear = new JTextField();
        txtSemister = new JTextField();

        // Make fetched fields read-only
        txtBookName.setEditable(false);
        txtEdition.setEditable(false);
        txtPublisher.setEditable(false);
        txtPrice.setEditable(false);
        txtStock.setEditable(false);
        txtStudentName.setEditable(false);
        txtFather.setEditable(false);
        txtCourse.setEditable(false);
        txtBranch.setEditable(false);
        txtYear.setEditable(false);
        txtSemister.setEditable(false);

        JButton btnSearchBook = new JButton("🔍 Search Book");
        JButton btnSearchStudent = new JButton("🔍 Search Student");
        JButton btnIssue = new JButton("✅ Issue Book");

        btnSearchBook.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtBookId.getText());
                Book b = getBook(id);
                if (b != null) {
                    txtBookName.setText(b.getName());
                    txtEdition.setText(String.valueOf(b.getEdition()));
                    txtPublisher.setText(b.getPublisher());
                    txtPrice.setText(String.valueOf(b.getPrice()));
                    txtStock.setText(String.valueOf(b.getStock()));
                } else {
                    JOptionPane.showMessageDialog(this, "Book not found!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid Book ID!");
            }
        });

        btnSearchStudent.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtStudentId.getText());
                Student s = getStudent(id);
                if (s != null) {
                    txtStudentName.setText(s.getName());
                    txtFather.setText(s.getFather());
                    txtCourse.setText(s.getCourse());
                    txtBranch.setText(s.getBranch());
                    txtYear.setText(s.getYear());
                    txtSemister.setText(s.getSemister());
                } else {
                    JOptionPane.showMessageDialog(this, "Student not found!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid Student ID!");
            }
        });

        btnIssue.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(txtBookId.getText());
                int studentId = Integer.parseInt(txtStudentId.getText());
                Book b = getBook(bookId);
                Student s = getStudent(studentId);
                issueBook(b, s, LocalDate.now());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid IDs!");
            }
        });

        // Add components
        add(lblBookId); add(txtBookId);
        add(lblBookName); add(txtBookName);
        add(lblEdition); add(txtEdition);
        add(lblPublisher); add(txtPublisher);
        add(lblPrice); add(txtPrice);
        add(lblStock); add(txtStock);
        add(btnSearchBook); add(new JLabel());
        add(lblStudentId); add(txtStudentId);
        add(lblStudentName); add(txtStudentName);
        add(lblFather); add(txtFather);
        add(lblCourse); add(txtCourse);
        add(lblBranch); add(txtBranch);
        add(lblYear); add(txtYear);
        add(lblSemister); add(txtSemister);
        add(btnSearchStudent); add(new JLabel());
        add(btnIssue); add(new JLabel());
    }

    // ------------------------------
    // Constructor
    // ------------------------------
    public Issue() {
        initComponents();
        setLocationRelativeTo(null);
    }

    // ------------------------------
    // Main
    // ------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Issue().setVisible(true));
    }
}
