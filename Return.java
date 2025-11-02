package library;

import DAO.DatabaseHelper;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Return extends JFrame {  // Inheritance: Return inherits JFrame features

    // Encapsulation: keeping variables private to protect data
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;

    public Return() {  // Constructor
        initComponents();   // Abstraction: hides UI setup details
        con = DatabaseHelper.getConnection();  // Abstraction: hides DB connection logic

        // Center the window
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
    }

    // -----------------------------
    // Search button - Polymorphism example: 
    // actionPerformed method behaves differently for each button
    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "select * from issue where stu_id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, txtsid.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                // Fills all fields from database
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
            } else {
                JOptionPane.showMessageDialog(null, "Student ID not found");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Return button - Encapsulation example
    private void btnreturnActionPerformed(java.awt.event.ActionEvent evt) {
        delete();
        returnUpdate();
    }

    // Encapsulation: delete method hides the delete logic
    private void delete() {
        String sql = "delete from issue where stu_id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, txtsid.getText());
            ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Encapsulation: inserting return data into return table
    private void returnUpdate() {
        String sql = "insert into returntbl(book_id, b_name, edition, publisher, price, stock, stu_id, s_name, f_name, course, branch, year, semister, doi, doreturn)"
        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            ps.execute();
            JOptionPane.showMessageDialog(null, "Book Returned");
            update();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Encapsulation: updates book stock
    private void update() {
        int stock = Integer.parseInt(txtstock.getText());
        stock = stock + 1;  // book returned, so +1
        txtstock.setText(String.valueOf(stock));

        try {
            String sql = "update book set stock=? where book_id=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, txtstock.getText());
            ps.setString(2, txtbid.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Stock Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Back button - Abstraction: hides navigation details
    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {
        Home home = new Home();
        home.setVisible(true);
        this.dispose();
    }

    // Polymorphism: main method starts the GUI
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Return().setVisible(true);
        });
    }

    // Variables (Encapsulation)
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
}
