# Homework Y_LAB: Training Diary
> Приложение для ведения дневника тренировок. Скоро лето и еще не поздно привести себя в порядок! Данное приложение
> поможет вам нагнать темп и следить за своим здоровьем. Записывайте свои тренировки, чтобы анализировать свой прогресс.
> И помните, что главное - это регулярность.
> 
> 
> # Список ссылок на PR
> 1. [Link PR №1](https://github.com/Jon7even/homework_ylab/pull/1)
>
> 
>
> # Список ссылок на ТЗ
> 1. [Link HW №1](https://github.com/Jon7even/homework_ylab/tree/homework_1/docs/tasks/technical-task-1.md)
>  
>  
> 

> [!IMPORTANT]
> Создано с помощью:
> - Java 17
> - JUnit 5
> 

> [!NOTE]
> Что это? Это консольное приложение для тренировок. На данный момент не поддерживает хранение данных при 
> прерывании/отключении процесса java. А при включении начинается все сначала. Но если у вас случайно завалялся 
> старый компьютер, который имеет хотя бы 1Gb памяти, на него можно установить Linux и включить наше приложение. 
> Желательно подключение делать через ИБП (надеемся свет у вас не так часто отключают). Но спешим обрадовать! Еще пару 
> бессонных ночей и мы сможем перевести этот проект на реальную базу данных.
>


Это первый опыт разработки на `Clean Architecture`. Стоит отменить, что с самого начала (в отличие от других) 
ощутилась простота и скорость разработки. Слои имеют `слабую связанность`, благодаря чему можно внедрять новый 
или тестировать текущий слой без проблем. Разработка велась от ядра `domain` до слоя `presentation`. Фактически 
находясь в любом слое можно разрабатывать его и улучшать по отдельности - в этом увидел огромную силу 
`чистой архитектуры`.

> ## Архитектура приложения
>
>
> ![Архитектура приложения на схеме](/docs/images/architecture.jpg)
>
> 

## Взаимодействие разрешений для групп

Разрешения для групп могут использоваться в разных вариациях:
* Для блокирования View определенных частей веб приложения обращаясь в сервис `GroupPermissionsService` (например,
можно скрывать часть меню для определенных групп и открывать для других).
Для реализации любого сервиса предусмотреть блокировку операций и частей сервисов в зависимости от выставленных 
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
> - подключить БД
> - увеличить покрытие кода тестами
> - перевести sout на логирование


## Сборка и запуск
- Обязательное наличие Java 17 на машине.
- С помощью Maven собрать проект `mvn clean install`
(проект соберется и запустятся автоматические тесты)
- Запустить можно двумя способами: 
   - 1). В среде разработки IntelliJ IDEA
   - 2). Запустить собранный jar из папки `target` командой `java -jar training-diary-1.0-SNAPSHOT.jar`
   - или из общего каталога `java -jar target/training-diary-1.0-SNAPSHOT.jar`
- Для работы потребуется тестовый логин `admin` и тестовый пароль `admin`, который уже есть в системе.
  
Работа приложения проверена на Windows 10 и Ubuntu 22.04.4.
