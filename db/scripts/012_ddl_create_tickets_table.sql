--Таблица хранения купленных билетов
create table tickets
(
    id           serial primary key,
    session_id   int not null references film_sessions (id),
    row_number   int not null,
    place_number int not null,
    user_id      int not null,
    unique (session_id, row_number, place_number)
);