DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Order;
DROP TABLE IF EXISTS Pallet;
DROP TABLE IF EXISTS OrderSpec;
DROP TABLE IF EXISTS Cookie;
DROP TABLE IF EXISTS RecipeItem;
DROP TABLE IF EXISTS Ingredient;

CREATE TABLE Customers (
    name VARCHAR(20),
    address VARCHAR(20),
    PRIMARY KEY (name)
);

CREATE TABLE Order (
    order_id INT AUTO_INCREMENT,
    order_date DATETIME,
    delivery_date DATETIME,
    customer_name VARCHAR(20),
    PRIMARY KEY (id),
    FOREIGN KEY (customer_name) REFERENCES Customers(name) 
);

CREATE TABLE Pallet (
    id INT AUTO_INCREMENT,
    production_date DATETIME,
    delivery_date DATETIME,
    blocked BOOLEAN,
    order_id INT,
    cookie_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES order(order_id)
    FOREIGN KEY (cookie_id) REFERENCES cookie(cookie_id) 
);

CREATE TABLE OrderSpec (
    quantity INT,
    order_id INT,
    cookie_id INT,
    FOREIGN KEY (order_id) REFERENCES order(order_id),
    FOREIGN KEY (cookie_id) REFERENCES cookie(cookie_id)
);

CREATE TABLE Cookie (
    cookie_id INT AUTO_INCREMENT,
    cookie_name VARCHAR(20),
    PRIMARY KEY (cookie_id)
);

CREATE TABLE RecipeItem(
    quantity INT,

);