# Security Configuration Guide for UFC Forum

## 🔐 Phase 1 Implementation Complete - Security Hardening

This guide explains the security enhancements implemented in Phase 1.

---

## 📁 Files Created

### 1. Environment-Specific Configuration Files

- **`application-dev.properties`** - Development environment
- **`application-prod.properties`** - Production environment  
- **`application-test.properties`** - Testing environment
- **`application.properties`** - Base configuration (profile selector)

### 2. Security Documentation

- **`ENVIRONMENT_VARIABLES_GUIDE.md`** - Complete guide for setting environment variables
- **`.env.example`** - Template for environment variables

---

## 🚀 How to Use

### For Development (Local Machine)

1. **Copy the environment template:**
   ```bash
   copy .env.example .env
   ```

2. **Edit `.env` file with your credentials:**
   ```properties
   DB_URL=jdbc:mysql://localhost:3306/ufcdb_dev
   DB_USERNAME=root
   DB_PASSWORD=your_password_here
   JWT_SECRET=generate_a_secure_random_key_here
   SPRING_PROFILES_ACTIVE=dev
   ```

3. **Set environment variables** (Windows CMD):
   ```cmd
   set DB_USERNAME=root
   set DB_PASSWORD=your_mysql_password_here
   set JWT_SECRET=myVerySecretKeyThatIsAtLeast256BitsLongForHS256AlgorithmSecurityAndIsInAPropertiesFile
   set SPRING_PROFILES_ACTIVE=dev
   ```

4. **Run the application:**
   ```bash
   mvnw.cmd spring-boot:run
   ```

### For Production

1. **Set environment variables on your server:**
   ```bash
   export DB_URL="jdbc:mysql://your-prod-server:3306/ufcdb"
   export DB_USERNAME="ufcuser"
   export DB_PASSWORD="your_secure_production_password"
   export JWT_SECRET="your_very_strong_random_secret_minimum_256_bits"
   export SPRING_PROFILES_ACTIVE=prod
   ```

2. **Deploy your application:**
   ```bash
   java -jar ufc-0.0.1-SNAPSHOT.jar
   ```

---

## 🔒 Security Improvements Made

### 1. ✅ Externalized Sensitive Data
- Database credentials removed from version control
- JWT secret removed from properties file
- All secrets now use environment variables

### 2. ✅ Environment Separation
- **Development:** Verbose logging, auto-create database, H2 console enabled
- **Production:** Minimal logging, validate schema only, security hardened
- **Test:** H2 in-memory database, fast startup

### 3. ✅ Database Connection Pooling (HikariCP)

**Development Settings:**
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
```

**Production Settings:**
```properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.leak-detection-threshold=60000
```

### 4. ✅ Enhanced Security Headers (Production)
- HTTP-only cookies
- Secure cookies (HTTPS only)
- 30-minute session timeout
- Compression enabled for better performance

### 5. ✅ Improved .gitignore
- `.env` files are now ignored
- Sensitive configuration protected
- Key files and certificates ignored

---

## 🎯 Key Configuration Differences by Environment

| Setting | Development | Production | Test |
|---------|-------------|------------|------|
| Logging Level | DEBUG | WARN/INFO | WARN |
| SQL Logging | Enabled | Disabled | Enabled |
| H2 Console | Enabled | Disabled | Enabled |
| DDL Auto | update | validate | create-drop |
| Error Details | Full | Minimal | Full |
| Pool Size | 10 | 20 | 5 |
| Session Timeout | Default | 30 min | N/A |

---

## 🔐 JWT Configuration

### Generate a Secure JWT Secret

**Option 1: OpenSSL (Recommended)**
```bash
openssl rand -base64 64
```

**Option 2: PowerShell**
```powershell
$bytes = New-Object byte[] 64
(New-Object Security.Cryptography.RNGCryptoServiceProvider).GetBytes($bytes)
[Convert]::ToBase64String($bytes)
```

**Requirements:**
- Minimum 256 bits (32 bytes)
- Randomly generated
- Different for each environment
- Never commit to version control

---

## 📊 Connection Pool Tuning

### Understanding HikariCP Settings

**maximum-pool-size:** Maximum number of connections in the pool
- Dev: 10 (lower resource usage)
- Prod: 20 (handle more concurrent users)

**minimum-idle:** Minimum number of idle connections
- Dev: 5
- Prod: 10 (faster response to sudden load)

**connection-timeout:** Maximum time to wait for a connection (ms)
- Dev: 20 seconds
- Prod: 30 seconds

**idle-timeout:** Maximum time a connection can be idle
- Dev: 5 minutes
- Prod: 10 minutes

**max-lifetime:** Maximum lifetime of a connection
- Dev: 20 minutes
- Prod: 30 minutes

**leak-detection-threshold:** Time before connection leak is reported
- Dev: Not set
- Prod: 60 seconds (catch connection leaks)

---

## ⚠️ Important Security Warnings

### Before Going to Production:

1. **Change All Secrets:**
   - Generate NEW JWT secret for production
   - Use STRONG database password (not the dev password!)
   - Never reuse development credentials

2. **Verify Configuration:**
   ```bash
   # Check active profile
   curl http://localhost:8080/actuator/info
   
   # Verify no secrets in logs
   tail -f logs/ufc-forum.log | grep -i secret
   ```

3. **Enable HTTPS:**
   - Obtain SSL certificate
   - Configure in application-prod.properties
   - Redirect HTTP to HTTPS

4. **Database Security:**
   - Use SSL for database connections
   - Restrict database access by IP
   - Use strong authentication

---

## 🧪 Testing Your Configuration

### Test Development Profile:
```bash
set SPRING_PROFILES_ACTIVE=dev
mvnw.cmd spring-boot:run
```

### Test Production Profile Locally:
```bash
set SPRING_PROFILES_ACTIVE=prod
set DB_URL=jdbc:mysql://localhost:3306/ufcdb
set DB_USERNAME=root
set DB_PASSWORD=your_password
set JWT_SECRET=your_strong_secret_here
mvnw.cmd spring-boot:run
```

### Verify Active Profile:
Check the console output on startup:
```
Active Profile: dev
```

---

## 📋 Security Checklist

- [x] Sensitive data externalized to environment variables
- [x] Environment-specific configurations created
- [x] Database connection pooling configured
- [x] .gitignore updated to protect secrets
- [x] Documentation created for team
- [x] Production logging minimized
- [x] Security headers configured
- [ ] Generate strong JWT secret for production
- [ ] Set up actual environment variables on production server
- [ ] Enable HTTPS in production
- [ ] Configure database SSL
- [ ] Set up monitoring and alerts
- [ ] Implement rate limiting (Phase 3)
- [ ] Add comprehensive tests (Phase 2)

---

## 🆘 Troubleshooting

### Application won't start
**Error:** "Could not resolve placeholder 'DB_PASSWORD'"
**Solution:** Set DB_PASSWORD environment variable before running

### JWT authentication fails
**Error:** "JWT signature does not match"
**Solution:** Ensure JWT_SECRET is set and consistent across app instances

### Database connection timeout
**Error:** "HikariPool - Connection is not available"
**Solution:** Check database is running and credentials are correct

---

## 📚 Next Steps

After completing Phase 1, proceed to:

1. **Phase 2: Testing** - Write comprehensive tests
2. **Phase 3: Monitoring** - Add Spring Boot Actuator and health checks
3. **Phase 4: Performance** - Implement caching

See `PRODUCTION_READINESS_ROADMAP.md` for detailed timeline.

---

## 🤝 Team Guidelines

### For Developers:
1. Never commit `.env` files
2. Use `dev` profile for local development
3. Don't share production secrets via chat/email
4. Generate your own JWT secret for local development

### For DevOps:
1. Set environment variables securely on servers
2. Use secret management tools (Vault, AWS Secrets Manager)
3. Rotate secrets every 90 days
4. Monitor logs for security issues

---

**Implemented:** Phase 1 - Security & Configuration  
**Date:** 2025-10-23  
**Status:** ✅ Complete  
**Next Phase:** Testing (Phase 2)

