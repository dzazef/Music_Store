CREATE OR REPLACE VIEW album_view AS
SELECT product_id,category,price,a.artist_id,name,genre,title,duration,release_year,songs_count,image_link,tracklist
FROM products JOIN albums a ON products.product_id = a.album_id
  JOIN artists a2 ON a.artist_id = a2.artist_id

CREATE OR REPLACE VIEW instrument_view AS
SELECT product_id,category,price,i.manufacturer_id,m.name AS manufacturer_name,
i.name AS instrument_name, type FROM products
   JOIN instruments i ON products.product_id = i.instrument_id
  JOIN instrument_manufacturers m ON i.manufacturer_id = m.manufacturer_id

CREATE OR REPLACE VIEW other_view AS
SELECT products.product_id,category,price,producer,name,type
FROM products JOIN other o ON products.product_id = o.product_id