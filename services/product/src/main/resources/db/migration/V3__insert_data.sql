insert into product (id, name, description, available_quantity, price, category_id) values
(nextval('product_seq'), 'Smartphone', 'Teléfono inteligente con pantalla OLED', 50, 599.99, 1),  -- Categoria "Electrónica"
(nextval('product_seq'), 'Laptop', 'Portátil ligera de 14 pulgadas', 20, 899.50, 1),            -- Categoria "Electrónica"
(nextval('product_seq'), 'Auriculares Bluetooth', 'Auriculares inalámbricos con cancelación de ruido', 100, 79.99, 1), -- Categoria "Electrónica"
(nextval('product_seq'), 'El Principito', 'Libro clásico de Antoine de Saint-Exupéry', 200, 9.99, 51),  -- Categoria "Libros"
(nextval('product_seq'), '1984', 'Novela distópica de George Orwell', 150, 12.49, 51),              -- Categoria "Libros"
(nextval('product_seq'), 'Polera básica', 'Polera de algodón color negro', 80, 14.99, 101),          -- Categoria "Ropa"
(nextval('product_seq'), 'Pantalón jeans', 'Jeans azul clásico', 60, 29.99, 101);                  -- Categoria "Ropa"