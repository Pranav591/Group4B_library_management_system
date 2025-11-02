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

    // Action Listeners
        btnSearch.addActionListener(e -> searchUser());
        btnRetrieve.addActionListener(e -> retrievePassword());
        btnBack.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Back to Login Screen");
            dispose();
        });
    }

    private void searchUser() {
        String username = txtUsername.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username!");
            return;
        }

        Account acc = accountDAO.findByUsername(username);
        if (acc != null) {
            txtName.setText(acc.getName());
            txtQuestion.setText(acc.getSecurityQuestion());
        } else {
            JOptionPane.showMessageDialog(this, "User not found!");
        }
    }

    private void retrievePassword() {
        String username = txtUsername.getText().trim();
        String answer = txtAnswer.getText().trim();

        if (username.isEmpty() || answer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both Username and Answer!");
            return;
        }

        String password = accountDAO.getPassword(username, answer);
        if (password != null) {
            txtPassword.setText(password);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect answer!");
        }
    }
