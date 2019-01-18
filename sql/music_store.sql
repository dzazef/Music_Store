DROP DATABASE IF EXISTS music_store;
CREATE DATABASE music_store CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE music_store;
CREATE TABLE products
(
  product_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category VARCHAR(30) NOT NULL,
  price FLOAT NOT NULL
);
CREATE UNIQUE INDEX products_product_id_uindex ON products (product_id);


CREATE TABLE artists
(
  artist_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(80) NOT NULL,
  genre VARCHAR(50) NOT NULL DEFAULT 'other'
);


CREATE TABLE albums
(
  album_id INT PRIMARY KEY NOT NULL,
  artist_id INT NOT NULL,
  duration INT,
  release_year INT,
  title VARCHAR(80),
  songs_count INT,
  image_link VARCHAR(120),
  tracklist TEXT,
  CONSTRAINT albums_products_product_id_fk FOREIGN KEY (album_id) REFERENCES products (product_id),
  CONSTRAINT albums_artists_artist_id_fk FOREIGN KEY (artist_id) REFERENCES artists (artist_id)
);
CREATE UNIQUE INDEX albums_title ON albums (title);



CREATE TABLE instrument_manufacturers
(
  manufacturer_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL
);


CREATE TABLE instruments
(
  instrument_id INT PRIMARY KEY NOT NULL,
  manufacturer_id INT NOT NULL,
  type VARCHAR(80) NOT NULL,
  name VARCHAR(50) NOT NULL,
  CONSTRAINT instruments_products_product_id_fk FOREIGN KEY (instrument_id) REFERENCES products (product_id),
  CONSTRAINT instruments_instrument_manufacturers_manufacturer_id_fk FOREIGN KEY (manufacturer_id) REFERENCES instrument_manufacturers (manufacturer_id)
);


CREATE TABLE other
(
  product_id INT PRIMARY KEY NOT NULL,
  producer VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  type VARCHAR(50) NOT NULL,
  CONSTRAINT other_products_product_id_fk FOREIGN KEY (product_id) REFERENCES products (product_id)
);


create table delivery
(
  delivery_id int auto_increment
    primary key,
  name varchar(30) not null,
  price float not null,
  delivery_time int not null
);


CREATE TABLE storage
(
  product_id INT PRIMARY KEY NOT NULL,
  products_available INT DEFAULT 0 NOT NULL,
  CONSTRAINT storage_products_product_id_fk FOREIGN KEY (product_id) REFERENCES products (product_id)
);
CREATE UNIQUE INDEX storage_product_id_uindex ON storage (product_id);


CREATE TABLE users
(
  user_id VARCHAR(30) PRIMARY KEY NOT NULL,
  password VARCHAR(100) NOT NULL, #changed to contain passwords hash
  access_level VARCHAR(30) NOT NULL
);
CREATE UNIQUE INDEX users_user_id_uindex ON users (user_id);


CREATE TABLE orders
(
  order_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  delivery_id INT NOT NULL,
  customer_name VARCHAR(50) NOT NULL,
  customer_address VARCHAR(80) NOT NULL,
  phone_number VARCHAR(15) NOT NULL,
  current_status VARCHAR(30) NOT NULL,
  payment VARCHAR(30) NOT NULL,
  transaction_document VARCHAR(30) NOT NULL,
  CONSTRAINT orders_delivery_delivery_id_fk FOREIGN KEY (delivery_id) REFERENCES delivery (delivery_id)
);
CREATE UNIQUE INDEX orders_order_id_uindex ON orders (order_id);


CREATE TABLE orders_products
(
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL DEFAULT 1,
  PRIMARY KEY(order_id,product_id),
  CONSTRAINT orders_products_products_product_id_fk FOREIGN KEY (product_id) REFERENCES products (product_id),
  CONSTRAINT orders_products_orders_order_id_fk FOREIGN KEY (order_id) REFERENCES orders (order_id)
);


CREATE TABLE status_logs
(
  log_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  order_id INT NOT NULL,
  user_id VARCHAR(30) NOT NULL,
  date_time TIMESTAMP NOT NULL,
  status_old VARCHAR(30) NOT NULL,
  status_new VARCHAR(30) NOT NULL,
  CONSTRAINT status_logs_users_user_id_fk FOREIGN KEY (user_id) REFERENCES users (user_id),
  CONSTRAINT status_logs_orders_order_id_fk FOREIGN KEY (order_id) REFERENCES orders (order_id)
);
