DELIMITER $$
CREATE TRIGGER storage_decrement AFTER INSERT ON orders_products FOR EACH ROW
    BEGIN
        UPDATE storage SET products_available = products_available - NEW.quantity
        WHERE NEW.product_id = storage.product_id;
    END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER before_remove_user BEFORE DELETE  ON users FOR EACH ROW
  BEGIN
    IF OLD.user_id IN (SELECT user_id FROM  status_logs) THEN
      SIGNAL SQLSTATE '40005' SET MESSAGE_TEXT = 'User is present in logs. Clean the logs to remove this user.' ;
    END IF ;
  END $$
DELIMITER ;