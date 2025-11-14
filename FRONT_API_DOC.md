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
    isFavorite": false,
    ingredients: [
      { name: "Flour", quantity: 500, unit: "g" },
      { name: "Water", quantity: 250, unit: "ml" }
    ],
    steps: [
      { stepNumber: 1, description: "Mix ingredients" },
      { stepNumber: 2, description: "Bake for 30 minutes" }
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
    durationMinutes": 55,
    isFavorite: true,
    ingredients": [
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

-   **Endpoint**: `POST /api/auth/recipe`
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

fetch('http://localhost:8080/api/auth/recipe', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    ingredients: [
      { name: "Tomatoes", quantity: 4, unit: "units" },
      { name: "Eggs", quantity: 4, unit: "units" },
      { name: "Onion", quantity: 1, unit: "unit" }
    ]
  })
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch(error => console.error('Error:', error));
```

### Generate recipe titles from ingredients

-   **Endpoint**: `POST /api/auth/recipeTitle`
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

fetch('http://localhost:8080/api/auth/recipeTitle', {
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

-   **Endpoint**: `POST /api/auth/recipeTitle/image`
-   **Description**: Takes an image of ingredients and suggests recipe titles.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const fileInput = document.querySelector('input[type="file"]'); // Your file input element

const formData = new FormData();
formData.append('file', fileInput.files[0]);

fetch('http://localhost:8080/api/auth/recipeTitle/image', {
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

-   **Endpoint**: `POST /api/auth/recipe/image`
-   **Description**: Takes an image of ingredients and generates a full recipe.
-   **Authorization**: `Bearer YOUR_JWT_TOKEN`

**`fetch` Example:**

```javascript
const token = localStorage.getItem('jwtToken');
const fileInput = document.querySelector('input[type="file"]'); // Your file input element

const formData = new FormData();
formData.append('file', fileInput.files[0]);

fetch('http://localhost:8080/api/auth/recipe/image', {
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
