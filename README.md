# counterpartyDirectory
Модель данных и сервисы для работы со «Справочником контрагентов»

# Запуск приложения
Команда для запуска приложения:
>gradlew bootrun

Либо запустите через "CounterpartyDirectoryApplication"

Команда для запуска тестов:
>gradlew test

# Swagger Api Documentation
После запуска документация доступна по ссылке: http://localhost:8080/swagger-ui/

# Доступ к базе данных
Приложение запускается с h2 in memory database
После запуска клиент БД доступе по ссылке: http://localhost:8080/h2-console/login.jsp

Креды для подключения к h2:

>JDBC url: jdbc:h2:file:./db/directory
>
>login : admin
>password : admin


Чтобы обратиться к справочнику перейдите на страницу 
http://localhost:8080/counterparties 