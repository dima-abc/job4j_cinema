package ru.my.cinema.service;

import ru.my.cinema.model.dto.FilmDto;

import java.util.List;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FilmService interface описывает поведение бизнес логики обработки модели Film.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
public interface FilmService {
    Optional<FilmDto> getFilmById(int id);

    List<FilmDto> getAllFilm();
}
