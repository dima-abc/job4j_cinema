package ru.my.cinema.repository;

import ru.my.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FilmRepository interface описывает поведение хранилища фильмов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.02.2023
 */
public interface FilmRepository {
    Optional<Film> getFilmById(int filmId);

    Collection<Film> getAllFilm();
}
