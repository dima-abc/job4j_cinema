package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.Hall;
import ru.my.cinema.model.dto.HallDto;
import ru.my.cinema.repository.FilmRepository;
import ru.my.cinema.repository.FilmSessionRepository;
import ru.my.cinema.repository.HallRepository;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * SimpleHallService реализация бизнес логики для модели Hall.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.02.2023
 */
@Service
public class SimpleHallService implements HallService {
    private final HallRepository hallRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;

    public SimpleHallService(HallRepository hallRepository,
                             FilmSessionRepository filmSessionRepository,
                             FilmRepository filmRepository) {
        this.hallRepository = hallRepository;
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public Optional<Hall> getHallById(int hallId) {
        return hallRepository.getHallById(hallId);
    }

    @Override
    public Collection<Hall> getAllHall() {
        return hallRepository.getAllHall();
    }

    /**
     * Генерация массива мест по его количеству.
     *
     * @param size int
     * @return int Array
     */
    private int[] getArrayPlaces(int size) {
        return IntStream.range(1, size + 1).toArray();
    }

    /**
     * Формируем модель для отображения формы покупки билетов на сеанс.
     * При этом DTO модель содержит сформированную карты свободных и занятых мест.
     *
     * @param sessionId int
     * @return Optional HallDto
     */
    @Override
    public Optional<HallDto> getHallDtoBySessionId(int sessionId) {
        var sessionOptional = filmSessionRepository.getFilmSessionById(sessionId);
        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }
        var session = sessionOptional.get();
        var hallOptional = hallRepository.getHallById(session.getHallId());
        if (hallOptional.isEmpty()) {
            return Optional.empty();
        }
        var hall = hallOptional.get();
        var filmOptional = filmRepository.getFilmById(session.getFilmId());
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        var film = filmOptional.get();
        return Optional.of(
                new HallDto.Builder()
                        .buildSessionId(session.getId())
                        .buildStartTime(session.getStartTime())
                        .buildFilmName(film.getName())
                        .buildMinimalAge(film.getMinimalAge())
                        .buildPrice(session.getPrice())
                        .buildHallId(hall.getId())
                        .buildHallName(hall.getName())
                        .buildRows(getArrayPlaces(hall.getRowCount()))
                        .buildPlaces(getArrayPlaces(hall.getPlaceCount()))
                        .build()
        );
    }
}
