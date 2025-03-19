# 🔗 Shorti.fy - Backend (🚀 In Progress)

    ✨ URL Shortening API with Authentication, Expiration & Security

## 📝 Overview

    This repository contains the backend for Shorti.fy, a URL shortener built with Spring Boot and PostgreSQL. It provides a secure and efficient API for managing shortened links and user authentication, offering features like custom short URLs, expiration dates, click tracking, password protection, and QR code generation (coming soon).

---


### 🚀 Features

#### 🔑 Authentication

- Sign In & Sign Up – Secure authentication using JWT.

- Google OAuth2 Login – Seamless login via Google.

- Forgot Password – Users can reset their password securely.

#### 🔗 URL Shortening

- Convert Long URLs to Short URLs – Generates unique short links.

- Expiration Dates – Optional expiration dates to disable links after a set period.

#### ✏️ URL Management

- Edit Shortened URLs – Update the destination URL.

- Delete URLs – Remove shortened links permanently.

#### 🔒 Password-Protected Short URLs

- Secure Access – Set a password for short links.

- Password Validation – Users must enter the correct password to access the original URL.

#### 📊 Click Tracking & Analytics

- Track link clicks – Count total visits.

- Visitor insights – Log masked IP addresses, device info, and location.


#### 📌 QR Code Generation (Coming Soon)

- Auto-generated QR Codes – Each shortened URL gets a QR code.

- Downloadable QR Codes – Users can download QR codes for easy sharing.


#### 🛠 Technologies Used

- Backend: Spring Boot (Spring Security, Spring Data JPA, JWT, OAuth2)

- Database: PostgreSQL

- Caching: Caffeine Cache (to optimize URL lookups and redirections)

- Authentication: JWT-based authentication with Google OAuth2 support

---


[//]: # (### 🏗️ Project Setup)

[//]: # ()
[//]: # (#### Prerequisites)

[//]: # ()
[//]: # (Ensure you have the following installed:)

[//]: # (- Java 23)

[//]: # (- Maven)

[//]: # (- PostgreSQL)

[//]: # ()
[//]: # (#### Installation)

[//]: # ()
[//]: # (1. **Clone the repository:**)

[//]: # (   1. ``git clone https://github.com/your-repo/shortify-backend.git``)

[//]: # (   2. ``cd shortify-backend```)

[//]: # ()
[//]: # (2. **Configure environment variables or update application.properties:**)

[//]: # (    ``spring.datasource.url=jdbc:postgresql://localhost:5432/shortify``)

[//]: # (    ``spring.datasource.username=your_db_user``)

[//]: # (    ``spring.datasource.password=your_db_password``)

[//]: # (    ``jwt.secret.key=your_secret_key``)

[//]: # (    ``jwt.expiration=86400000 # 24 hours in milliseconds``)

[//]: # ()
[//]: # (3. **Run the application:**)

[//]: # (    ``mvn spring-boot:run``)

[//]: # (4. **The API will be available at http://localhost:8080.**)

---

[//]: # (🔥 API Endpoints)

[//]: # ()
[//]: # (🔑 Authentication)

[//]: # ()
[//]: # (POST /auth/register – Register a new user.)

[//]: # ()
[//]: # (POST /auth/login – Authenticate and get JWT token.)

[//]: # ()
[//]: # (POST /auth/oauth/google – Login using Google OAuth.)

[//]: # ()
[//]: # (POST /auth/forgot-password – Request password reset.)

[//]: # ()
[//]: # (🔗 URL Shortening)

[//]: # ()
[//]: # (POST /urls/shorten – Shorten a long URL.)

[//]: # ()
[//]: # (GET /urls/{shortCode} – Retrieve the original URL and redirect.)

[//]: # ()
[//]: # (PATCH /urls/{id} – Update a shortened URL.)

[//]: # ()
[//]: # (DELETE /urls/{id} – Delete a shortened URL.)

[//]: # ()
[//]: # (📊 Analytics)

[//]: # ()
[//]: # (GET /urls/{id}/stats – Get analytics for a specific short URL.)

👨‍💻 Developed & maintained by *Jon Arbell De Ocampo*