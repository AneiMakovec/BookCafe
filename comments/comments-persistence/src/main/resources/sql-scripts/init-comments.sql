CREATE TABLE comments (
    comment_id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    created_timestamp TIMESTAMP NOT NULL,
    book_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL
);

INSERT INTO comments(comment_id, content, created_timestamp, book_id, user_id) VALUES (1, 'Verry nice.', CURRENT_TIME, 1, 1);
INSERT INTO comments(comment_id, content, created_timestamp, book_id, user_id) VALUES (2, 'I didnt like it.', CURRENT_TIME, 1, 2);
INSERT INTO comments(comment_id, content, created_timestamp, book_id, user_id) VALUES (3, 'Really good.', CURRENT_TIME, 2, 2);
INSERT INTO comments(comment_id, content, created_timestamp, book_id, user_id) VALUES (4, 'Not bad, but could be better.', CURRENT_TIME, 2, 1);