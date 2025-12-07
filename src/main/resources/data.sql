
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

-- Shopping list for users
INSERT INTO shoppings (creation_date,user_id,shopping_list_name) VALUES
('2025-08-01',1,'Liste de courses pour samedi'),
('2025-08-01',1,'Liste de courses pour dimanche'),
('2025-09-10',2, 'Liste de courses pour vendredi');

-- Items in Shopping list
INSERT INTO items(name,quantity,unit,shopping_id,sequence,bought) VALUES
('Chicken breast', 500, 'g',1,1,false),
('Guanciale', 150, 'g',1,2,false),
('Flour', 200, 'g',1,2,false),
('Eggs', 4, 'units',1,3,false),
('Lettuce', 1, 'head',2,3,false),
('Tomato', 2, 'units',2,1,false),
('Bread', 2, 'units',3,1,false),
('Milk', 300, 'ml',3,2,false);

-- Setting for user
INSERT INTO settings (dark_mode,nb_people,user_id,language,request_quantity,last_request_date,is_trial) VALUES
(true, 2, 1, 'EN',0,'2025-08-01',false),
(false, 1, 2, 'FR',0, '2025-09-10',false),
(false, 1, 3, 'EN',0 ,'2025-10-01',false);

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

-- ============================
-- USER 1 — SEPTEMBRE (10)
-- ============================
insert into metrics (creation_date, execution_time, input_token, output_token, total_token, user_id) values
('2025-09-15 16:40:18', 400, 600, 200, 800, 1),
('2025-09-18 11:29:08', 260, 320, 280, 600, 1),
('2025-09-20 08:14:55', 310, 410, 330, 740, 1),
('2025-09-24 19:48:18', 150, 230, 260, 490, 1),
('2025-09-28 07:56:44', 210, 500, 300, 800, 1);

-- ============================
-- USER 1 — OCTOBRE (10)
-- ============================
insert into metrics (creation_date, execution_time, input_token, output_token, total_token, user_id) values
('2025-10-02 13:22:09', 180, 400, 350, 750, 1),
('2025-10-04 17:16:32', 240, 520, 310, 830, 1),
('2025-10-07 20:40:18', 320, 300, 420, 720, 1),
('2025-10-11 11:12:03', 100, 120, 200, 320, 1),
('2025-10-14 09:23:45', 410, 560, 260, 820, 1),
('2025-10-17 22:14:50', 350, 470, 310, 780, 1),
('2025-10-19 06:33:28', 270, 340, 280, 620, 1),
('2025-10-22 12:48:11', 200, 420, 250, 670, 1),
('2025-10-26 15:03:09', 310, 380, 320, 700, 1),
('2025-10-29 18:54:44', 130, 200, 170, 370, 1);

-- ============================
-- USER 1 — NOVEMBRE (10)
-- ============================
insert into metrics (creation_date, execution_time, input_token, output_token, total_token, user_id) values
('2025-11-01 10:10:45', 230, 440, 300, 740, 1),
('2025-11-04 14:33:22', 350, 330, 410, 740, 1),
('2025-11-06 16:18:59', 200, 280, 260, 540, 1),
('2025-11-09 08:25:17', 150, 150, 300, 450, 1),
('2025-11-12 21:15:44', 420, 520, 350, 870, 1),
('2025-11-15 19:50:03', 390, 610, 220, 830, 1),
('2025-11-18 07:14:55', 260, 360, 290, 650, 1),
('2025-11-21 11:33:28', 180, 230, 310, 540, 1),
('2025-11-25 13:48:14', 300, 430, 280, 710, 1),
('2025-11-28 20:40:51', 140, 300, 200, 500, 1);





-- =======================================================
-- USER 2 — SEPTEMBRE (10)
-- =======================================================
insert into metrics (creation_date, execution_time, input_token, output_token, total_token, user_id) values
('2025-09-02 08:12:23', 310, 430, 360, 790, 2),
('2025-09-04 12:45:55', 200, 520, 260, 780, 2),
('2025-09-07 17:20:01', 150, 230, 340, 570, 2),
('2025-09-10 09:30:12', 180, 410, 300, 710, 2),
('2025-09-13 13:41:40', 300, 340, 320, 660, 2),
('2025-09-17 10:28:18', 220, 380, 310, 690, 2),
('2025-09-19 20:50:34', 400, 600, 260, 860, 2),
('2025-09-22 07:22:14', 110, 120, 220, 340, 2),
('2025-09-25 15:44:09', 350, 410, 390, 800, 2),
('2025-09-29 18:58:55', 260, 310, 310, 620, 2);

-- =======================================================
-- USER 2 — OCTOBRE (10)
-- =======================================================
insert into metrics (creation_date, execution_time, input_token, output_token, total_token, user_id) values
('2025-10-01 11:22:11', 170, 320, 260, 580, 2),
('2025-10-05 19:34:44', 340, 430, 310, 740, 2),
('2025-10-08 09:14:27', 210, 350, 240, 590, 2),
('2025-10-10 14:48:55', 290, 480, 310, 790, 2),
('2025-10-13 08:22:33', 400, 550, 300, 850, 2),
('2025-10-16 10:35:18', 260, 380, 280, 660, 2),
('2025-10-20 21:00:03', 300, 430, 330, 760, 2),
('2025-10-23 13:18:40', 200, 290, 270, 560, 2),
('2025-10-27 07:44:51', 330, 500, 340, 840, 2),
('2025-10-30 16:55:09', 120, 150, 190, 340, 2);

-- =======================================================
-- USER 2 — NOVEMBRE (10)
-- =======================================================
insert into metrics (creation_date, execution_time, input_token, output_token, total_token, user_id) values
('2025-11-03 10:32:02', 300, 480, 310, 790, 2),
('2025-11-05 18:25:41', 320, 520, 250, 770, 2),
('2025-11-08 14:12:31', 250, 330, 300, 630, 2),
('2025-11-11 09:44:22', 110, 120, 260, 380, 2),
('2025-11-14 21:56:08', 450, 600, 330, 930, 2),
('2025-11-17 06:22:45', 380, 510, 260, 770, 2),
('2025-11-20 11:34:55', 230, 300, 310, 610, 2),
('2025-11-23 12:48:14', 190, 230, 250, 480, 2),
('2025-11-26 16:15:33', 330, 490, 290, 780, 2),
('2025-11-29 19:08:21', 140, 200, 180, 380, 2);




-- =======================================================
-- USER 3 — SEPTEMBRE (10)
-- =======================================================
insert into metrics (creation_date, execution_time, input_token, output_token, total_token, user_id) values
('2025-09-01 07:15:33', 100, 150, 200, 350, 3),
('2025-09-03 12:18:29', 240, 400, 280, 680, 3),
('2025-09-06 16:55:11', 300, 520, 310, 830, 3),
('2025-09-09 10:44:02', 200, 330, 260, 590, 3),
('2025-09-12 21:30:59', 350, 470, 290, 760, 3),
('2025-09-16 08:25:21', 160, 200, 250, 450, 3),
('2025-09-18 19:52:14', 420, 600, 260, 860, 3),
('2025-09-21 11:45:33', 260, 360, 290, 650, 3),
('2025-09-26 15:12:09', 310, 410, 320, 730, 3),
('2025-09-30 18:30:44', 130, 180, 200, 380, 3);

-- =======================================================
-- USER 3 — OCTOBRE (10)
-- =======================================================
insert into metrics (creation_date, execution_time, input_token, output_token, total_token, user_id) values
('2025-10-03 10:14:55', 210, 350, 300, 650, 3),
('2025-10-06 13:48:28', 340, 480, 330, 810, 3),
('2025-10-09 20:44:03', 270, 390, 280, 670, 3),
('2025-10-12 07:11:40', 190, 260, 240, 500, 3),
('2025-10-15 14:33:59', 300, 420, 310, 730, 3),
('2025-10-18 11:42:22', 150, 200, 260, 410, 3),
('2025-10-21 16:18:09', 430, 560, 320, 880, 3),
('2025-10-24 22:50:45', 360, 440, 350, 790, 3),
('2025-10-27 06:44:19', 300, 330, 290, 620, 3),
('2025-10-31 17:34:03', 140, 180, 210, 390, 3);

-- =======================================================
-- USER 3 — NOVEMBRE (10)
-- =======================================================
insert into metrics (creation_date, execution_time, input_token, output_token, total_token, user_id) values
('2025-11-02 12:10:22', 240, 400, 260, 660, 3),
('2025-11-05 09:23:41', 300, 450, 310, 760, 3),
('2025-11-07 14:18:09', 260, 320, 310, 630, 3),
('2025-11-10 22:30:51', 420, 600, 350, 950, 3),
('2025-11-13 19:48:25', 310, 430, 300, 730, 3),
('2025-11-16 08:33:18', 150, 200, 260, 410, 3),
('2025-11-19 07:55:59', 380, 520, 280, 800, 3),
('2025-11-22 16:44:03', 290, 340, 310, 650, 3),
('2025-11-25 11:58:14', 210, 300, 240, 540, 3),
('2025-11-28 20:33:47', 140, 180, 190, 370, 3);