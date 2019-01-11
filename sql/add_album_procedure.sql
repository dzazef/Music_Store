USE music_store;
DROP PROCEDURE IF EXISTS add_album;
DELIMITER $$
CREATE PROCEDURE add_album(price FLOAT, title VARCHAR(80), realease_year INT(11), img_link VARCHAR(120),
                           artist_name VARCHAR(50), artist_genre VARCHAR(50), songs_count INT(11), tracklist TEXT, duration INT(11))
  BEGIN
      DECLARE var_artist_id INT DEFAULT NULL ;
      DECLARE var_artist_name VARCHAR(50) DEFAULT NULL;
     START TRANSACTION;
      /*inserting into products*/
      INSERT INTO products (category, price) VALUES ('album',price);
      SET @product_id = LAST_INSERT_ID();
      /*check if artist with given name exists, if not add it to artists*/
      SELECT artist_id,`name`
        INTO var_artist_id,var_artist_name
        FROM artists WHERE `name` LIKE artist_name;
      IF var_artist_id IS NULL THEN
        INSERT INTO artists (name, genre)
        VALUES(artist_name,artist_genre);
        SET var_artist_id = LAST_INSERT_ID();
      END IF;
      /*add album to albums*/
      INSERT INTO albums (album_id, artist_id, duration, release_year, title, songs_count, image_link, tracklist)
      VALUES (@product_id,var_artist_id,duration,realease_year,title,songs_count,img_link,tracklist);
            /*add album to view*/
--       INSERT INTO album_view (product_id, category, price, artist_id, name, genre, title,
--             duration, release_year, songs_count, image_link, tracklist)
--             VALUES (@product_id,category,price,var_artist_id,artist_name,artist_genre,
--             title,duration,realease_year,songs_count,img_link,tracklist);
      /*add album to storage*/
      INSERT INTO storage (product_id, products_available) VALUES (@product_id,0);
     COMMIT;
  END $$
DELIMITER ;