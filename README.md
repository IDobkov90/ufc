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

1. Клонирайте репозиторито:
```bash
git clone https://github.com/IDobkov90/ufc.git
cd ufc
```

2. Конфигурирайте базата данни в `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ufcdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Стартирайте приложението:
```bash
./mvnw spring-boot:run
```

4. Отворете браузър на адрес: `http://localhost:8080`

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

