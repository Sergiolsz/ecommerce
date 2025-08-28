INSERT INTO product (id, product_id, brand_id, price_list, start_date, end_date, price, currency, priority)
VALUES
  (RANDOM_UUID(), 35455, 1, 1, '2020-06-14T00:00:00', '2020-12-31T23:59:59', 35.50, 'EUR', 0),
  (RANDOM_UUID(), 35455, 1, 2, '2020-06-14T15:00:00', '2020-06-14T18:30:00', 25.45, 'EUR', 1),
  (RANDOM_UUID(), 35455, 1, 3, '2020-06-15T00:00:00', '2020-06-15T11:00:00', 30.50, 'EUR', 1),
  (RANDOM_UUID(), 35455, 1, 4, '2020-06-15T16:00:00', '2020-12-31T23:59:59', 38.95, 'EUR', 1);
