DROP USER IF EXISTS 'client'@'localhost';
CREATE USER 'client'@'localhost' IDENTIFIED BY 'client';
DROP USER IF EXISTS 'storekeeper'@'localhost';
CREATE USER 'storekeeper'@'localhost' IDENTIFIED BY 'storekeeper';
DROP USER IF EXISTS 'storage_manager'@'localhost';
CREATE USER 'storage_manager'@'localhost' IDENTIFIED BY 'store_manager';
DROP USER IF EXISTS 'manager'@'localhost';
CREATE USER 'manager'@'localhost' IDENTIFIED BY 'manager';
DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';

USE music_store;

#client
GRANT SELECT ON music_store.products TO 'client'@'localhost';
GRANT SELECT ON music_store.albums TO 'client'@'localhost';
GRANT SELECT ON music_store.artists TO 'client'@'localhost';
GRANT SELECT ON music_store.instruments TO 'client'@'localhost';
GRANT SELECT ON music_store.instrument_manufacturers TO 'client'@'localhost';
GRANT SELECT ON music_store.other TO 'client'@'localhost';
GRANT SELECT ON music_store.delivery TO 'client'@'localhost';
GRANT INSERT ON music_store.orders TO 'client'@'localhost';

#storekeeper
GRANT SELECT ON music_store.orders TO 'storekeeper'@'localhost';
GRANT SELECT ON music_store.storage TO 'storekeeper'@'localhost';
GRANT SELECT ON music_store.products TO 'storekeeper'@'localhost';
GRANT SELECT ON music_store.albums TO 'storekeeper'@'localhost';
GRANT SELECT ON music_store.instruments TO 'storekeeper'@'localhost';
GRANT SELECT ON music_store.other TO 'storekeeper'@'localhost';
GRANT UPDATE ON music_store.orders TO 'storekeeper'@'localhost';

#storage_manager
GRANT SELECT ON music_store.orders TO 'storage_manager'@'localhost';
GRANT SELECT ON music_store.storage TO 'storage_manager'@'localhost';
GRANT SELECT ON music_store.products TO 'storage_manager'@'localhost';
GRANT SELECT ON music_store.albums TO 'storage_manager'@'localhost';
GRANT SELECT ON music_store.instruments TO 'storage_manager'@'localhost';
GRANT SELECT ON music_store.other TO 'storage_manager'@'localhost';
GRANT UPDATE ON music_store.orders TO 'storage_manager'@'localhost';
GRANT UPDATE ON music_store.storage TO 'storage_manager'@'localhost';

#manager
GRANT SELECT ON music_store.orders TO 'manager'@'localhost';
GRANT SELECT ON music_store.storage TO 'manager'@'localhost';
GRANT SELECT ON music_store.products TO 'manager'@'localhost';
GRANT SELECT ON music_store.albums TO 'manager'@'localhost';
GRANT SELECT ON music_store.instruments TO 'manager'@'localhost';
GRANT SELECT ON music_store.other TO 'manager'@'localhost';
GRANT UPDATE ON music_store.storage TO 'manager'@'localhost';
GRANT UPDATE ON music_store.orders TO 'manager'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.products TO 'manager'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.artists TO 'manager'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.albums TO 'manager'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.other TO 'manager'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.instruments TO 'manager'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.instrument_manufacturers TO 'manager'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.delivery TO 'manager'@'localhost';

#admin
GRANT SELECT ON music_store.orders TO 'admin'@'localhost';
GRANT SELECT ON music_store.storage TO 'admin'@'localhost';
GRANT SELECT ON music_store.products TO 'admin'@'localhost';
GRANT SELECT ON music_store.albums TO 'admin'@'localhost';
GRANT SELECT ON music_store.instruments TO 'admin'@'localhost';
GRANT SELECT ON music_store.other TO 'admin'@'localhost';
GRANT UPDATE ON music_store.storage TO 'admin'@'localhost';
GRANT UPDATE ON music_store.orders TO 'admin'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.products TO 'admin'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.artists TO 'admin'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.albums TO 'admin'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.other TO 'admin'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.instruments TO 'admin'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.instrument_manufacturers TO 'admin'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.delivery TO 'admin'@'localhost';
GRANT UPDATE, INSERT, DELETE ON music_store.users TO 'admin'@'localhost';