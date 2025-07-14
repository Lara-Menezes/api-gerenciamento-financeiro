-- Inserção de Clientes
INSERT INTO cliente (nome, cpf, data_nascimento, status_bloqueio, limite_credito) VALUES
('João Silva', '12345678901', '1990-01-01', 'A', 1500.00),
('Maria Souza', '98765432100', '1985-05-20', 'A', 2000.00),
('Carlos Lima', '11223344556', '1978-12-15', 'B', 0.00),
('Ana Costa', '99887766554', '1992-07-08', 'A', 1000.00),
('Paulo Fernandes', '55667788990', '1988-03-14', 'A', 1200.00),
('Beatriz Santos', '22334455667', '1995-09-30', 'A', 1800.00),
('Ricardo Alves', '33445566778', '1975-11-22', 'B', 0.00),
('Fernanda Moura', '44556677889', '1983-06-10', 'A', 1300.00),
('Lucas Pereira', '66778899001', '1991-12-05', 'A', 1600.00),
('Mariana Rocha', '77889900112', '1987-08-18', 'A', 1400.00);

-- Inserção de Faturas
INSERT INTO fatura (cliente_id, data_vencimento, data_pagamento, valor, status) VALUES
(1, CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE - INTERVAL '1 days', 500.00, 'P'),
(2, CURRENT_DATE - INTERVAL '10 days', NULL, 750.00, 'A'),
(3, CURRENT_DATE - INTERVAL '15 days', NULL, 300.00, 'A'),
(4, CURRENT_DATE + INTERVAL '10 days', NULL, 200.00, 'B'),
(5, CURRENT_DATE - INTERVAL '3 days', NULL, 650.00, 'B'),
(6, CURRENT_DATE - INTERVAL '7 days', CURRENT_DATE - INTERVAL '2 days', 700.00, 'P'),
(7, CURRENT_DATE - INTERVAL '20 days', NULL, 450.00, 'A'),
(8, CURRENT_DATE + INTERVAL '5 days', NULL, 900.00, 'B'),
(9, CURRENT_DATE - INTERVAL '8 days', NULL, 1200.00, 'A'),
(10, CURRENT_DATE + INTERVAL '15 days', NULL, 1100.00, 'B');
