package library;

import DAO.DatabaseHelper;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Return extends JFrame {

    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;

    // ---------------- UI Components ----------------
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnreturn;
    private javax.swing.JButton btnsearch;
    private javax.swing.JTextField txtbid;
    private javax.swing.JTextField txtbname;
    private javax.swing.JTextField txtbranch;
    private javax.swing.JTextField txtcourse;
    private javax.swing.JTextField txtdoi;
    private javax.swing.JTextField txtedition;
    private javax.swing.JTextField txtfname;
    private javax.swing.JTextField txtprice;
    private javax.swing.JTextField txtpub;
    private javax.swing.JTextField txtreturn;
    private javax.swing.JTextField txtsemister;
    private javax.swing.JTextField txtsid;
    private javax.swing.JTextField txtsname;
    private javax.swing.JTextField txtstock;
    private javax.swing.JTextField txtyear;

    public Return() {
        initComponents();
        con = DatabaseHelper.getConnection();

        if (con == null) {
            JOptionPane.showMessageDialog(this, "❌ Database connection failed. Check DatabaseHelper.");
        }

        // Center window
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
    }

    // -------------------- SEARCH BUTTON --------------------
    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "SELECT * FROM issue WHERE stu_id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, txtsid.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                txtsname.setText(rs.getString("s_name"));
                txtfname.setText(rs.getString("f_name"));
                txtcourse.setText(rs.getString("course"));
                txtbranch.setText(rs.getString("branch"));
                txtyear.setText(rs.getString("year"));
                txtsemister.setText(rs.getString("semister"));
                txtbid.setText(rs.getString("book_id"));
                txtbname.setText(rs.getString("b_name"));
                txtedition.setText(rs.getString("edition"));
                txtpub.setText(rs.getString("publisher"));
                txtprice.setText(rs.getString("price"));
                txtstock.setText(rs.getString("stock"));
                txtdoi.setText(rs.getString("doi"));

                // Auto-fill today's date as return date
                txtreturn.setText(java.time.LocalDate.now().toString());
            } else {
                JOptionPane.showMessageDialog(this, "❌ Student ID not found in issue table!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // -------------------- RETURN BUTTON --------------------
    private void btnreturnActionPerformed(java.awt.event.ActionEvent evt) {
        deleteIssue();
        insertReturn();
    }

    private void deleteIssue() {
        String sql = "DELETE FROM issue WHERE stu_id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, txtsid.getText());
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting issue: " + e.getMessage());
        }
    }

    private void insertReturn() {
        String sql = """
            INSERT INTO returntbl(book_id, b_name, edition, publisher, price, stock, 
            stu_id, s_name, f_name, course, branch, year, semister, doi, doreturn)
            VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        """;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, txtbid.getText());
            ps.setString(2, txtbname.getText());
            ps.setString(3, txtedition.getText());
            ps.setString(4, txtpub.getText());
            ps.setString(5, txtprice.getText());
            ps.setString(6, txtstock.getText());
            ps.setString(7, txtsid.getText());
            ps.setString(8, txtsname.getText());
            ps.setString(9, txtfname.getText());
            ps.setString(10, txtcourse.getText());
            ps.setString(11, txtbranch.getText());
            ps.setString(12, txtyear.getText());
            ps.setString(13, txtsemister.getText());
            ps.setString(14, txtdoi.getText());
            ps.setString(15, txtreturn.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Book Returned Successfully!");
            updateStock();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error returning book: " + e.getMessage());
        }
    }

    private void updateStock() {
        int stock = Integer.parseInt(txtstock.getText()) + 1;
        txtstock.setText(String.valueOf(stock));

        try {
            String sql = "UPDATE book SET stock=? WHERE book_id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, stock);
            ps.setString(2, txtbid.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "📦 Stock Updated!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating stock: " + e.getMessage());
        }
    }

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {
        new Home().setVisible(true);
        this.dispose();
    }

    // -------------------- UI SETUP --------------------
    private void initComponents() {
        setTitle("📚 Return Book");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(18, 2, 5, 5));

        txtsid = new JTextField();
        txtsname = new JTextField();
        txtfname = new JTextField();
        txtcourse = new JTextField();
        txtbranch = new JTextField();
        txtyear = new JTextField();
        txtsemister = new JTextField();
        txtbid = new JTextField();
        txtbname = new JTextField();
        txtedition = new JTextField();
        txtpub = new JTextField();
        txtprice = new JTextField();
        txtstock = new JTextField();
        txtdoi = new JTextField();
        txtreturn = new JTextField();

        btnsearch = new JButton("🔍 Search");
        btnreturn = new JButton("✅ Return");
        btnback = new JButton("⬅ Back");

        add(new JLabel("Student ID:")); add(txtsid);
        add(btnsearch); add(new JLabel(""));
        add(new JLabel("Student Name:")); add(txtsname);
        add(new JLabel("Father's Name:")); add(txtfname);
        add(new JLabel("Course:")); add(txtcourse);
        add(new JLabel("Branch:")); add(txtbranch);
        add(new JLabel("Year:")); add(txtyear);
        add(new JLabel("Semester:")); add(txtsemister);
        add(new JLabel("Book ID:")); add(txtbid);
        add(new JLabel("Book Name:")); add(txtbname);
        add(new JLabel("Edition:")); add(txtedition);
        add(new JLabel("Publisher:")); add(txtpub);
        add(new JLabel("Price:")); add(txtprice);
        add(new JLabel("Stock:")); add(txtstock);
        add(new JLabel("Date of Issue:")); add(txtdoi);
        add(new JLabel("Date of Return:")); add(txtreturn);
        add(btnreturn); add(btnback);

        btnsearch.addActionListener(this::btnsearchActionPerformed);
        btnreturn.addActionListener(this::btnreturnActionPerformed);
        btnback.addActionListener(this::btnbackActionPerformed);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Return().setVisible(true));
    }
}
