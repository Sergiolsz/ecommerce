CREATE TABLE IF NOT EXISTS product (
    id BINARY(16) PRIMARY KEY,
    product_id INT NOT NULL,
    brand_id INT NOT NULL,
    price_list INT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    priority INT NOT NULL
);