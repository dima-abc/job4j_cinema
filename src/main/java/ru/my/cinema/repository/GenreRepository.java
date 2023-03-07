package ru.my.cinema.repository;

import ru.my.cinema.model.Genre;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * GenreRepository interface описывает поведение хранилища модели GENRE.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.02.2023
 */
public interface GenreRepository {
    Optional<Genre> getGenreById(int genreId);
}
