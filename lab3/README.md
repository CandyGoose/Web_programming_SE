# Лабораторная работа #3
### Номер варианта: _20001_

Интерфейс:

![image](https://github.com/VeraKasianenko/Web_programming_SE/assets/112972833/f331c444-5530-4ac2-937f-ef9009567cbe)

![image](https://github.com/VeraKasianenko/Web_programming_SE/assets/112972833/f5520ba9-69c4-49de-82ad-a16acef408c7)

## Внимание! У разных вариантов разный текст задания!

![image](https://github.com/VeraKasianenko/VeraKasianenko/assets/112972833/6fbc57ba-e218-47a3-8d8d-ba84af3b2dea)

__Изменения данных__
- изменение X: p:slider (-5 … 5), шаг изменения - 0.1
- изменение Y: inputText (-3 … 5)
- изменение R: p:slider (1 … 4), шаг изменения - 0.25

Разработать приложение на базе JavaServer Faces Framework, которое осуществляет проверку попадания точки в заданную область на координатной плоскости.

Приложение должно включать в себя 2 facelets-шаблона - стартовую страницу и основную страницу приложения, а также набор управляемых бинов (managed beans), реализующих логику на стороне сервера.

__Стартовая страница должна содержать следующие элементы:__
- "Шапку", содержащую ФИО студента, номер группы и номер варианта.
- Интерактивные часы, показывающие текущие дату и время, обновляющиеся раз в 11 секунд.
- Ссылку, позволяющую перейти на основную страницу приложения.

__Основная страница приложения должна содержать следующие элементы:__
- Набор компонентов для задания координат точки и радиуса области в соответствии с вариантом задания. Может потребоваться использование дополнительных библиотек компонентов - ICEfaces (префикс "ace") и PrimeFaces (префикс "p"). Если компонент допускает ввод заведомо некорректных данных (таких, например, как буквы в координатах точки или отрицательный радиус), то приложение должно осуществлять их валидацию.
- Динамически обновляемую картинку, изображающую область на координатной плоскости в соответствии с номером варианта и точки, координаты которых были заданы пользователем. Клик по картинке должен инициировать сценарий, осуществляющий определение координат новой точки и отправку их на сервер для проверки её попадания в область. Цвет точек должен зависить от факта попадания / непопадания в область. Смена радиуса также должна инициировать перерисовку картинки.
- Таблицу со списком результатов предыдущих проверок.
- Ссылку, позволяющую вернуться на стартовую страницу.

__Дополнительные требования к приложению:__
- Все результаты проверки должны сохраняться в базе данных под управлением СУБД PostgreSQL.
- Для доступа к БД необходимо использовать ORM EclipseLink.
- Для управления списком результатов должен использоваться Application-scoped Managed Bean.
- Конфигурация управляемых бинов должна быть задана с помощью параметров в конфигурационном файле.
- Правила навигации между страницами приложения должны быть заданы в отдельном конфигурационном файле.

__Вопросы к защите лабораторной работы:__
1. Технология JavaServer Faces. Особенности, отличия от сервлетов и JSP, преимущества и недостатки. Структура JSF-приложения.
2. Использование JSP-страниц и Facelets-шаблонов в JSF-приложениях.
3. JSF-компоненты - особенности реализации, иерархия классов. Дополнительные библиотеки компонентов. Модель обработки событий в JSF-приложениях.
4. Конвертеры и валидаторы данных.
5. Представление страницы JSF на стороне сервера. Класс UIViewRoot.
6. Управляемые бины - назначение, способы конфигурации. Контекст управляемых бинов.
7. Конфигурация JSF-приложений. Файл faces-config.xml. Класс FacesServlet.
8. Навигация в JSF-приложениях.
9. Доступ к БД из Java-приложений. Протокол JDBC, формирование запросов, работа с драйверами СУБД.
10. Концепция ORM. Библиотеки ORM в приложениях на Java. Основные API. Интеграция ORM-провайдеров с драйверами JDBC.
11. Библиотеки ORM Hibernate и EclipseLink. Особенности, API, сходства и отличия.
12. Технология JPA. Особенности, API, интеграция с ORM-провайдерами.

# Запуск
Я использую 21 версию wildfly и локальный postgres (гайд по установке [тут](https://github.com/VeraKasianenko/Programming_2_term_SE/tree/main/lab7))
1) Откройте `standalone.xml` в `standalone/configuration` 
2) Необходимо заменить
```xml
<subsystem xmlns="urn:jboss:domain:datasources:6.0">
    <datasources>
        <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
            <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
            <driver>h2</driver>
            <security>
                <user-name>sa</user-name>
                <password>sa</password>
            </security>
        </datasource>
        <drivers>
            <driver name="h2" module="com.h2database.h2">
                <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
            </driver>
        </drivers>
    </datasources>
</subsystem>
```
на 
```xml
<subsystem xmlns="urn:jboss:domain:datasources:6.0">
    <datasources>
        <datasource jndi-name="java:jboss/datasources/postgresDS" pool-name="database-datasource-pg" enabled="true" use-java-context="false">
            <connection-url>jdbc:postgresql://pg:5432/studs</connection-url>
            <driver>postgresql-42.2.5.jar</driver>
            <security>
                <user-name>sXXXXXX</user-name>
                <password>password_from_pgpass</password>
            </security>
        </datasource>
    </datasources>
</subsystem>
```
а также удалить строку
```xml
<default-bindings context-service="java:jboss/ee/concurrency/context/default" datasource="java:jboss/datasources/ExampleDS" managed-executor-service="java:jboss/ee/concurrency/executor/default" managed-scheduled-executor-service="java:jboss/ee/concurrency/scheduler/default" managed-thread-factory="java:jboss/ee/concurrency/factory/default"/>
```
3) Поместить [postgresql-42.2.5.jar](https://github.com/VeraKasianenko/Web_programming_SE/tree/main/lab3/pg/postgresql-42.2.5.jar) в `standalone/deployments`
4) Создать необходимые [таблицы для бд](https://github.com/VeraKasianenko/Web_programming_SE/blob/main/lab3/result_table.sql)
5) Поменять логин и пароль в [конфигурации](https://github.com/VeraKasianenko/Web_programming_SE/blob/main/lab3/src/main/resources/META-INF/persistence.xml)
6) Далее запуск как во 2 лабе