ALTER TABLE creatures
ADD COLUMN removed INT DEFAULT 0;

UPDATE creatures
SET removed = 0;
