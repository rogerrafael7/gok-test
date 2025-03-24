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

CREATE TABLE IF NOT EXISTS sub_categories
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name       VARCHAR NOT NULL,
    category_id INT NOT NULL REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS products
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name       VARCHAR        NOT NULL,
    price      NUMERIC(10, 2) NOT NULL,
    sub_category_id INT NOT NULL REFERENCES sub_categories (id)
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
