package ru.my.cinema.repository;

import ru.my.cinema.model.Ticket;
import ru.my.cinema.model.dto.TicketDto;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * TicketRepository interface описывает поведение хранилища купленных билетов
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.02.2023
 */
public interface TicketRepository {
    Optional<Ticket> save(Ticket ticket);

    Collection<Ticket> getTicketBySessionId(int sessionId);

    Collection<Ticket> getTicketByUserId(int userId);

    Collection<Ticket> getAllTicket();
}
