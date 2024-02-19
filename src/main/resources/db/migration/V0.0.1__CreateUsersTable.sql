CREATE TABLE users(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(64) NOT NULL,
    login       VARCHAR(32) UNIQUE NOT NULL,
    password    VARCHAR(256) NOT NULL
)