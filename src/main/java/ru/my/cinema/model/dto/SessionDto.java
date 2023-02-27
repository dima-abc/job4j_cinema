package ru.my.cinema.model.dto;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * SessionDto DTO (Data Transfer Object) класс модели Session.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 20.02.2023
 */
public class SessionDto {
    private int id;
    private LocalTime startTime;
    private int fileId;
    private String filmName;
    private String genre;
    private int minimalAge;
    private int hallId;
    private String hallName;

    public SessionDto() {
    }

    public static class Builder {
        private int id;
        private LocalTime startTime;
        private int fileId;
        private String filmName;
        private String genre;
        private int minimalAge;
        private int hallId;
        private String hallName;

        public Builder buildId(int id) {
            this.id = id;
            return this;
        }

        public Builder buildStartTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder buildFileId(int fileId) {
            this.fileId = fileId;
            return this;
        }

        public Builder buildFilmName(String filmName) {
            this.filmName = filmName;
            return this;
        }

        public Builder buildGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder buildMinimalAge(int minimalAge) {
            this.minimalAge = minimalAge;
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

        public SessionDto build() {
            SessionDto sessionDto = new SessionDto();
            sessionDto.id = id;
            sessionDto.startTime = startTime;
            sessionDto.fileId = fileId;
            sessionDto.filmName = filmName;
            sessionDto.genre = genre;
            sessionDto.minimalAge = minimalAge;
            sessionDto.hallId = hallId;
            sessionDto.hallName = hallName;
            return sessionDto;
        }
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

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
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
        SessionDto that = (SessionDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SessionDto{id=" + id + ", startTime='" + startTime + '\'' + ", filId='" + fileId
               + ", filmName='" + filmName + '\'' + ", genre='" + genre + '\''
               + ", minimalAge=" + minimalAge + ", hallId=" + hallId
               + ", hallName='" + hallName + '\'' + '}';
    }
}
