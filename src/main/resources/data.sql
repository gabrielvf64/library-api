INSERT INTO books (title, author, publisher, isbn, status)
VALUES
    ('Clean Code', 'Robert Martin', 'Prentice Hall', '9780132350884', 'AVAILABLE'), -- ID 1
    ('Effective Java', 'Joshua Bloch', 'Addison-Wesley', '9780134685991', 'AVAILABLE'), -- ID 2
    ('Design Patterns', 'Erich Gamma', 'Addison-Wesley', '9780201633610', 'BORROWED'), -- ID 3
    ('Refactoring', 'Martin Fowler', 'Addison-Wesley', '9780201485677', 'AVAILABLE'), -- ID 4
    ('The Pragmatic Programmer', 'Andrew Hunt', 'Addison-Wesley', '9780201616224', 'AVAILABLE'), -- ID 5
    ('Head First Design Patterns', 'Eric Freeman', 'O’Reilly', '9780596007126', 'BORROWED'), -- ID 6
    ('Java Concurrency in Practice', 'Brian Goetz', 'Addison-Wesley', '9780321349606', 'AVAILABLE'), -- ID 7
    ('Spring in Action', 'Craig Walls', 'Manning', '9781617294945', 'AVAILABLE'); -- ID 8

INSERT INTO users (id, cpf, name, city, street, number, complement, zip_code)
VALUES
    (1, '12345678901', 'John Doe', 'New York', '123 Main St', '101', 'Apt 1A', '10001'), -- ID 1
    (2, '98765432109', 'Jane Smith', 'Los Angeles', '456 Elm St', '202', 'Suite 5B', '90001'), -- ID 2
    (3, '56789012345', 'Michael Johnson', 'Chicago', '789 Oak St', '303', 'Bloco C', '60601'), -- ID 2
    (4, '67890123456', 'Emily Davis', 'Houston', '321 Maple St', '404', 'Casa 2', '77001'), -- ID 4
    (5, '78901234567', 'William Brown', 'Phoenix', '654 Pine St', '505', 'Apartamento 3', '85001'), -- ID 5
    (6, '89012345678', 'Sophia Wilson', 'Philadelphia', '987 Birch St', '606', 'Andar 4', '19101'), -- ID 6
    (7, '90123456789', 'James Anderson', 'San Antonio', '741 Cedar St', '707', 'Bloco A', '78201'), -- ID 7
    (8, '01234567890', 'Olivia Martinez', 'San Diego', '258 Spruce St', '808', 'Loja 12', '92101'), -- ID 8
    (9, '12309876543', 'Daniel Thomas', 'Dallas', '369 Redwood St', '909', 'Sala 301', '75201'), -- ID 9
    (10, '23456789012', 'Isabella White', 'San Francisco', '159 Walnut St', '1010', 'Cobertura', '94101'); -- ID 10

INSERT INTO loans (id, user_id, loan_date, expected_return_date, return_date, status)
VALUES
    (1, 1, CURRENT_DATE, DATEADD('DAY', 3, CURRENT_DATE), NULL, 0), -- John Doe pegou emprestado 2 livros
    (2, 2, CURRENT_DATE, DATEADD('DAY', 3, CURRENT_DATE), NULL, 0), -- Jane Smith pegou emprestado 2 livros
    (3, 1, '2025-02-03', '2025-02-06', '2025-02-06', 2), -- John Doe pegou emprestado 2 livros
    (4, 2, '2025-02-04', '2025-02-07', '2025-02-07', 2), -- Jane Smith pegou emprestado 2 livros
    (5, 1, '2025-02-10', '2025-02-13', NULL, 1); -- John Doe pegou emprestado 2 livros

CREATE TABLE loan_books (
    loan_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    PRIMARY KEY (loan_id, book_id),
    FOREIGN KEY (loan_id) REFERENCES loans(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

INSERT INTO loan_books (loan_id, book_id)
VALUES
    (1, 1), (1, 2), -- John Doe pegou "Clean Code" e "Effective Java"
    (2, 3), (2, 4), -- Jane Smith pegou "Design Patterns" e "Refactoring"
    (3, 5), (3, 6), -- John Doe pegou "The Pragmatic Programmer" e "Head First Design Patterns"
    (4, 7), (4, 8), -- Jane Smith pegou "Java Concurrency in Practice" e "Spring in Action"
    (5, 2), (5, 3); -- John Doe pegou "Effective Java" e "Design Patterns"

