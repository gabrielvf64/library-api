INSERT INTO
    authors (name)
VALUES
    ('Robert Martin'),
    ('Joshua Bloch'),
    ('Erich Gamma'),
    ('Richard Helm'),
    ('Ralph Johnson'),
    ('John Vlissides');

INSERT INTO
    books (title, publisher, isbn, status)
VALUES
    ('Clean Code', 'Prentice Hall', '9780132350884', 'BORROWED'),
    ('Effective Java', 'Addison-Wesley', '9780134685991', 'AVAILABLE'),
    ('Design Patterns', 'Addison-Wesley', '9780201633610', 'BORROWED'),
    ('Clean Architecture', 'Prentice Hall', '9780134494166', 'BORROWED');

INSERT INTO
    book_author (book_id, author_id)
VALUES
    (1, 1), -- Clean Code - Robert Martin
    (2, 2), -- Effective Java - Joshua Bloch
    (3, 3), -- Design Patterns - Erich Gamma
    (3, 4), -- Design Patterns - Richard Helm
    (3, 5), -- Design Patterns - Ralph Johnson
    (3, 6), -- Design Patterns - John Vlissides
    (4, 1); -- Clean Architecture - Robert Martin

INSERT INTO
    users (username)
VALUES
    ('batman'),
    ('spiderman');

INSERT INTO
    customers (cpf, name, city, street, address_number, complement, zip_code)
VALUES
    ('118.043.180-43', 'Bruce Wayne', 'Gotham City', 'Wayne Manor', '1007', 'Batcave', '12345678'),
    ('385.424.840-70', 'Peter Parker', 'New York', 'Queens', '78', 'Apt 4A', '87654321'),
    ('123.456.789-00', 'Diana Prince', 'Themyscira', 'Amazon Island', '1', 'Apt 1', '65432178');

INSERT INTO
    LOANS (CUSTOMER_ID, LOAN_DATE, EXPECTED_RETURN_DATE, RETURN_DATE, STATUS)
VALUES
    (1, CURRENT_DATE, DATEADD(DAY, 3, CURRENT_DATE), NULL, 'ACTIVE'),
    (2, '2025-02-04', '2025-02-07', '2025-02-07', 'FINISHED'),
    (3, '2025-02-10', '2025-02-13', NULL, 'OVERDUE');

INSERT INTO
    BOOK_LOAN (BOOK_ID, LOAN_ID)
VALUES
    (1, 1), -- Clean Code - Bruce Wayne
    (4, 1), -- Clean Architecture - Bruce Wayne
    (2, 2), -- Effective Java - Peter Parker
    (3, 3); -- Design Patterns - Diana Prince




