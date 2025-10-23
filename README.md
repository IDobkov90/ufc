# UFC България - Форум за UFC и MMA

Официален форум за UFC, MMA и бойни спортове на български език.

## 🥊 Функционалности

- **Потребителска система** - Регистрация, вход и профили
- **Форум** - Създаване на теми и коментари
- **Категории** - Организирани дискусии по теми
- **Търсене** - Намиране на теми, коментари и потребители
- **Профили** - Персонализирани потребителски профили с статистики
- **Коментарна система** - Коментиране и дискусии

## 🚀 Технологии

- **Backend**: Spring Boot 3.2.1
- **Security**: Spring Security с JWT
- **Database**: MySQL
- **Template Engine**: Thymeleaf
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven

## 📋 Изисквания

- Java 17 или по-нова версия
- MySQL 8.0+
- Maven 3.6+

## ⚙️ Инсталация

### 1. Клонирайте репозиторито:
```bash
git clone https://github.com/IDobkov90/ufc.git
cd ufc
```

### 2. Конфигурирайте environment variables:

**Windows (CMD):**
```cmd
set DB_USERNAME=root
set DB_PASSWORD=your_password
set JWT_SECRET=your_very_long_and_random_secret_key_at_least_256_bits
set SPRING_PROFILES_ACTIVE=dev
```

**Windows (PowerShell):**
```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="your_very_long_and_random_secret_key_at_least_256_bits"
$env:SPRING_PROFILES_ACTIVE="dev"
```

**Linux/Mac:**
```bash
export DB_USERNAME="root"
export DB_PASSWORD="your_password"
export JWT_SECRET="your_very_long_and_random_secret_key_at_least_256_bits"
export SPRING_PROFILES_ACTIVE="dev"
```

📖 **Detailed configuration guide:** See [ENVIRONMENT_VARIABLES_GUIDE.md](ENVIRONMENT_VARIABLES_GUIDE.md)

### 3. Създайте MySQL база данни:
```sql
CREATE DATABASE ufcdb_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 4. Стартирайте приложението:
```bash
./mvnw spring-boot:run
```

### 5. Отворете браузър на адрес: 
`http://localhost:8080`

### 🔐 Security Note:
Never commit sensitive information like passwords or JWT secrets to version control!
All secrets should be set as environment variables.

## 🗂️ Структура на проекта

```
ufc/
├── src/
│   ├── main/
│   │   ├── java/com/example/ufc/
│   │   │   ├── config/          # Конфигурации
│   │   │   ├── controller/      # REST контролери
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA ентита
│   │   │   ├── repository/      # Spring Data репозитории
│   │   │   ├── service/         # Бизнес логика
│   │   │   └── util/            # Помощни класове
│   │   └── resources/
│   │       ├── static/css/      # CSS файлове
│   │       ├── templates/       # Thymeleaf шаблони
│   │       └── application.properties
│   └── test/                    # Тестове
└── pom.xml                      # Maven конфигурация
```

## 🎨 Функционалности по модули

### Потребителска система
- Регистрация с валидация
- Вход със Spring Security
- JWT автентикация за API
- Роли (USER, MODERATOR, ADMIN)

### Форум
- Създаване на теми по категории
- Коментиране на теми
- Редактиране и изтриване на собствени публикации
- View counter за теми

### Търсене
- Търсене на теми по заглавие и съдържание
- Търсене на коментари
- Търсене на потребители
- Филтриране по тип

### Потребителски профили
- Персонална информация
- Статистики (теми, коментари, репутация)
- История на активност
- Редактиране на профил

## 🔐 Сигурност

- Паролите се хешират с BCrypt
- JWT токени за API автентикация
- CSRF защита
- XSS защита чрез Thymeleaf

## 📝 Лиценз

MIT License

## 👨‍💻 Автор

**Ivan Dobkov**
- GitHub: [@IDobkov90](https://github.com/IDobkov90)

## 🤝 Принос

Приносите са добре дошли! Моля, отворете Issue или Pull Request.

---

⭐ Ако проектът ви харесва, оставете звезда!

