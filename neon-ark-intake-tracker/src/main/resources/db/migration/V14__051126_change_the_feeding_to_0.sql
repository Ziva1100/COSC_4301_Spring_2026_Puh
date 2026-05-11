-- This file will alter the feeding table by making some feeding inactive
-- this will help with removing the creature.
-- the creature cannot be removed with an active feeding schedule

UPDATE feedings
SET active = 0
WHERE creature_id = 15;

