INSERT INTO deposit_types (name, interest_rate, min_amount, term_months)
VALUES
    ('Сберегательный', 6.50, 1000, 12),
    ('Накопительный', 7.00, 5000, 6),
    ('Срочный', 8.50, 10000, 24)
    ON CONFLICT DO NOTHING;