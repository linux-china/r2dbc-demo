DROP TABLE IF EXISTS account;
CREATE TABLE account (
  id         SERIAL,
  nick       VARCHAR(64),
  email      VARCHAR(128),
  phone      VARCHAR(16),
  passwd     VARCHAR(64),
  created_at timestamp,
  updated_at timestamp
);