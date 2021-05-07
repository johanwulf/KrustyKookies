DROP TABLE IF EXISTS OrderSpec;
DROP TABLE IF EXISTS RecipeItem;
DROP TABLE IF EXISTS Ingredient;
DROP TABLE IF EXISTS Pallet;
DROP TABLE IF EXISTS Cookie;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Customers;

CREATE TABLE Customers (
    customer_name VARCHAR(20),
    address VARCHAR(20),
    PRIMARY KEY (customer_name)
);

CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT,
    order_date DATETIME,
    delivery_date DATETIME,
    customer_name VARCHAR(20),
    PRIMARY KEY (order_id),
    FOREIGN KEY (customer_name) REFERENCES Customers(customer_name) 
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
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (cookie_id) REFERENCES Cookie(cookie_id) 
);

CREATE TABLE OrderSpec (
    quantity INT,
    order_id INT,
    cookie_id INT,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (cookie_id) REFERENCES Cookie(cookie_id)
);

CREATE TABLE Ingredient(
    ingredient_id INT AUTO_INCREMENT,
    ingredient_name VARCHAR(32),
    stock INT,
    unit VARCHAR(20),
    delivery_date DATETIME,
    delivery_quantity DATETIME,
    PRIMARY KEY (ingredient_id)
);

CREATE TABLE Recipes
(
    cookie_id INTEGER NOT NULL,
    ingredient_Id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,    
    FOREIGN KEY (cookie_id) REFERENCES Cookie(cookie_id),
    FOREIGN KEY (ingredient_id) REFERENCES Ingredient(ingredient_id)
    );
