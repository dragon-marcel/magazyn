 
INSERT INTO users (id,enabled,name,password,role) VALUES
(1,true ,'admin','$2a$10$JBqoIgquukYf9ZOqrTnlU.qjSIcb1MYqDY/p9FAX7T1XIEq7mAgqq','ADMIN');

INSERT INTO warehouse (id, name) VALUES (1,'Magazyn główny');
INSERT INTO warehouse (id, name) VALUES (2,'Magazyn sklepu');

INSERT  INTO document (id, name) VALUES (1,'Wydanie magazynowe');
INSERT  INTO document (id, name) VALUES (2,'Przyjęcie magazynowe');
INSERT  INTO document (id, name) VALUES (3,'Przyjęcie towaru');
INSERT  INTO document (id, name) VALUES (4,'Klient');

INSERT INTO products(id, name, price) VALUES (1,'telefon',200);
INSERT INTO products(id, name, price) VALUES (2,'tablet',1000);
INSERT INTO products(id, name, price) VALUES (3,'laptop',3099);

INSERT  INTO state_products(quantity, products_id, warehouse_id) VALUES (20,1,1);
INSERT  INTO state_products(quantity, products_id, warehouse_id) VALUES (20,1,2);

INSERT  INTO state_products(quantity, products_id, warehouse_id) VALUES (5,2,1);
INSERT  INTO state_products(quantity, products_id, warehouse_id) VALUES (10,2,2);

INSERT  INTO state_products(quantity, products_id, warehouse_id) VALUES (11,3,1);
INSERT  INTO state_products(quantity, products_id, warehouse_id) VALUES (80,3,2);
