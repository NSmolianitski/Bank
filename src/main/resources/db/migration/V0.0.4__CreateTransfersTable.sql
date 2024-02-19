CREATE TABLE transfers(
    id              SERIAL PRIMARY KEY,
    operation_id    UUID,
    from_account_id INTEGER,
    to_account_id   INTEGER,
    money           NUMERIC(100, 2) NOT NULL,
    currency        VARCHAR(10) NOT NULL,
    operation_type  VARCHAR(20) NOT NULL
)