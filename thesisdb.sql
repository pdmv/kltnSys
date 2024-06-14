-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS thesisdb;

USE thesisdb;

-- Tạo bảng account
CREATE TABLE account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    avatar VARCHAR(255) DEFAULT NULL,
    role ENUM('ADMIN', 'LECTURER', 'STUDENT', 'AFFAIR') NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

-- Tạo bảng admin
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    gender ENUM('male', 'female', 'other') DEFAULT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    dob DATE DEFAULT NULL,
    address TEXT DEFAULT NULL,
    account_id INT UNIQUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (account_id) REFERENCES account(id)
);

-- Tạo bảng faculty
CREATE TABLE faculty (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

-- Tạo bảng major
CREATE TABLE major (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    faculty_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (faculty_id) REFERENCES faculty(id)
);

-- Tạo bảng class
CREATE TABLE class (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    faculty_id INT,
    major_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (faculty_id) REFERENCES faculty(id),
    FOREIGN KEY (major_id) REFERENCES major(id)
);

-- Tạo bảng student
CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    gender ENUM('male', 'female', 'other') DEFAULT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    dob DATE DEFAULT NULL,
    address TEXT DEFAULT NULL,
    account_id INT UNIQUE,
    faculty_id INT,
    major_id INT,
    class_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (account_id) REFERENCES Account(id),
    FOREIGN KEY (faculty_id) REFERENCES faculty(id),
    FOREIGN KEY (major_id) REFERENCES major(id),
    FOREIGN KEY (class_id) REFERENCES class(id)
);

-- Tạo bảng lecturer
CREATE TABLE lecturer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    gender ENUM('male', 'female', 'other') DEFAULT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    dob DATE DEFAULT NULL,
    address TEXT DEFAULT NULL,
    degree VARCHAR(255) DEFAULT NULL,
    account_id INT UNIQUE,
    faculty_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (faculty_id) REFERENCES faculty(id)
);

-- Tạo bảng affair
CREATE TABLE affair (
    id INT AUTO_INCREMENT PRIMARY KEY,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    gender ENUM('male', 'female', 'other') DEFAULT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    dob DATE DEFAULT NULL,
    address TEXT DEFAULT NULL,
    account_id INT UNIQUE,
    faculty_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (faculty_id) REFERENCES faculty(id)
);

-- Tạo bảng school_year
CREATE TABLE school_year (
    id INT AUTO_INCREMENT PRIMARY KEY,
    start_year YEAR NOT NULL,
    end_year YEAR NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

-- Tạo bảng criterion
CREATE TABLE criterion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT DEFAULT NULL,
    affair_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (affair_id) REFERENCES affair(id)
);

-- Tạo bảng thesis
CREATE TABLE thesis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    report_file VARCHAR(255) DEFAULT NULL,
    start_date DATE DEFAULT NULL,
    end_date DATE DEFAULT NULL,
    exp_date DATE DEFAULT NULL,
    avg_score FLOAT DEFAULT NULL,
    comment TEXT DEFAULT NULL,
    status ENUM('in_progress', 'submitted', 'under_review', 'defended', 'canceled') DEFAULT 'in_progress',
    affair_id INT,
    critical_lecturer_id INT,
    school_year_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (affair_id) REFERENCES affair(id),
    FOREIGN KEY (critical_lecturer_id) REFERENCES lecturer(id),
    FOREIGN KEY (school_year_id) REFERENCES school_year(id)
);

-- Tạo bảng thesis_lecturer
CREATE TABLE thesis_lecturer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    thesis_id INT,
    lecturer_id INT,
    FOREIGN KEY (thesis_id) REFERENCES thesis(id),
    FOREIGN KEY (lecturer_id) REFERENCES lecturer(id)
);

-- Tạo bảng thesis_student
CREATE TABLE thesis_student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    thesis_id INT,
    student_id INT,
    FOREIGN KEY (thesis_id) REFERENCES thesis(id),
    FOREIGN KEY (student_id) REFERENCES student(id)
);

-- Tạo bảng council
CREATE TABLE council (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status ENUM('pending', 'blocked') DEFAULT 'pending',
    school_year_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (school_year_id) REFERENCES school_year(id)
);

-- Tạo bảng council_lecturer
CREATE TABLE council_lecturer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    council_id INT,
    lecturer_id INT,
    position ENUM('president', 'secretary', 'critical') DEFAULT 'critical',
    FOREIGN KEY (council_id) REFERENCES council(id),
    FOREIGN KEY (lecturer_id) REFERENCES lecturer(id)
);

-- Tạo bảng council_criterion
CREATE TABLE council_criterion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    council_id INT,
    criterion_id INT,
	weight FLOAT DEFAULT NULL,
    FOREIGN KEY (council_id) REFERENCES council(id),
    FOREIGN KEY (criterion_id) REFERENCES criterion(id)
);

-- Tạo bảng score
CREATE TABLE score (
    id INT AUTO_INCREMENT PRIMARY KEY,
    score FLOAT DEFAULT NULL,
    council_id INT,
    lecturer_id INT,
    thesis_id INT,
    criterion_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (council_id) REFERENCES council(id),
    FOREIGN KEY (lecturer_id) REFERENCES lecturer(id),
    FOREIGN KEY (thesis_id) REFERENCES thesis(id),
    FOREIGN KEY (criterion_id) REFERENCES criterion(id)
);