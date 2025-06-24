create schema if not exists panda;

SET search_path TO panda;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS accouts;
DROP TABLE IF EXISTS mails;

CREATE TABLE IF NOT EXISTS users
(
    id   SERIAL PRIMARY KEY,
    uuid uuid not null unique
);

CREATE TABLE IF NOT EXISTS emails
(
    id    SERIAL PRIMARY KEY,
    email VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS types
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS accounts
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER     NOT NULL,
    email_id    INTEGER     NOT NULL,
    type_id     INTEGER     NOT NULL,
    account     VARCHAR(50),
    name        VARCHAR(90) NOT NULL,
    password    VARCHAR(30) NOT NULL,
    link        text,
    description text,
    updated     timestamp   NOT NULL DEFAULT current_timestamp
);

ALTER TABLE accounts
    ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE accounts
    ADD FOREIGN KEY (email_id) REFERENCES emails (id);
ALTER TABLE accounts
    ADD FOREIGN KEY (type_id) REFERENCES types (id);


