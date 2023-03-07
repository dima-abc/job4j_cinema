package ru.my.cinema.model.dto;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * TicketDto DTO (Data Transfer Object) класс модели Ticket.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.03.2023
 */
public class TicketDto {
    private int sessionId;
    private int filmId;
    private String filmName;
    private String hallName;
    private int row;
    private int place;

    public TicketDto() {
    }

    public TicketDto(int sessionId,
                     int filmId,
                     String filmName,
                     String hallName,
                     int row,
                     int place) {
        this.sessionId = sessionId;
        this.filmId = filmId;
        this.filmName = filmName;
        this.hallName = hallName;
        this.row = row;
        this.place = place;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
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
}
