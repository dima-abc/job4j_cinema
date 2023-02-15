--Создание таблицы FILM содержит список справочников фильмов.
create table films
(
    id                  serial primary key,
    name                varchar,
    description         varchar,
    "year"              int,
    genre_id            int references genres (id),
    minimal_age         int,
    duration_in_minutes int,
    file_id             int references files (id)
);