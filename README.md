# Дипломный проект профессии «Тестировщик ПО»

## О проекте
Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка. База данных хранит информацию о заказах, платежах, статусах карт, способах оплаты. Приложение предлагает купить тур по определённой цене с помощью двух способов:

1. Обычная оплата по дебетовой карте
2. Уникальная технология: выдача кредита по данным банковской карты

## Данные по картам обрабатываются отдельными сервисами:

- Сервис платежей - Payment Gate
- Кредитный сервис - Credit Gate


## Запуск приложения
1. Запустить необходимые базы данных. Параметры для запуска хранятся в файле docker-compose.yml. Для запуска необходимо ввести в терминале команду: docker-compose up -d --force-recreate --build  
2. В новой вкладке терминала ввести следующую команду в зависимости от базы данных:

- java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/base_mysql  - для базы данных MySQL

- java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/base_postgresql  - для базы данных PostgreSQL

3. Приложение должно запуститься по адресу http://localhost:8080/

## Запуск тестов
В новой вкладке терминала ввести команду, в зависимости от запущенной БД, в п.2 запуска:

В терминале IntelliJ IDEA выполнить команду для прогона автотестов: (Полный прогон автотестов занял 2 минуты.)
- для MySQL: .\gradlew clean test -D dbUrl=jdbc:mysql://localhost:3306/base_mysql -D dbUser=app -D dbPass=pass
- для Postgres: .\gradlew clean test -D dbUrl=jdbc:postgresql://localhost:5432/base_postgresql -D dbUser=app -D dbPass=pass


## Формирование отчета AllureReport
В новой вкладке терминала или нажав двойной Ctrl ввести команду:

- ./gradlew allureserve

## Ссылки на документацию:
1) [Дипломное задание](https://github.com/netology-code/qa-diploma/blob/master/README.md) 
2) [План автоматизации](https://github.com/TATYANA-QA42/Diplom-QA/blob/main/documentation/Plan.md)  
3) [Отчет по итогам тестрования](https://github.com/TATYANA-QA42/Diplom-QA/blob/main/documentation/Summary.md)  
4) [Отчет о проведенной автоматизации](https://github.com/TATYANA-QA42/Diplom-QA/blob/main/documentation/TestReport.md)  