# Beacon Backend

## Overview
The backend of Beacon, a blogging website, is developed using Spring Boot. It provides the necessary APIs for user authentication, post management, and other functionalities required for a blogging platform. The backend interacts with a MySQL database that contains multiple tables to store data related to users, posts, comments, and more.

## Features
- User authentication and authorization
- Post creation, editing, and deletion
- Commenting on posts
- Liking posts
- Following users
- Saving posts
- Categorizing posts

## Database Schema
The backend uses a MySQL database with the following tables:
- user: Stores user information.
- user_role: Manages the relationship between users and their roles.
- follow: Tracks user follow relationships.
- likes: Stores information about post likes.
- posts: Contains data related to user posts.
- comment: Stores comments on posts.
- roles: Defines various roles for users.
- saved: Stores information about saved posts.
- categories: Manages post categories.

## Table Relationships
### User and Roles
- The user table stores user details.
- The roles table defines the different roles users can have.
- The user_role table establishes a many-to-many relationship between users and roles, indicating the roles assigned to each user.
### User and Posts
- The posts table contains a foreign key referencing the user table, indicating which user created each post. This establishes a one-to-many relationship, where one user can have multiple posts.
### Post and Comments
- The comment table contains a foreign key referencing the posts table, indicating which post each comment belongs to. This establishes a one-to-many relationship, where one post can have multiple comments.
### Post and Likes
- The likes table contains foreign keys referencing both the posts and user tables, indicating which user liked which post. This establishes a many-to-many relationship, where a post can be liked by multiple users and a user can like multiple posts.
### Post and Saved
- The saved table contains foreign keys referencing both the posts and user tables, indicating which user saved which post. This establishes a many-to-many relationship, where a post can be saved by multiple users and a user can save multiple posts.
### User and Follow
- The follow table contains foreign keys referencing the user table for both the follower and the followee, indicating which user follows which other user. This establishes a many-to-many relationship, where a user can follow multiple users and can be followed by multiple users.

## Setup Instructions
### Prerequisites
- Java 8 or higher
- Maven
- MySQL
  
## Steps to Setup
### 1. Clone the repository
```bash
git clone https://github.com/AmanWasti9/Blogging-App-Backend-.git
cd Blogging-App-Backend-
```
### 2. Create a MySQL database
```bash
CREATE DATABASE beacon_db;
```
### 3. Configure the database
- Open src/main/resources/application.properties
- Set your MySQL database credentials
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/beacon_db
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```
### 4. Build the project
```bash
mvn clean install
```
### 5. Run the application
```bash
mvn spring-boot:run
```

## API Endpoints
The backend provides various REST API endpoints for interacting with the application. These include endpoints for user registration, login, post management, commenting, liking, and following users.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request to contribute to the project.
















