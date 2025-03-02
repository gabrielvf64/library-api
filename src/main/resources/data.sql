INSERT INTO books (title, author, publisher, isbn, status)
VALUES
('Clean Code', 'Robert Martin', 'Prentice Hall', '9780132350884', 'AVAILABLE'),
('Effective Java', 'Joshua Bloch', 'Addison-Wesley', '9780134685991', 'AVAILABLE'),
('Design Patterns', 'Erich Gamma', 'Addison-Wesley', '9780201633610', 'BORROWED');

INSERT INTO users (username, password, role)
VALUES
('batman', '$2a$10$Nh1M35SC9QX5Qn6Cm9pK3.LYAkRLI03qZI1w3l/YRFxVy.4x1V/oe', 'ADMIN'), --password: 123
('vader', '$2a$10$ToXihRsZaAIj/MAwsB5pHeRqdPuBW6e9H4SUI1F4nOEG2IT08FLV6', 'ADMIN'), --password: 456
('spiderman', '$2a$10$n5VxUOHCpcv7f8CJwdrdAOP72zfEqbWzBB4CTP1xqbz7w3CUqfvW2', 'CLIENT'), --password: 789
('superman', '$2a$10$6HhxiIDYziOtChtHBaiF7uc0HKtfkyaddknkd9KGh8LuzWEndE7OS', 'CLIENT'); --password: 111

INSERT INTO customers (user_id, cpf, name)
VALUES
(1, '60236589032', 'Bruce Wayne'),
(2, '24322949045', 'Anakin Skywalker'),
(3, '62006878034', 'Peter Parker'),
(4, '44787350005', 'Clark Kent');

INSERT INTO loans (user_id, books_ids, loan_date, expected_return_date, return_date, status)
VALUES
    (1, ARRAY[2,7], CURRENT_DATE, DATEADD(DAY, 3, CURRENT_DATE), NULL, 0),
    (2, ARRAY[3,6], CURRENT_DATE, DATEADD(DAY, 3, CURRENT_DATE), NULL, 0),
    (3, ARRAY[4,5], '2025-02-03', '2025-02-06', '2025-02-06', 2),
    (4, ARRAY[10,7], '2025-02-04', '2025-02-07', '2025-02-07', 2),
    (5, ARRAY[30,15], '2025-02-10', '2025-02-13', NULL, 1),
    (6, ARRAY[22,20], '2025-02-10', '2025-02-13', NULL, 1),
    (7, ARRAY[14,11], '2025-02-11', '2025-02-14', NULL, 1);





