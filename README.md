## springbootsecurityt1

### Описание проекта
Веб-приложение для отслеживания метрик производительности с использованием Spring Kafka.
Включает в себя Producer для отправки метрик, Consumer для их обработки и анализа, 
а также REST API для просмотра метрик.
Учебный проект по заданию от T1 образовательной школы по Java направлению.

### Стэк проекта
* Java 17, Spring Boot (Web, Actuator, Test), Lombok, MongoDb, Kafka, KRaft, Docker, OpenApi (Swagger), Junit5


### Содержание:
1. [Техническое задание](#техническое-задание)
2. [API сервиса](#api-сервиса)
3. [Инструкция по запуску проекта](#инструкция-по-установке-и-запуску-проекта)
4. [Ссылки](#ссылки)
5. [Автор](#автор)

### Техническое задание
* [Тех-задание](docs/OpenSchoolDz3.txt)

### API сервиса
UI. Запустите сервис и перейдите по ссылке.
* MetricsProducerMicroservice [swagger-ui](http://localhost:8082/swagger-ui.html)
* MetricsConsumerMicroservice [swagger-ui](http://localhost:8081/swagger-ui.html)

#### JSON файл документации:
* MetricsProducerMicroservice [producer.json](docs/MetricsProducerMicroservice.json)
* MetricsConsumerMicroservice [consumer.json](docs/MetricsConsumerMicroservice.json)


### Инструкция по установке и запуску проекта
1. Установите Git: с официального сайта: https://git-scm.com/
2. Установите Docker c официального сайта: https://www.docker.com/
3. Клонируйте репозиторий: Откройте командную строку или терминал и выполните команду клонирования для репозитория
   GitHub. Например:
```
git clone https://github.com/KoryRunoMain/springkafkametricst1.git
```

4. Откройте проект в IDE: Откройте вашу среду разработки (IDE), такую как IntelliJ IDEA, Eclipse или NetBeans.
5. Запустите и соберите контейнер: точка входа называется "services" в [docker-compose.yml](docker-compose.yml)
6. Либо запустите командой из терминала находясь в директории с файлом "docker-compose.yml":
```   
docker-compose up
```

### Ссылки
1. MetricsProducerMicroservice
- [application.properties](MetricsProducerMicroservice/src/main/resources/application.properties)
- [test](MetricsProducerMicroservice/src/test/java/ru/koryruno/MetricsProducerMicroservice)
- [pom.xml](MetricsProducerMicroservice/pom.xml)

2. MetricsConsumerMicroservice
- [application.properties](MetricsConsumerMicroservice/src/main/resources/application.properties)
- [test](MetricsConsumerMicroservice/src/test/java/ru/koryruno/MetricsConsumerMicroservice)
- [pom.xml](MetricsConsumerMicroservice/pom.xml)

### Автор
* KoryRunoMain