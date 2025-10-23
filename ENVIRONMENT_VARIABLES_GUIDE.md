# Environment Variables Configuration Guide

## 🔐 Security Configuration for UFC Forum

This guide explains how to properly configure environment variables for different environments.

---

## 📋 Required Environment Variables

### Database Configuration
- `DB_URL` - Database connection URL
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password

### Security Configuration
- `JWT_SECRET` - Secret key for JWT token generation (minimum 256 bits)
- `JWT_EXPIRATION` - Token expiration time in milliseconds (optional, defaults to 3600000)

### Application Configuration
- `SPRING_PROFILES_ACTIVE` - Active profile (dev/test/prod)
- `PORT` - Server port (optional, defaults to 8080)

---

## 🖥️ Windows Configuration

### Option 1: System Environment Variables (Permanent)

1. Open System Properties → Advanced → Environment Variables
2. Add the following variables:

```
DB_URL=jdbc:mysql://localhost:3306/ufcdb
DB_USERNAME=your_username
DB_PASSWORD=your_secure_password
JWT_SECRET=your_very_long_and_random_secret_key_at_least_256_bits_long
SPRING_PROFILES_ACTIVE=dev
```

### Option 2: Command Prompt (Temporary - Current Session)

```cmd
set DB_URL=jdbc:mysql://localhost:3306/ufcdb
set DB_USERNAME=your_username
set DB_PASSWORD=your_secure_password
set JWT_SECRET=your_very_long_and_random_secret_key_at_least_256_bits_long
set SPRING_PROFILES_ACTIVE=dev

mvnw.cmd spring-boot:run
```

### Option 3: PowerShell (Temporary - Current Session)

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/ufcdb"
$env:DB_USERNAME="your_username"
$env:DB_PASSWORD="your_secure_password"
$env:JWT_SECRET="your_very_long_and_random_secret_key_at_least_256_bits_long"
$env:SPRING_PROFILES_ACTIVE="dev"

.\mvnw.cmd spring-boot:run
```

### Option 4: IntelliJ IDEA Run Configuration

1. Open Run → Edit Configurations
2. Select your Spring Boot application
3. Add to Environment Variables:
```
DB_URL=jdbc:mysql://localhost:3306/ufcdb;DB_USERNAME=your_username;DB_PASSWORD=your_secure_password;JWT_SECRET=your_very_long_and_random_secret_key;SPRING_PROFILES_ACTIVE=dev
```

---

## 🐧 Linux/macOS Configuration

### Option 1: Export in Shell (Temporary)

```bash
export DB_URL="jdbc:mysql://localhost:3306/ufcdb"
export DB_USERNAME="your_username"
export DB_PASSWORD="your_secure_password"
export JWT_SECRET="your_very_long_and_random_secret_key_at_least_256_bits_long"
export SPRING_PROFILES_ACTIVE="dev"

./mvnw spring-boot:run
```

### Option 2: Add to ~/.bashrc or ~/.zshrc (Permanent)

```bash
# Add to end of file
export DB_URL="jdbc:mysql://localhost:3306/ufcdb"
export DB_USERNAME="your_username"
export DB_PASSWORD="your_secure_password"
export JWT_SECRET="your_very_long_and_random_secret_key_at_least_256_bits_long"
export SPRING_PROFILES_ACTIVE="dev"

# Then reload
source ~/.bashrc  # or source ~/.zshrc
```

### Option 3: Create .env file (with dotenv-java)

```bash
# .env file (add to .gitignore!)
DB_URL=jdbc:mysql://localhost:3306/ufcdb
DB_USERNAME=your_username
DB_PASSWORD=your_secure_password
JWT_SECRET=your_very_long_and_random_secret_key_at_least_256_bits_long
SPRING_PROFILES_ACTIVE=dev
```

---

## 🐳 Docker Configuration

### docker-compose.yml

```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DB_URL=jdbc:mysql://db:3306/ufcdb
      - DB_USERNAME=ufcuser
      - DB_PASSWORD=${DB_PASSWORD}
      - JWT_SECRET=${JWT_SECRET}
    depends_on:
      - db

  db:
    image: mysql:8.0
    environment:
      - MYSQL_DATABASE=ufcdb
      - MYSQL_USER=ufcuser
      - MYSQL_PASSWORD=${DB_PASSWORD}
      - MYSQL_ROOT_PASSWORD=${DB_ROOT_PASSWORD}
    volumes:
      - mysql-data:/var/lib/mysql

volumes:
  mysql-data:
```

### .env file for Docker Compose (add to .gitignore!)

```
DB_PASSWORD=your_secure_db_password
DB_ROOT_PASSWORD=your_secure_root_password
JWT_SECRET=your_very_long_and_random_secret_key_at_least_256_bits_long
```

---

## ☁️ Cloud Deployment (Production)

### Heroku

```bash
heroku config:set DB_URL="your_database_url"
heroku config:set DB_USERNAME="your_username"
heroku config:set DB_PASSWORD="your_password"
heroku config:set JWT_SECRET="your_jwt_secret"
heroku config:set SPRING_PROFILES_ACTIVE="prod"
```

### AWS Elastic Beanstalk

Use Configuration → Software → Environment Properties

### Azure App Service

Use Configuration → Application Settings

### Google Cloud Platform

```bash
gcloud run services update ufc-forum \
  --set-env-vars DB_URL="your_database_url",\
DB_USERNAME="your_username",\
DB_PASSWORD="your_password",\
JWT_SECRET="your_jwt_secret",\
SPRING_PROFILES_ACTIVE="prod"
```

---

## 🔑 Generating a Secure JWT Secret

### Option 1: OpenSSL (Recommended)

```bash
openssl rand -base64 64
```

### Option 2: Java

```java
import java.security.SecureRandom;
import java.util.Base64;

SecureRandom random = new SecureRandom();
byte[] bytes = new byte[64];
random.nextBytes(bytes);
String secret = Base64.getEncoder().encodeToString(bytes);
System.out.println(secret);
```

### Option 3: Online Generator

Use a trusted password generator (minimum 256 bits / 32 characters)

---

## 📝 Environment-Specific Configuration

### Development Environment

```bash
# Local MySQL
DB_URL=jdbc:mysql://localhost:3306/ufcdb_dev
DB_USERNAME=root
DB_PASSWORD=your_dev_password
JWT_SECRET=dev_secret_key_for_development_only
SPRING_PROFILES_ACTIVE=dev
```

### Testing Environment

```bash
# H2 in-memory database (configured in application-test.properties)
SPRING_PROFILES_ACTIVE=test
```

### Production Environment

```bash
# Managed MySQL (e.g., AWS RDS, Azure MySQL)
DB_URL=jdbc:mysql://production-db.example.com:3306/ufcdb
DB_USERNAME=ufcuser_prod
DB_PASSWORD=your_very_secure_production_password
JWT_SECRET=your_production_jwt_secret_minimum_256_bits_long
SPRING_PROFILES_ACTIVE=prod
PORT=8080
```

---

## ✅ Verification

After setting environment variables, verify they are loaded:

### Check in Application

Add this to your main application or a @PostConstruct method:

```java
@Value("${spring.profiles.active}")
private String activeProfile;

@PostConstruct
public void logProfile() {
    log.info("Active Profile: {}", activeProfile);
    log.info("JWT Secret configured: {}", jwtSecret != null && !jwtSecret.isEmpty());
}
```

### Check via Endpoint (Development Only!)

```java
@GetMapping("/actuator/env")
public Map<String, String> getEnvironment() {
    // Only enable in development!
    return Map.of(
        "profile", environment.getActiveProfiles()[0],
        "dbUrl", environment.getProperty("spring.datasource.url")
    );
}
```

---

## 🚨 Security Best Practices

### ✅ DO:
- Use strong, randomly generated secrets (minimum 256 bits)
- Store secrets in environment variables or secret managers
- Use different secrets for each environment
- Rotate secrets regularly (every 90 days)
- Use HTTPS in production
- Enable database SSL connections in production

### ❌ DON'T:
- Commit secrets to version control
- Use the same secrets across environments
- Share production secrets via email/chat
- Use weak or predictable secrets
- Log secret values
- Expose secrets in error messages

---

## 🔒 Secret Management Solutions

For production, consider using:

- **AWS Secrets Manager** - For AWS deployments
- **Azure Key Vault** - For Azure deployments
- **Google Secret Manager** - For GCP deployments
- **HashiCorp Vault** - Platform-agnostic
- **Spring Cloud Config Server** - Centralized configuration

---

## 📋 Quick Start Checklist

- [ ] Create strong JWT secret (minimum 256 bits)
- [ ] Set database credentials as environment variables
- [ ] Configure appropriate profile (dev/test/prod)
- [ ] Verify application starts successfully
- [ ] Test authentication flow
- [ ] Remove any hardcoded credentials
- [ ] Add .env file to .gitignore
- [ ] Document secrets location for team (not the actual secrets!)

---

## 🆘 Troubleshooting

### Problem: Application won't start - "Could not resolve placeholder 'DB_PASSWORD'"

**Solution:** Ensure DB_PASSWORD environment variable is set before running the application.

### Problem: Authentication fails with JWT errors

**Solution:** Verify JWT_SECRET is set and is at least 256 bits (32 characters) long.

### Problem: Database connection refused

**Solution:** Check DB_URL, DB_USERNAME, and DB_PASSWORD are correct and database is running.

### Problem: Using wrong profile

**Solution:** Check SPRING_PROFILES_ACTIVE is set correctly. Default is 'dev'.

---

**Last Updated:** 2025-10-23  
**Version:** 1.0

