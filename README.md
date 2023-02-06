# task1
Team3. Дмитрий Булавицкий, Павел Клименко, Анна Микулич
Приложение предназначено для проведения голосования по двум номинациям:
1. Артисты - можно проголосовать только за одного артиста.
2. Жанры - пожно проголосовать за от 3 до 5 жанров.
В приложении предусмотрены следующие странцы и функции:
 <b><p>1. Странница приветствия</b></p>
URL "/welcome"
 <b><p>2. Странница артистов</b></p>
URL "/singer"
На данной странице предусмотрено выполнения следующих действий:
 - Просмотреть артистов (метод GET)
 - Добавить артист (метод POST) и необходимо передать ключ "add"
 - Обновить артиста (метод PUT) и необходимо передать ключи "updateId" и "newName"
 - Удалить артиста по id (метод DELETE) и необходимо передать ключи "deleteId"
 <b><p>3. Странница жанров</b></p>
URL "/genre"
На данной странице предусмотрено выполнения следующих действий:
 - Просмотреть жанры (метод GET)
 - Добавить жанр (метод POST) и необходимо передать ключ "add"
 - Обновить жанр (метод PUT) и необходимо передать ключи "updateId" и "newName"
 - Удалить жанры по id (метод DELETE) и необходимо передать ключи "deleteId"
  <b><p> 4. Странница голосования</b></p>
URL "/vote"
На данной странице предусмотрено голосование. Необходимо указать следующие ключи:
 - Для голосования за артиста "singer" и указать id
 - Для голосования за жанры "genre" и указать id
 - Добавить информации к голосу "message" и написать текст сообщения
 - Указать свой email для отправки информации об отправленном голосе "email".
 После отправки голоса пользователь получает информацию о текущих результатах голосования
  <b><p>4. Странница результатов голосования</b></p>
  На данной станице можно просмотреть текущие результаты голосования