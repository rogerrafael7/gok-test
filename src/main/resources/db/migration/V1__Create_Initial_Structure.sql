CREATE TABLE IF NOT EXISTS customers
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name       VARCHAR(255) NOT NULL,
    tax_id     VARCHAR(14)  NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name       VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name       VARCHAR(255)   NOT NULL,
    price      NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS product_categories
(
    id          SERIAL PRIMARY KEY,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    product_id  INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_category
        FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS purchases
(
    id          SERIAL PRIMARY KEY,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    customer_id INT            NOT NULL REFERENCES customers (id),
    product_id  INT            NOT NULL REFERENCES products (id),
    quantity    INT            NOT NULL,
    unit_price  NUMERIC(10, 2) NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL
);

-- NÃO DEVERIA FICAR NUMA MIGRATION
-- É apenas para fins do Teste, pois a api do teste não está retornando dados, apenas 404

INSERT INTO customers (name, tax_id)
VALUES ('João Silva', '123.456.789-10'),
       ('Maria Souza', '234.567.890-11'),
       ('Carlos Pereira', '345.678.901-12'),
       ('Ana Oliveira', '456.789.012-13'),
       ('Pedro Santos', '567.890.123-14'),
       ('Mariana Costa', '678.901.234-15'),
       ('Rafael Lima', '789.012.345-16'),
       ('Fernanda Almeida', '890.123.456-17'),
       ('Bruno Martins', '901.234.567-18'),
       ('Larissa Rocha', '012.345.678-19');

INSERT INTO categories (name)
VALUES ('Vinho'),
       ('Cerveja'),
       ('Destilados');

INSERT INTO products (name, price)
VALUES ('Vinho Tinto Reserva', 49.90),
       ('Vinho Branco Chardonnay', 39.90),
       ('Vinho Rosé', 44.90),
       ('Vinho Espumante Brut', 59.90),
       ('Cerveja Pilsen Premium', 7.90),
       ('Cerveja Artesanal IPA', 9.90),
       ('Cerveja Weiss', 8.50),
       ('Cerveja Lager', 8.00),
       ('Destilado Cachaça Ouro', 29.90),
       ('Destilado Gin Especial', 34.90);

INSERT INTO product_categories (product_id, category_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 2),
       (6, 2),
       (7, 2),
       (8, 2),
       (9, 3),
       (10, 3);

INSERT INTO purchases (customer_id, product_id, quantity, unit_price, total_price)
VALUES (1, 1, 2, 49.90, 99.80),
       (1, 5, 3, 7.90, 23.70),
       (2, 2, 1, 39.90, 39.90),
       (2, 6, 2, 9.90, 19.80),
       (2, 9, 1, 29.90, 29.90),
       (3, 3, 4, 44.90, 179.60),
       (3, 7, 2, 8.50, 17.00),
       (4, 4, 1, 59.90, 59.90),
       (4, 8, 3, 8.00, 24.00),
       (5, 1, 2, 49.90, 99.80),
       (5, 2, 2, 39.90, 79.80),
       (5, 5, 1, 7.90, 7.90),
       (6, 6, 4, 9.90, 39.60),
       (6, 9, 1, 29.90, 29.90),
       (7, 7, 2, 8.50, 17.00),
       (7, 8, 2, 8.00, 16.00),
       (7, 10, 1, 34.90, 34.90),
       (8, 4, 1, 59.90, 59.90),
       (8, 5, 2, 7.90, 15.80),
       (8, 9, 1, 29.90, 29.90),
       (9, 3, 3, 44.90, 134.70),
       (9, 8, 1, 8.00, 8.00),
       (9, 10, 2, 34.90, 69.80),
       (10, 2, 2, 39.90, 79.80),
       (10, 6, 1, 9.90, 9.90),
       (10, 7, 3, 8.50, 25.50),
       (10, 10, 1, 34.90, 34.90);