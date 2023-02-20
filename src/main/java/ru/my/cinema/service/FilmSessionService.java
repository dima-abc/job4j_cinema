package ru.my.cinema.service;

import ru.my.cinema.model.dto.FilmSessionDto;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FilmSessionService interface описывает поведение бизнес логики обработки модели FilmSession.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
public interface FilmSessionService {
    Optional<FilmSessionDto> getFilmSessionDtoById(int filmSessionId);

    Collection<FilmSessionDto> getFilmSessionDtoByFilmId(int filmId);

    Collection<FilmSessionDto> getAllFilmSessionDtoSortedByStarTime(LocalTime timeNow);

    Collection<FilmSessionDto> getAllFilmSessionDto();
}
