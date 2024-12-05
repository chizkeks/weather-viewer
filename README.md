# Проект “Погода” #
Веб-приложение для просмотра текущей погоды. Пользователь может зарегистрироваться и добавить в коллекцию одну или несколько локаций (городов, сёл, других пунктов), после чего главная страница приложения начинает отображать список локаций с их текущей погодой.

## Содержание
- [Мотивация проекта](#мотивация-проекта)
- [Функционал приложения](#функционал-приложения)
  
## Мотивация проекта
Использование cookies и сессий для авторизации пользователей
Знакомство со Spring
Работа с внешними API

## Функционал приложения
Работа с пользователями:
- Регистрация
- Авторизация
- Logout
  
Работа с локациями:
- Поиск
- Добавление в список
- Просмотр списка локаций, для каждой локации отображается название и температура
- Удаление из списка
## База данных
В этом проекте предлагаю использовать MySQL/MariaDB/Postgres. Создавать таблицы будем не через Hibernate, как в предыдущем проекте, а с помощью инструмента миграций - Flyway/Liquibase.

# Таблица Users
| Колонка	| Тип	| Комментарий |
| ------- | --- | ----------- |
| ID	| int	| Айди пользователя, автоинкремент, первичный ключ |
| Login	| Varchar	| Логин пользователя, username или email |
| Password	| Varchar	| Хранить пароль в открытом виде небезопасно, лучше использовать шифрование, например BCrypt |

# Таблица Locations 
Локации пользователя, в которых он хочет знать погоду. Одна и та же локация может повторяться для нескольких пользователей.

| Колонка	| Тип	| Комментарий |
| ------- | --- | ----------- |
| ID |	int |	Айди локации, автоинкремент, первичный ключ |
| Name	| Varchar	| Название |
| UserId	| int	| Пользователь, добавивший эту локацию |
| Latitude	| Decimal	| Широта локации |
| Longitude	| Decimal	| Долгота локации |

# Таблица Sessions
| Колонка	| Тип	| Комментарий |
| ------- | --- | ----------- |
| ID	| Varchar	| Айди сессии, GUID, первичный ключ |
| UserId	| int	| Пользователь, для которого сессия создана |
| ExpiresAt |	Datetime	| Время истечения сессии. Равно времени создания сессии плюс N часов |
