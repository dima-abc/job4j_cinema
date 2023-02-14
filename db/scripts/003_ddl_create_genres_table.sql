--Таблица справочника жанров Фильмов.
create table genres
(
    id   serial primary key,
    name varchar unique not null
);