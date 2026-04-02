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

DROP TABLE IF EXISTS certification_log CASCADE;
DROP TABLE IF EXISTS status_log CASCADE;
DROP TABLE IF EXISTS wardens CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS clearances CASCADE;
DROP TABLE IF EXISTS dimensions CASCADE;
DROP TABLE IF EXISTS certifications CASCADE;

--------------------------------------------------------------
-- STEP 1: CREATING TABLES

-- create roles table
-- keeps track of all the roles that exist in the organization
-- keeps detailed description of what each role does
-- roles determine what responsibilities each warden has
CREATE TABLE roles (
    role_id INT GENERATED ALWAYS AS IDENTITY,
    role_name VARCHAR(30) NOT NULL,
    role_desc TEXT,

    CONSTRAINT role_name_uk UNIQUE (role_name)
);

-- create table clearances
-- keeps track of all the clearances that the system has
-- with detailed descriptions
-- clearances determine the access that each warden has
CREATE TABLE clearances (
    clearance_id INT GENERATED ALWAYS AS IDENTITY,
    clearance_name VARCHAR(30) NOT NULL,
    clearance_desc TEXT,

    CONSTRAINT clearance_name_uk UNIQUE(clearance_name)
);

-- create table dimensions
-- keeps track of the dimensions from where the creatures
-- and wardens are coming from
-- this table is used in new creatures as well
CREATE TABLE dimensions (
    dimension_id INT GENERATED ALWAYS AS IDENTITY,
    dimension_name VARCHAR(30) NOT NULL,
    dimension_desc TEXT,

    CONSTRAINT dimension_name_uk UNIQUE (dimension_name)
);

-- create table certifications
-- keeps track of all certifications possible in the company
-- certifications determine the skill set that the warden has
-- to work with astral beings
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
    start_date DATE DEFAULT CURRENT_DATE,
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

-- create table status_log
-- status log keeps track of the current status that the
-- warden holds
-- active: the warden currently works in the appropriate department
-- onLeave: the warden is temporary non-active
-- terminated: the warden does not work for the department anymore
CREATE TABLE status_log (
    status_id INT GENERATED ALWAYS AS IDENTITY,
    warden_id INT NOT NULL,
    update_date DATE DEFAULT CURRENT_DATE,
    new_status VARCHAR(20),

    CONSTRAINT status_id_pk PRIMARY KEY (status_id),
    CONSTRAINT new_status_ck CHECK ( new_status IN ('active','onLeave', 'terminated') ),
    CONSTRAINT status_warden_id_fk FOREIGN KEY (warden_id) REFERENCES wardens (warden_id)
);

-- create table certification_log
-- certification log keeps track of all certifications issued to wardens
-- it determines when is the expiration date and which warden has
-- which certification
-- each warden can hold more than one certification
-- active: the warden currently holds this certification
-- expired: the certification has to be renewed
-- suspended: the warden lost the certification before the expiration date
-- if suspended, an incident report has to be issued
-- if expiration date is null, the certification never expires
CREATE TABLE certification_log (
    certification_log_id INT GENERATED ALWAYS AS IDENTITY ,
    certification_id INT NOT NULL ,
    warden_id INT NOT NULL ,
    date_created DATE DEFAULT CURRENT_DATE,
    certification_status varchar(10) NOT NULL ,
    expiration_date DATE,

    CONSTRAINT certification_log_id_pk PRIMARY KEY (certification_log_id),
    CONSTRAINT certification_status_ck CHECK ( certification_status IN ('active','expired', 'suspended') ),
    CONSTRAINT certifications_log_warden_id_fk FOREIGN KEY (warden_id) REFERENCES wardens (warden_id),
    CONSTRAINT certifications_log_certifications_id_fk FOREIGN KEY (certification_id) REFERENCES certifications (certification_id)

);
--------------------------------------------------------------
-- STEP 2: FILL THE TABLES WITH DATA

-- fill role table with roles:
-- Admin, Field, Rift, Trainer, Astral
INSERT INTO roles (role_name, role_desc)
VALUES ('Admin', 'Manages system access and internal operations');
INSERT INTO roles (role_name, role_desc)
VALUES ('Field', 'Deployed on active ground-level assignments');
INSERT INTO roles (role_name, role_desc)
VALUES ('Rift', 'Specializes in dimensional breach containment');
INSERT INTO roles (role_name, role_desc)
VALUES ('Trainer', 'MOversees recruit onboarding and skill development');
INSERT INTO roles (role_name, role_desc)
VALUES ('Astral', 'Conducts operations beyond physical dimensions');

SELECT  * FROM roles;

-- fill clearances table with data
-- Alpha, Omega, Eclipse
INSERT INTO clearances (clearance_name, clearance_desc)
VALUES ('Alpha','Entry level clearance for standard operations');
INSERT INTO clearances (clearance_name, clearance_desc)
VALUES ('Omega','Highest clearance for classified mission access');
INSERT INTO clearances (clearance_name, clearance_desc)
VALUES ('Eclipse','Restricted clearance for off-record operations');

SELECT * FROM clearances;

-- fill dimensions with data
INSERT INTO dimensions (dimension_name, dimension_desc)
VALUES ('Veylan', 'A crystalline dimension of refracted light and fractured time');
INSERT INTO dimensions (dimension_name, dimension_desc)
VALUES ('Noctara', 'A shadowed realm existing between sleep and consciousness');
INSERT INTO dimensions (dimension_name, dimension_desc)
VALUES ('Solmere', 'A radiant dimension closest to the origin of all energy');
INSERT INTO dimensions (dimension_name, dimension_desc)
VALUES ('Duskfall', 'A decaying dimension on the edge of dimensional collapse');
INSERT INTO dimensions (dimension_name, dimension_desc)
VALUES ('Eryndor', 'A vast dimension of ancient ruins and forgotten civilizations');

SELECT * FROM dimensions;

-- fill certifications with data
INSERT INTO certifications (certification_name, certification_desc)
VALUES ('Dimensional Navigation', 'Certified to traverse and navigate unstable dimensional rifts');
INSERT INTO certifications (certification_name, certification_desc)
VALUES ('Combat Readiness', 'Cleared for active engagement in hostile dimensional zones');
INSERT INTO certifications (certification_name, certification_desc)
VALUES ('Rift Containment', 'Trained in sealing and stabilizing dimensional breaches');
INSERT INTO certifications (certification_name, certification_desc)
VALUES ('Astral Mapping', 'Certified to chart and document uncharted dimensional space');
INSERT INTO certifications (certification_name, certification_desc)
VALUES ('Hazard Exposure', 'Cleared for operations in high radiation dimensional zones');
INSERT INTO certifications (certification_name, certification_desc)
VALUES ('Psych Evaluation', 'Certified mentally stable for prolonged astral assignments');
INSERT INTO certifications (certification_name, certification_desc)
VALUES ('Temporal Awareness', 'Trained to operate in time-distorted dimensional environments');

SELECT * FROM certifications;

-- fill wardens with data
INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1001, 'badge',    'Kael Dawnveil',    'Neon Ark HQ',   'kael.dawnveil@neonark.com',    '2024-01-15', 1, 1, 1);

INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1002, 'passport', 'Lyra Stormfeld',   'Field Division', 'lyra.stormfeld@neonark.com',  '2024-02-20', 2, 2, 2);

INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1003, 'visa',     'Oryn Ashveil',     'Rift Corps',    'oryn.ashveil@neonark.com',     '2024-03-10', 3, 3, 3);

INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1004, 'badge',    'Seren Voss',       'Neon Ark HQ',   'seren.voss@neonark.com',       '2024-04-05', 4, 4, 1);

INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1005, 'passport', 'Thane Mirova',     'Astral Division','thane.mirova@neonark.com',    '2024-05-18', 5, 5, 2);

INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1006, 'badge',    'Zara Nighthollow', 'Field Division', 'zara.nighthollow@neonark.com','2024-06-22', 1, 2, 3);

INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1007, 'visa',     'Caden Solmere',    'Rift Corps',    'caden.solmere@neonark.com',    '2024-07-30', 2, 3, 1);

INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1008, 'badge',    'Isla Eryndor',     'Neon Ark HQ',   'isla.eryndor@neonark.com',     '2024-08-14', 3, 1, 2);

INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1009, 'passport', 'Riven Duskfall',   'Astral Division','riven.duskfall@neonark.com',  '2024-09-09', 4, 4, 3);

INSERT INTO wardens (alternate_id, id_type, full_name, referred, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1010, 'visa',     'Nova Veylan',      'Field Division', 'nova.veylan@neonark.com',     '2024-10-01', 5, 5, 1);

SELECT * FROM wardens;