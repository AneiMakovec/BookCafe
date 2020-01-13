CREATE TABLE comments (
    commentId SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    createdTimestamp TIMESTAMP NOT NULL,
    bookId INTEGER NOT NULL,
    userId INTEGER NOT NULL
);

INSERT INTO comments(content, createdTimestamp, bookId, userId) VALUES ('Verry nice.', CURRENT_TIME, 1, 1);
INSERT INTO comments(content, createdTimestamp, bookId, userId) VALUES ('I didnt like it.', CURRENT_TIME, 1, 2);
INSERT INTO comments(content, createdTimestamp, bookId, userId) VALUES ('Really good.', CURRENT_TIME, 2, 2);
INSERT INTO comments(content, createdTimestamp, bookId, userId) VALUES ('Not bad, but could be better.', CURRENT_TIME, 2, 1);
