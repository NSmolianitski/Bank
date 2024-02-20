ALTER TABLE transfers RENAME TO old_transfers;

CREATE TABLE transfers (
    id              SERIAL PRIMARY KEY,
    date            TIMESTAMP,
    operation_id    UUID,
    from_account_id INTEGER,
    to_account_id   INTEGER,
    money           NUMERIC(100, 2) NOT NULL,
    currency        VARCHAR(10) NOT NULL,
    operation_type  VARCHAR(20) NOT NULL
);

INSERT INTO transfers (operation_id, from_account_id, to_account_id, money, currency, operation_type)
SELECT operation_id, from_account_id, to_account_id, money, currency, operation_type FROM old_transfers;