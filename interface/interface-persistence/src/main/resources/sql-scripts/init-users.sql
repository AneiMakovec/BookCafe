CREATE TABLE accounts (
    userId SERIAL PRIMARY KEY,
    username VARCHAR (50) UNIQUE NOT NULL,
    password VARCHAR (64) NOT NULL,
    isAdmin BOOLEAN NOT NULL
);

CREATE TABLE favourites (
    favouriteId SERIAL PRIMARY KEY,
    userId INTEGER NOT NULL,
    bookId INTEGER NOT NULL
);

INSERT INTO accounts(username, password, isAdmin) VALUES ('admin', 'admin', TRUE);
INSERT INTO accounts(username, password, isAdmin) VALUES ('user1', 'password', FALSE);
INSERT INTO accounts(username, password, isAdmin) VALUES ('user2', 'password', FALSE);
INSERT INTO accounts(username, password, isAdmin) VALUES ('user3', 'password', FALSE);
INSERT INTO accounts(username, password, isAdmin) VALUES ('user4', 'password', FALSE);

INSERT INTO favourites(userId, bookId) VALUES (2, 1);
INSERT INTO favourites(userId, bookId) VALUES (3, 2);
INSERT INTO favourites(userId, bookId) VALUES (3, 3);
