CREATE TABLE IF NOT EXISTS customers
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name       VARCHAR NOT NULL,
    tax_id     VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name       VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name       VARCHAR        NOT NULL,
    price      NUMERIC(10, 2) NOT NULL,
    category_id INT NOT NULL REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS orders
(
    id          SERIAL PRIMARY KEY,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    customer_id INT NOT NULL REFERENCES customers (id),
    total_price NUMERIC(10, 2)
);
comment on column orders.total_price is 'total_price será automaticamente calculado via trigger';

CREATE TABLE IF NOT EXISTS order_products
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    quantity   INT            NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL,
    product_id INT            NOT NULL REFERENCES products (id),
    order_id   INT            NOT NULL REFERENCES orders (id)
);

CREATE OR REPLACE FUNCTION update_total_price()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE orders
    SET total_price = COALESCE(total_price, 0) + NEW.unit_price * COALESCE(NEW.quantity, 1)
    WHERE id = NEW.order_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_total_price
    AFTER INSERT
    ON order_products
    FOR EACH ROW
EXECUTE PROCEDURE update_total_price();

-- AS LINHAS ABAIXO NÃO DEVERIAM FICAR NUMA MIGRATION
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

INSERT INTO products (name, price, category_id)
VALUES ('Vinho Tinto Reserva', 49.90, 1),
       ('Vinho Branco Chardonnay', 39.90, 1),
       ('Vinho Rosé', 44.90, 1),
       ('Vinho Espumante Brut', 59.90, 1),
       ('Cerveja Pilsen Premium', 7.90, 2),
       ('Cerveja Artesanal IPA', 9.90, 2),
       ('Cerveja Weiss', 8.50, 2),
       ('Cerveja Lager', 8.00, 2),
       ('Destilado Cachaça Ouro', 29.90, 3),
       ('Destilado Gin Especial', 34.90, 3);

INSERT INTO orders (customer_id)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9),
       (10);

INSERT INTO order_products (order_id, product_id, quantity, unit_price)
VALUES (1, 1, 2, 49.90),
       (1, 5, 3, 7.90),
       (2, 2, 1, 39.90),
       (2, 6, 2, 9.90),
       (2, 9, 1, 29.90),
       (3, 3, 4, 44.90),
       (3, 7, 2, 8.50),
       (4, 4, 1, 59.90),
       (4, 8, 3, 8.00),
       (5, 1, 2, 49.90),
       (5, 2, 2, 39.90),
       (5, 5, 1, 7.90),
       (6, 6, 4, 9.90),
       (6, 9, 1, 29.90),
       (7, 7, 2, 8.50),
       (7, 8, 2, 8.00),
       (7, 10, 1, 34.90),
       (8, 4, 1, 59.90),
       (8, 5, 2, 7.90),
       (8, 9, 1, 29.90),
       (9, 3, 3, 44.90),
       (9, 8, 1, 8.00),
       (9, 10, 2, 34.90),
       (10, 2, 2, 39.90),
       (10, 6, 1, 9.90),
       (10, 7, 3, 8.50),
       (10, 10, 1, 34.90);