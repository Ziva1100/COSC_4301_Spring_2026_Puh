-- 1) Add habitat_id if not exists yet
ALTER TABLE creatures
ADD COLUMN IF NOT EXISTS habitat_id BIGINT;

-- 2) Create a default habitat for existing creatures
INSERT INTO habitats (biome, location, min_temp_c, max_temp_c, created_at)
VALUES ('FOREST', 'Default Habitat', 10, 25, NOW());

-- 3) Assign default habitat to the creatures that currently have none
UPDATE creatures
SET habitat_id = (SELECT id FROM habitats WHERE location = 'Default Habitat' ORDER BY id LIMIT 1)
WHERE habitat_id IS NULL;

-- 4) Now make habitat_id not null
ALTER TABLE creatures
ALTER COLUMN habitat_id SET NOT NULL;

-- 5) if the foreign key constraint doesn't exists already, add one
DO $$
BEGIN
IF NOT EXISTS (
   -- query the postgreSQL constraints table for this constraint
   SELECT 1
   FROM pg_constraint
   WHERE conname = 'fk_creatures_habitats'
   -- if the table returns false, create the constraint
) THEN
   ALTER TABLE creatures
   ADD CONSTRAINT fk_creatures_habitat
        FOREIGN KEY (habitat_id) REFERENCES habitats(id);
END IF;
END $$;


