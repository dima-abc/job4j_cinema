--Создание таблицы FILMS содержит список справочников фильмов.
create table films
(
    id                  serial primary key,
    name                varchar not null,
    description         varchar not null,
    "year"              int     not null,
    genre_id            int     not null references genres (id),
    minimal_age         int     not null,
    duration_in_minutes int     not null,
    file_id             int     not null references files (id)
);