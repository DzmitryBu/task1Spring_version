# Team3. task1. Voting

` Команда проекта:  Дмитрий Булавицкий, Павел Клименко, Анна Микулич`
` Завершал проект:  Дмитрий Булавицкий`

Приложение предназначено для проведения голосования по двум номинациям:
1. Номинация Singer - можно проголосовать только за одного исполнителя.
2. Номинация Genres - можно проголосовать за  3 - 5 жанров.

Также есть возможность просмотреть статистику голосования.

> Логическая модель базы данных

![schema](https://github.com/DzmitryBu/task1Spring_version/blob/55c64302340d5e476a1e18aeee08cfcdec8ecaef/%D0%A1%D1%85%D0%B5%D0%BC%D0%B0.jpg)


## В приложении предусмотрены следующие страницы и функции:
## 1. Страница исполнителей
На данной странице предусмотрено выполнение следующих действий:

Просмотреть  список исполнителей:
```sh
 (GET) http://host:port/WarFileName/singer
  ```
Просмотреть карточку исполнителя:
  ```sh
 (GET) http://host:port/WarFileName/singer/{id}
  ```
Добавить, обновить, удалить исполнителя. Обновление и удаление производится с проверкой актуальной указанной версией:
```sh
 (POST)   http://host:port/WarFileName/singer
 И необходимо отправить запрос с JSON.
 Пример: {"name" : "Леонтьев"}
 
 (PUT)    http://host:port/WarFileName/singer/{id}/version/{version}
 И необходимо отправить запрос с JSON и указать новое имя.
 Пример: {"name" : "Shakira"}
 
 (DELETE) http://host:port/WarFileName/singer/{id}/version/{version}
  ```
## 2. Страница жанров
На данной странице предусмотрено выполнение следующих действий:

Просмотреть  список жанров:
```sh
 (GET) http://host:port/WarFileName/genre
  ```
Просмотреть карточку жанра:
  ```sh
 (GET) http://host:port/WarFileName/genre/{id}
  ```
Добавить, обновить, удалить жанр. Обновление и удаление производится с проверкой актуальной указанной версией:
```sh
 (POST)   http://host:port/WarFileName/genre
 И необходимо отправить запрос с JSON.
 Пример: {"name" : "Рок"}
 
 (PUT)    http://host:port/WarFileName/genre/{id}/version/{version}
 И необходимо отправить запрос с JSON и указать новое название жанра.
 Пример: {"name" : "Классика"}
 
 (DELETE) http://host:port/WarFileName/genre/{id}/version/{version}
  ```
## 3. Страница голосования
URL "/vote"  (1 vote singer, 3-5 votes for genres)
```sh
 (POST)  http://host:port/WarFileName/vote
 И необходимо отправить запрос с JSON.
 Пример:
 {
    "singerID" : 2,
 "genresID" : [5, 4, 3],
  "message" : "Hi",
   "email" : "*@mail.*"
   }
 ``` 
## 4. Страница результатов голосования
На данной станице можно увидеть текущие результаты голосования:
  ```sh
 (GET) http://host:port/WarFileName/result
 ``` 
 
