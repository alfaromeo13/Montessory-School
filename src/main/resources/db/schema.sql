-- Drop existing tables if they exist
DROP TABLE IF EXISTS
    Parent,
    Child,
    Event,
    Image,
    Donation,
    Static_Pages;

-- Create table for parents of children
CREATE TABLE Parent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Create the Child_Registration table
CREATE TABLE Child (
    id INT AUTO_INCREMENT PRIMARY KEY,
    parent_id INT NOT NULL, -- Foreign key to the parent
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    enrollment_year INT NOT NULL,
    grade ENUM(
        'Kindergarten',
        '1st grade',
        '2nd grade',
        '3rd grade',
        '4th grade',
        '5th grade',
        '6th grade',
        '7th grade',
        '8th grade',
        '9th grade'
    ) NOT NULL,
    gender ENUM('Boy', 'Girl') NOT NULL,
    address TEXT NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    city VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES Parent(id) ON DELETE CASCADE
);

-- Create the Event table
CREATE TABLE Event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    event_date DATETIME NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the Image table
CREATE TABLE Image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL,
    description TEXT NULL,
    event_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES Event(id) ON DELETE CASCADE
);

-- Create the Donation table
CREATE TABLE Donation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    donor_name VARCHAR(255) NULL,
    donor_email VARCHAR(255) NULL,
    amount DECIMAL(10,2) NOT NULL,
    message TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the Static_Pages table
CREATE TABLE Static_Pages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    page_name VARCHAR(255) UNIQUE NOT NULL,
    content JSON NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

desc Child;