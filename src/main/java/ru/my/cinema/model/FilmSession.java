package ru.my.cinema.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FilmSession DAO модель описывает киносеанс фильма идущем в конкретном зале.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.02.2023
 */
public class FilmSession {
    private int id;
    private int filmId;
    private int hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public FilmSession() {
    }

    public FilmSession(int id, int filmId, int hallId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.filmId = filmId;
        this.hallId = hallId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSession that = (FilmSession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FilmSession{id=" + id + ", filmId=" + filmId + ", hallId=" + hallId
                + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
}
