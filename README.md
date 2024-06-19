# Hashtest
Сервис, позволяющий тестировать разные хэш-функции с помощью дифференциального анализа.

## Содержание
- [Технологии](#технологии)
- [Начало работы](#начало-работы)

## Технологии
- Java 21
- Spring
- Swagger 3
- PostgreSQL 16
- Lombok
- Maven
- Docker

## Начало работы
1. Соберите проект с помощью Maven:
```sh
mvn clean compile package
```

Если Maven отсутствует, воспользуйтесь:
```sh
./mvnw clean compile package
``` 
2. Запустите образ:
```sh
docker-compose up
```