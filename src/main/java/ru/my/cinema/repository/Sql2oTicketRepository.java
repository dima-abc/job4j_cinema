package ru.my.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.my.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Sql2oTicketRepository реализация хранилища билетов в базе данных.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.02.2023
 */
@Repository
public class Sql2oTicketRepository implements TicketRepository {
    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO tickets(session_id, row_number, place_number, user_id) 
                    VALUES (:sessionId, :row, :place, :userId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("row", ticket.getRow())
                    .addParameter("place", ticket.getPlace())
                    .addParameter("userId", ticket.getUserId());
            int generateId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generateId);
            return Optional.ofNullable(ticket);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Ticket> getTicketBySessionId(int sessionId) {
        try (var connection = sql2o.open()) {
            var query = connection
                    .createQuery("SELECT * FROM tickets WHERE session_id = :sessionId")
                    .addParameter("sessionId", sessionId);
            return query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetch(Ticket.class);
        }
    }

    @Override
    public Collection<Ticket> getTicketByUserId(int userId) {
        try (var connection = sql2o.open()) {
            var query = connection
                    .createQuery("SELECT * FROM tickets WHERE user_id = :userId")
                    .addParameter("userId", userId);
            return query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetch(Ticket.class);
        }
    }

    @Override
    public Collection<Ticket> getAllTicket() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetch(Ticket.class);
        }
    }
}
