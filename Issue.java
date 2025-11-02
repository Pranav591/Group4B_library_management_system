public class Issue extends JFrame {

    // Database Configuration
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/library_management";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // <-- change this

    // Model Classes (Encapsulation)
    static class Book {
        private int id;
        private String name, publisher;
        private int stock;

        public int getId() { 
            return id; 
        }
        public String getName() { 
            return name; 
        }
        public String getPublisher() { 
            return publisher; 
        }
        public int getStock() { 
            return stock; 
        }

        public void setId(int id) { 
            this.id = id; 
        }
        public void setName(String name) { 
            this.name = name; 
        }
        public void setPublisher(String publisher) { 
            this.publisher = publisher; 
        }
        public void setStock(int stock) { 
            this.stock = stock; 
        }
    }

    static class Student {
        private int id;
        private String name, course;

        public int getId() { 
            return id; 
        }
        public String getName() { 
            return name; 
        }
        public String getCourse() { 
            return course;
        }

        public void setId(int id) { 
            this.id = id; 
        }
        public void setName(String name) { 
            this.name = name; 
        }
        public void setCourse(String course) { 
            this.course = course; 
        }
    }
    
    private Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "MySQL Driver not found!");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

   
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
                b.setPublisher(rs.getString("publisher"));
                b.setStock(rs.getInt("stock"));
                return b;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching book: " + e.getMessage());
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
                s.setCourse(rs.getString("course"));
                return s;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching student: " + e.getMessage());
        }
        return null;
    }

    private void issueBook(Book b, Student s, LocalDate date) {
        if (b == null || s == null) {
            JOptionPane.showMessageDialog(this, "❌ Invalid Book or Student!");
            return;
        }
        if (b.getStock() <= 0) {
            JOptionPane.showMessageDialog(this, "⚠ Book out of stock!");
            return;
        }

        try (Connection conn = connect()) {
            conn.setAutoCommit(false);

            String issueSql = """
                INSERT INTO issue(book_id, b_name, stu_id, s_name, doi)
                VALUES (?, ?, ?, ?, ?)
            """;
            String updateSql = "UPDATE book SET stock = stock - 1 WHERE book_id = ?";

            try (PreparedStatement pst1 = conn.prepareStatement(issueSql);
                PreparedStatement pst2 = conn.prepareStatement(updateSql)) {

                pst1.setInt(1, b.getId());
                pst1.setString(2, b.getName());
                pst1.setInt(3, s.getId());
                pst1.setString(4, s.getName());
                pst1.setDate(5, Date.valueOf(date));
                pst1.executeUpdate();

                pst2.setInt(1, b.getId());
                pst2.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(this, "✅ Book Issued Successfully!");
            } catch (SQLException e) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Error issuing book: " + e.getMessage());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }
