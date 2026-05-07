-- add biome constraint
ALTER TABLE habitats
ADD CONSTRAINT chk_habitats_biome
CHECK (biome IN ('FOREST', 'DESERT', 'OCEAN', 'AIR', '0-GRAVITY')) ;

-- add danger constraint
ALTER TABLE creatures
ADD CONSTRAINT chk_habitat_danger_level
CHECK (danger_level IN ('LOW', 'MEDIUM', 'HIGH')) ;

-- add condition constraint
ALTER TABLE creatures
ADD CONSTRAINT chk_habitat_condition
CHECK ( condition IN ('STABLE', 'QUARANTINED', 'CRITICAL'));