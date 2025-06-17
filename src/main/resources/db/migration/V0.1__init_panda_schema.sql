create schema if not exists panda;

SET search_path TO panda;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS accouts;
DROP TABLE IF EXISTS mails;

CREATE TABLE IF NOT EXISTS users
(
    id      SERIAL PRIMARY KEY,
    user_id VARCHAR(100) not null unique
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
    mail        integer     NOT NULL,
    type        VARCHAR(10) NOT NULL DEFAULT 'TEMP'
);

ALTER TABLE accounts
    ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE accounts
    ADD FOREIGN KEY (mail) REFERENCES mails (id);


