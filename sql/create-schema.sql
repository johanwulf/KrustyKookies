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
    cookie_name VARCHAR(20),
    PRIMARY KEY (cookie_name)
);

CREATE TABLE Pallet (
    id INT AUTO_INCREMENT,
    production_date DATETIME,
    delivery_date DATETIME,
    blocked varchar(3) DEFAULT 'no',
    order_id INT,
    cookie_name VARCHAR(20),
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (cookie_name) REFERENCES Cookie(cookie_name) 
);

CREATE TABLE OrderSpec (
    quantity INT,
    order_id INT,
    cookie_name VARCHAR(20),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (cookie_name) REFERENCES Cookie(cookie_name)
);

CREATE TABLE Ingredient(    
    ingredient_name VARCHAR(32),
    stock INT,
    unit VARCHAR(20),
    delivery_date DATETIME,
    delivery_quantity DATETIME,
    PRIMARY KEY (ingredient_name)
);

CREATE TABLE Recipes
(
    cookie_name VARCHAR(20),
    ingredient_name VARCHAR(32),
    quantity INTEGER NOT NULL,    
    FOREIGN KEY (cookie_name) REFERENCES Cookie(cookie_name),
    FOREIGN KEY (ingredient_name) REFERENCES Ingredient(ingredient_name)
    );
