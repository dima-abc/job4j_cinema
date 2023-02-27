package ru.my.cinema.model.dto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * TicketSessionHallDto (Data Transfer Object) класс модели Session + Hall + ticket.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.02.2023
 */
public class TicketSessionHallDto {
    private int sessionId;
    private LocalDateTime startTime;
    private String filmName;
    private int minimalAge;
    private int price;
    private int hallId;
    private String hallName;
    private Map<Integer, Map<Integer, TicketDto>> tickets;

    public TicketSessionHallDto() {
    }

    public static class Builder {
        private int sessionId;
        private String filmName;
        private LocalDateTime startTime;
        private int minimalAge;
        private int price;
        private int hallId;
        private String hallName;
        private Map<Integer, Map<Integer, TicketDto>> tickets;

        public Builder buildSessionId(int sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder buildFilmName(String filmName) {
            this.filmName = filmName;
            return this;
        }

        public Builder buildStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder buildMinimalAge(int minimalAge) {
            this.minimalAge = minimalAge;
            return this;
        }

        public Builder buildPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder buildHallId(int hallId) {
            this.hallId = hallId;
            return this;
        }

        public Builder buildHallName(String hallName) {
            this.hallName = hallName;
            return this;
        }

        public Builder buildTickets(Map<Integer, Map<Integer, TicketDto>> tickets) {
            this.tickets = tickets;
            return this;
        }

        public TicketSessionHallDto build() {
            TicketSessionHallDto dto = new TicketSessionHallDto();
            dto.sessionId = sessionId;
            dto.filmName = filmName;
            dto.startTime = startTime;
            dto.minimalAge = minimalAge;
            dto.price = price;
            dto.hallId = hallId;
            dto.hallName = hallName;
            dto.tickets = tickets;
            return dto;
        }
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Map<Integer, Map<Integer, TicketDto>> getTickets() {
        return tickets;
    }

    public void setTickets(Map<Integer, Map<Integer, TicketDto>> tickets) {
        this.tickets = tickets;
    }
}
