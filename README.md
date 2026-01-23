# log-batch-system
A simple log batch processing project built with Spring Boot.

---
## 🌱 Environment
- Java 17 ☕
- Gradle 9.2.1
- Spring Boot 3.5.9
- MySQL 8.0.39



## 🚀 Run with Docker

### Create `.env` file (not committed to Git)
Create a `.env` file in the project root with the following content:

```env
DB_URL=jdbc:p6spy:mysql://localhost:3306/your_db
DB_USER=batch
DB_PASSWORD=your_password
DB=org.mariadb.jdbc.Driver
```

### Environment Variables
Please refer to `.env.example` for environment variables.