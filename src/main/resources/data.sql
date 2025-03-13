INSERT INTO
    authors (name)
VALUES
    ('Robert Martin'),
    ('Joshua Bloch'),
    ('Erich Gamma'),
    ('Richard Helm'),
    ('Ralph Johnson'),
    ('John Vlissides'),
    ('Martin Fowler'),
    ('Kent Beck'),
    ('Brian Goetz'),
    ('Andrew Hunt'),
    ('David Thomas');

INSERT INTO
    books (title, publisher, isbn, status)
VALUES
    ('Clean Code', 'Prentice Hall', '9780132350884', 'BORROWED'),
    ('Effective Java', 'Addison-Wesley', '9780134685991', 'AVAILABLE'),
    ('Design Patterns', 'Addison-Wesley', '9780201633610', 'BORROWED'),
    ('Clean Architecture', 'Prentice Hall', '9780134494166', 'BORROWED'),
    ('Refactoring', 'Addison-Wesley', '9780201485677', 'AVAILABLE'),
    ('Test Driven Development', 'Addison-Wesley', '9780321146533', 'AVAILABLE'),
    ('Java Concurrency in Practice', 'Addison-Wesley', '9780321349606', 'AVAILABLE'),
    ('The Pragmatic Programmer', 'Addison-Wesley', '9780201616224', 'AVAILABLE'),
    ('Agile Software Development', 'Prentice Hall', '9780135974440', 'AVAILABLE'),
    ('Patterns of Enterprise Application Architecture', 'Addison-Wesley', '9780321127426', 'AVAILABLE'),
    ('Extreme Programming Explained', 'Addison-Wesley', '9780321278654', 'AVAILABLE'),
    ('Domain-Driven Design', 'Addison-Wesley', '9780321125217', 'AVAILABLE'),
    ('Continuous Delivery', 'Addison-Wesley', '9780321601919', 'AVAILABLE');


INSERT INTO
    book_author (book_id, author_id)
VALUES
    (1, 1), -- Clean Code - Robert Martin
    (2, 2), -- Effective Java - Joshua Bloch
    (3, 3), -- Design Patterns - Erich Gamma
    (3, 4), -- Design Patterns - Richard Helm
    (3, 5), -- Design Patterns - Ralph Johnson
    (3, 6), -- Design Patterns - John Vlissides
    (4, 1), -- Clean Architecture - Robert Martin
    (5, 7), -- Refactoring - Martin Fowler
    (6, 8), -- Test Driven Development - Kent Beck
    (7, 9), -- Java Concurrency in Practice - Brian Goetz
    (8, 10), -- The Pragmatic Programmer - Andrew Hunt
    (8, 11), -- The Pragmatic Programmer - David Thomas
    (9, 1), -- Agile Software Development - Robert Martin
    (10, 7), -- Patterns of Enterprise Application Architecture - Martin Fowler
    (11, 8), -- Extreme Programming Explained - Kent Beck
    (12, 7), -- Domain-Driven Design - Martin Fowler
    (13, 8); -- Continuous Delivery - Kent Beck

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




