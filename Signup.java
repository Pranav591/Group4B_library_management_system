package library;

import DAO.DatabaseHelper;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Signup Class – Handles user registration for the Library System.
 *
 * OOP Concepts Demonstrated:
 * 1. *Encapsulation* – Keeps data (connection, form fields) private.
 * 2. *Abstraction* – Hides database details inside DatabaseHelper and SQL queries.
 * 3. *Inheritance* – Extends JFrame to reuse window properties and behavior.
 * 4. *Polymorphism* – Button actions behave differently (Create / Back).
 */
public class Signup extends JFrame {

    // Encapsulation: private database connection and statement
    private Connection con;
    private PreparedStatement ps;

    // Constructor – initializes the form
    public Signup() {
        initComponents(); // Abstraction: sets up GUI
        con = DatabaseHelper.getConnection(); // Abstraction: hides DB details

        // Center window on screen
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
    }

    /**
     * Adds a new user to the database when "Create" button is clicked.
     */
    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "INSERT INTO account (username, name, password, security_ques, answer) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(sql);

            ps.setString(1, txtuname.getText());
            ps.setString(2, txtname.getText());
            ps.setString(3, new String(txtpassword.getPassword()));
            ps.setString(4, (String) cmbsqes.getSelectedItem());
            ps.setString(5, txtans.getText());

            ps.execute();
            JOptionPane.showMessageDialog(this, "✅ Account created successfully!");
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
        }
    }

    /**
     * Goes back to the Login window.
     */
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        new Login().setVisible(true);
        this.dispose();
    }

    /**
     * Clears all text fields.
     */
    private void clearFields() {
        txtuname.setText("");
        txtname.setText("");
        txtpassword.setText("");
        txtans.setText("");
    }

    /**
     * Main method – entry point.
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Signup().setVisible(true));
    }

    // ------------------- GUI COMPONENTS (Encapsulation) -------------------

    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreate;
    private javax.swing.JComboBox<String> cmbsqes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtans;
    private javax.swing.JTextField txtname;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtuname;

    // ------------------- Layout Design (auto-generated simplified) -------------------

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        txtuname = new javax.swing.JTextField();
        txtname = new javax.swing.JTextField();
        txtpassword = new javax.swing.JPasswordField();
        cmbsqes = new javax.swing.JComboBox<>();
        txtans = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel("Username:");
        jLabel2 = new javax.swing.JLabel("Name:");
        jLabel3 = new javax.swing.JLabel("Password:");
        jLabel4 = new javax.swing.JLabel("Security Question:");
        jLabel5 = new javax.swing.JLabel("Answer:");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("New Account");

        cmbsqes.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[]{
                "What is your mother tongue?",
                "What is your nickname?",
                "What is your first childhood friend?",
                "What is your school name?"
            }
        ));

        btnCreate.setText("Create");
        btnCreate.addActionListener(this::btnCreateActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("New Account"));

        // Basic layout using GroupLayout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(30)
                            .addComponent(txtuname))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(60)
                            .addComponent(txtname))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(50)
                            .addComponent(txtpassword))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(10)
                            .addComponent(cmbsqes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(80)
                            .addComponent(txtans))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(100)
                            .addComponent(btnCreate)
                            .addGap(18)
                            .addComponent(btnBack)))
                    .addContainerGap())
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtuname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cmbsqes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(20)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCreate)
                        .addComponent(btnBack))
                    .addContainerGap())
        );

        getContentPane().add(jPanel1);
        pack();
    }
}
