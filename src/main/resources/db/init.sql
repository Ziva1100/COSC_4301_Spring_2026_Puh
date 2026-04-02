----------------------------------------
-- Developer: Ziva Puh
--
-- Project#: Project #3
--
-- File Name: init.sql
--
-- Course: COSC 4301 Modern Programming
--
-- Due Date: 4/18/2026
--
-- Instructor: Prof. Jon-Mikel Pearson
--
-- Description: Create a single sql file that creates
-- the tables needed for Warden Menu functions,
-- populates them and queries them to see the data
-- needed for the menu.
------------------------------------------

-- STEP 0: DELETE ANY EXISTING TABLES

DROP TABLE IF EXISTS wardens CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS clearances CASCADE;
DROP TABLE IF EXISTS dimentions CASCADE;
DROP TABLE IF EXISTS status_log CASCADE;
DROP TABLE IF EXISTS certification_log CASCADE;
DROP TABLE IF EXISTS certifications CASCADE;

-- STEP 1: CREATING TABLES
-- create roles table
CREATE TABLE roles (
    role_id INT GENERATED ALWAYS AS IDENTITY,
    role_name VARCHAR(30) NOT NULL,
    role_desc TEXT,

    CONSTRAINT role_name_uk UNIQUE (role_name)
);

-- create table clearances
CREATE TABLE clearances (
    clearance_id INT GENERATED ALWAYS AS IDENTITY,
    clearance_name VARCHAR(30) NOT NULL,
    clearance_desc TEXT,

    CONSTRAINT clearance_name_uk UNIQUE(clearance_name)
);

-- create table dimensions
CREATE TABLE dimensions (
    dimension_id INT GENERATED ALWAYS AS IDENTITY,
    dimension_name VARCHAR(30) NOT NULL,
    dimension_desc TEXT,

    CONSTRAINT dimension_name_uk UNIQUE (dimension_name)
);

-- create table certifications
CREATE TABLE certifications (
    certification_id INT GENERATED ALWAYS AS IDENTITY,
    certification_name VARCHAR(30) NOT NULL,
    certification_desc TEXT,

    CONSTRAINT certification_name_uk UNIQUE (certification_name)
);

-- Add missing primary key constraints
ALTER TABLE roles ADD CONSTRAINT role_id_pk PRIMARY KEY (role_id);
ALTER TABLE clearances ADD CONSTRAINT clearance_id_pk PRIMARY KEY (clearance_id);
ALTER TABLE dimensions ADD CONSTRAINT dimension_id_pk PRIMARY KEY (dimension_id);
ALTER TABLE certifications ADD CONSTRAINT certification_id_pk PRIMARY KEY (certification_id);

-- create table wardens
CREATE TABLE wardens (
    warden_id INT GENERATED ALWAYS AS IDENTITY,
    alternate_id INT NOT NULL,
    id_type VARCHAR(10) NOT NULL,
    full_name VARCHAR(50),
    referred VARCHAR(50),
    email VARCHAR(50),
    start_date DATE,
    dimension_id INT NOT NULL,
    role_id INT NOT NULL,
    clearance_id INT NOT NULL ,

    CONSTRAINT warden_id_pk PRIMARY KEY (warden_id),
    CONSTRAINT alternate_id_uk UNIQUE (alternate_id),
    CONSTRAINT id_type_ck CHECK(id_type IN('badge', 'passport', 'visa')),
    CONSTRAINT warder_dimension_id_fk FOREIGN KEY (dimension_id) REFERENCES dimensions (dimension_id),
    CONSTRAINT warden_role_id_fk FOREIGN KEY (role_id) REFERENCES roles (role_id),
    CONSTRAINT warden_clearance_id FOREIGN KEY (clearance_id) REFERENCES clearances (clearance_id)
    );

