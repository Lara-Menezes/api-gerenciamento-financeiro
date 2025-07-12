-- Criação da tabela Cliente

CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    status_bloqueio CHAR(1) NOT NULL CHECK (status_bloqueio IN ('A', 'B')),
    limite_credito DECIMAL(10,2) NOT NULL
);

-- Criação da tabela Fatura
CREATE TABLE fatura (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor DECIMAL(10,2) NOT NULL,
    status CHAR(1) NOT NULL CHECK (status IN ('P', 'A', 'B')),
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);
