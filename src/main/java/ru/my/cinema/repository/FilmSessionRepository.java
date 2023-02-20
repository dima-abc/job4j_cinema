package ru.my.cinema.repository;

import ru.my.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FilmSessionRepository описывает поведение хранилища сеансов фильмов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.02.2023
 */
public interface FilmSessionRepository {
    Optional<FilmSession> getFilmSessionById(int sessionId);

    Collection<FilmSession> getFilmSessionByFilmId(int filmId);

    Collection<FilmSession> getAllFilmSession();
}
