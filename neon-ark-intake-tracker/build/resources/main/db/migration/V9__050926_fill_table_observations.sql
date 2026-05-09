-- add the missing primary key constraint
ALTER TABLE observations
    ADD CONSTRAINT observations_id_pk PRIMARY KEY (id);

-- Fix missing column from V8
ALTER TABLE observations ADD COLUMN IF NOT EXISTS category VARCHAR NOT NULL DEFAULT 'behavior';
ALTER TABLE observations ADD CONSTRAINT observations_category_ck
    CHECK (category IN ('medical', 'behavior', 'safety', 'accountability'));

-- fix the foreign key colun mismatch
ALTER TABLE observations DROP CONSTRAINT observations_creatures_fk;

ALTER TABLE observations ADD CONSTRAINT observations_creatures_fk
    FOREIGN KEY (creature_id) REFERENCES creatures (id);

-- fill the table observations with data
INSERT INTO observations (creature_id, date, category, observation) VALUES
                                                                        (1,  '2026-01-05', 'medical',        'Nyx showing signs of light sensitivity. Recommend blackout curtains in habitat.'),
                                                                        (1,  '2026-01-12', 'behavior',       'Nyx pacing the perimeter repeatedly during night cycle.'),
                                                                        (1,  '2026-02-01', 'safety',         'Nyx attempted to escape through ventilation shaft. Sealed and reinforced.'),
                                                                        (2,  '2026-01-06', 'behavior',       'Lumina emitting unusually bright pulses. Possibly stress-related.'),
                                                                        (2,  '2026-01-20', 'medical',        'Lumina wing membranes appear slightly torn. Applied regenerative moss treatment.'),
                                                                        (3,  '2026-01-08', 'safety',         'Echo echolocation caused nearby electronics to malfunction. Shielding upgraded.'),
                                                                        (3,  '2026-01-25', 'behavior',       'Echo responding well to low-frequency sound enrichment activities.'),
                                                                        (4,  '2026-01-10', 'behavior',       'Shimmer antlers refracting light into handler eyes. Polarized visors now required.'),
                                                                        (4,  '2026-02-14', 'accountability', 'Shimmer feeding log updated. Consuming standard herbivore rations without issue.'),
                                                                        (5,  '2026-01-03', 'medical',        'Scorch body temperature spiked to 240°C. Cooling protocols activated immediately.'),
                                                                        (5,  '2026-01-18', 'safety',         'Scorch melted containment latch. Replaced with heat-resistant titanium hardware.'),
                                                                        (5,  '2026-02-10', 'medical',        'Scorch temperature stabilized at 195°C after dietary adjustment.'),
                                                                        (6,  '2026-01-07', 'behavior',       'Dune successfully camouflaged against habitat floor for 6 hours undetected.'),
                                                                        (6,  '2026-01-22', 'accountability', 'Dune feeding confirmed. Sand wraiths require mineral supplement added to diet.'),
                                                                        (7,  '2026-01-09', 'medical',        'Blaze showing UV absorption beyond safe thresholds. UV lamps reduced by 40%.'),
                                                                        (7,  '2026-02-03', 'safety',         'Blaze quarantine extended. UV discharge risk to surrounding habitats.'),
                                                                        (8,  '2026-01-11', 'behavior',       'Mirage produced 3 simultaneous illusions during enrichment session.'),
                                                                        (8,  '2026-01-28', 'accountability', 'Mirage health check completed. All vitals within normal range.'),
                                                                        (9,  '2026-01-04', 'safety',         'Abyssal containment pressure dropped briefly. Emergency repressurization performed.'),
                                                                        (9,  '2026-02-08', 'medical',        'Abyssal showing signs of decompression stress. Pressure increased by 15 PSI.'),
                                                                        (10, '2026-01-13', 'behavior',       'Glimmer bioluminescent pulses calming neighboring creatures in adjacent habitats.'),
                                                                        (10, '2026-01-30', 'accountability', 'Glimmer feeding log updated. Consuming jellyfish nutrient solution as expected.'),
                                                                        (11, '2026-01-15', 'medical',        'Coral algae colonies on scales showing healthy symbiotic growth.'),
                                                                        (11, '2026-02-05', 'behavior',       'Coral displaying territorial behavior when Tide approached habitat boundary.'),
                                                                        (12, '2026-01-16', 'safety',         'Tide water current generation flooded adjacent corridor. Drainage upgraded.'),
                                                                        (12, '2026-02-12', 'medical',        'Tide fin laceration treated. Healing progressing well under aquatic care protocol.'),
                                                                        (13, '2026-01-17', 'behavior',       'Zephyr observed walking along ceiling air currents for extended period.'),
                                                                        (13, '2026-02-07', 'accountability', 'Zephyr enrichment session logged. Responded positively to wind tunnel activity.'),
                                                                        (14, '2026-01-19', 'behavior',       'Breeze gliding patterns suggest increased comfort with habitat dimensions.'),
                                                                        (14, '2026-02-15', 'medical',        'Breeze wing span measurement taken. Growth of 3cm noted since last check.'),
                                                                        (15, '2026-01-21', 'safety',         'Tempest discharged electricity during feeding. Handler received minor shock.'),
                                                                        (15, '2026-02-02', 'safety',         'Tempest quarantine protocols reviewed. Rubber-insulated suits now mandatory.'),
                                                                        (15, '2026-02-18', 'medical',        'Tempest electrical output reduced after stress management treatment applied.'),
                                                                        (16, '2026-01-23', 'behavior',       'Gale generating localized storm anomalies during night cycle. Monitoring increased.'),
                                                                        (16, '2026-02-06', 'medical',        'Gale showing respiratory distress. Atmospheric pressure in habitat adjusted.'),
                                                                        (17, '2026-01-24', 'accountability', 'Nebula cosmic radiation intake logged. Within acceptable absorption limits.'),
                                                                        (17, '2026-02-09', 'behavior',       'Nebula observed emitting faint starlight patterns during sleep cycle.'),
                                                                        (18, '2026-01-26', 'safety',         'Void anti-gravity field expanding beyond habitat boundaries. Containment reinforced.'),
                                                                        (18, '2026-02-11', 'medical',        'Void showing signs of gravitational disorientation. Zero-G stabilizers recalibrated.'),
                                                                        (19, '2026-01-27', 'behavior',       'Nova wing shimmer patterns intensifying. Possibly linked to seasonal behavior.'),
                                                                        (19, '2026-02-13', 'accountability', 'Nova feeding confirmed. Stellar nutrient solution consumed in full.'),
                                                                        (20, '2026-01-29', 'safety',         'Cosmo gravitational field manipulation caused loose objects to orbit habitat.'),
                                                                        (20, '2026-02-16', 'medical',        'Cosmo quarantine extended pending gravitational stability assessment.'),
                                                                        (21, '2026-01-31', 'behavior',       'Flicker phasing between dimensions 4 times during enrichment session.'),
                                                                        (21, '2026-02-17', 'accountability', 'Flicker feeding log updated. Consumed neon nutrient solution without incident.'),
                                                                        (22, '2026-02-01', 'medical',        'Radiance laser refraction intensity increasing. Eye protection protocol updated.'),
                                                                        (22, '2026-02-19', 'behavior',       'Radiance showing playful behavior with light-reflective enrichment toys.'),
                                                                        (23, '2026-02-04', 'safety',         'Ember spontaneous combustion event occurred. Fireproof habitat held successfully.'),
                                                                        (24, '2026-02-20', 'behavior',       'Whisper communicating via sound wave manipulation with Zephyr across habitats.'),
                                                                        (25, '2026-02-21', 'accountability', 'Infinity full health inspection completed. All vitals stable despite extreme size.');