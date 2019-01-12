DELIMITER $$
DROP PROCEDURE IF EXISTS update_order_status;
CREATE PROCEDURE update_order_status (order_id_ INT,new_status VARCHAR(25),user_id VARCHAR(30))
  BEGIN
    DECLARE old_status VARCHAR(25) DEFAULT NULL ;
    SELECT current_status INTO old_status FROM orders WHERE order_id = order_id_;
    IF old_status = new_status THEN
      SIGNAL SQLSTATE '40005' SET MESSAGE_TEXT = CONCAT('An attempt to change status to the same value.');
    # Status already is new_status.
    END IF ;
    UPDATE orders SET current_status = new_status WHERE order_id = order_id_;
    INSERT INTO status_logs (order_id, user_id, date_time, status_old, status_new)
    VALUES (order_id_,user_id,CURRENT_TIMESTAMP(),old_status,new_status);
  END $$
DELIMITER ;