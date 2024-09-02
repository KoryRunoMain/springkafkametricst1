## springbootsecurityt1

### Описание проекта
Веб-приложение для отслеживания метрик производительности с использованием Spring Kafka.
Включает в себя Producer для отправки метрик, Consumer для их обработки и анализа, 
а также REST API для просмотра метрик.
Учебный проект по заданию от T1 образовательной школы по Java направлению.

### Стэк проекта
* Java 17, Spring Boot (Web, Actuator, Test), Kafka, MongoDb, Docker, OpenApi (Swagger), Lombok, Junit5


### Содержание:
1. [Техническое задание](#техническое-задание)
2. [API сервиса](#api-сервиса)
3. [Валидация регистрационных данных](#регистрация-пользователей-валидация)
4. [Схема базы данных](#схема-базы-данных)
5. [Инструкция по запуску проекта](#пошаговая-инструкция-по-установке-и-запуску-проета)
5. [Ссылки](#ссылки)
6. [Автор](#автор)

### Техническое задание
* [Тех-задание](docs/OpenSchoolDz3.txt)

### API сервиса
UI. Запустите сервис и перейдите по ссылке: [swagger-ui](http://localhost:8080/swagger-ui.html)

* JSON файл документации:
  [springbootsecurityt1_specification](docs/springbootsecurityt1-openApi-specification.json)


### Регистрация пользователей (валидация)
* В проекте используется Spring AOP для валидации входных регистрационных данных.

##### Ограничения:
1. Логин (допускается использовать кириллицу, цифры. Остальные символы запрещены)
2. Email (формат почтового адреса: example@example.example)


### Схема базы данных
![img.png](docs/schema-diagram.png)

## Пошаговая инструкция по установке и запуску проета
1. Установите Git: Если у вас еще не установлен Git, загрузите и установите его с официального сайта
   Git: https://git-scm.com/.
2. Клонируйте репозиторий: Откройте командную строку или терминал и выполните команду клонирования для репозитория
   GitHub. Например:

```
git clone https://github.com/KoryRunoMain/springbootsecurityt1.git
```

3. Откройте проект в IDE: Откройте вашу среду разработки (IDE), такую как IntelliJ IDEA, Eclipse или NetBeans.
4. Импортируйте проект как Maven проект: Если вы используете IntelliJ IDEA,
   выберите File -> Open и выберите папку, в которую был склонирован репозиторий.
   IntelliJ IDEA должна автоматически распознать проект как Maven проект и импортировать его.
   В Eclipse вы можете выбрать File -> Import -> Existing Maven Projects и выбрать корневую папку проекта.
   В NetBeans вы можете выбрать File -> Open Project и выбрать папку проекта.
5. Запустите приложение: точка входа находится в классе "SpringbootaopApplication" помеченном аннотацией
   @SpringBootApplication.
   Либо запустите через Maven:

```
mvn spring-boot:run
```

### Ссылки
1. Используемые зависимости: [pom.xml](pom.xml)
2. Настройки проекта: [application.properties](src/main/resources/application.properties)
3. Схема базы данных: [schema.sql](src/main/resources/schema.sql)
4. Тесты: [springbootsecurityt1-test](src/test/java/ru/koryruno/springbootsecurityt1)

### Автор
* KoryRunoMain