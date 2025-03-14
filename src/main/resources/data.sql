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
    users (username, password, role, cpf, name)
VALUES
('batman', '$2a$10$Nh1M35SC9QX5Qn6Cm9pK3.LYAkRLI03qZI1w3l/YRFxVy.4x1V/oe', 'ADMIN', '60236589032', 'Bruce Wayne'), --password: 123

('spiderman', '$2a$10$n5VxUOHCpcv7f8CJwdrdAOP72zfEqbWzBB4CTP1xqbz7w3CUqfvW2', 'CLIENT', '62006878034', 'Peter Parker'), --password: 789

('superman', '$2a$10$6HhxiIDYziOtChtHBaiF7uc0HKtfkyaddknkd9KGh8LuzWEndE7OS', 'CLIENT', '44787350005', 'Clark Kent'); --password: 111

INSERT INTO
    customers (user_id, cpf, name, city, street, address_number, complement, zip_code)
VALUES
    (1, '118.043.180-43', 'Bruce Wayne', 'Gotham City', 'Wayne Manor', '1007', 'Batcave', '12345678'),
    (2, '385.424.840-70', 'Peter Parker', 'New York', 'Queens', '78', 'Apt 4A', '87654321'),
    (3, '987.654.321-00', 'Clark Kent', 'Smallville', 'Kansas', '1', 'Apt 1', '12345678');

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




