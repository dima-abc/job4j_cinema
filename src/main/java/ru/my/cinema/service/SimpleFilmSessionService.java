package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.FilmSession;
import ru.my.cinema.model.Genre;
import ru.my.cinema.model.Hall;
import ru.my.cinema.model.dto.TicketDto;
import ru.my.cinema.model.dto.TicketSessionHallDto;
import ru.my.cinema.model.dto.SessionDto;
import ru.my.cinema.repository.*;

import javax.websocket.Session;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 20.02.2023
 */
@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final HallRepository hallRepository;
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final TicketService ticketService;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository,
                                    HallRepository hallRepository,
                                    FilmRepository filmRepository,
                                    GenreRepository genreRepository,
                                    TicketService ticketService) {
        this.filmSessionRepository = filmSessionRepository;
        this.hallRepository = hallRepository;
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
        this.ticketService = ticketService;
    }

    /**
     * Создание SessionDto из FilmSessions
     *
     * @param filmSession FilmSession
     * @return SessionDto
     */
    private Optional<SessionDto> getDtoByFilmSession(FilmSession filmSession) {
        var film = filmRepository.getFilmById(filmSession.getFilmId());
        if (film.isEmpty()) {
            return Optional.empty();
        }
        var genre = genreRepository.getGenreById(film.get().getGenreId());
        if (genre.isEmpty()) {
            genre = Optional.of(new Genre(0, ""));
        }
        var hall = hallRepository.getHallById(filmSession.getHallId());
        if (hall.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new SessionDto.Builder()
                        .buildId(filmSession.getId())
                        .buildStartTime(filmSession.getStartTime().toLocalTime())
                        .buildFileId(film.get().getFileId())
                        .buildFilmName(film.get().getName())
                        .buildGenre(genre.get().getName())
                        .buildMinimalAge(film.get().getMinimalAge())
                        .buildHallId(hall.get().getId())
                        .buildHallName(hall.get().getName())
                        .build()
        );
    }

    /**
     * Создание карты всех билетов на заданный сеанс в состоянии свободные,
     * поле free = true;
     *
     * @param sessionId
     * @param rowHall
     * @param placeHall
     * @return
     */
    private Map<Integer, Map<Integer, TicketDto>> getAllTicketBySessionId(int sessionId, int rowHall, int placeHall) {
        Map<Integer, Map<Integer, TicketDto>> allTicket = new HashMap<>();
        for (int i = 1; i <= rowHall; i++) {
            allTicket.put(i, new HashMap<>());
            for (int j = 1; j <= placeHall; j++) {
                allTicket.get(i).put(j,
                        new TicketDto(sessionId, i, j, true));
            }
        }
        return allTicket;
    }

    /**
     * Метод формирует карту свободных и занятых билетов.
     * Map<rowHall, Map<placeHall, TicketDto>
     *
     * @param sessionId
     * @return Map<Integer, Map < Integer, TicketDto> mapTicketFreeAndBuse
     */
    private Map<Integer, Map<Integer, TicketDto>> getTicketFreeAndBusy(int sessionId, int rowHall, int placeHall, Collection<TicketDto> ticketsBusy) {
        Map<Integer, Map<Integer, TicketDto>> allTickets = getAllTicketBySessionId(sessionId, rowHall, placeHall);
        if (allTickets.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        for (TicketDto ticketDto : ticketsBusy) {
            allTickets.get(ticketDto.getRow())
                    .get(ticketDto.getPlace())
                    .setFree(ticketDto.getFree());
        }
        return allTickets;
    }

    @Override
    public Optional<SessionDto> getSessionDtoById(int sessionId) {
        return Optional.empty();
    }

    @Override
    public Collection<SessionDto> getSessionDtoByFilmId(int filmId) {
        return filmSessionRepository.getFilmSessionByFilmId(filmId)
                .stream()
                .map(this::getDtoByFilmSession)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SessionDto> getAllSessionDtoSortedByStarTime(LocalTime timeNow) {

        return null;
    }

    /**
     * Вернуть все доступные сеансы. Сортировка по времени начала.
     *
     * @return Collection.
     */
    @Override
    public Collection<SessionDto> getAllSessionDto() {
        return filmSessionRepository.getAllFilmSession()
                .stream()
                .map(this::getDtoByFilmSession)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Формируем модель для отображения формы покупки билетов на сеанс.
     * При этом DTO модель содержит сформированную карты свободных и занятых мест.
     *
     * @param sessionId int
     * @return Optional TicketSessionHallDto
     */
    @Override
    public Optional<TicketSessionHallDto> getSessionHallDtoBySessionId(int sessionId) {
        var sessionOptional = filmSessionRepository.getFilmSessionById(sessionId);
        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }
        var hallOptional = hallRepository.getHallById(sessionOptional.get().getHallId());
        if (hallOptional.isEmpty()) {
            return Optional.empty();
        }
        var filmOptional = filmRepository.getFilmById(sessionId);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        var session = sessionOptional.get();
        var hall = hallOptional.get();
        var film = filmOptional.get();
        var ticketsBusy = ticketService.getTicketDtoBySessionId(session.getId());
        var ticketsFreeAndBusy = getTicketFreeAndBusy(
                session.getId(),
                hall.getRowCount(),
                hall.getPlaceCount(),
                ticketsBusy);
        return Optional.of(
                new TicketSessionHallDto.Builder()
                        .buildSessionId(session.getId())
                        .buildStartTime(session.getStartTime())
                        .buildFilmName(film.getName())
                        .buildMinimalAge(film.getMinimalAge())
                        .buildPrice(session.getPrice())
                        .buildHallId(hall.getId())
                        .buildHallName(hall.getName())
                        .buildTickets(ticketsFreeAndBusy)
                        .build()
        );
    }


}
