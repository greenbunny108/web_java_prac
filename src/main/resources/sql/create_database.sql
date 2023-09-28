DROP TABLE IF EXISTS address CASCADE;
CREATE TABLE address (
id                  bigserial   PRIMARY KEY,
street_address      text,
city                text,
state_province      text,
country             text
);

DROP TABLE IF EXISTS person CASCADE;
CREATE TABLE person (
id            bigserial                     PRIMARY KEY,
first_name    text                          NOT NULL,
last_name     text                          NOT NULL,
gender_type   text                        NOT NULL,
birth_date    timestamp without time zone   NOT NULL,
death_date    timestamp without time zone,
description   text,
address_id    bigint REFERENCES address (id) ON DELETE CASCADE,
CHECK (death_date is NULL OR birth_date < death_date)
);

DROP TABLE IF EXISTS relation CASCADE;
CREATE TABLE relation (
id                  bigserial PRIMARY KEY,
person1_id          bigint REFERENCES person (id) ON DELETE CASCADE,
person2_id          bigint REFERENCES person (id) ON DELETE CASCADE,
relationship_type   integer NOT NULL,
start_date          timestamp without time zone   NOT NULL,
end_date            timestamp without time zone,
CHECK (end_date is NULL OR start_date < end_date),
CHECK (person1_id != person2_id)
);



