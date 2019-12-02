CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR (50) NOT NULL,
    password VARCHAR (64) NOT NULL,
);

INSERT INTO users(user_id, username, password) VALUES (1, 'User1', 'password');
INSERT INTO users(user_id, username, password) VALUES (2, 'User2', 'password');
INSERT INTO users(user_id, username, password) VALUES (3, 'User3', 'password');
INSERT INTO users(user_id, username, password) VALUES (4, 'User4', 'password');