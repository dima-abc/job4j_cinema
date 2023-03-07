package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.Ticket;
import ru.my.cinema.model.dto.TicketDto;
import ru.my.cinema.repository.TicketRepository;

import java.util.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * SimpleTicketService реализация бизнес логики для модели Ticket.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 27.02.2023
 */
@Service
public class SimpleTicketService implements TicketService {
    private final TicketRepository ticketRepository;
    private final FilmSessionService filmSessionService;


    public SimpleTicketService(TicketRepository ticketRepository,
                               FilmSessionService filmSessionService) {
        this.ticketRepository = ticketRepository;
        this.filmSessionService = filmSessionService;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /**
     * Метод возвращает DTO модель для отображения билетов принадлежащих пользователю.
     *
     * @param userId ID User
     * @return List TicketDto
     */
    @Override
    public Collection<TicketDto> getTicketByUserId(int userId) {
        var tickets = ticketRepository.getTicketByUserId(userId);
        if (tickets.isEmpty()) {
            return Collections.emptyList();
        }
        List<TicketDto> dtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            var sessionDto = filmSessionService.getSessionDtoById(ticket.getSessionId());
            if (sessionDto.isEmpty()) {
                continue;
            }
            var dto = new TicketDto(
                    ticket.getSessionId(),
                    sessionDto.get().getFileId(),
                    sessionDto.get().getFilmName(),
                    sessionDto.get().getHallName(),
                    ticket.getRow(),
                    ticket.getPlace()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
