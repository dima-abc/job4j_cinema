package ru.my.cinema.model.dto;

import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FilmDto DTO (Data Transfer Object) класс модели Film.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
public class FilmDto {
    private int id;
    private String name;
    private String description;
    private int year;
    private String genre;
    private int minimalAge;
    private int durationInMinutes;

    public FilmDto() {
    }

    public FilmDto(int id, String name, String description, int year,
                   String genre, int minimalAge, int durationInMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.genre = genre;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmDto filmDto = (FilmDto) o;
        return id == filmDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FilmDto{id=" + id + ", name='" + name + '\''
               + ", description='" + description + '\'' + ", year=" + year
               + ", genre='" + genre + '\'' + ", minimalAge=" + minimalAge
               + ", durationInMinutes=" + durationInMinutes + '}';
    }
}
