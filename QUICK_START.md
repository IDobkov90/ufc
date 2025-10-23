# 🚀 Quick Start - Developer Setup

## One-Time Setup (5 minutes)

### Step 1: Set Environment Variables

**Windows CMD:**
```cmd
set DB_USERNAME=root
set DB_PASSWORD=your_mysql_password_here
set JWT_SECRET=myVerySecretKeyThatIsAtLeast256BitsLongForHS256AlgorithmSecurityAndIsInAPropertiesFile
set SPRING_PROFILES_ACTIVE=dev
```

**Windows PowerShell:**
```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password_here"
$env:JWT_SECRET="myVerySecretKeyThatIsAtLeast256BitsLongForHS256AlgorithmSecurityAndIsInAPropertiesFile"
$env:SPRING_PROFILES_ACTIVE="dev"
```

### Step 2: Run Application
```bash
mvnw.cmd spring-boot:run
```

### Step 3: Access
- **Application:** http://localhost:8080
- **H2 Console:** http://localhost:8080/h2-console (dev only)

---

## 🎯 Available Profiles

| Profile | Purpose | Database | Logging |
|---------|---------|----------|---------|
| `dev` | Development | MySQL | DEBUG |
| `test` | Testing | H2 (in-memory) | WARN |
| `prod` | Production | MySQL | WARN/INFO |

**Switch profile:**
```cmd
set SPRING_PROFILES_ACTIVE=test
```

---

## 📁 Key Files

- `application.properties` - Main config (selects profile)
- `application-dev.properties` - Development settings
- `application-prod.properties` - Production settings
- `application-test.properties` - Test settings
- `.env.example` - Environment variable template

---

## 🔐 Security Checklist

- [ ] Environment variables set
- [ ] MySQL running on port 3306
- [ ] Database `ufcdb_dev` created
- [ ] Never commit `.env` files
- [ ] Use `dev` profile for local work

---

## 🆘 Common Issues

**App won't start - "Could not resolve placeholder"**
→ Set environment variables first!

**Database connection failed**
→ Check MySQL is running and credentials are correct

**JWT errors**
→ Ensure JWT_SECRET is set and at least 256 bits long

---

## 📚 Full Documentation

- [Environment Variables Guide](ENVIRONMENT_VARIABLES_GUIDE.md)
- [Security Configuration](SECURITY_CONFIGURATION_GUIDE.md)
- [Production Roadmap](PRODUCTION_READINESS_ROADMAP.md)

