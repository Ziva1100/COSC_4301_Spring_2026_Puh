-- Correct the 0-gravity constraint because it cannot be used by enum object in front-end client

ALTER TABLE habitats DROP CONSTRAINT chk_habitats_biome;

UPDATE habitats
SET biome  = 'OGRAVITY'
WHERE biome = '0-GRAVITY';

ALTER TABLE habitats ADD CONSTRAINT chk_habitats_biome
CHECK (biome IN ('FOREST', 'DESERT', 'OCEAN', 'AIR', 'OGRAVITY'));

-- Add the constraint to column removed to ensure it's either
-- 0 not removed or 1 removed
ALTER TABLE creatures
ADD CONSTRAINT chk_creatures_removed
CHECK (removed IN (0,1));
