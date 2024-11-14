Установка и запуск проекта
Клонируйте репозиторий:

git clone https://github.com/sen9ka/fulfillment.git
cd fulfillment

Описание проекта
Проект представляет собой RESTful API для управления продуктами. Он реализован с использованием Spring Boot и обеспечивает основные операции, такие как получение списка продуктов, добавление, обновление и удаление продуктов, а также получение информации по статусу и стоимости продуктов.

Шаги для запуска проекта
1. Установка зависимостей
Убедитесь, что у вас установлен Maven. В корневом каталоге проекта выполните следующую команду для загрузки всех зависимостей:
mvn clean install

3. Настройка базы данных
Убедитесь, что у вас установлен PostgreSQL.
Создайте базу данных с именем fulfillment.
Миграция базы произойдет автоматически при запуске приложения при помощи flyway
Обновите файл src/main/resources/application.yaml с вашими данными для подключения к базе данных, если это необходимо.
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/fulfillment
    username: postgres
    password: postgres
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        show_sql: true
  flyway:
    url: jdbc:postgresql://localhost:5432/fulfillment
    user: postgres
    password: postgres
   
5. Запуск приложения
Для запуска приложения выполните следующую команду:
mvn spring-boot:run

Приложение будет доступно по адресу http://localhost:8080.

Основные операции API
1. Получение списка продуктов
Метод: GET
URL: /products
Описание: Получение списка всех продуктов.
2. Получение продукта по идентификатору, идентификатору центра и стоимости
Метод: GET
URL: /products/{productId}/{centerId}/{value}
Параметры:
productId - Идентификатор продукта
centerId - Идентификатор центра
value - Стоимость продукта
Описание: Получает продукт по указанным параметрам.
3. Обновление или добавление продукта
Метод: POST
URL: /products
Тело запроса: ProductDTO
Описание: Обновляет существующий продукт или добавляет новый.
4. Удаление продукта
Метод: DELETE
URL: /products
Тело запроса: ProductDTO 
Описание: Удаляет указанный продукт.
5. Получение списка продуктов по статусу
Метод: GET
URL: /products/{status}
Параметры:
status - Статус продукта
Описание: Получает список продуктов с указанным статусом.
6. Получение общего значения всех продуктов с состоянием Sellable
Метод: GET
URL: /products/value
Параметры:
centerId (необязательный) - Идентификатор центра
Описание: Получает общее значение всех продуктов с состоянием Sellable.

Документация API доступна по адресу:

Swagger UI: http://localhost:8080/fulfillment/swagger-ui.html
API Docs: http://localhost:8080/fulfillment/api-docs
