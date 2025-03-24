-- Os inserts abaixo são apenas para fins de teste, pois a api do teste não está retornando dados, apenas 404

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


INSERT INTO sub_categories (name, category_id)
VALUES
    ('Tinto', 1),
    ('Branco', 1),
    ('Rosé', 1),
    ('Espumante', 1),
    ('Suave', 1),
    ('Seco', 1),
    ('Importado', 1),
    ('Nacional', 1),

    ('Pilsen', 2),
    ('IPA', 2),
    ('Weiss', 2),
    ('Lager', 2),
    ('Stout', 2),
    ('Porter', 2),
    ('Ale', 2),
    ('Artesanal', 2),

    ('Whisky', 3),
    ('Vodka', 3),
    ('Rum', 3),
    ('Cachaça', 3),
    ('Gin', 3),
    ('Tequila', 3),
    ('Licor', 3),
    ('Conhaque', 3);


INSERT INTO products (name, price, sub_category_id)
VALUES
    ('Vinho Tinto Cabernet', 79.90, 1),
    ('Vinho Tinto Merlot', 69.90, 1),
    ('Vinho Branco Sauvignon', 54.90, 2),
    ('Espumante Prosecco', 89.90, 4),
    ('Vinho Rosé Seco', 49.90, 3),
    ('Cerveja Stout Imperial', 12.90, 13),
    ('Cerveja Porter', 11.90, 14),
    ('Cerveja Ale', 10.90, 15),
    ('Whisky 12 anos', 129.90, 17),
    ('Vodka Premium', 89.90, 18),
    ('Rum Especial', 79.90, 19),
    ('Tequila Gold', 99.90, 22),
('Gyn Special', 109.90, 22);

CREATE OR REPLACE PROCEDURE sp_insert_random_rows(p_year integer)
LANGUAGE plpgsql
AS $$
DECLARE
  v_order_id integer;
  v_customer_id integer;
  v_created_at timestamp;
  v_num_products integer;
  v_product_id integer;
  v_quantity integer;
  v_unit_price numeric;
  v_random_day int;
  i int;
  j int;
BEGIN
  FOR i IN 1..10 LOOP
    v_customer_id := floor(random() * 10 + 1)::int;
    v_random_day := floor(random() * 365)::int;
    v_created_at := to_timestamp(p_year || '-01-01', 'YYYY-MM-DD') + (v_random_day * interval '1 day');

    INSERT INTO orders (customer_id, created_at)
    VALUES (v_customer_id, v_created_at)
    RETURNING id INTO v_order_id;

    v_num_products := floor(random() * 10 + 1)::int;

    FOR j IN 1..v_num_products LOOP
      v_product_id := floor(random() * 13 + 1)::int;
      v_quantity := floor(random() * 5 + 1)::int;
      SELECT price INTO v_unit_price FROM products WHERE id = v_product_id;

      INSERT INTO order_products (order_id, product_id, quantity, unit_price)
      VALUES (v_order_id, v_product_id, v_quantity, v_unit_price);
    END LOOP;
  END LOOP;
END;
$$;

CALL sp_insert_random_rows(2025);
CALL sp_insert_random_rows(2024);
CALL sp_insert_random_rows(2023);
