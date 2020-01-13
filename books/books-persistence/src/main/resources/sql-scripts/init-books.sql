CREATE TABLE books (
    bookId SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    description TEXT,
    numPages INTEGER NOT NULL,
    publisher TEXT NOT NULL,
    publishDate TIMESTAMP NOT NULL
);

INSERT INTO books(title, author, description, numPages, publisher, publishDate) VALUES ('How to cook dinner for two.', 'Isaac Bentley', 'A verry good cooking book.', 200, 'Jonhs publishing', CURRENT_TIMESTAMP);
INSERT INTO books(title, author, description, numPages, publisher, publishDate) VALUES ('Summer breeze', 'Helen Lovejoy', 'A breathtaking summer romance.', 350, 'Jonhs publishing', CURRENT_TIMESTAMP);
INSERT INTO books(title, author, description, numPages, publisher, publishDate) VALUES ('Build your own barbeque grill.', 'Bro Bronson', 'A step-by-step guide to build your own barbeque grill.', 120, 'Jonhs publishing', CURRENT_TIMESTAMP);
INSERT INTO books(title, author, description, numPages, publisher, publishDate) VALUES ('To the edges of the universe.', 'John Jensen', 'Intense Sci-fi adventure.', 450, 'Jonhs publishing', CURRENT_TIMESTAMP);
