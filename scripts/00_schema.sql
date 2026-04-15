-- Drop tables if they exist to start from scratch
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS evaluations, reports, defenses, internships, companies, users;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. Users Table (Administrators, Teachers, Students)
-- Manages registration and login
CREATE TABLE users (
    email VARCHAR(150) PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMINISTRATOR', 'TEACHER', 'STUDENT') NOT NULL,
    major VARCHAR(100),
    is_active BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Partner Companies Table
-- Management of partner structures offering internships
CREATE TABLE companies (
    siret VARCHAR(14) PRIMARY KEY,
    corporate_name VARCHAR(200) NOT NULL,
    address TEXT,
    contact_email VARCHAR(150),
    contact_phone VARCHAR(20)
);

-- 3. Internships Table
-- Created by admin/teacher and assigned to a student
CREATE TABLE internships (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    objective TEXT NOT NULL,
    start_date DATE NOT NULL,
    duration_weeks INT,
    status ENUM('ONGOING', 'COMPLETED', 'VALIDATED', 'REJECTED') DEFAULT 'ONGOING',

    student_email VARCHAR(150),
    teacher_email VARCHAR(150), -- The supervisor/tutor
    company_siret VARCHAR(14),
    FOREIGN KEY (student_email) REFERENCES users(email) ON DELETE SET NULL,
    FOREIGN KEY (teacher_email) REFERENCES users(email) ON DELETE SET NULL,
    FOREIGN KEY (company_siret) REFERENCES companies(siret) ON DELETE CASCADE
);

-- 4. Reports Table
-- Uploaded by students in PDF format
CREATE TABLE reports (
    file_name VARCHAR(255) PRIMARY KEY,
    storage_path VARCHAR(500) NOT NULL,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    internship_id BIGINT UNIQUE,
    FOREIGN KEY (internship_id) REFERENCES internships(id) ON DELETE CASCADE
);

-- 5. Evaluations Table (Grades and Comments)
-- Conducted by teachers
CREATE TABLE evaluations (
    grade DECIMAL(4,2),
    comment TEXT,
    `date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    report_file_name VARCHAR(255) PRIMARY KEY,
    teacher_email VARCHAR(150),
    FOREIGN KEY (report_file_name) REFERENCES reports(file_name) ON DELETE CASCADE,
    FOREIGN KEY (teacher_email) REFERENCES users(email) ON DELETE SET NULL
);

-- 6. Defenses Table (Oral Exams)
-- Organized by the administrator with a jury
CREATE TABLE defenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `date` DATETIME NOT NULL,
    room VARCHAR(50),

    student_email VARCHAR(150),
    FOREIGN KEY (student_email) REFERENCES users(email) ON DELETE CASCADE
);
