package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.Hall;
import ru.my.cinema.model.dto.TicketDto;
import ru.my.cinema.model.dto.TicketSessionHallDto;
import ru.my.cinema.repository.FilmRepository;
import ru.my.cinema.repository.FilmSessionRepository;
import ru.my.cinema.repository.HallRepository;

import java.util.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * SimpleHallService
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.02.2023
 */
@Service
public class SimpleHallService implements HallService {
    private final HallRepository hallRepository;
    private final TicketService ticketService;
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;

    public SimpleHallService(HallRepository hallRepository,
                             TicketService ticketService,
                             FilmSessionRepository filmSessionRepository,
                             FilmRepository filmRepository) {
        this.hallRepository = hallRepository;
        this.ticketService = ticketService;
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

    /**
     * Формируем модель для отображения формы покупки билетов на сеанс.
     * При этом DTO модель содержит сформированную карты свободных и занятых мест.
     *
     * @param sessionId int
     * @return Optional TicketSessionHallDto
     */
    @Override
    public Optional<TicketSessionHallDto> getTicketSessionHallDtoBySessionId(int sessionId) {
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
