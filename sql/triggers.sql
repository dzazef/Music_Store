DELIMITER $$
CREATE TRIGGER storage_decrement AFTER INSERT ON orders_products FOR EACH ROW
    BEGIN
        UPDATE storage SET products_available = products_available - NEW.quantity
        WHERE NEW.product_id = storage.product_id;
    END $$
DELIMITER ;