-- Inserção de Clientes
INSERT INTO cliente (nome, cpf, data_nascimento, status_bloqueio, limite_credito) VALUES
('João Silva', '12345678901', '1990-01-01', 'A', 1500.00),
('Maria Souza', '98765432100', '1985-05-20', 'A', 2000.00),
('Carlos Lima', '11223344556', '1978-12-15', 'B', 0.00),
('Ana Costa', '99887766554', '1992-07-08', 'A', 1000.00);

-- Inserção de Faturas
INSERT INTO fatura (cliente_id, data_vencimento, data_pagamento, valor, status) VALUES
(1, CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE - INTERVAL '1 days', 500.00, 'P'),
(2, CURRENT_DATE - INTERVAL '10 days', NULL, 750.00, 'A'),
(3, CURRENT_DATE - INTERVAL '15 days', NULL, 300.00, 'A'),
(4, CURRENT_DATE + INTERVAL '10 days', NULL, 200.00, 'B');
