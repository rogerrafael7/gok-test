# GoK - Teste

## 🐳 Get Started

Para rodar o projeto(no Docker):
```bash
make run
```

---

## ✅ Testes

Para executar os testes da aplicação:
```bash
make test
```

---

### Collection do Postman com todos os endpoints:
  [Link para a coleção Postman](https://github.com/rogerrafael7/gok-test.git/tree/master/docs/gok-collection-teste.json)

---

## 🛠️ Principais Comandos do Makefile

| Comando         | Descrição                                                                 |
|-----------------|---------------------------------------------------------------------------|
| `make run`      | Inicia a aplicação localmente.                                            |
| `make build`    | Compila o projeto.                                                        |
| `make test`     | Executa os testes automatizados.                                          |

---

## 🌐 Endpoints da API

### 🔍 Health Check
- **GET** `/health`  
  Verifica se a API está em funcionamento.

---

### 📦 Categorias
- **GET** `/categorias`  
  Retorna a lista de categorias com suas respectivas subcategorias.

---

### 🛒 Compras

- **GET** `/compras?page=1&limit=5`  
  Retorna uma lista paginada de compras realizadas, com informações de cliente e produtos comprados.

- **GET** `/compras/maior-compra/:year`  
  Retorna a maior compra do ano especificado.  
  Parâmetros aceitos(devido a atual massa de dados): `2023`, `2024`, `2025`.

---

### 👤 Clientes

- **GET** `/clientes/top`  
  Retorna os 3 clientes que mais compraram.

- **GET** `/clientes/recomendacao/client/:customerId/tipo`  
  Retorna recomendações de produtos com base no histórico de compras do cliente.  
  Parâmetro: `customerId` (ID do cliente)

---

### 🧪 Endpoints Mock

Utilizados para testes e simulações:

- **GET** `https://run.mocky.io/v3/45e7bcac-6f2c-40f1-8662-eb3a62aa005c`  
  Lista de produtos mockados.

- **GET** `https://run.mocky.io/v3/d1992fa3-e01d-43ea-97d6-e8ef302c886d`  
  Lista de clientes e suas compras (mock).
