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
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 27.02.2023
 */
@Service
public class SimpleTicketService implements TicketService {
    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * Возвращает доменную модель билета TicketDto с признаком занятого(купленного).
     * Поле free = false;
     *
     * @param ticket Ticket
     * @return TicketDto
     */
    private TicketDto getTicketDto(Ticket ticket) {
        return new TicketDto(ticket.getSessionId(),
                ticket.getRow(),
                ticket.getRow(),
                false);
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Collection<Ticket> getTicketBySessionId(int sessionId) {
        return ticketRepository.getTicketBySessionId(sessionId);
    }

    @Override
    public Collection<Ticket> getTicketByUserId(int userId) {
        return ticketRepository.getTicketByUserId(userId);
    }

    @Override
    public Collection<Ticket> getAllTicket() {
        return ticketRepository.getAllTicket();
    }

    /**
     * Создает список купленных билетов по указанному сеансу.
     *
     * @param sessionId int Session ID
     * @return Collection<TicketDto>
     */
    @Override
    public Collection<TicketDto> getTicketDtoBySessionId(int sessionId) {
        var ticketBySessionId = getTicketBySessionId(sessionId);
        if (ticketBySessionId.isEmpty()) {
            return Collections.emptyList();
        }
        var ticketsDto = new ArrayList<TicketDto>();
        for (Ticket ticket : ticketBySessionId) {
            ticketsDto.add(getTicketDto(ticket));
        }
        return ticketsDto;
    }
}
