DROP TABLE IF EXISTS recipe;

CREATE TABLE recipe
(
    id          SERIAL PRIMARY KEY,
    name        TEXT         NOT NULL UNIQUE,
    ingredients TEXT         NOT NULL,
    steps       TEXT         NOT NULL,
    type        VARCHAR(100) NOT NULL,
    picture     TEXT,
    created_at  TIMESTAMP    NOT NULL
);
