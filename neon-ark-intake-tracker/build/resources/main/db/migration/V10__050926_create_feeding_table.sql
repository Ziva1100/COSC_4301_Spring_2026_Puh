-- This table will show the feeding times, quantities, and types of
-- food for creatures
-- Capstone -- [ Find Creatures by feeding time ] Menu Choice

CREATE TABLE feedings (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    creature_id BIGINT NOT NULL,
    food VARCHAR NOT NULL,
    quantity VARCHAR NOT NULL,
    time TIME NOT NULL,

    CONSTRAINT feedings_id_pk PRIMARY KEY (id),
    CONSTRAINT feedings_creatures_fk FOREIGN KEY (creature_id)
                      REFERENCES creatures (id)
)