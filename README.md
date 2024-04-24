# Homework Y_LAB: Training Diary

> Приложение для ведения дневника тренировок. Скоро лето и еще не поздно привести себя в порядок! Данное приложение
> поможет вам нагнать темп и следить за своим здоровьем. Записывайте свои тренировки, чтобы анализировать свой прогресс.
> И помните, что главное - это регулярность.
>
>
> # Ссылка на текущий PR
> 1. [Link PR №1](https://github.com/Jon7even/homework_ylab/pull/1)
>
>
>
> # Список ссылок на ТЗ
> 1. [Link HW №1](https://github.com/Jon7even/homework_ylab/tree/main/docs/tasks/technical-task-1.md)
> 2. [Link HW №2](https://github.com/Jon7even/homework_ylab/tree/main/docs/tasks/technical-task-2.md)
>
>
>

> [!IMPORTANT]
> Создано с помощью:
> - Java 17
> - Lombok
> - Mapstruct
> - JUnit 5
> - Mockito 5
> - Maven
> - Docker
> - Test-containers
>

> [!NOTE]
> Что это? Это консольное приложение для тренировок. Оно позволяет вести учет своих тренировок. Вы сможете запрашивать
> важную для вас статистику, например количество потраченных калорий в течение тренировки, ваш вес, длительность
> тренировки, сколько вы отдыхали и сколько подходов/км сделали/прошли. Это приложение поможет достичь результата
> с помощью регулярности. Мы не стоим на месте и развиваемся дальше, поэтому уже сегодня наше приложение можно запустить
> на любой машине, где есть Java 17 и Docker.
>

Это первый опыт разработки на `Clean Architecture`. Стоит отменить, что с самого начала (в отличие от других)
ощутилась простота и скорость разработки. Слои имеют `слабую связанность`, благодаря чему можно внедрять новый
или тестировать текущий слой без проблем. Разработка велась от ядра `domain` до слоя `presentation`. Фактически
находясь в любом слое можно разрабатывать его и улучшать по отдельности - в этом увидел огромную силу
`чистой архитектуры`.

## Архитектура приложения

![Архитектура приложения на схеме](/docs/images/architecture.jpg)

## Взаимодействие разрешений для групп

Разрешения для групп могут использоваться в разных вариациях:

* Для блокирования View определенных частей веб приложения обращаясь в сервис `GroupPermissionsService` (например,
  можно скрывать часть меню для определенных групп и открывать для других).
* Для реализации любого сервиса предусмотреть блокировку операций и частей сервисов в зависимости от выставленных
  разрешений

Предусмотрено (но еще не реализованно) создавать новые группы разрешений неограниченное количество
(например модераторы, редакторы и т.д.), Vip пользователи, которые могут редактировать тип тренировки.
При создании нового сервиса его тип нужно создать в БД (еще не реализованно), таким образом, это упрощает
масштабирование проекта, можно создавать неограниченное количество сервисов и выставлять разрешения для каждого для
каждой отдельной группы.

* Для нормального функционирования приложения требуется, чтобы в базу загрузились все первоначально-задуманные
  данные (Пользователь admin, группы разрешений для пользователей и админа, базовые типы тренировки). Все это
  делается автоматически с помощью миграций Liquibase.

## Диаграмма взаимодействия в БД

![Диаграмма БД](/docs/images/diagram.png)

> [!TIP]
> Что можно улучшить:
> - перевести на микросервисную архитектуру
> - перевести на Spring Boot
> - увеличить покрытие кода тестами
> - перевести sout на логирование
> - создать сервис TypeService и внедрить в другие сервисы, который будет получать idTypeService для каждого сервиса

## Сборка и запуск

- Обязательное наличие Java 17
- Обязательное наличие Docker version 24.0.5
- Обязательное наличие Docker Compose version v2.20
- Переименовываем файл переменных окружения`.env1` в `.env`(если требуется, внесите изменения)
- В главном каталоге командой `docker-compose up -d` собрать образ СУБД PostgreSQL в Docker (запуск должен
  произойти автоматически)
- С помощью Maven собрать проект `mvn clean install`
  (Проект соберется и запустятся автоматические тесты. Обратите внимание, в тестах используются тест-контейнеры,
  поэтому будет запускаться образ в докере для тестов. После того как тесты отработают, контейнер для тестов
  сам деактивируется.
- После всех проделанных выше действий есть 2 варианта запустить проект:
    1. Запустить проект в IntelliJ IDEA исполняемый файл `TrainingDiaryApp.java`
    2. Запустить собранный jar (временно не работающий метод, скоро исправим):

    - Собранный общий jar из папки `target` модуля `presentation` запускаем командой:
      `java -jar presentation-1.0-SNAPSHOT-jar-with-dependencies.jar`
    - Или из папки с проектом: `java -jar presentation\target\presentation-1.0-SNAPSHOT-jar-with-dependencies.jar`

- Для работы в самом приложении потребуется тестовый логин `admin` и пароль `admin`, который уже есть в системе.
