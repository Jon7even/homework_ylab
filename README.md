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

![Архитектура приложения на схеме](/docs/images/architectureApp.jpg)

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
  данные (Пользователь admin, группы разрешений для пользователей и админа, базовые типы тренировки)

> [!TIP]
> Что можно улучшить:
> - перевести на микросервисную архитектуру
> - перевести на Spring Boot
> - увеличить покрытие кода тестами
> - перевести sout на логирование
> - создать сервис TypeService и внедрить в другие сервисы, который будет получать idTypeService для каждого сервиса

## Сборка и запуск

- Обязательное наличие Java 17 на машине.
- С помощью Maven собрать проект `mvn clean install`
  (проект соберется и запустятся автоматические тесты)
- Запустить можно двумя способами:
    - 1). В среде разработки IntelliJ IDEA
    - 2). Запустить собранный jar из папки `target` командой `java -jar main-service-1.0-SNAPSHOT.jar`
    - или из общего каталога main-service `java -jar target/main-service-1.0-SNAPSHOT.jar`
- Для работы в самом приложении потребуется тестовый логин `admin` и пароль `admin`, который уже есть в системе.

Работа приложения проверена на Windows 10 и Ubuntu 22.04.4.
