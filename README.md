#  Fintech - Gestão de Clientes, Faturas e Pagamentos 

Sistema que simula um ecossistema de pagamentos, permitindo o gerenciamento de **clientes**, **faturas** e **pagamentos**, oferecendo uma **API RESTful** e uma **interface web**.

---

##  Funcionalidades

### Clientes:
- Listagem de todos os clientes
- Cadastro de novo cliente
- Consulta de cliente por Id, nome e CPF
- Listagem de clientes bloqueados

###  Faturas:
- Listagem de faturas de um cliente
- Registro de pagamento de fatura
- Listagem de faturas em atraso
- Criar novas faturas


##  Regras de Negócio

- Pagamentos atualizam automaticamente o status da fatura para **"Paga"**.
- Faturas com mais de **3 dias de atraso** fazem com que o cliente seja **bloqueado**.
- Clientes bloqueados têm o **limite de crédito zerado**.

---

## Tecnologias Utilizadas

### Back-End
- Java 21
- Spring Boot
- Spring Data JPA
- Banco de dados relacional (PostgreSQL)
- Swagger (documentação da API)
- JUnit (testes unitários)

### Front-End
- HTML e CSS
- JavaScript

### DevOps
- Docker
- Docker Compose

---

##  Como Executar o projeto

1. Clone este repositório com o seguinte código:
   
```bash
git clone https://github.com/Lara-Menezes/api-gerenciamento-financeiro.git
```

2. Mude as variáveis de ambiente no application.properties para seu usuário e senha do banco de dados(PostgreSQL). 

3. Navegue até a pasta do projeto clonado:
   
```bash
cd seu-repositorio
```

3. Suba os containers com Docker Compose:
```bash
docker-compose up --build
```


4. Acesse os links abaixo no seu navegador:

 - Front-End: http://localhost:8080/html/index.html
 - API (Swagger): http://localhost:8080/swagger-ui.html

---

## Melhorias Futuras

Melhorar a interface web (usando React ou Bootstrap)

Adicionar mais funcionalidades para Registro de Pagamentos

Implementar sistema de Login e operações por tipo de usuários

Adicionar histórico de pagamentos
