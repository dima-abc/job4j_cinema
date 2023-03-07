package ru.my.cinema.service;

import ru.my.cinema.model.dto.SessionDto;

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
    Optional<SessionDto> getSessionDtoById(int filmSessionId);

    Collection<SessionDto> getSessionDtoByFilmId(int filmId);

    Collection<SessionDto> getAllSessionDto();
}
