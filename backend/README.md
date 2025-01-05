## backend
#### Для запуска необоходимо
- Прописать креды от бд в .env файле в корне freakify/backend
- Создать бд 
        CREATE DATABASE music \
        WITH                  \
        OWNER = postgres      \
        ENCODING = 'UTF8'     \
        LC_COLLATE = 'C'      \
        LC_CTYPE = 'C'        \
        ICU_LOCALE = 'ru'     \
        LOCALE_PROVIDER = 'icu'\
        TABLESPACE = pg_default\
        CONNECTION LIMIT = -1\
        IS_TEMPLATE = False;
- зависимости 
        python3 -m pip install Django
        python3 -m pip install python-dotenv
- в корне с проектом написать 
        python3 manage.py migrate - для миграция бд
        python3 manage.py runserver

#### Описание api
- GET freakify/ - ручка которая выводит "ты пидор"
- GET songs/ - ручка которая выводит список песен, фильтруя по параметру ```name``` указанному в query параметрах (если не указано - возвращает всё)
    - response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                name = "name",
                artist = "artist_name",
                jenre = "jenre_name"
            }
        ]
        }
        ```
- GET songs/${song_id}/mp3 - получить по mp3 по id, ```songs/1/mp3```
- GET albums/ - ручка которая выводит список альбомов, фильтруя по параметру ```name``` указанному в query параметрах (если не указано - возвращает всё)
    - response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                name = "name",
                artist = "artist_name",
                creation_date = "1985‑09‑25 17:45:30.005"
            }
        ]
        }
        ```

- GET albums/${album_id}/songs - выводит список всех песен по album_id
    -   response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                name = "name",
                artist = "artist_name",
                jenre = "jenre_name"
            }
        ]
        }
        ```
- GET playlists/ - ручка которая выводит список плейлистов, фильтруя по параметру ```name``` указанному в query параметрах (если не указано - возвращает всё)
    -   response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                name = "name",
                creator = "creator_name",
                update_time = "1985‑09‑25 17:45:30.005"
                creation_date = "1985‑09‑25 17:45:30.005"
            }
        ]
        }
        ```
- GET playlists/{playlist_id}/songs - выводит список всех песен в плейлисте по playlist_id
    -   response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                name = "name",
                artist = "artist_name",
                jenre = "jenre_name"
            }
        ]
        }
        ```
- POST playlists/create - создать плейлист (требуется авторизация)
    - request body:
    - ```
        {
            email: "email@email.com",
            password: "password",
            playlist_name: "playlist_name"
        }
    - response 201
- POST playlists/${playlist_id}/songs/${song_id}/add - добавить песню в плейлист (требуется авторизация)
    - request body:
    - ```
        {
            email: "email@email.com",
            password: "password"
        }
              
- POST playlists/${playlist_id}/songs/${song_id}/remove - добавить песню в плейлист (требуется авторизация)
    - request body:
    - ```
        {
            email: "email@email.com",
            password: "password"
        }
    
- GET users/${usser_id}/songs - все любимые треки пользователя
    -   response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                name = "name",
                artist = "artist_name",
                jenre = "jenre_name"
            }
        ]
        }
        ```
- GET users/${usser_id}/playlists - все плейлисты пользователя
    -   response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                name = "name",
                artist = "artist_name",
                update_time = "1985‑09‑25 17:45:30.005"
                creation_date = "1985‑09‑25 17:45:30.005"
            }
        ]
        }
        ```
- POST users/register
    - request body
    - ```
        {
            email: "email@email.com",
            username: "asdasdas",
            password: "password"
        }
- POST users/login
    - request body
    - ```
        {
            email: "email@email.com",
            password: "password"
        }
    
    - response 
    - user_login
- POST songs/${songs_id}/add-to-favourites - добавить музыку в любимое, требуется авторизация
    - request body
    - ```
        {
            email: "email@email.com",
            password: "password"
        }
- GET users/ ручка которая выводит список пользователей, фильтруя по параметру ```name``` указанному в query параметрах (если не указано - возвращает всё)
    - response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                nickname = "name"
            }
        ]
        }
- GET artists/ ручка которая выводит список исполнителей, фильтруя по параметру ```name``` указанному в query параметрах (если не указано возвращает всё)
    - response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                name = "name"
            }
        ]
        }
- GET artists/${artist_id}/songs все треки артиста
    -   response body
    - ```{
        result = 'success',
        body = [
            {
                id = "str",
                name = "name",
                artist = "artist_name",
                jenre = "jenre_name"
            }
        ]
        }
        ```
#nb
для всех ручек типа entity/ которые выводят список определенной entity можно указать offset и limit для пагинации




