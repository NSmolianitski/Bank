ALTER TABLE accounts
ADD COLUMN owner_id INTEGER NOT NULL
CONSTRAINT users_accounts_pk_owner_id REFERENCES users(id)
ON UPDATE CASCADE ON DELETE CASCADE DEFAULT 1