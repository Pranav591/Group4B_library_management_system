package library;

import DAO.DatabaseHelper;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Basic version of Statistics screen using only core Java.
 * Demonstrates Encapsulation + Abstraction.
 */
public class Statistics extends javax.swing.JFrame {

    private Connection connection;

    public Statistics() {
        initComponents();
        connection = DatabaseHelper.getConnection();
        centerWindow();
        loadIssuedBooks();
        loadReturnedBooks();
    }

    // --- Abstraction: Center window logic ---
    private void centerWindow() {
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
    }

    // --- Encapsulation: Load Issued Books ---
    private void loadIssuedBooks() {
        String sql = "SELECT book_id, b_name, edition, publisher, price, stock FROM issue";
        try (PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) tblIssue.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("book_id"),
                        rs.getString("b_name"),
                        rs.getString("edition"),
                        rs.getString("publisher"),
                        rs.getString("price"),
                        rs.getString("stock")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading issued books: " + e.getMessage());
        }
    }

    // --- Encapsulation: Load Returned Books ---
    private void loadReturnedBooks() {
        String sql = "SELECT stu_id, s_name, f_name, course, branch, year, semister FROM returntbl";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) tblReturn.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("stu_id"),
                        rs.getString("s_name"),
                        rs.getString("f_name"),
                        rs.getString("course"),
                        rs.getString("branch"),
                        rs.getString("year"),
                        rs.getString("semister")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading returned books: " + e.getMessage());
        }
    }

    // --- Event handler for Back button ---
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        new Home().setVisible(true);
        dispose();
    }

    // --- Auto-generated GUI code (simplified) ---
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIssue = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblReturn = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Statistics");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Issue Details"));
        tblIssue.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Book ID", "Book Name", "Edition", "Publisher", "Price", "Stock"}
        ));
        jScrollPane1.setViewportView(tblIssue);
        jPanel1.add(jScrollPane1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Return Details"));
        tblReturn.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Student ID", "Name", "Father Name", "Course", "Branch", "Year", "Semester"}
        ));
        jScrollPane2.setViewportView(tblReturn);
        jPanel2.add(jScrollPane2);

        btnBack.setText("Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));

        // Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                                        .addComponent(btnBack))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Statistics().setVisible(true));
    }

    // --- Variables declaration ---
    private javax.swing.JButton btnBack;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblIssue;
    private javax.swing.JTable tblReturn;
}
