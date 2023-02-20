package ru.my.cinema.model.dto;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 20.02.2023
 */
public class FilmSessionDto {
    private int id;
    private LocalTime startTime;
    private String filmName;
    private String genre;
    private int minimalAge;
    private int hallId;
    private String hallName;

    public FilmSessionDto() {
    }

    public FilmSessionDto(int id, LocalTime startTime,
                          String filmName, String genre,
                          int minimalAge, int hallId, String hallName) {
        this.id = id;
        this.startTime = startTime;
        this.filmName = filmName;
        this.genre = genre;
        this.minimalAge = minimalAge;
        this.hallId = hallId;
        this.hallName = hallName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSessionDto that = (FilmSessionDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FilmSessionDto{id=" + id + ", startTime='" + startTime + '\''
               + ", filmName='" + filmName + '\'' + ", genre='" + genre + '\''
               + ", minimalAge=" + minimalAge + ", hallId=" + hallId
               + ", hallName='" + hallName + '\'' + '}';
    }
}
