--Таблица хранения сеансов фильмов.
create table film_sessions
(
    id         serial primary key,
    film_id    int       not null references films (id),
    hall_id    int       not null references halls (id),
    start_time timestamp not null,
    end_time   timestamp not null,
    price      int       not null
)