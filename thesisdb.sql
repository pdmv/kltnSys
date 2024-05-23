-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS thesisdb;

USE thesisdb;

-- Tạo bảng Account
CREATE TABLE Account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    avatar VARCHAR(255) DEFAULT NULL,
    role ENUM('ADMIN', 'LECTURER', 'STUDENT', 'AFFAIR') NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

-- Tạo bảng Admin
CREATE TABLE Admin (
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
    FOREIGN KEY (account_id) REFERENCES Account(id)
);

-- Tạo bảng Faculty
CREATE TABLE Faculty (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

-- Tạo bảng Major
CREATE TABLE Major (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    faculty_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (faculty_id) REFERENCES Faculty(id)
);

-- Tạo bảng Class
CREATE TABLE Class (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    faculty_id INT,
    major_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (faculty_id) REFERENCES Faculty(id),
    FOREIGN KEY (major_id) REFERENCES Major(id)
);

-- Tạo bảng Student
CREATE TABLE Student (
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
    FOREIGN KEY (faculty_id) REFERENCES Faculty(id),
    FOREIGN KEY (major_id) REFERENCES Major(id),
    FOREIGN KEY (class_id) REFERENCES Class(id)
);

-- Tạo bảng Lecturer
CREATE TABLE Lecturer (
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
    FOREIGN KEY (account_id) REFERENCES Account(id),
    FOREIGN KEY (faculty_id) REFERENCES Faculty(id)
);

-- Tạo bảng Affair
CREATE TABLE Affair (
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
    FOREIGN KEY (account_id) REFERENCES Account(id),
    FOREIGN KEY (faculty_id) REFERENCES Faculty(id)
);

-- Tạo bảng School_Year
CREATE TABLE School_Year (
    id INT AUTO_INCREMENT PRIMARY KEY,
    start_year YEAR NOT NULL,
    end_year YEAR NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

-- Tạo bảng Criterion
CREATE TABLE Criterion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT DEFAULT NULL,
    weight FLOAT DEFAULT NULL,
    affair_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (affair_id) REFERENCES Affair(id)
);

-- Tạo bảng Thesis
CREATE TABLE Thesis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    report_file VARCHAR(255) DEFAULT NULL,
    start_date DATE DEFAULT NULL,
    end_date DATE DEFAULT NULL,
    exp_date DATE DEFAULT NULL,
    avg_score FLOAT DEFAULT NULL,
    comment TEXT DEFAULT NULL,
    status ENUM('in_progress', 'completed', 'canceled') DEFAULT 'in_progress',
    affair_id INT,
    critical_lecturer_id INT,
    school_year_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (affair_id) REFERENCES Affair(id),
    FOREIGN KEY (critical_lecturer_id) REFERENCES Lecturer(id),
    FOREIGN KEY (school_year_id) REFERENCES School_Year(id)
);

-- Tạo bảng Thesis_Lecturer
CREATE TABLE Thesis_Lecturer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    thesis_id INT,
    lecturer_id INT,
    FOREIGN KEY (thesis_id) REFERENCES Thesis(id),
    FOREIGN KEY (lecturer_id) REFERENCES Lecturer(id)
);

-- Tạo bảng Thesis_Student
CREATE TABLE Thesis_Student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    thesis_id INT,
    student_id INT,
    FOREIGN KEY (thesis_id) REFERENCES Thesis(id),
    FOREIGN KEY (student_id) REFERENCES Student(id)
);

-- Tạo bảng Council
CREATE TABLE Council (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status ENUM('pending', 'blocked') DEFAULT 'pending',
    school_year_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (school_year_id) REFERENCES School_Year(id)
);

-- Tạo bảng Council_Lecturer
CREATE TABLE Council_Lecturer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    council_id INT,
    lecturer_id INT,
    position ENUM('president', 'secretary', 'critical') DEFAULT 'critical',
    FOREIGN KEY (council_id) REFERENCES Council(id),
    FOREIGN KEY (lecturer_id) REFERENCES Lecturer(id)
);

-- Tạo bảng Council_Criterion
CREATE TABLE Council_Criterion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    council_id INT,
    criterion_id INT,
    FOREIGN KEY (council_id) REFERENCES Council(id),
    FOREIGN KEY (criterion_id) REFERENCES Criterion(id)
);

-- Tạo bảng Score
CREATE TABLE Score (
    id INT AUTO_INCREMENT PRIMARY KEY,
    score FLOAT DEFAULT NULL,
    council_id INT,
    lecturer_id INT,
    thesis_id INT,
    criterion_id INT,
    FOREIGN KEY (council_id) REFERENCES Council(id),
    FOREIGN KEY (lecturer_id) REFERENCES Lecturer(id),
    FOREIGN KEY (thesis_id) REFERENCES Thesis(id),
    FOREIGN KEY (criterion_id) REFERENCES Criterion(id)
);