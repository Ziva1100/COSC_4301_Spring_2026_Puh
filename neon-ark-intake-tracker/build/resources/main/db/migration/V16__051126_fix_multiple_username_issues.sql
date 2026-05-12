-- this flyway migration will correct the multiple returns for the service of finding the right
-- username and the right password


-- V16__fix_warden_credentials.sql
-- Set unique usernames from email for all wardens
-- Only warden_id 1 gets admin/admin123
-- Only warden_id 2 gets user/user123
-- All others get unique usernames from email

-- set all usernames from email first
UPDATE wardens SET username = SPLIT_PART(email, '@', 1);

-- override warden_id 1 with admin credentials
UPDATE wardens SET username = 'admin', password = 'admin123' WHERE warden_id = 1;

-- override warden_id 2 with user credentials
UPDATE wardens SET username = 'user', password = 'user123' WHERE warden_id = 2;

-- set default password for everyone else
UPDATE wardens SET password = 'neonark123' WHERE warden_id NOT IN (1, 2);