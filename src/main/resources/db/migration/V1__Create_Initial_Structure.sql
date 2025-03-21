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

-- Apenas para fins de testes irei inserir alguns dados fakes(visto que a api do teste não está retornando dados, apenas 404)
