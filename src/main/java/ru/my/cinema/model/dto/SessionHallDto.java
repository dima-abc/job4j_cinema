package ru.my.cinema.model.dto;

import java.time.LocalDateTime;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.02.2023
 */
public class SessionHallDto {
    private int sessionId;
    private String filmName;
    private LocalDateTime startTime;
    private int minimalAge;
    private int price;
    private int hallId;
    private String hallName;
    private int[] rowCount;
    private int[] placeCount;

    public SessionHallDto() {
    }

    public SessionHallDto(int sessionId, String filmName,
                          LocalDateTime startTime,
                          int minimalAge, int price,
                          int hallId, String hallName,
                          int[] rowCount, int[] placeCount) {
        this.sessionId = sessionId;
        this.filmName = filmName;
        this.startTime = startTime;
        this.minimalAge = minimalAge;
        this.price = price;
        this.hallId = hallId;
        this.hallName = hallName;
        this.rowCount = rowCount;
        this.placeCount = placeCount;
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

    public int[] getRowCount() {
        return rowCount;
    }

    public void setRowCount(int[] rowCount) {
        this.rowCount = rowCount;
    }

    public int[] getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int[] placeCount) {
        this.placeCount = placeCount;
    }
}
