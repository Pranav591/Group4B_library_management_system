package library;

import DAO.DatabaseHelper;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Statistics Screen — Displays Issued & Returned Book Data
 * Demonstrates:
 * - Encapsulation: Private connection & methods
 * - Abstraction: Database logic hidden in load methods
 * - Inheritance: Extends JFrame for UI reuse
 */
public class Statistics extends JFrame {

    private Connection connection;

    public Statistics() {
        initComponents();
        connection = DatabaseHelper.getConnection();

        if (connection == null) {
            JOptionPane.showMessageDialog(this, "❌ Database connection failed. Check DatabaseHelper settings.");
            return;
        }

        centerWindow();
        loadIssuedBooks();
        loadReturnedBooks();
    }

    // ------------------ Utility Methods ------------------
    private void centerWindow() {
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
    }

    // ------------------ Load Issued Books ------------------
    private void loadIssuedBooks() {
        String sql = """
            SELECT book_id, b_name, s_name, f_name, course, branch, doi
            FROM issue
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) tblIssue.getModel();
            model.setRowCount(0);

            boolean hasData = false;
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("book_id"),
                        rs.getString("b_name"),
                        rs.getString("s_name"),
                        rs.getString("f_name"),
                        rs.getString("course"),
                        rs.getString("branch"),
                        rs.getString("doi")
                });
                hasData = true;
            }

            if (!hasData) {
                JOptionPane.showMessageDialog(this, "ℹ️ No issued book records found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading issued books: " + e.getMessage());
        }
    }

    // ------------------ Load Returned Books ------------------
    private void loadReturnedBooks() {
        String sql = """
            SELECT stu_id, s_name, f_name, course, branch, year, semister, doreturn
            FROM returntbl
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) tblReturn.getModel();
            model.setRowCount(0);

            boolean hasData = false;
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("stu_id"),
                        rs.getString("s_name"),
                        rs.getString("f_name"),
                        rs.getString("course"),
                        rs.getString("branch"),
                        rs.getString("year"),
                        rs.getString("semister"),
                        rs.getString("doreturn")
                });
                hasData = true;
            }

            if (!hasData) {
                JOptionPane.showMessageDialog(this, "ℹ️ No returned book records found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading returned books: " + e.getMessage());
        }
    }

    // ------------------ Event Handlers ------------------
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        new Home().setVisible(true);
        dispose();
    }

    // ------------------ GUI Setup ------------------
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new JPanel(new BorderLayout());
        jPanel2 = new JPanel(new BorderLayout());
        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();
        tblIssue = new JTable();
        tblReturn = new JTable();
        btnBack = new JButton("⬅ Back");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("📊 Library Statistics");

        // --- Table 1: Issue ---
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Issued Books"));
        tblIssue.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Book ID", "Book Name", "Student Name", "Father", "Course", "Branch", "Date of Issue"}
        ));
        jScrollPane1.setViewportView(tblIssue);
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);

        // --- Table 2: Return ---
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Returned Books"));
        tblReturn.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Student ID", "Name", "Father Name", "Course", "Branch", "Year", "Semester", "Date of Return"}
        ));
        jScrollPane2.setViewportView(tblReturn);
        jPanel2.add(jScrollPane2, BorderLayout.CENTER);

        // --- Back Button ---
        btnBack.addActionListener(this::btnBackActionPerformed);

        // --- Layout ---
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(btnBack)
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnBack)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    // ------------------ Main ------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Statistics().setVisible(true));
    }

    // ------------------ Variables ------------------
    private JButton btnBack;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable tblIssue;
    private JTable tblReturn;
}
