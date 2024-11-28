-- Drop existing tables if they exist
DROP TABLE IF EXISTS
    parent,
    child,
    event,
    image,
    admin,
    donation;

-- Create table for parents of children
CREATE TABLE parent (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Create the child registration table
CREATE TABLE child (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT NOT NULL, -- Foreign key to the parent
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    enrollment_year INT NOT NULL,
    grade ENUM(
        'KINDERGARTEN',
        'FIRST_GRADE',
        'SECOND_GRADE',
        'THIRD_GRADE',
        'FOURTH_GRADE',
        'FIFTH_GRADE',
        'SIXTH_GRADE',
        'SEVENTH_GRADE',
        'EIGHTH_GRADE',
        'NINTH_GRADE'
    ) NOT NULL,
    gender ENUM('BOY', 'GIRL', 'OTHER', 'PREFER_NOT_TO_SAY') NOT NULL,
    address TEXT NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    city VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES parent(id) ON DELETE CASCADE
);

-- Create the event table
CREATE TABLE event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    event_date DATETIME NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the image table
CREATE TABLE image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    url TEXT NOT NULL,
    description TEXT NULL,
    event_id BIGINT NOT NULL,
    FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
    INDEX (event_id) --  We plan to query images by event_id frequently, we add an index to improve query performance.
);

-- Create the donation table
CREATE TABLE donation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    donor_name VARCHAR(255) DEFAULT 'Anonymous',
    donor_email VARCHAR(255) NULL,
    amount DECIMAL(10,2) NOT NULL,
    message TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- table for admin info
CREATE TABLE admin (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL, -- Hashed password (bcrypt hash)
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp of creation
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Timestamp of last update
);