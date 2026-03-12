-- Generate seed data for the habitat table: 10 rows
INSERT INTO habitats (biome, location, min_temp_c, max_temp_c, created_at)
VALUES
    ('FOREST', 'Sector A — Moss Caverns', 8, 18, NOW()),
    ('FOREST', 'Sector B — Bioluminescent Grove', 12, 22, NOW()),
    ('DESERT', 'Sector C — Crystal Dunes', 35, 50, NOW()),
    ('DESERT', 'Sector D — Neon Badlands', 28, 45, NOW()),
    ('OCEAN', 'Sector E — Deep Trench Observatory', 2, 8, NOW()),
    ('OCEAN', 'Sector F — Coral Reef Sanctuary', 18, 26, NOW()),
    ('AIR', 'Sector G — Floating Gardens', -5, 15, NOW()),
    ('AIR', 'Sector H — Storm Perch', -10, 5, NOW()),
    ('0-GRAVITY', 'Sector I — Starlight Chamber', -270, -200, NOW()),
    ('0-GRAVITY', 'Sector J — Void Nexus', -260, -180, NOW());

-- Generate seed data for the creatures table: 25 rows
INSERT INTO creatures (name, species, danger_level, condition, notes, habitat_id, created_at)
VALUES
    ('Nyx', 'Void Fox', 'HIGH', 'QUARANTINED', 'Avoid bright light exposure', 1, NOW()),
    ('Lumina', 'Glow Moth', 'LOW', 'STABLE', 'Feeds on phosphorescent moss', 1, NOW()),
    ('Echo', 'Crystal Bat', 'MEDIUM', 'STABLE', 'Echolocation disrupts electronics', 2, NOW()),
    ('Shimmer', 'Prism Deer', 'LOW', 'STABLE', 'Antlers refract light into rainbows', 2, NOW()),
    ('Scorch', 'Plasma Serpent', 'HIGH', 'CRITICAL', 'Body temperature exceeds 200°C', 3, NOW()),
    ('Dune', 'Sand Wraith', 'MEDIUM', 'STABLE', 'Camouflages perfectly in desert environments', 3, NOW()),
    ('Blaze', 'Solar Lizard', 'MEDIUM', 'QUARANTINED', 'Absorbs UV radiation aggressively', 4, NOW()),
    ('Mirage', 'Heat Phantom', 'LOW', 'STABLE', 'Creates holographic illusions when threatened', 4, NOW()),
    ('Abyssal', 'Trench Leviathan', 'HIGH', 'STABLE', 'Requires extreme pressure containment', 5, NOW()),
    ('Glimmer', 'Jellyfish Sprite', 'LOW', 'STABLE', 'Emits calming bioluminescent pulses', 5, NOW()),
    ('Coral', 'Reef Dragon', 'MEDIUM', 'STABLE', 'Symbiotic relationship with algae colonies', 6, NOW()),
    ('Tide', 'Wave Serpent', 'MEDIUM', 'QUARANTINED', 'Generates localized water currents', 6, NOW()),
    ('Zephyr', 'Cloud Panther', 'MEDIUM', 'STABLE', 'Can walk on air currents', 7, NOW()),
    ('Breeze', 'Sky Manta', 'LOW', 'STABLE', 'Glides effortlessly through upper atmosphere', 7, NOW()),
    ('Tempest', 'Lightning Falcon', 'HIGH', 'QUARANTINED', 'Discharges electricity when stressed', 8, NOW()),
    ('Gale', 'Storm Hound', 'MEDIUM', 'CRITICAL', 'Generates localized weather anomalies', 8, NOW()),
    ('Nebula', 'Star Wisp', 'LOW', 'STABLE', 'Feeds on cosmic radiation', 9, NOW()),
    ('Void', 'Zero-G Leviathan', 'HIGH', 'STABLE', 'Cannot survive in gravity environments', 9, NOW()),
    ('Nova', 'Starlight Moth', 'LOW', 'STABLE', 'Wings shimmer with stellar patterns', 10, NOW()),
    ('Cosmo', 'Gravity Cat', 'MEDIUM', 'QUARANTINED', 'Manipulates local gravitational fields', 10, NOW()),
    ('Flicker', 'Neon Sprite', 'LOW', 'STABLE', 'Phases between dimensions when frightened', 1, NOW()),
    ('Radiance', 'Prism Wolf', 'MEDIUM', 'STABLE', 'Fur refracts light into laser beams', 2, NOW()),
    ('Ember', 'Ash Phoenix', 'HIGH', 'CRITICAL', 'Spontaneous combustion risk — fireproof habitat required', 4, NOW()),
    ('Whisper', 'Echo Wraith', 'MEDIUM', 'QUARANTINED', 'Communicates through sound wave manipulation', 7, NOW()),
    ('Infinity', 'Void Whale', 'HIGH', 'STABLE', 'Largest specimen in containment — handle with extreme care', 10, NOW());

