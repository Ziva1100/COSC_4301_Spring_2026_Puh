-- drop the constraint first, then the column
ALTER TABLE feedings DROP CONSTRAINT feedings_active_ck;
ALTER TABLE feedings DROP COLUMN active;

-- re-add the column with default value and constraint
ALTER TABLE feedings ADD COLUMN active INT NOT NULL DEFAULT 1;
ALTER TABLE feedings ADD CONSTRAINT feedings_active_ck CHECK (active IN (0, 1));