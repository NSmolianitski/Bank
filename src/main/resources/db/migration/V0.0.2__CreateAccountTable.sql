CREATE TABLE accounts(
    id          SERIAL PRIMARY KEY,
    money       NUMERIC(100, 2) NOT NULL,
    currency    VARCHAR(10) NOT NULL 
)