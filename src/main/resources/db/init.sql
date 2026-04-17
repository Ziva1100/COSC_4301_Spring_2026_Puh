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
    first_name VARCHAR(50),
    last_name VARCHAR(50),
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

-- SELECT  * FROM roles;

-- fill clearances table with data
-- Alpha, Omega, Eclipse
INSERT INTO clearances (clearance_name, clearance_desc)
VALUES ('Alpha','Entry level clearance for standard operations');
INSERT INTO clearances (clearance_name, clearance_desc)
VALUES ('Omega','Highest clearance for classified mission access');
INSERT INTO clearances (clearance_name, clearance_desc)
VALUES ('Eclipse','Restricted clearance for off-record operations');

-- SELECT * FROM clearances;

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

-- SELECT * FROM dimensions;

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

-- SELECT * FROM certifications;

-- fill wardens with data
INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1001, 'badge',    'Kael',   'Dawnveil',    'kael.dawnveil@neonark.com',    '2024-01-15', 1, 1, 1);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1002, 'passport', 'Lyra',   'Stormfeld',   'lyra.stormfeld@neonark.com',   '2024-02-20', 2, 2, 2);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1003, 'visa',     'Oryn',   'Ashveil',     'oryn.ashveil@neonark.com',     '2024-03-10', 3, 3, 3);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1004, 'badge',    'Seren',  'Voss',        'seren.voss@neonark.com',       '2024-04-05', 4, 4, 1);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1005, 'passport', 'Thane',  'Mirova',      'thane.mirova@neonark.com',     '2024-05-18', 5, 5, 2);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1006, 'badge',    'Zara',   'Nighthollow', 'zara.nighthollow@neonark.com', '2024-06-22', 1, 2, 3);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1007, 'visa',     'Caden',  'Solmere',     'caden.solmere@neonark.com',    '2024-07-30', 2, 3, 1);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1008, 'badge',    'Isla',   'Eryndor',     'isla.eryndor@neonark.com',     '2024-08-14', 3, 1, 2);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1009, 'passport', 'Riven',  'Duskfall',    'riven.duskfall@neonark.com',   '2024-09-09', 4, 4, 3);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1010, 'visa',     'Nova',   'Veylan',      'nova.veylan@neonark.com',      '2024-10-01', 5, 5, 1);

-- add earth dimension
INSERT INTO dimensions (dimension_name, dimension_desc) VALUES ('Earth', 'Earth');

-- earth wardens (dimension_id = 6) get proper first + last names
INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1011, 'passport', 'Marcus',  'Reid',       'marcus.reid@neonark.com',      '2024-01-08', 6, 1, 1);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1012, 'visa',     'Sofia',   'Navarro',    'sofia.navarro@neonark.com',    '2024-02-14', 6, 2, 2);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1013, 'passport', 'James',   'Holloway',   'james.holloway@neonark.com',   '2024-03-22', 6, 3, 1);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1014, 'visa',     'Amara',   'Osei',       'amara.osei@neonark.com',       '2024-04-17', 6, 4, 3);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1015, 'passport', 'Daniel',  'Cruz',       'daniel.cruz@neonark.com',      '2024-05-29', 6, 5, 2);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1016, 'visa',     'Priya',   'Sharma',     'priya.sharma@neonark.com',     '2024-06-11', 6, 2, 1);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1017, 'passport', 'Ethan',   'Blackwood',  'ethan.blackwood@neonark.com',  '2024-07-03', 6, 1, 3);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1018, 'visa',     'Leila',   'Hassan',     'leila.hassan@neonark.com',     '2024-08-25', 6, 3, 2);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1019, 'passport', 'Noah',    'Castellano', 'noah.castellano@neonark.com',  '2024-09-16', 6, 4, 1);

INSERT INTO wardens (alternate_id, id_type, first_name, last_name, email, start_date, dimension_id, role_id, clearance_id)
VALUES (1020, 'visa',     'Zoe',     'Pemberton',  'zoe.pemberton@neonark.com',    '2024-10-30', 6, 5, 3);
-- SELECT * FROM wardens;

-- a snapshot of status_log table with data
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (1,  '2024-01-15', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (11, '2024-01-18', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (2,  '2024-02-20', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (12, '2024-02-25', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (3,  '2024-03-10', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (13, '2024-03-22', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (4,  '2024-04-05', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (14, '2024-04-17', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (1,  '2024-04-30', 'onLeave');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (5,  '2024-05-18', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (15, '2024-05-29', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (3,  '2024-06-01', 'onLeave');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (6,  '2024-06-22', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (16, '2024-06-11', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (1,  '2024-07-01', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (7,  '2024-07-30', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (17, '2024-07-03', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (2,  '2024-07-15', 'onLeave');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (8,  '2024-08-14', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (18, '2024-08-25', 'onLeave');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (13, '2024-09-01', 'onLeave');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (9,  '2024-09-09', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (19, '2024-09-16', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (2,  '2024-09-20', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (15, '2024-09-30', 'terminated');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (10, '2024-10-01', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (20, '2024-10-30', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (6,  '2024-11-05', 'onLeave');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (3,  '2024-11-10', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (13, '2024-11-20', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (9,  '2024-12-01', 'onLeave');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (7,  '2024-12-15', 'onLeave');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (18, '2025-01-05', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (10, '2025-01-15', 'onLeave');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (6,  '2025-01-28', 'terminated');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (9,  '2025-02-10', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (7,  '2025-02-20', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (19, '2025-03-01', 'terminated');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (10, '2025-03-10', 'active');
INSERT INTO status_log (warden_id, update_date, new_status) VALUES (20, '2025-03-25', 'onLeave');

-- SELECT * FROM status_log;

-- fill the certification_log table with data
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (1, 1,  '2024-01-15', 'active',    '2025-01-15');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (2, 2,  '2024-02-20', 'active',    '2025-02-20');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (3, 3,  '2024-03-10', 'active',    '2025-03-10');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (4, 4,  '2024-04-05', 'active',    '2025-04-05');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (5, 5,  '2024-05-18', 'active',    '2025-05-18');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (1, 6,  '2024-06-22', 'suspended', '2025-06-22');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (6, 7,  '2024-07-30', 'active',    '2025-07-30');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (2, 8,  '2024-08-14', 'active',    '2025-08-14');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (3, 9,  '2024-09-09', 'active',    '2025-09-09');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (7, 10, '2024-10-01', 'active',    '2025-10-01');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (4, 11, '2024-10-15', 'active',    '2025-10-15');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (5, 12, '2024-11-01', 'expired',   '2024-11-01');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (6, 13, '2024-11-20', 'active',    '2025-11-20');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (1, 14, '2024-12-05', 'active',    '2025-12-05');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (2, 15, '2024-12-20', 'suspended', '2025-12-20');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (3, 16, '2025-01-10', 'active',    '2026-01-10');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (4, 17, '2025-01-25', 'active',    '2026-01-25');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (5, 18, '2025-02-10', 'active',    '2026-02-10');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (1, 19, '2025-02-28', 'expired',   '2025-02-28');
INSERT INTO certification_log (certification_id, warden_id, date_created, certification_status, expiration_date) VALUES (7, 20, '2025-03-15', 'active',    '2026-03-15');

-- SELECT * FROM certification_log;

--------------------------------------------------------------------------------------------------------
-- STEP 4: QUERY THE DATABASE TO FIND THE INFO IN THE MENU

-- [2] View Wardens
-- 1. View All Wardens
CREATE OR REPLACE VIEW all_wardens AS
SELECT DISTINCT ON (warden_id)
    w.warden_id, w.first_name, w.last_name, w.email, r.role_name,
    c.clearance_name, d.dimension_name, sl.new_status, sl.update_date
FROM wardens w JOIN roles r ON w.role_id = r.role_id JOIN clearances c
ON w.clearance_id = c.clearance_id JOIN dimensions d ON w.dimension_id = d.dimension_id
    JOIN status_log sl ON w.warden_id = sl.warden_id
ORDER BY w.warden_id, sl.update_date
DESC;
SELECT * FROM all_wardens;

-- 2. View Warden by Id
CREATE OR REPLACE VIEW each_warden AS
SELECT
    w.warden_id, w.alternate_id, w.id_type, w.first_name, w.last_name, w.email,
    w.start_date, r.role_name,
    c.clearance_name, d.dimension_name,
    (SELECT sl.new_status
     FROM status_log sl
     WHERE w.warden_id = sl.warden_id
     ORDER BY update_date DESC
     LIMIT 1) as new_status,
    (SELECT sl.update_date
    FROM status_log sl
    WHERE w.warden_id = sl.warden_id
    ORDER BY update_date DESC
    LIMIT 1) as update_date,
    cr.certification_name
FROM wardens w JOIN roles r ON w.role_id = r.role_id
    JOIN clearances c ON w.clearance_id = c.clearance_id
    JOIN dimensions d ON w.dimension_id = d.dimension_id
    LEFT JOIN certification_log cl ON w.warden_id = cl.warden_id
    LEFT JOIN certifications cr ON cl.certification_id = cr.certification_id
;
SELECT * FROM each_warden
WHERE warden_id = 4;

-- 3. View Wardens by employment status
CREATE OR REPLACE VIEW wardens_by_employment AS
SELECT DISTINCT ON (sl.warden_id)
    wardens.warden_id, wardens.first_name, wardens.last_name, sl.new_status,
    sl.update_date
FROM wardens JOIN status_log sl on wardens.warden_id = sl.warden_id
WHERE sl.new_status = 'terminated';

SELECT * FROM wardens_by_employment;


-- 4. View Wardens by Roles
CREATE OR REPLACE VIEW wardens_by_roles AS
SELECT wardens.warden_id, wardens.first_name, wardens.last_name, roles.role_name,
       roles.role_desc
FROM wardens JOIN roles ON wardens.role_id = roles.role_id;
SELECT * FROM wardens_by_roles;

-- [4] Manage Certifications
-- 2. View Certifications
CREATE OR REPLACE VIEW view_certifications AS
SELECT w.warden_id, w.first_name, cr.certification_name, cr.certification_desc,
       cl.date_created, cl.certification_status, cl.expiration_date
FROM wardens w JOIN certification_log cl ON w.warden_id = cl.warden_id
JOIN certifications cr ON cl.certification_id = cr.certification_id
ORDER BY cl.date_created DESC ;
SELECT * FROM view_certifications;

---------------------------------------------------------------------------------------------
-- STEP 5: UPDATING AND INSERTING  ROWS INTO THE DATABASE AND ENSURE DATA INTEGRITY
-- [1] Add New Warden
-- create a procedure that adds a new warden and has default value of Earth as his dimension
CREATE OR REPLACE PROCEDURE add_new_warden (in_fname VARCHAR, in_id_num INT, in_id_type VARCHAR,
       in_email VARCHAR, in_role VARCHAR, in_emp_status VARCHAR, in_clearance VARCHAR,
       in_start_date DATE, in_lname VARCHAR DEFAULT NULL, in_end_date DATE DEFAULT NULL,
       in_dimension VARCHAR DEFAULT 'Earth')
       LANGUAGE plpgsql
       AS $$
       DECLARE
        role_id_in INT;
        clearance_id_in INT;
        dimension_id_in INT;
        warden_id_new INT;
       BEGIN
        -- find the primary key values for role, clearance, and dimension
        SELECT role_id INTO role_id_in
           FROM roles
               WHERE role_name = in_role;
        IF role_id_in IS NULL THEN
            RAISE EXCEPTION 'Role % does not exists.', in_role;
        END IF;

        SELECT clearance_id INTO clearance_id_in
            FROM clearances
                WHERE clearance_name = in_clearance;
        IF clearance_id_in IS NULL THEN
            RAISE EXCEPTION 'Clearance % does not exists.', in_clearance;
        END IF;

        SELECT dimension_id INTO dimension_id_in
            FROM dimensions
                WHERE dimension_name = in_dimension;
        IF dimension_id_in IS NULL THEN
            RAISE EXCEPTION 'Dimension % does not exists.', in_dimension;
        END IF;

        -- check if the status is within the check constraint
        IF in_emp_status NOT IN ('active','onLeave','terminated')
           THEN RAISE EXCEPTION 'Employment status does not exists. Please enter active, onLeave, or terminated';
        END IF;

        -- insert into table wardens the values
        INSERT INTO wardens (alternate_id, id_type, first_name,
                             last_name, email, start_date, dimension_id, role_id, clearance_id)
            VALUES
                (in_id_num, in_id_type, in_fname, in_lname,
                 in_emaIl, in_start_date, dimension_id_in, role_id_in,
                 clearance_id_in);

        -- get the new warden ID so you can insert it into the status log
        SELECT warden_id INTO warden_id_new
            FROM wardens
                WHERE alternate_id = in_id_num;


        -- insert into employment status table
        INSERT INTO status_log (warden_id, update_date, new_status)
            VALUES
                (warden_id_new, in_start_date, LOWER(in_emp_status));

        IF in_end_date IS NOT NULL THEN
            INSERT INTO status_log (warden_id, update_date, new_status)
                VALUES
                (warden_id_new, in_end_date, 'terminated');
        END IF;


       EXCEPTION
        -- catch all other exceptions
        WHEN OTHERS THEN
            RAISE EXCEPTION 'Unexpected error: %', SQLERRM;
       END;
       $$;

-- check if the procedure works correctly
CALL add_new_warden(
    in_fname       => 'Zoe',
    in_id_num      => 1021,
    in_id_type     => 'passport',
    in_email       => 'zoe.newwarden@neonark.com',
    in_role        => 'Field',
    in_emp_status  => 'active',
    in_clearance   => 'Alpha',
    in_start_date  => '2024-11-01',
    in_lname       => 'Pemberton'
);

SELECT * FROM each_warden
WHERE alternate_id = 1021;

-- [3] Update Warden
-- create a schema that will hold all the updates for warden table
CREATE SCHEMA IF NOT EXISTS update_warden;

-- [3.1] Update Role
CREATE OR REPLACE PROCEDURE update_warden.update_role(in_warden_id INT, new_role VARCHAR)
       LANGUAGE plpgsql
       AS $$
       DECLARE
        role_id_new INT;
       BEGIN
        SELECT role_id INTO role_id_new
            FROM roles
                WHERE role_name = new_role;

        IF role_id_new IS NULL THEN
           RAISE EXCEPTION 'The role % does not exists', new_role;
        END IF;

        UPDATE wardens
            SET role_id = role_id_new
                WHERE warden_id = in_warden_id;

        END;
        $$;

-- [3.2] Update Clearance
CREATE OR REPLACE PROCEDURE update_warden.update_clearance(in_warden_id INT, new_clearance VARCHAR)
       LANGUAGE plpgsql
       AS $$
       DECLARE
        clearance_id_new INT;
       BEGIN
        SELECT clearance_id INTO clearance_id_new
            FROM clearances
                WHERE clearance_name = new_clearance;

        IF clearance_id_new IS NULL THEN
           RAISE EXCEPTION 'The clearance % does not exists', new_clearance;
        END IF;

        UPDATE wardens
            SET clearance_id = clearance_id_new
                WHERE warden_id = in_warden_id;

        END;
        $$;

-- [3.3] Update Employment Status
CREATE OR REPLACE PROCEDURE update_warden.update_emp_status (in_warden_id INT, new_status_in VARCHAR)
       LANGUAGE plpgsql
       AS $$
       BEGIN
        IF new_status_in NOT IN ('active', 'onLeave','terminated') THEN
           RAISE EXCEPTION 'Status % needs to be active, onLeave, or terminated',new_status_in;
        END IF;
        INSERT INTO status_log (warden_id, new_status)
            VALUES (in_warden_id, new_status_in);
        END;
        $$;

CREATE OR REPLACE PROCEDURE update_warden.update_start_date (in_warden_id INT, new_start_date DATE)
       LANGUAGE plpgsql
       AS $$
       BEGIN
        UPDATE wardens
            SET start_date = new_start_date
                WHERE warden_id = in_warden_id;
        END;
        $$;




CALL update_warden.update_role (
    in_warden_id => 21,
    new_role    => 'Admin'
);

SELECT * FROM each_warden
WHERE alternate_id = 1021;

CALL update_warden.update_clearance (
    in_warden_id => 21,
    new_clearance    => 'Omega'
);

SELECT * FROM each_warden
WHERE alternate_id = 1021;


CALL update_warden.update_emp_status (
    in_warden_id => 21,
    new_status_in => 'onLeave'
);

SELECT * FROM each_warden
WHERE alternate_id = 1021;

CALL update_warden.update_start_date (
    in_warden_id => 21,
    new_start_date => '2025-04-17'
);

SELECT * FROM each_warden
WHERE alternate_id = 1021;