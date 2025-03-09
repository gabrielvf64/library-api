 
INSERT INTO books (title, author, publisher, isbn, status)
VALUES
('Clean Code', 'Robert Martin', 'Prentice Hall', '9780132350884', 'AVAILABLE'),
('Effective Java', 'Joshua Bloch', 'Addison-Wesley', '9780134685991', 'AVAILABLE'),
('Design Patterns', 'Erich Gamma', 'Addison-Wesley', '9780201633610', 'BORROWED');
INSERT INTO users (cpf, name)
VALUES
('12345678901', 'John Doe'),
('98765432109', 'Jane Doe');
INSERT INTO LOANS (USER_ID, BOOKS_IDS, LOAN_DATE, EXPECTED_RETURN_DATE, RETURN_DATE,
STATUS)
VALUES
    (1, ARRAY[2,7], CURRENT_DATE, DATEADD(DAY, 3, CURRENT_DATE), NULL, 0),
    (2, ARRAY[3,6], CURRENT_DATE, DATEADD(DAY, 3, CURRENT_DATE), NULL, 0),
    (3, ARRAY[4,5], '2025-02-03', '2025-02-06', '2025-02-06', 2),
    (4, ARRAY[10,7], '2025-02-04', '2025-02-07', '2025-02-07', 2),
    (5, ARRAY[30,15], '2025-02-10', '2025-02-13', NULL, 1),
    (6, ARRAY[22,20], '2025-02-10', '2025-02-13', NULL, 1),
    (7, ARRAY[14,11], '2025-02-11', '2025-02-14', NULL, 1);