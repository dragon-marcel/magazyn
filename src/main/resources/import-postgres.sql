 
INSERT INTO users (id,enabled,name,password,role) VALUES
(1,true ,'admin','$2a$10$2B3t7yRJo5xUIsZGR6x1V.cSoonLYm1tKPhrGKTUNA1GEOQX3V1I','ADMIN');

INSERT INTO warehouse (id, name) VALUES (1,'Magazyn główny');
INSERT INTO warehouse (id, name) VALUES (2,'Magazyn sklepu');

INSERT  INTO document (id, name) VALUES (1,'Wydanie magazynowe');
INSERT  INTO document (id, name) VALUES (2,'Przyjęcie magazynowe');
INSERT  INTO document (id, name) VALUES (3,'Przyjęcie towaru');
INSERT  INTO document (id, name) VALUES (4,'Klient');

INSERT  INTO state_products(quantity, products_id, warehouse_id) VALUES (0,1,1);
INSERT  INTO state_products(quantity, products_id, warehouse_id) VALUES (0,1,2);

SELECT * FROM state_products;users
