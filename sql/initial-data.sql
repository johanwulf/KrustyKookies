INSERT INTO Customers(customer_name, address) VALUES
    ('Finkakor AB', 'Helsingborg'),
    ('Småbröd AB', 'Malmö'),
    ('Kaffebröd AB', 'Landskrona'),
    ('Bjudkakor AB', 'Ystad'),
    ('Kalaskakor AB', 'Trelleborg'),
    ('Partykakor AB', 'Kristianstad'),
    ('Gästkakor AB', 'Hässleholm'),
    ('Skånekakor AB', 'Perstorp')
;

INSERT INTO Cookie(cookie_name) VALUES
    ('Almond Delight'),
    ('Amneris'),
    ('Berliner'),
    ('Nut Cookie'),
    ('Nut Ring'),
    ('Tango')
;

INSERT INTO Ingredient(ingredient_name, stock, unit) VALUES
    ('Bread crumbs', 500000, 'g'),
    ('Butter', 500000, 'g'),
    ('Chocolate', 500000, 'g'),
    ('Chopped Almonds', 500000, 'g'),
    ('Cinnamon', 500000, 'g'),
    ('Egg whites', 500000, 'ml'),
    ('Eggs', 500000, 'g'),
    ('Fine-ground nuts', 500000, 'g'),
    ('Flour', 500000, 'g'),
    ('Ground, roasted nuts', 500000, 'g'),
    ('Icing sugar', 500000, 'g'),
    ('Marzipan', 500000, 'g'),
    ('Potato starch', 500000, 'g'),
    ('Roasted, chopped nuts', 500000, 'g'),
    ('Sodium bicarbonate', 500000, 'g'),
    ('Sugar', 500000, 'g'),
    ('Vanilla sugar', 500000, 'g'),
    ('Vanilla', 500000, 'g'),
    ('Wheat flour', 500000, 'g')
;

INSERT INTO Recipes (cookie_name, ingredient_name, quantity) VALUES
    ('Almond Delight', 'Butter', 400),('Almond Delight', 'Chopped Almonds', 279),('Almond Delight', 'Cinnamon', 10),('Almond Delight', 'Flour', 400),('Almond Delight', 'Sugar', 270),
    ('Amneris', 'Butter', 250),('Amneris', 'Eggs', 250),('Amneris', 'Marzipan', 750),('Amneris', 'Potato starch', 25),('Amneris', 'Wheat flour', 25),
    ('Berliner', 'Butter', 250),('Berliner', 'Chocolate', 50),('Berliner', 'Eggs', 50),('Berliner', 'Flour', 350),('Berliner', 'Icing sugar', 100),('Berliner', 'Vanilla sugar', 5),
    ('Nut Cookie', 'Bread crumbs', 125),('Nut Cookie', 'Chocolate', 50),('Nut Cookie', 'Egg whites', 350),('Nut Cookie', 'Fine-ground nuts', 750),('Nut Cookie', 'Ground, roasted nuts', 625),('Nut Cookie', 'Sugar', 375),
    ('Nut Ring', 'Butter', 450),('Nut Ring', 'Flour', 450),('Nut Ring', 'Icing sugar', 190),('Nut Ring', 'Roasted, chopped nuts', 225),
    ('Tango', 'Butter', 200),('Tango', 'Flour', 300),('Tango', 'Sodium bicarbonate', 4),('Tango', 'Sugar', 250),('Tango', 'Vanilla', 2)
;


	
