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

CREATE TABLE IF NOT EXISTS mails
(
    id   SERIAL PRIMARY KEY,
    mail VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS accounts
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER     NOT NULL,
    name        VARCHAR(90) NOT NULL,
    account     VARCHAR(50),
    password    VARCHAR(30) NOT NULL,
    link        text,
    description text,
    mail        INTEGER     NOT NULL,
    type        VARCHAR(10) NOT NULL,
    updated     timestamp   NOT NULL DEFAULT current_timestamp
);

ALTER TABLE accounts
    ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE accounts
    ADD FOREIGN KEY (mail) REFERENCES mails (id);


