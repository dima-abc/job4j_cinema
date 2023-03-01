package ru.my.cinema.model.dto;

import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * TicketDto (Data Transfer Object) класс модели Ticket
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 27.02.2023
 */
public class TicketDto {
    private int sessionId;
    private int row;
    private int place;
    private boolean free;

    public TicketDto() {
    }

    public TicketDto(int sessionId, int row, int place, boolean free) {
        this.sessionId = sessionId;
        this.row = row;
        this.place = place;
        this.free = free;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public boolean getFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketDto ticketDto = (TicketDto) o;
        return sessionId == ticketDto.sessionId && row == ticketDto.row && place == ticketDto.place;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, row, place);
    }

    @Override
    public String toString() {
        return "TicketDto{sessionId=" + sessionId + ", row=" + row
               + ", place=" + place + ", free=" + free + '}';
    }
}
