CREATE TABLE users (
                     id SERIAL PRIMARY KEY,
                     username varchar(100) NOT NULL UNIQUE,
                     email varchar(255) NOT NULL UNIQUE,
                     password varchar(255) NOT NULL,
                     created timestamp NOT NULL default CURRENT_TIMESTAMP,
                     updated timestamp NOT NULL default CURRENT_TIMESTAMP,
                     status varchar(25) NOT NULL default 'ACTIVE'
);

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name varchar(100) NOT NULL UNIQUE,
                       created timestamp NOT NULL default CURRENT_TIMESTAMP,
                       updated timestamp NOT NULL default CURRENT_TIMESTAMP,
                       status varchar(25) NOT NULL default 'ACTIVE'
);

CREATE TABLE user_roles (
                        user_id BIGINT REFERENCES users(id),
                        role_id BIGINT REFERENCES roles(id)
);

INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER'),(2, 'ROLE_ADMIN');

INSERT INTO users (id, username, email, password)
VALUES (1, 'admin', 'admin@admin.ru', '$2y$08$NLiO1jb2zqnVgT4apha3oeVfP0F84CcgaGikH80C7CPdzAuQ3LgcO');

