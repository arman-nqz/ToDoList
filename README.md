# ToDoList

A desktop To-Do List application built with JavaFX. The app includes user authentication and connects to a MySQL database to create, read, update, and delete users' tasks.

<img width="1920" height="1080" alt="Untitled" src="https://github.com/user-attachments/assets/23dd2330-91a1-4cbd-a244-2c7c03deab16" />


## Features

- User sign up with first name, last name, username, password, location, and gender
- User login with database-backed credential validation
- Create new tasks with a title, description, and creation date
- View all tasks for the logged-in user
- Update existing tasks
- Delete tasks from the list
- Refresh the task list after changes
- JavaFX/FXML interface with JFoenix controls

## Tech Stack

- Java 21
- JavaFX 21
- FXML
- Maven
- MySQL
- JDBC
- JFoenix

## Project Structure

```text
src/main/java/ar/todolist
- Controllers/      # JavaFX controllers for login, sign up, task list, add, edit, and task cells
- Database/         # Database connection settings, table constants, and SQL operations
- animations/       # UI animation helper
- model/            # User and Task data models
- HelloApplication.java
- Launcher.java

src/main/resources/ar/todolist
- login.fxml
- signUp.fxml
- addItem.fxml
- addItemForm.fxml
- list.fxml
- cell.fxml
- updateTaskForm.fxml
```

## Getting Started

### Prerequisites

Make sure you have the following installed:

- JDK 21
- MySQL Server
- Maven, or use the included Maven wrapper
- MySQL Connector/J available to the project

### Database Setup

The application expects a MySQL database named `todo` with `users` and `tasks` tables.

Example schema:

```sql
CREATE DATABASE IF NOT EXISTS todo;
USE todo;

CREATE TABLE users (
    userid INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    location VARCHAR(100),
    gender VARCHAR(20)
);

CREATE TABLE tasks (
    taskid INT AUTO_INCREMENT PRIMARY KEY,
    userid INT NOT NULL,
    datecreated TIMESTAMP NOT NULL,
    description TEXT,
    task VARCHAR(255) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(userid)
        ON DELETE CASCADE
);
```

Update the database connection values in:

```text
src/main/java/ar/todolist/Database/Configs.java
```

The configuration includes the database host, port, username, password, and database name.

## Running the Application

From the project root, run:

```bash
./mvnw clean javafx:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd clean javafx:run
```

The application starts on the login screen. New users can sign up, then log in to manage their personal task list.

## Main Screens

- Login screen
- Sign up screen
- Add task screen
- Task list screen
- Update task screen

## What I Learned

This project helped me practice:

- Building desktop applications with JavaFX and FXML
- Connecting Java applications to a MySQL database with JDBC
- Structuring an application with controllers, models, and a database access layer
- Handling user authentication and user-specific data
- Implementing CRUD operations for application data

## Future Improvements

- Hash user passwords before storing them
- Move database credentials out of source code into environment variables or a config file
- Add form validation and clearer error messages
- Add unit tests for the model and database logic
- Improve the UI layout and responsiveness
- Package the application as an executable desktop app

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
