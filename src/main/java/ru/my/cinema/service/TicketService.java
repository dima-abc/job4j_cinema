package ru.my.cinema.service;

import ru.my.cinema.model.Ticket;
import ru.my.cinema.model.dto.TicketDto;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * TicketService interface описывает поведение бизнес логики обработки модели Ticket.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
public interface TicketService {
    Optional<Ticket> save(Ticket ticket);

    Collection<Ticket> getTicketBySessionId(int sessionId);

    Collection<TicketDto> getTicketByUserId(int userId);

    Collection<Ticket> getAllTicket();
}
