-- add a column that will say if the feeding is still in effect

ALTER TABLE feedings
ADD COLUMN active INT NOT NULL;

-- ensure the column only shows 0 for inactive and 1 for active
ALTER TABLE feedings
ADD CONSTRAINT feedings_active_ck
CHECK (active IN (0,1));

