---Таблица хранения сеансов фильмов.
create table film_sessions
(
    id         serial primary key,
    film_id    int references films (id) not null,
    hall_id   int references halls (id) not null,
    start_time timestamp,
    end_time   timestamp
);