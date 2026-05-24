# 🎬 CinemaVault – Movie Catalog Application

A Spring Boot enterprise web application for managing a curated movie catalog, built with Spring MVC, Spring Security, JPA/Hibernate, and MySQL.

---

## Prerequisites

Make sure the following are installed before running the project:

| Tool     | Version            |
| -------- | ------------------ |
| Java JDK | 17 or higher       |
| Maven    | 3.8 or higher      |
| MySQL    | 8.0 or higher      |
| Git      | Any recent version |

---

## Database Setup

1. Open MySQL and log in as root:

    ```bash
    mysql -u root -p
    ```

2. Create the database:

    ```sql
    CREATE DATABASE movie_db;
    ```

3. Make sure your MySQL root password is set to `root`. If not, update it:
    ```sql
    ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
    FLUSH PRIVILEGES;
    ```

> The application will auto-create all tables on first run via Hibernate.

---

## Dataset Setup

1. Download the IMDb Top 1000 Movies CSV from Kaggle:
    - URL: https://www.kaggle.com/datasets/harshitshankhdhar/imdb-dataset-of-top-1000-movies-and-tv-shows
    - You need a free Kaggle account to download

2. Extract the ZIP file and locate `imdb_top_1000.csv`

3. Place the file at exactly this path inside the project:
    ```
    src/main/resources/data/imdb_top_1000.csv
    ```

> The app will automatically load all movies from this CSV into the database on first startup.

---

## Running the Project

### Step 1 — Clone the repository

```bash
git clone <your-repo-url>
cd movie-catalog
```

### Step 2 — Place the CSV dataset

Copy `imdb_top_1000.csv` into:

```
src/main/resources/data/
```

### Step 3 — Build the project

```bash
mvn clean install
```

Verify the CSV was picked up by checking:

```
target/classes/data/imdb_top_1000.csv
```

If this file is missing, double-check the CSV path and re-run `mvn clean install`.

### Step 4 — Run the application

```bash
mvn spring-boot:run
```

### Step 5 — Open in browser

```
http://localhost:8080
```

---

## First Time Use

1. Click **Sign Up** and create a new account
2. Log in with your credentials
3. The movie catalog loads automatically from the IMDb dataset
4. You can **Add**, **Edit**, **Delete**, and **Search** movies

---

## application.properties (reference)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movie_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

---

## Project Structure

```
src/
├── main/
│   ├── java/com/movieapp/
│   │   ├── config/          # Spring Security configuration
│   │   ├── controller/      # MVC Controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── model/           # JPA Entities
│   │   ├── repository/      # Spring Data JPA Repositories
│   │   └── service/         # Business logic + DataLoader
│   └── resources/
│       ├── data/            # imdb_top_1000.csv goes here
│       ├── static/css/      # Custom CSS
│       ├── static/images/   # Placeholder images
│       └── templates/       # Thymeleaf HTML templates
```

---

## Common Issues

**App starts but no movies appear**

- Check that `target/classes/data/imdb_top_1000.csv` exists
- Run `mvn clean install` again then restart

**Database connection error**

- Make sure MySQL is running
- Confirm the password is `root`
- Check that `movie_db` database exists

**Port 8080 already in use**

- Change the port in `application.properties`:
    ```properties
    server.port=8081
    ```

**Dialect warning in logs**

- Remove this line from `application.properties` if present:
    ```properties
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    ```
    Hibernate 6+ selects the dialect automatically.
