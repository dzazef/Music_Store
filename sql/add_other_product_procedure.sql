USE music_store;
DELIMITER $$
CREATE PROCEDURE add_other_product(price FLOAT,product_name VARCHAR(50),product_type VARCHAR(50),
  producer_name VARCHAR(50))
  BEGIN
     START TRANSACTION;
      INSERT INTO products (category, price) VALUES('other',price);
      SET @product_id = LAST_INSERT_ID();
      INSERT INTO other (product_id, producer, name, type)
        VALUES (@product_id,producer_name,product_name,product_type);
     COMMIT;
  END $$
DELIMITER ;