USE music_store;
DELIMITER $$
DROP PROCEDURE IF EXISTS create_random_instruments;
CREATE PROCEDURE create_random_instruments()
BEGIN
  DECLARE i INT UNSIGNED DEFAULT 0;
  DECLARE price FLOAT DEFAULT 0;
  DECLARE instrument_type VARCHAR(80);
  DECLARE instrument_name VARCHAR(50);
  DECLARE manufacturer_name VARCHAR(50);

  w1: WHILE i<300 DO
    SET i=i+1;
    SET price = FLOOR(RAND()*(60-1)+1)*100+99;
    SET instrument_type = ELT(RAND()*(13-1)+1, 'gitara', 'perkusja', 'keyboard', 'wiolonczela', 'flet',
                            'saksofon', 'akordeon', 'gitara basowa', 'skrzypce', 'tamburyn', 'waltornia', 'puzon', 'klarnet');
    SET instrument_name = CONCAT(
      SUBSTRING('ABCDEFGHIJKLMNOPQRSTUVWXYZ', RAND()*(26-1)+1, 1),
      SUBSTRING('ABCDEFGHIJKLMNOPQRSTUVWXYZ', RAND()*(26-1)+1, 1),
      SUBSTRING('ABCDEFGHIJKLMNOPQRSTUVWXYZ', RAND()*(26-1)+1, 1),
      '-',
      SUBSTRING('123456789', RAND()*(9-1)+1, 1),
      SUBSTRING('1234567890', RAND()*(10-1)+1, 1),
      SUBSTRING('1234567890', RAND()*(10-1)+1, 1),
      SUBSTRING('1234567890', RAND()*(10-1)+1, 1),
      SUBSTRING('1234567890', RAND()*(10-1)+1, 1));
    SET manufacturer_name = ELT(RAND()*(14-1)+1, 'YAMAHA', 'SHURE', 'INSTRUMENT-POL', 'HOHNER', 'ACOUSTA', 'FENDER', 'CHARVER',
                              'REGAL', 'GRETSCH', 'AKG', 'AKAI', 'ARMEL', 'GEMINI', 'GEWA', 'LARSEN', 'LANEY', 'MOTU', 'MORLEY');
  CALL add_instrument(price, instrument_type, instrument_name, manufacturer_name);
  end while w1;
END $$
DELIMITER ;
CALL create_random_instruments();