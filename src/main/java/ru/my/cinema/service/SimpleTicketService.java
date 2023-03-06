package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.Ticket;
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

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
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
}
