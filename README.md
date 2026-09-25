# Expense Tracker

A full-stack personal expense management application built with Java, Spring Boot, Spring Security, JWT, Spring Data JPA, Hibernate, MySQL, HTML, CSS, and JavaScript.

The application allows users to securely manage their income and expenses, organize transactions using categories, create monthly budgets, view budget reports, and monitor their overall financial summary through a dashboard.

## Features

### Authentication & Security

- User registration and login
- BCrypt password hashing
- JWT-based authentication
- Spring Security protected REST APIs
- Stateless authentication
- User-specific resource access
- Ownership validation for transactions, categories, and budgets

### Transaction Management

- Add income and expense transactions
- View all transactions
- View individual transactions
- Update transactions
- Delete transactions
- Associate transactions with categories
- Associate transactions with users

### Category Management

- Create custom categories
- Support for INCOME and EXPENSE categories
- View user-specific categories
- View individual categories
- Delete categories
- Category ownership validation

### Budget Management

- Create monthly budgets
- Assign budgets to categories
- Prevent duplicate budgets for the same category and month
- View all budgets
- View individual budgets
- Delete budgets
- Generate budget reports
- Compare budgeted amount with actual spending

### Dashboard

- Total income
- Total expenses
- Current balance
- User-specific financial summary

### Frontend

- User registration
- User login
- Dashboard
- Transaction management
- Category management
- Budget management
- JWT-based authentication
- API integration using JavaScript Fetch API
- Simple interface using vanilla HTML, CSS and JavaScript

---

## Tech Stack

### Backend

| Technology | Purpose |
|------------|---------|
| Java | Backend programming language |
| Spring Boot | Backend framework |
| Spring MVC | REST API development |
| Spring Data JPA | Database access |
| Hibernate | ORM implementation |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication |
| BCrypt | Password hashing |
| Maven | Dependency management |

### Database

- MySQL

### Frontend

- HTML5
- CSS3
- JavaScript
- Fetch API
- Local Storage

---

## Architecture

The application follows a layered architecture.

    Frontend
    HTML / CSS / JS
           |
           | REST API
           v
    +-------------------+
    |    Controller     |
    |  HTTP Handling    |
    +---------+---------+
              |
              v
    +-------------------+
    |      Service      |
    |   Business Logic  |
    +---------+---------+
              |
              v
    +-------------------+
    |    Repository     |
    |  Database Access  |
    +---------+---------+
              |
              v
            MySQL

Authentication is handled separately through Spring Security and JWT:

    Login
      |
      v
    UserService
      |
      | BCrypt password verification
      v
    JwtService
      |
      v
    JWT Token
      |
      v
    Frontend
      |
      | Authorization: Bearer <token>
      v
    Spring Security
      |
      | JWT validation
      v
    Protected Controller
      |
      v
    Service
      |
      v
    Repository
      |
      v
    MySQL

---

## Project Structure

    ExpenseTracker/
    │
    ├── src/
    │   └── main/
    │       ├── java/
    │       │   └── com/
    │       │       └── expensetracker/
    │       │           │
    │       │           ├── config/
    │       │           │   └── SecurityConfig.java
    │       │           │
    │       │           ├── controller/
    │       │           │   ├── UserController.java
    │       │           │   ├── TransactionController.java
    │       │           │   ├── CategoryController.java
    │       │           │   ├── BudgetController.java
    │       │           │   └── DashboardController.java
    │       │           │
    │       │           ├── dto/
    │       │           │   ├── LoginRequest.java
    │       │           │   ├── LoginResponse.java
    │       │           │   └── RegisterRequest.java
    │       │           │
    │       │           ├── exception/
    │       │           │   ├── ResourceNotFoundException.java
    │       │           │   └── DuplicateResourceException.java
    │       │           │
    │       │           ├── model/
    │       │           │   ├── User.java
    │       │           │   ├── Category.java
    │       │           │   ├── CategoryType.java
    │       │           │   ├── Transaction.java
    │       │           │   ├── Budget.java
    │       │           │   ├── BudgetReport.java
    │       │           │   └── Dashboard.java
    │       │           │
    │       │           ├── repository/
    │       │           │   ├── UserRepository.java
    │       │           │   ├── CategoryRepository.java
    │       │           │   ├── TransactionRepository.java
    │       │           │   ├── BudgetRepository.java
    │       │           │   ├── DashboardRepository.java
    │       │           │   └── DashboardProjection.java
    │       │           │
    │       │           ├── security/
    │       │           │   └── JwtService.java
    │       │           │
    │       │           └── service/
    │       │               ├── UserService.java
    │       │               ├── TransactionService.java
    │       │               ├── CategoryService.java
    │       │               ├── BudgetService.java
    │       │               └── DashboardService.java
    │       │
    │       └── resources/
    │           └── application.properties
    │
    ├── frontend/
    │   ├── index.html
    │   ├── login.html
    │   ├── register.html
    │   ├── dashboard.html
    │   ├── css/
    │   └── js/
    │
    ├── pom.xml
    └── README.md

---

## Database Design

The application uses MySQL as the relational database.

The main entities are:

    User
       / | \
      /  |  \
     /   |   \
    v    v    v
    Category Transaction Budget
        \       |       /
         \      |      /
          \     |     /
           +----+----+

### Relationships

#### User → Category

One user can have multiple categories.

    User 1 ──────── * Category

#### User → Transaction

One user can have multiple transactions.

    User 1 ──────── * Transaction

#### Category → Transaction

One category can be associated with multiple transactions.

    Category 1 ──────── * Transaction

#### User → Budget

One user can create multiple budgets.

    User 1 ──────── * Budget

#### Category → Budget

A category can have multiple monthly budgets.

    Category 1 ──────── * Budget

---

## Main Tables

### users

Stores registered users.

Important fields:

    user_id
    username
    password_hash
    created_at

Passwords are stored as BCrypt hashes rather than plain text.

### categories

Stores user-specific categories.

Important fields:

    category_id
    user_id
    name
    type

Category types include:

    INCOME
    EXPENSE

### transactions

Stores income and expense transactions.

Important fields:

    transaction_id
    user_id
    category_id
    amount
    description
    transaction_date

### budgets

Stores monthly category budgets.

Important fields:

    budget_id
    user_id
    category_id
    budget_amount
    budget_month

A database-level unique constraint prevents duplicate budgets for the same:

    User + Category + Month

---

## REST API

All protected endpoints require a valid JWT.

The token is sent using:

    Authorization: Bearer <JWT_TOKEN>

### Authentication

#### Register

    POST /api/users/register

Example request:

    {
        "username": "testuser",
        "password": "password123"
    }

#### Login

    POST /api/users/login

Example request:

    {
        "username": "testuser",
        "password": "password123"
    }

Login returns a JWT token that is used for subsequent protected requests.

---

## Transaction APIs

### Add Transaction

    POST /api/transactions

Example:

    {
        "category": {
            "categoryId": 10
        },
        "amount": 1500,
        "description": "Food expenses",
        "transactionDate": "2026-08-10"
    }

The user is obtained from the authenticated JWT.

### Get All Transactions

    GET /api/transactions

### Get Transaction

    GET /api/transactions/{transactionId}

### Update Transaction

    PUT /api/transactions/{transactionId}

### Delete Transaction

    DELETE /api/transactions/{transactionId}

---

## Category APIs

### Add Category

    POST /api/categories

### Get Categories

    GET /api/categories?userId={userId}

### Get Category

    GET /api/categories/{categoryId}?userId={userId}

### Delete Category

    DELETE /api/categories/{categoryId}?userId={userId}

---

## Budget APIs

### Add Budget

    POST /api/budgets

### Get Budgets

    GET /api/budgets?userId={userId}

### Get Budget

    GET /api/budgets/{budgetId}?userId={userId}

### Delete Budget

    DELETE /api/budgets/{budgetId}?userId={userId}

### Budget Report

    GET /api/budgets/report?userId={userId}

The report provides information such as:

    Category
    Budget Amount
    Actual Spending

---

## Dashboard API

    GET /api/dashboard

The dashboard returns:

    Total Income
    Total Expense
    Balance

The authenticated user's ID is extracted from the JWT.

The frontend does not need to provide the user ID for this endpoint.

---

## Authentication Flow

The application uses JWT-based stateless authentication.

### Registration

    User
     |
     | username + password
     v
    UserController
     |
     v
    UserService
     |
     | BCrypt hash
     v
    UserRepository
     |
     v
    MySQL

### Login

    User
     |
     | username + password
     v
    UserController
     |
     v
    UserService
     |
     | Verify BCrypt password
     v
    JwtService
     |
     v
    JWT
     |
     v
    Frontend

### Protected Request

    Frontend
     |
     | Authorization: Bearer <JWT>
     v
    Spring Security
     |
     | Validate JWT
     v
    Controller
     |
     | Extract userId
     v
    Service
     |
     v
    Repository
     |
     v
    MySQL

---

## JWT Structure

The generated JWT contains:

    subject  → username
    userId   → authenticated user's ID
    issuedAt → token creation time
    expiresAt → token expiration time

The token is signed using:

    HS256

The same secret is used by the backend to generate and validate the token.

---

## Security

The application uses Spring Security to protect all endpoints except registration and login.

Public endpoints:

    /api/users/register
    /api/users/login

All other endpoints require authentication.

---

## Password Security

Passwords are never stored directly.

During registration:

    Plain Password
          |
          v
        BCrypt
          |
          v
    Password Hash
          |
          v
        MySQL

During login:

    Entered Password
          |
          v
    BCrypt Verification
          |
          v
    Stored Hash

---

## User Ownership & Authorization

User-specific resources are validated against the authenticated user.

For example, when creating a transaction, the application:

1. Gets the authenticated user's ID from the JWT.
2. Finds the requested category.
3. Checks whether the category belongs to that user.
4. Only then creates the transaction.

This prevents a user from using another user's category or accessing another user's transaction.

The same ownership principle is applied to budgets and other user-specific resources.

---

## Layered Architecture

The application separates responsibilities into three major layers.

### Controller Layer

Responsible for:

- Receiving HTTP requests
- Reading request parameters
- Reading request bodies
- Returning HTTP responses
- Calling service methods

Examples:

    TransactionController
    CategoryController
    BudgetController
    DashboardController
    UserController

### Service Layer

Responsible for:

- Business logic
- Validation
- Ownership checks
- Coordinating repositories
- Preparing data before persistence

Examples:

    TransactionService
    BudgetService
    CategoryService
    UserService
    DashboardService

### Repository Layer

Responsible for:

- Database interaction
- CRUD operations
- Custom queries

Repositories use Spring Data JPA.

For example:

    public interface TransactionRepository
            extends JpaRepository<Transaction, Integer>

This provides built-in methods such as:

    save()
    findById()
    findAll()
    delete()
    existsById()

---

## Spring Data JPA

Spring Data JPA reduces boilerplate database code.

The project also uses derived query methods.

Example:

    findByUserUserIdOrderByTransactionDateDesc(userId)

Spring Data JPA interprets the method name and generates the corresponding query.

Meaning:

> Find transactions belonging to a specific user and order them by transaction date in descending order.

---

## Budget Report

The budget report uses a native SQL query because it requires:

- Joins
- Aggregation
- Conditional logic
- Date-based filtering
- Grouping

Conceptually:

    Budget
       |
       +── Category
       |
       +── Transactions
                |
                v
           SUM(expenses)
                |
                v
          Budget Report

The report compares:

    Budget Amount
          vs
    Actual Spending

The service converts the SQL result into a BudgetReport DTO.

---

## Dashboard

The dashboard uses database aggregation to calculate:

    Total Income
    Total Expense

The balance is calculated as:

    Balance = Total Income - Total Expense

The dashboard repository uses a projection so that only the required aggregate values are retrieved instead of loading all transactions.

---

## Frontend Architecture

The frontend is intentionally simple and uses vanilla web technologies.

    HTML
     |
     | Structure
     v
    CSS
     |
     | Styling
     v
    JavaScript
     |
     | API requests
     v
    Spring Boot REST API

JavaScript uses the Fetch API to communicate with the backend.

### Login Flow

    Login Form
        |
        v
    JavaScript
        |
        | POST /api/users/login
        v
    Spring Boot
        |
        v
    JWT Response
        |
        v
    localStorage

For protected requests:

    JavaScript
        |
        | Get JWT from localStorage
        v
    Authorization Header
        |
        | Bearer <JWT>
        v
    Spring Boot

---

## Running the Project

### Prerequisites

Install:

- Java 17 or later
- Maven
- MySQL
- IntelliJ IDEA or another Java IDE
- Git

### 1. Clone the Repository

    git clone <YOUR_GITHUB_REPOSITORY_URL>
    cd ExpenseTracker

Replace `<YOUR_GITHUB_REPOSITORY_URL>` with the repository URL.

### 2. Create the Database

Open MySQL and create:

    CREATE DATABASE expense_tracker;

### 3. Configure MySQL

Update:

    src/main/resources/application.properties

Example:

    spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
    spring.datasource.username=root
    spring.datasource.password=YOUR_PASSWORD

    jwt.secret=YOUR_SECRET_KEY

Do not commit real credentials or production secrets to GitHub.

### 4. Run the Backend

Using Maven:

    mvn spring-boot:run

Or run the Spring Boot main class from IntelliJ IDEA.

The backend runs on:

    http://localhost:8080

### 5. Run the Frontend

Open the `frontend` directory using a local development server.

For example, using VS Code Live Server:

    frontend/index.html

The frontend communicates with:

    http://localhost:8080

---

## Example Usage

### 1. Register

    {
        "username": "testuser",
        "password": "password123"
    }

### 2. Login

    {
        "username": "testuser",
        "password": "password123"
    }

The response contains a JWT token.

### 3. Create a Category

Example:

    {
        "name": "Food",
        "type": "EXPENSE"
    }

### 4. Create a Transaction

    {
        "category": {
            "categoryId": 1
        },
        "amount": 1500,
        "description": "Food expenses",
        "transactionDate": "2026-08-10"
    }

### 5. Create a Budget

Example:

    {
        "category": {
            "categoryId": 1
        },
        "budgetAmount": 10000,
        "budgetMonth": "2026-08-01"
    }

### 6. View Dashboard

    GET /api/dashboard

Example response:

    {
        "totalIncome": 50000.00,
        "totalExpense": 27950.00,
        "balance": 22050.00
    }

---

## Key Design Decisions

### 1. Layered Architecture

The project follows:

    Controller → Service → Repository

This provides separation of concerns and makes the application easier to maintain.

### 2. JWT Authentication

JWT was chosen for stateless authentication.

The server does not need to maintain a traditional login session for every user.

### 3. BCrypt Password Hashing

BCrypt is used because passwords should never be stored as plain text.

### 4. User ID from JWT

Protected operations use the authenticated user's ID from the JWT rather than blindly trusting a user ID supplied by the client.

This improves security and prevents simple user-ID manipulation attacks.

### 5. Resource Ownership Validation

Transactions, categories and budgets are checked against the authenticated user's ID.

This prevents users from accessing resources belonging to other users.

### 6. DTOs

DTOs such as:

    LoginRequest
    RegisterRequest
    LoginResponse
    BudgetReport

separate API request/response structures from database entities.

This helps control what data is exposed through the REST API.

### 7. Database Constraints

Important business rules are also enforced at the database level.

For budgets:

    User + Category + Month

must be unique.

This provides an additional layer of data integrity.

### 8. Database Aggregation

Dashboard and budget reports use SQL aggregation where appropriate instead of retrieving unnecessary records into Java.

---

## Error Handling

The backend uses custom exceptions such as:

    ResourceNotFoundException
    DuplicateResourceException

Examples:

    User not found
    Category not found
    Transaction not found
    Budget not found
    Username already exists
    Budget already exists for this category and month

This keeps business errors separate from controller logic.

---

## Validation

The project uses Jakarta Bean Validation.

Examples include:

    @NotNull
    @NotBlank
    @Positive
    @Size

This allows invalid requests to be rejected before being processed further.

For example:

    Amount <= 0
          |
          v
       Rejected

    Empty category name
          |
          v
       Rejected

    Missing transaction date
          |
          v
       Rejected

---

## What I Learned

This project provided practical experience with:

- Java
- Spring Boot
- Spring MVC
- REST API development
- Dependency Injection
- Spring Data JPA
- Hibernate
- MySQL
- SQL joins
- SQL aggregation
- Spring Security
- JWT authentication
- BCrypt password hashing
- DTOs
- Entity relationships
- Bean Validation
- Exception handling
- Frontend-backend integration
- JavaScript Fetch API
- Local Storage
- Git
- GitHub

---

## Future Improvements

Possible improvements include:

- Expense and income charts
- Advanced transaction filtering
- Search functionality
- Pagination
- Recurring transactions
- CSV/PDF export
- Budget limit notifications
- Email notifications
- Refresh-token authentication
- Role-based authorization
- Docker support
- Automated tests
- CI/CD pipeline
- Cloud deployment
- Production-grade secret management

---

## Security Notes

This project is primarily intended for educational and portfolio purposes.

For production deployment, additional security measures should be considered, including:

- HTTPS
- Secure secret management
- Secure cookie/token strategies
- Refresh tokens
- Token revocation
- Rate limiting
- Input sanitization
- Security headers
- Database credential protection
- Production-grade logging and monitoring

Never commit:

    Database passwords
    JWT secrets
    API keys
    Private credentials

to the repository.

---

## Author

**Smit Bangar**

B.Tech Information Technology  
Pune Institute of Computer Technology (PICT)

---

## License

This project is intended for educational and portfolio purposes.