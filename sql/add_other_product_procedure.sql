USE music_store;
DELIMITER $$
DROP PROCEDURE IF EXISTS add_other_product;
CREATE PROCEDURE add_other_product(price FLOAT,product_name VARCHAR(50),product_type VARCHAR(50),
  producer_name VARCHAR(50))
  BEGIN
     START TRANSACTION;
      INSERT INTO products (category, price) VALUES('other',price);
      SET @product_id = LAST_INSERT_ID();
      INSERT INTO other (product_id, producer, name, type)
        VALUES (@product_id,producer_name,product_name,product_type);
--         INSERT INTO other_view (product_id, category, price, producer, name, type)
--       VALUES (@product_id,category,price,producer_name,product_name,product_type);
    INSERT INTO storage (product_id, products_available)
    VALUES (@product_id,0);
     COMMIT;
  END $$
DELIMITER ;