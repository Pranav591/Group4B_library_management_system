DROP DATABASE IF EXISTS librarydb;
CREATE DATABASE librarydb;
USE librarydb;

-------------------------------------------------------
-- Table: account
-------------------------------------------------------
CREATE TABLE account (
  username VARCHAR(15) NOT NULL PRIMARY KEY,
  name VARCHAR(25) NOT NULL,
  password VARCHAR(50) NOT NULL,  -- changed to VARCHAR for safety
  security_ques VARCHAR(100) NOT NULL,
  answer VARCHAR(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Default admin user
INSERT INTO account (username, name, password, security_ques, answer)
VALUES ('admin', 'Administrator', 'admin123', 'What is your favorite color?', 'blue')
ON DUPLICATE KEY UPDATE username=username;

-- -----------------------------------------------------
-- Table: book
-- -----------------------------------------------------
CREATE TABLE book (
  book_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  edition INT UNSIGNED NOT NULL,
  publisher VARCHAR(50) NOT NULL,
  price INT UNSIGNED NOT NULL,
  stock INT UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 
-- Using InnoDB for reliable transactions and foreign key support. Using utf8mb4 for full Unicode support (all languages and emojis).

-- -----------------------------------------------------
-- Table: student
-- -----------------------------------------------------
CREATE TABLE student (
  student_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  fathers_name VARCHAR(50) NOT NULL,
  course VARCHAR(30) NOT NULL,
  branch VARCHAR(50) NOT NULL,
  year VARCHAR(15) NOT NULL,
  semister VARCHAR(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- Table: issue
-- -----------------------------------------------------
CREATE TABLE issue (
  id INT AUTO_INCREMENT PRIMARY KEY,
  book_id INT UNSIGNED NOT NULL,
  b_name VARCHAR(100) NOT NULL,
  edition INT UNSIGNED NOT NULL,
  publisher VARCHAR(50) NOT NULL,
  price INT UNSIGNED NOT NULL,
  stock INT UNSIGNED NOT NULL,
  stu_id INT UNSIGNED NOT NULL,
  s_name VARCHAR(50) NOT NULL,
  f_name VARCHAR(50) NOT NULL,
  course VARCHAR(30) NOT NULL,
  branch VARCHAR(50) NOT NULL,
  year VARCHAR(15) NOT NULL,
  semister VARCHAR(15) NOT NULL,
  doi DATE NOT NULL,
  FOREIGN KEY (book_id) REFERENCES book(book_id),
  FOREIGN KEY (stu_id) REFERENCES student(student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- Table: returntbl
-- -----------------------------------------------------
CREATE TABLE returntbl (
  id INT AUTO_INCREMENT PRIMARY KEY,
  stu_id INT UNSIGNED NOT NULL,
  s_name VARCHAR(50) NOT NULL,
  f_name VARCHAR(50) NOT NULL,
  course VARCHAR(30) NOT NULL,
  branch VARCHAR(50) NOT NULL,
  year VARCHAR(15) NOT NULL,
  semister VARCHAR(15) NOT NULL,
  book_id INT UNSIGNED NOT NULL,
  b_name VARCHAR(100) NOT NULL,
  edition VARCHAR(25) NOT NULL,
  publisher VARCHAR(50) NOT NULL,
  price INT UNSIGNED NOT NULL,
  stock INT UNSIGNED NOT NULL,
  doi DATE NOT NULL,
  doreturn DATE NOT NULL,
  FOREIGN KEY (book_id) REFERENCES book(book_id),
  FOREIGN KEY (stu_id) REFERENCES student(student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- Sample Data
-- -----------------------------------------------------
INSERT INTO student (name, father_name, course, branch, year, semister)
VALUES ('Adrian', 'Arnulfo', 'BBA', 'Bear Branch', '4th', '2nd');

INSERT INTO book (name, edition, publisher, price, stock)
VALUES ('Data Structures', 3, 'McGraw Hill', 500, 10),
       ('Operating Systems', 2, 'Pearson', 650, 5);

COMMIT;
