
-- Users
-- Passwords are encrypted using BCrypt ("password")
INSERT INTO users (username, password,role) VALUES
('userFree', '$2a$10$yt/HxLeVbRhwESRqb54P6O5N.9ku1I9arSOdyBvh8l1icgUiP1IrS','FREE'),
( 'userPremium', '$2a$10$yt/HxLeVbRhwESRqb54P6O5N.9ku1I9arSOdyBvh8l1icgUiP1IrS','PREMIUM'),
( 'userAdmin', '$2a$10$yt/HxLeVbRhwESRqb54P6O5N.9ku1I9arSOdyBvh8l1icgUiP1IrS','ADMIN');

-- Préférence for user
INSERT INTO preferences (allergen) VALUES
('GLUTEN'),
('LACTOSE'),
('OEUF'),
('ARACHIDE'),
('FRUITS_COQUE'),
('SOJA'),
('CRUSTACE');

-- Préférence for users
INSERT INTO user_preferences (user_id, preference_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(3, 2),
(1, 5);

-- Setting for user
INSERT INTO settings (dark_mode,nb_people,user_id,language,request_quantity,last_request_date) VALUES
(true, 2, 1, 'EN',0,'2025-08-01'),
(false, 1, 2, 'FR',0, '2025-09-10'),
(false, 1, 3, 'EN',0 ,'2025-10-01');

-- Recipes for user1
INSERT INTO recipes (name, duration_minutes, is_favorite, user_id) VALUES
('Spaghetti Carbonara', 30, true, 1),
('Chicken Curry', 45, false, 1);

-- Recipes for user2
INSERT INTO recipes (name, duration_minutes, is_favorite, user_id) VALUES
('Pancakes', 20, true, 2),
('Beef Tacos', 35, true, 2);

-- Recipes for admin
INSERT INTO recipes (name, duration_minutes, is_favorite, user_id) VALUES
('Chocolate Cake', 60, false, 3),
('Vegetable Soup', 40, false, 3);

-- Ingredients for Recipe 1 (Spaghetti Carbonara)
INSERT INTO ingredients (recipe_id, name, quantity, unit) VALUES
(1, 'Spaghetti', 400, 'g'),
(1, 'Guanciale', 150, 'g'),
(1, 'Eggs', 4, 'units'),
(1, 'Pecorino Romano cheese', 50, 'g'),
(1, 'Black pepper', 1, 'tsp');

-- Steps for Recipe 1 (Spaghetti Carbonara)
INSERT INTO steps (recipe_id, step_number, description) VALUES
(1, 1, 'Boil water for pasta. Add salt.'),
(1, 2, 'Cut guanciale into small pieces and cook until crispy.'),
(1, 3, 'In a bowl, mix eggs and pecorino cheese.'),
(1, 4, 'Cook spaghetti until al dente.'),
(1, 5, 'Drain pasta and mix with guanciale, then add egg mixture and pepper.');

-- Ingredients for Recipe 2 (Chicken Curry)
INSERT INTO ingredients (recipe_id, name, quantity, unit) VALUES
(2, 'Chicken breast', 500, 'g'),
(2, 'Onion', 1, 'unit'),
(2, 'Garlic', 2, 'cloves'),
(2, 'Ginger', 1, 'tbsp'),
(2, 'Coconut milk', 400, 'ml'),
(2, 'Curry powder', 2, 'tbsp');

-- Steps for Recipe 2 (Chicken Curry)
INSERT INTO steps (recipe_id, step_number, description) VALUES
(2, 1, 'Cut chicken into cubes.'),
(2, 2, 'Chop onion, garlic, and ginger.'),
(2, 3, 'Sauté onion, garlic, and ginger in a pan.'),
(2, 4, 'Add chicken and cook until browned.'),
(2, 5, 'Add curry powder and coconut milk. Simmer for 20 minutes.');

-- Ingredients for Recipe 3 (Pancakes)
INSERT INTO ingredients (recipe_id, name, quantity, unit) VALUES
(3, 'Flour', 200, 'g'),
(3, 'Milk', 300, 'ml'),
(3, 'Egg', 1, 'unit'),
(3, 'Sugar', 2, 'tbsp'),
(3, 'Baking powder', 1, 'tsp');

-- Steps for Recipe 3 (Pancakes)
INSERT INTO steps (recipe_id, step_number, description) VALUES
(3, 1, 'Mix flour, sugar, and baking powder in a bowl.'),
(3, 2, 'In another bowl, whisk egg and milk.'),
(3, 3, 'Combine wet and dry ingredients and mix until smooth.'),
(3, 4, 'Heat a lightly oiled pan.'),
(3, 5, 'Pour batter onto the pan and cook until bubbles appear, then flip and cook the other side.');

-- Ingredients for Recipe 4 (Beef Tacos)
INSERT INTO ingredients (recipe_id, name, quantity, unit) VALUES
(4, 'Ground beef', 500, 'g'),
(4, 'Taco shells', 12, 'units'),
(4, 'Onion', 1, 'unit'),
(4, 'Taco seasoning', 1, 'packet'),
(4, 'Lettuce', 1, 'head'),
(4, 'Tomato', 2, 'units'),
(4, 'Cheese', 100, 'g');

-- Steps for Recipe 4 (Beef Tacos)
INSERT INTO steps (recipe_id, step_number, description) VALUES
(4, 1, 'Cook ground beef and chopped onion in a pan.'),
(4, 2, 'Drain fat and add taco seasoning and a little water. Simmer.'),
(4, 3, 'Warm taco shells in the oven.'),
(4, 4, 'Chop lettuce and tomatoes.'),
(4, 5, 'Assemble tacos with beef, lettuce, tomato, and cheese.');

-- Ingredients for Recipe 5 (Chocolate Cake)
INSERT INTO ingredients (recipe_id, name, quantity, unit) VALUES
(5, 'Flour', 250, 'g'),
(5, 'Sugar', 300, 'g'),
(5, 'Cocoa powder', 75, 'g'),
(5, 'Baking soda', 1.5, 'tsp'),
(5, 'Eggs', 2, 'units'),
(5, 'Milk', 250, 'ml'),
(5, 'Vegetable oil', 125, 'ml'),
(5, 'Boiling water', 250, 'ml');

-- Steps for Recipe 5 (Chocolate Cake)
INSERT INTO steps (recipe_id, step_number, description) VALUES
(5, 1, 'Preheat oven to 180°C (350°F). Grease and flour two 9-inch round baking pans.'),
(5, 2, 'In a large bowl, sift together flour, sugar, cocoa, and baking soda.'),
(5, 3, 'Add eggs, milk, and oil. Beat for 2 minutes on medium speed.'),
(5, 4, 'Stir in boiling water (batter will be thin).'),
(5, 5, 'Pour batter evenly into prepared pans and bake for 30 to 35 minutes.');

-- Ingredients for Recipe 6 (Vegetable Soup)
INSERT INTO ingredients (recipe_id, name, quantity, unit) VALUES
(6, 'Carrots', 3, 'units'),
(6, 'Celery', 3, 'stalks'),
(6, 'Onion', 1, 'unit'),
(6, 'Potatoes', 2, 'units'),
(6, 'Vegetable broth', 1, 'liter'),
(6, 'Thyme', 1, 'tsp');

-- Steps for Recipe 6 (Vegetable Soup)
INSERT INTO steps (recipe_id, step_number, description) VALUES
(6, 1, 'Chop all vegetables into small pieces.'),
(6, 2, 'Sauté onion, carrots, and celery in a large pot.'),
(6, 3, 'Add potatoes and cook for 5 minutes.'),
(6, 4, 'Pour in vegetable broth and add thyme.'),
(6, 5, 'Bring to a boil, then simmer for 20-25 minutes until vegetables are tender.');
