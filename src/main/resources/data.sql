INSERT INTO books (title, author, publisher, isbn, status)
VALUES
('Clean Code', 'Robert Martin', 'Prentice Hall', '9780132350884', 'AVAILABLE'),
('Effective Java', 'Joshua Bloch', 'Addison-Wesley', '9780134685991', 'AVAILABLE'),
('Design Patterns', 'Erich Gamma', 'Addison-Wesley', '9780201633610', 'BORROWED');

INSERT INTO users (cpf, name)
VALUES
('12345678901', 'John Doe'),
('98765432109', 'Jane Doe');

INSERT INTO LOANS (USER_ID, BOOKS_IDS, LOAN_DATE, EXPECTED_RETURN_DATE, RETURN_DATE, STATUS)
VALUES
    (1, ARRAY[2,7], '2025-02-15', '2025-02-22', NULL, 0),
    (2, ARRAY[3,6], '2025-02-15', '2025-02-22', NULL, 1),
    (3, ARRAY[4,5], '2025-02-15', '2025-02-22', NULL, 2);





