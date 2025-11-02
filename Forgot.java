    package library;

    import java.sql.*;
    import javax.swing.*;
// model class (Encapsulation for acc details)

    class Account {
        private String username;
        private String name;
        private String securityQuestion;
        private String answer;
        private String password;

        public Account() {}

        public Account(String username, String name, String securityQuestions, String answer, String password) {
            this.username = username;
            this.name = name;
            this.securityQuestion = securityQuestion;
            this.answer = answer;
            this.password = password;
        }

        public string getUsername() {
            return username;
        }
        public String getName() {
            return name;
        }
        public String getSecurityQuestion() {
            return securityQuestion;
        }
        public String getAnswer() {
            return answer;
        }
        public String getPassword() { 
            return password; 
        }
}
    // Database Helper (for connecting to MySQL)
// ------------------------------------------------------------
class DatabaseHelper {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

// ------------------------------------------------------------
// DAO Class (Handles Database Queries)
// ------------------------------------------------------------
class AccountDAO {
    private final Connection con;

    public AccountDAO() {
        con = DatabaseHelper.getConnection();
    }

    // Find user by username
    public Account findByUsername(String username) {
        String query = "SELECT * FROM account WHERE username = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("security_question"),
                        rs.getString("answer"),
                        rs.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        );
        
public class ForgotPassword extends JFrame {
    private JTextField txtUsername, txtName, txtQuestion, txtAnswer, txtPassword;
    private final AccountDAO accountDAO = new AccountDAO();

    public ForgotPassword() {
        setTitle("Forgot Password");
        setSize(450, 350);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(30, 30, 100, 25);
        add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 30, 150, 25);
        add(txtUsername);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(320, 30, 80, 25);
        add(btnSearch);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 70, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(150, 70, 150, 25);
        txtName.setEditable(false);
        add(txtName);

        JLabel lblQ = new JLabel("Security Question:");
        lblQ.setBounds(30, 110, 120, 25);
        add(lblQ);

        txtQuestion = new JTextField();
        txtQuestion.setBounds(150, 110, 250, 25);
        txtQuestion.setEditable(false);
        add(txtQuestion);

        JLabel lblAns = new JLabel("Answer:");
        lblAns.setBounds(30, 150, 100, 25);
        add(lblAns);

        txtAnswer = new JTextField();
        txtAnswer.setBounds(150, 150, 150, 25);
        add(txtAnswer);

        JButton btnRetrieve = new JButton("Retrieve");
        btnRetrieve.setBounds(320, 150, 80, 25);
        add(btnRetrieve);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(30, 190, 100, 25);
        add(lblPass);

        txtPassword = new JTextField();
        txtPassword.setBounds(150, 190, 150, 25);
        txtPassword.setEditable(false);
        add(txtPassword);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(150, 240, 80, 25);
        add(btnBack);

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        search();
    }//GEN-LAST:event_btnsearchActionPerformed

    private void btnretriveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretriveActionPerformed
        retrive();
    }//GEN-LAST:event_btnretriveActionPerformed

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        Login lo = new Login();
        lo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnbackActionPerformed

    public void search(){
        String a1= txtuname.getText();
        String sql = "select * from account where username='"+a1+"'";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                txtname.setText(rs.getString(2));
                txtsques.setText(rs.getString(4));
                rs.close();
                ps.close();
            }else{
                JOptionPane.showMessageDialog(null, "Incorrect Username");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void retrive(){
        String a1 = txtuname.getText();
        String a2 = txtanswer.getText();
        String sql = "select * from account where answer='"+a2+"'";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                txtpassword.setText(rs.getString(3));
//                rs.close();
//                ps.close();
            }else{
                JOptionPane.showMessageDialog(null, "Incorrect Username");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } 
    } 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Forgot().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnretrive;
    private javax.swing.JButton btnsearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtanswer;
    private javax.swing.JTextField txtname;
    private javax.swing.JTextField txtpassword;
    private javax.swing.JTextField txtsques;
    private javax.swing.JTextField txtuname;
    // End of variables declaration//GEN-END:variables
}
