DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Orders;
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

CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT,
    order_date DATETIME,
    delivery_date DATETIME,
    customer_name VARCHAR(20),
    PRIMARY KEY (order_id),
    FOREIGN KEY (customer_name) REFERENCES Customers(name) 
);
CREATE TABLE Cookie (
    cookie_id INT AUTO_INCREMENT,
    cookie_name VARCHAR(20),
    PRIMARY KEY (cookie_id)
);
CREATE TABLE Pallet (
    id INT AUTO_INCREMENT,
    production_date DATETIME,
    delivery_date DATETIME,
    blocked BOOLEAN,
    order_id INT,
    cookie_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (cookie_id) REFERENCES cookie(cookie_id) 
);

CREATE TABLE OrderSpec (
    quantity INT,
    order_id INT,
    cookie_id INT,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (cookie_id) REFERENCES cookie(cookie_id)
);





CREATE TABLE Ingredient(
    ingredient_ID INT AUTO_INCREMENT,
    ingredient_name VARCHAR(32),
    stock INT,
    unit VARCHAR(20),
    delivery_date DATETIME,
    delivery_quantity DATETIME
);
    
CREATE TABLE RecipeItem(
    ingredient VARCHAR(32),
    quantity INT,
    FOREIGN KEY (ingredient) REFERENCES Ingredient(ingredient_name) 
);
    