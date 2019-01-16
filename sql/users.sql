DROP USER IF EXISTS 'client'@'localhost';
CREATE USER 'client'@'localhost' IDENTIFIED BY 'client';
DROP USER IF EXISTS 'storagekeeper'@'localhost';
CREATE USER 'storagekeeper'@'localhost' IDENTIFIED BY 'storagekeeper';
DROP USER IF EXISTS 'storagemanager'@'localhost';
CREATE USER 'storagemanager'@'localhost' IDENTIFIED BY 'storagemanager';
DROP USER IF EXISTS 'manager'@'localhost';
CREATE USER 'manager'@'localhost' IDENTIFIED BY 'manager';
DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
DROP USER IF EXISTS 'login'@'localhost';
CREATE USER 'login'@'localhost' IDENTIFIED BY 'login';

USE music_store;

GRANT SELECT ON music_store.users TO 'login'@'localhost';

#client
GRANT SELECT ON music_store.products TO 'client'@'localhost';
GRANT SELECT ON music_store.albums TO 'client'@'localhost';
GRANT SELECT ON music_store.artists TO 'client'@'localhost';
GRANT SELECT ON music_store.instruments TO 'client'@'localhost';
GRANT SELECT ON music_store.instrument_manufacturers TO 'client'@'localhost';
GRANT SELECT ON music_store.other TO 'client'@'localhost';
GRANT SELECT ON music_store.delivery TO 'client'@'localhost';
GRANT SELECT ON music_store.storage TO 'client'@'localhost';
GRANT INSERT ON music_store.orders TO 'client'@'localhost';
GRANT INSERT ON music_store.orders_products TO 'client'@'localhost';
# GRANT SELECT ON music_store.album_view TO 'client'@'localhost';
# GRANT SELECT ON music_store.instrument_view TO 'client'@'localhost';
# GRANT SELECT ON music_store.other_view TO 'client'@'localhost';

#storekeeper
GRANT SELECT ON music_store.orders TO 'storagekeeper'@'localhost';
GRANT SELECT ON music_store.storage TO 'storagekeeper'@'localhost';
GRANT SELECT ON music_store.products TO 'storagekeeper'@'localhost';
GRANT SELECT ON music_store.albums TO 'storagekeeper'@'localhost';
GRANT SELECT ON music_store.instruments TO 'storagekeeper'@'localhost';
GRANT SELECT ON music_store.other TO 'storagekeeper'@'localhost';
GRANT UPDATE ON music_store.orders TO 'storagekeeper'@'localhost';
GRANT SELECT ON music_store.album_view TO 'storagekeeper'@'localhost';
GRANT SELECT ON music_store.instrument_view TO 'storagekeeper'@'localhost';
GRANT SELECT ON music_store.other_view TO 'storagekeeper'@'localhost';

GRANT SELECT ON music_store.delivery TO 'storagekeeper'@'localhost';
GRANT SELECT ON music_store.orders_products TO 'storagekeeper'@'localhost';
GRANT EXECUTE ON *.* TO 'storagekeeper'@'localhost';


#storage_manager
GRANT SELECT ON music_store.orders TO 'storagemanager'@'localhost';
GRANT SELECT ON music_store.storage TO 'storagemanager'@'localhost';
GRANT SELECT ON music_store.products TO 'storagemanager'@'localhost';
GRANT SELECT ON music_store.albums TO 'storagemanager'@'localhost';
GRANT SELECT ON music_store.instruments TO 'storagemanager'@'localhost';
GRANT SELECT ON music_store.other TO 'storagemanager'@'localhost';
GRANT UPDATE ON music_store.orders TO 'storagemanager'@'localhost';
GRANT UPDATE ON music_store.storage TO 'storagemanager'@'localhost';
GRANT SELECT ON music_store.album_view TO 'storagemanager'@'localhost';
GRANT SELECT ON music_store.instrument_view TO 'storagemanager'@'localhost';
GRANT SELECT ON music_store.other_view TO 'storagemanager'@'localhost';

GRANT SELECT ON music_store.delivery TO 'storagemanager'@'localhost';
GRANT SELECT ON music_store.orders_products TO 'storagemanager'@'localhost';
GRANT EXECUTE ON *.* TO 'storagemanager'@'localhost';

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
GRANT SELECT ON music_store.album_view TO 'manager'@'localhost';
GRANT SELECT ON music_store.instrument_view TO 'manager'@'localhost';
GRANT SELECT ON music_store.other_view TO 'manager'@'localhost';

GRANT SELECT ON music_store.delivery TO 'manager'@'localhost';
GRANT SELECT ON music_store.orders_products TO 'manager'@'localhost';
GRANT EXECUTE ON *.* TO 'manager'@'localhost';

#admin
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost';
# GRANT SELECT ON music_store.orders TO 'admin'@'localhost';
# GRANT SELECT ON music_store.storage TO 'admin'@'localhost';
# GRANT SELECT ON music_store.products TO 'admin'@'localhost';
# GRANT SELECT ON music_store.albums TO 'admin'@'localhost';
# GRANT SELECT ON music_store.instruments TO 'admin'@'localhost';
# GRANT SELECT ON music_store.other TO 'admin'@'localhost';
# GRANT UPDATE ON music_store.storage TO 'admin'@'localhost';
# GRANT UPDATE ON music_store.orders TO 'admin'@'localhost';
# GRANT UPDATE ON music_store.users TO 'admin'@'localhost'; #TODO: Not in documentation, check
# GRANT UPDATE, INSERT, DELETE ON music_store.products TO 'admin'@'localhost';
# GRANT UPDATE, INSERT, DELETE ON music_store.artists TO 'admin'@'localhost';
# GRANT UPDATE, INSERT, DELETE ON music_store.albums TO 'admin'@'localhost';
# GRANT UPDATE, INSERT, DELETE ON music_store.other TO 'admin'@'localhost';
# GRANT UPDATE, INSERT, DELETE ON music_store.instruments TO 'admin'@'localhost';
# GRANT UPDATE, INSERT, DELETE ON music_store.instrument_manufacturers TO 'admin'@'localhost';
# GRANT UPDATE, INSERT, DELETE ON music_store.delivery TO 'admin'@'localhost';
# GRANT SELECT, UPDATE, INSERT, DELETE ON music_store.users TO 'admin'@'localhost';
# GRANT INSERT ON music_store.orders TO 'admin'@'localhost'; #TODO
# GRANT SELECT ON music_store.delivery TO 'admin'@'localhost'; #TODO
# GRANT USAGE ON * TO 'admin'@'localhost';
# GRANT SELECT, LOCK TABLES ON `mysql`.* TO 'admin'@'localhost'; #dump
# GRANT SELECT, LOCK TABLES, SHOW VIEW, EVENT, TRIGGER ON `music_store`.* TO 'admin'@'localhost'; #dump
# GRANT RELOAD ON *.* TO 'admin'@'localhost';
# GRANT CREATE, INSERT, DROP, UPDATE ON mysql.backup_progress TO 'admin'@'localhost';
# GRANT CREATE, INSERT, SELECT, DROP, UPDATE, ALTER ON mysql.backup_history TO 'admin'@'localhost';
# GRANT REPLICATION CLIENT ON *.* TO 'admin'@'localhost';
# GRANT SUPER ON *.* TO 'admin'@'localhost';
# GRANT PROCESS ON *.* TO 'admin'@'localhost';
# GRANT SELECT ON performance_schema.replication_group_members TO 'admin'@'localhost';
# GRANT CREATE, INSERT, DROP ON mysql.backup_history_old TO 'admin'@'localhost';
# GRANT CREATE, INSERT, DROP, ALTER ON mysql.backup_history_new TO 'admin'@'localhost';