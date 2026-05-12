-- this file will add the credentials to wardens so
-- the warden/user can be checked for his user and password
-- // Capstone -- [ View All System Users ] Menu Choice

ALTER TABLE wardens ADD COLUMN username VARCHAR(50) UNIQUE NOT NULL DEFAULT 'user';
ALTER TABLE wardens ADD COLUMN password VARCHAR(255) NOT NULL DEFAULT 'user123';

-- update user 1 with administrative privilages
UPDATE TABLE wardens
SET username = 'admin'
WHERE warden_id = 1;

UPDATE TABLE wardens
SET password = 'admin123'
WHERE warden_id = 1;


-- do not worry about encrypting passwords at this stage