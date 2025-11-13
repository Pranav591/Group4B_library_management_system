📚 Library Management System

A Java Swing + MySQL Desktop Application

🚀 Overview

The Library Management System is a desktop-based application developed in Java (Swing) with a MySQL database backend.
It provides an intuitive interface for managing books, students, issuing/returning operations, and overall library workflows.

This project was collaboratively built by Group 4B as part of an academic software development module.

🛠️ Tech Stack
Layer	Technology
Programming Language	Java
GUI Framework	Java Swing (.form + .java files)
Database	MySQL
Tools Used	IntelliJ/NetBeans GUI Builder, JDBC
📂 Project Structure
Group4B_library_management_system/
│
├── src/
│   ├── DatabaseHelper.java         # Handles DB connectivity (JDBC)
│   ├── Login.java                  # User login screen
│   ├── Signup.java                 # New user registration
│   ├── Forgot.java                 # Password recovery
│   ├── Home.java                   # Main dashboard after login
│   ├── NewBook.java                # Add new books
│   ├── Student.java                # Add/view student details
│   ├── Issue.java                  # Issue books to a student
│   ├── Return.java                 # Return issued books
│   ├── Statistics.java             # Display issue/return statistics
│   ├── ... (other UI .form files)  # Swing UI layouts
│
└── librarydb.sql                   # MySQL database schema

⭐ Features
🔐 User Authentication

Login for authorized users

Signup to register new users

Forgot password functionality

📘 Book Management

Add new books

View and manage book details

Maintain availability status

🎓 Student Management

Add student records

Maintain user details and IDs

📖 Issue & Return System

Issue books to registered students

Track issue dates and return deadlines

Mark books as returned

📊 Statistics Module

View total books

View issued and returned counts

View real-time student & book activity

🎨 Form-Based Navigation

Smooth page-to-page navigation

UI built using Java Swing form builder

🗄️ Database Setup (MySQL)

Install MySQL if not already installed.

Open MySQL Workbench / Terminal.

Import the provided database:

SOURCE librarydb.sql;


Update your database credentials inside DatabaseHelper.java:

Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/librarydb",
    "username",
    "password"
);

▶️ Running the Project

Clone the repo:

git clone https://github.com/Pranav591/Group4B_library_management_system.git


Open the project in IntelliJ / NetBeans.

Make sure MySQL is running locally.

Modify DB credentials in DatabaseHelper.java.

Run the main class (Login screen will open first).

👥 Development Team
Name	Contribution
Pranav Adhikari	Lead developer, package structure, core refactoring
Pragun Lal Shrestha	System components development
Unique Bhakta Shrestha	Development support
Nishit Patel	System implementation & UI logic

📌 Future Improvements

Add password hashing for security

Role-based access (Admin/Student)

Replace Swing with JavaFX for better UI

Add search & filtering

Implement fine calculation system

Add export/import features

📄 License

This project is for academic purposes.
Feel free to modify or extend it.
