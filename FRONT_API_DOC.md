# Docker : build images

docker build -t cookbot:latest .

## Docker : run image



# CookBot API Frontend Documentation

This document provides instructions for frontend developers to interact with the CookBot API endpoints. All examples use JavaScript's `fetch` API.

The base URL for the development environment is `http://localhost:8080`.

## Authentication

First, a user must register and then log in. The login endpoint returns a JWT (JSON Web Token) that must be included in the `Authorization` header for all protected endpoints.

### Register a new user

-   **Endpoint**: `POST /api/auth/register`
-   **Description**: Creates a new user account.
-   **Authorization**: None required.

**Request Body:**

```json
{
  "username": "your_username",
  "password": "your_password"
}
```

**`fetch` Example:**

```javascript
fetch('http://localhost:8080/api/auth/register', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    username: 'your_username',
    password: 'your_password'
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Log in to get a token

-   **Endpoint**: `POST /api/auth/login`
-   **Description**: Authenticates a user and returns a JWT token.
-   **Authorization**: None required.

**Request Body:**

```json
{
  "username": "your_username",
  "password": "your_password"
}
```

**`fetch` Example:**

```javascript
fetch('http://localhost:8080/api/auth/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    username: 'your_username',
    password: 'your_password'
  })
})
.then(response => response.json())
.then(data => {
  console.log('Success:', data);
  // Store the token (e.g., in localStorage) to use for other requests
  localStorage.setItem('jwtToken', data.token);
})
.catch(error => console.error('Error:', error));
```

---

## Recipes

These endpoints manage user-created recipes.

**Note**: All recipe endpoints require an `Authorization` header with a Bearer Token.

### Create a new recipe

-   **Endpoint**: `POST /api/v1/recipe`
-   **Description**: Adds a new recipe for the authenticated user.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "name": "My Awesome Recipe",
  "durationMinutes": 45,
  "isFavorite": false,
  "ingredients": [
    { "name": "Flour", "quantity": 500, "unit": "g" },
    { "name": "Water", "quantity": 250, "unit": "ml" }
  ],
  "steps": [
    { "stepNumber": 1, "description": "Mix ingredients" },
    { "stepNumber": 2, "description": "Bake for 30 minutes" }
  ]
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/recipe', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    name: "My Awesome Recipe",
    durationMinutes: 45,
    isFavorite: false,
    ingredients: [
      { name: "Flour", quantity: 500, unit: "g" },
      { name: "Water", quantity: 250, unit: "ml" }
    ],
    steps: [
      { stepNumber: 1, "description": "Mix ingredients" },
      { stepNumber: 2, "description": "Bake for 30 minutes" }
    ]
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Get a recipe by its ID

-   **Endpoint**: `GET /api/v1/recipe/{id}`
-   **Description**: Retrieves a single recipe by its unique ID.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const recipeId = 1;

fetch(`http://localhost:8080/api/v1/recipe/${recipeId}`, {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Get all recipes for a user

-   **Endpoint**: `GET /api/v1/recipe/all`
-   **Description**: Retrieves all recipes created by the authenticated user.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/recipe/all', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Get all favorite recipes

-   **Endpoint**: `GET /api/v1/recipe/favorites`
-   **Description**: Retrieves all favorite recipes for the authenticated user.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/recipe/favorites', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Toggle a recipe's favorite status

-   **Endpoint**: `PATCH /api/v1/recipe/{id}`
-   **Description**: Switches the `isFavorite` status of a recipe.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const recipeId = 1;

fetch(`http://localhost:8080/api/v1/recipe/${recipeId}`, {
  method: 'PATCH',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Delete a recipe by its ID

-   **Endpoint**: `DELETE /api/v1/recipe/{id}`
-   **Description**: Deletes a recipe by its unique ID.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const recipeId = 2;

fetch(`http://localhost:8080/api/v1/recipe/${recipeId}`, {
  method: 'DELETE',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => {
  if (response.ok) {
    console.log('Recipe deleted successfully');
  } else {
    console.error('Failed to delete recipe');
  }
})
.catch(error => console.error('Error:', error));
```

### Update a recipe

-   **Endpoint**: `PUT /api/v1/recipe/{id}`
-   **Description**: Updates an existing recipe.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "name": "Updated Recipe Name",
  "durationMinutes": 55,
  "isFavorite": true,
  "ingredients": [
    { "name": "New Ingredient", "quantity": 1, "unit": "piece" }
  ],
  "steps": [
    { "stepNumber": 1, "description": "Do the new thing" }
  ]
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const recipeId = 1;

fetch(`http://localhost:8080/api/v1/recipe/${recipeId}`, {
  method: 'PUT',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    name: "Updated Recipe Name",
    durationMinutes: 55,
    isFavorite: true,
    ingredients: [
        { "name": "New Ingredient", "quantity": 1, "unit": "piece" }
    ],
    steps: [
        { "stepNumber": 1, "description": "Do the new thing" }
    ]
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

---

## AI-Powered Suggestions

These endpoints use AI to generate recipe ideas from ingredients or images.

**Note**: All AI endpoints require an `Authorization` header with a Bearer Token.

### Generate a full recipe from ingredients

-   **Endpoint**: `POST /api/v1/ai/recipe`
-   **Description**: Generates a complete recipe (name, duration, ingredients, steps) based on a list of ingredients.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "ingredients": [
    { "name": "Tomatoes", "quantity": 4, "unit": "units" },
    { "name": "Eggs", "quantity": 4, "unit": "units" },
    { "name": "Onion", "quantity": 1, "unit": "unit" }
  ]
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/ai/recipe', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    ingredients: [
      { name: "Tomatoes", quantity: 4, unit: "units" },
      { name: "Eggs", quantity: 4, "unit": "units" },
      { name: "Onion", quantity: 1, "unit": "unit" }
    ]
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Generate recipe titles from ingredients

-   **Endpoint**: `POST /api/v1/ai/recipeTitle`
-   **Description**: Generates a list of possible recipe titles based on a list of ingredients.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "ingredients": [
    { "name": "Tomatoes", "quantity": 4, "unit": "units" },
    { "name": "Eggs", "quantity": 4, "unit": "units" }
  ]
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/ai/recipeTitle', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    ingredients: [
      { name: "Tomatoes", quantity: 4, unit: "units" },
      { name: "Eggs", quantity: 4, unit: "units" }
    ]
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Generate recipe titles from an image

-   **Endpoint**: `POST /api/v1/ai/recipeTitle/image`
-   **Description**: Takes an image of ingredients and suggests recipe titles.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const fileInput = document.querySelector('input[type="file"]'); // Your file input element

const formData = new FormData();
formData.append('file', fileInput.files[0]);

fetch('http://localhost:8080/api/v1/ai/recipeTitle/image', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`
    // 'Content-Type' header is set automatically by the browser with FormData
  },
  body: formData
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Generate a full recipe from an image

-   **Endpoint**: `POST /api/v1/ai/recipe/image`
-   **Description**: Takes an image of ingredients and generates a full recipe.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const fileInput = document.querySelector('input[type="file"]'); // Your file input element

const formData = new FormData();
formData.append('file', fileInput.files[0]);

fetch('http://localhost:8080/api/v1/ai/recipe/image', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`
    // 'Content-Type' header is set automatically by the browser with FormData
  },
  body: formData
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Generate a recipe from a dish name

-   **Endpoint**: `POST /api/v1/ai/dish`
-   **Description**: Generates a recipe from a dish name (e.g., "Chocolate Cake").
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "dish": "Chocolate Cake"
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/ai/dish', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    dish: "Chocolate Cake"
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Get seasonal recipe suggestions

-   **Endpoint**: `GET /api/v1/ai/recipe/season`
-   **Description**: Suggests recipes based on the current season.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/ai/recipe/season', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

---

## AI Word Comparison (Demo)

This endpoint is for demonstration purposes.

### Compare two lists of words

-   **Endpoint**: `POST /api/auth/demo/compare`
-   **Description**: Compares two lists of strings and returns the strings that are unique to the first list.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "listA": ["apple", "banana", "orange"],
  "listB": ["banana", "grape"]
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/auth/demo/compare', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    listA: ["apple", "banana", "orange"],
    listB: ["banana", "grape"]
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data)) // Expected output: ["apple", "orange"]
.catch(error => console.error('Error:', error));
```

---

## Shopping Lists

These endpoints manage user shopping lists.

**Note**: All shopping list endpoints require an `Authorization` header with a Bearer Token.

### Get all shopping lists for a user

-   **Endpoint**: `GET /api/v1/shopping`
-   **Description**: Retrieves all shopping lists for the authenticated user.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/shopping', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Get a shopping list by ID

-   **Endpoint**: `GET /api/v1/shopping/{id}`
-   **Description**: Retrieves a single shopping list by its ID.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const shoppingListId = 1;

fetch(`http://localhost:8080/api/v1/shopping/${shoppingListId}`, {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Create a new shopping list

-   **Endpoint**: `POST /api/v1/shopping`
-   **Description**: Creates a new, empty shopping list.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "name": "My Weekly Groceries"
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/shopping', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    name: "My Weekly Groceries"
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Update a shopping list's name

-   **Endpoint**: `PUT /api/v1/shopping`
-   **Description**: Updates the name of an existing shopping list.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "id": 1,
  "name": "Updated Groceries"
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/shopping', {
  method: 'PUT',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    id: 1,
    name: "Updated Groceries"
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Delete a shopping list by ID

-   **Endpoint**: `DELETE /api/v1/shopping/{id}`
-   **Description**: Deletes a shopping list and all its items.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const shoppingListId = 2;

fetch(`http://localhost:8080/api/v1/shopping/${shoppingListId}`, {
  method: 'DELETE',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Add a single item to a shopping list

-   **Endpoint**: `POST /api/v1/shopping/item/{shoppingListId}`
-   **Description**: Adds one item to a specified shopping list.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "name": "Milk",
  "quantity": "1 Gallon"
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const shoppingListId = 1;

fetch(`http://localhost:8080/api/v1/shopping/item/${shoppingListId}`, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    name: "Milk",
    quantity: "1 Gallon"
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Add a list of items to a shopping list

-   **Endpoint**: `POST /api/v1/shopping/item/list/{shoppingListId}`
-   **Description**: Adds multiple items to a specified shopping list.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "itemList": [
    { "name": "Bread", "quantity": "1 loaf" },
    { "name": "Eggs", "quantity": "1 dozen" }
  ]
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const shoppingListId = 1;

fetch(`http://localhost:8080/api/v1/shopping/item/list/${shoppingListId}`, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    itemList: [
      { name: "Bread", quantity: "1 loaf" },
      { name: "Eggs", quantity: "1 dozen" }
    ]
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Delete an item from a shopping list

-   **Endpoint**: `DELETE /api/v1/shopping/item/delete/{id}`
-   **Description**: Deletes a single item by its ID from a shopping list.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const itemId = 3;

fetch(`http://localhost:8080/api/v1/shopping/item/delete/${itemId}`, {
  method: 'DELETE',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

---

## Metrics

These endpoints are for fetching AI usage metrics. They likely require admin privileges.

### Get metrics by user ID

-   **Endpoint**: `GET /api/v1/metric/{id}`
-   **Description**: Retrieves all metrics for a specific user.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const userId = 1;

fetch(`http://localhost:8080/api/v1/metric/${userId}`, {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Get metrics by date range

-   **Endpoint**: `GET /api/v1/metric`
-   **Description**: Retrieves all metrics within a specified date range.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Query Parameters:**
- `dateStart`: The start date (e.g., "2023-01-01")
- `dateEnd`: The end date (e.g., "2023-01-31")

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const startDate = '2023-01-01';
const endDate = '2023-01-31';

fetch(`http://localhost:8080/api/v1/metric?dateStart=${startDate}&dateEnd=${endDate}`, {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

---

## User Profile

These endpoints manage user-specific settings and preferences.

**Note**: All user profile endpoints require an `Authorization` header with a Bearer Token.

### Get user preferences

-   **Endpoint**: `GET /api/v1/user/preferences`
-   **Description**: Retrieves the allergen preferences for the authenticated user.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/user/preferences', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

**Example Response Body:**
```json
{
    "responseCode": 200,
    "responseMessage": "Operation done successfully",
    "data": [
        {
            "allergen": "Peanuts"
        },
        {
            "allergen": "Gluten"
        }
    ],
    "success": true
}
```

### Set user preferences

-   **Endpoint**: `POST /api/v1/user/preferences`
-   **Description**: Sets the allergen preferences for the authenticated user. This will replace any existing preferences.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
[
  { "allergen": "Peanuts" },
  { "allergen": "Gluten" }
]
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/user/preferences', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify([
    { "allergen": "Peanuts" },
    { "allergen": "Gluten" }
  ])
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Get user settings

-   **Endpoint**: `GET /api/v1/user/settings`
-   **Description**: Retrieves the settings for the authenticated user.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/user/settings', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

**Example Response Body:**
```json
{
    "responseCode": 200,
    "responseMessage": "Operation done successfully",
    "data": {
        "darkMode": true,
        "language": "EN",
        "nbPeople": 2
    },
    "success": true
}
```

### Update user settings

-   **Endpoint**: `PUT /api/v1/user/settings`
-   **Description**: Updates the settings for the authenticated user.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**Request Body:**

```json
{
  "darkMode": true,
  "language": "EN",
  "nbPeople": 2
}
```

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');

fetch('http://localhost:8080/api/v1/user/settings', {
  method: 'PUT',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    "darkMode": true,
    "language": "EN",
    "nbPeople": 2
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```
