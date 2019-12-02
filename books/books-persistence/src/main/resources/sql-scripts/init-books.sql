CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    description TEXT,
    num_pages INTEGER NOT NULL,
    publisher TEXT NOT NULL,
    publish_date TIMESTAMP NOT NULL
);

INSERT INTO books(book_id, title, author, description, num_pages, publisher, publish_date) VALUES (1, 'How to cook dinner for two.', 'Isaac Bentley', 'A verry good cooking book.', 200, 'Jonhs publishing', CURRENT_TIMESTAMP);
INSERT INTO books(book_id, title, author, description, num_pages, publisher, publish_date) VALUES (2, 'Summer breeze', 'Helen Lovejoy', 'A breathtaking summer romance.', 350, 'Jonhs publishing', CURRENT_TIMESTAMP);
INSERT INTO books(book_id, title, author, description, num_pages, publisher, publish_date) VALUES (3, 'Build your own barbeque grill.', 'Bro Bronson', 'A step-by-step guide to build your own barbeque grill.', 120, 'Jonhs publishing', CURRENT_TIMESTAMP);
INSERT INTO books(book_id, title, author, description, num_pages, publisher, publish_date) VALUES (4, 'To the edges of the universe.', 'John Jensen', 'Intense Sci-fi adventure.', 450, 'Jonhs publishing', CURRENT_TIMESTAMP);