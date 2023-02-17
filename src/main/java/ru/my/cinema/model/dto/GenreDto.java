package ru.my.cinema.model.dto;

import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
public class GenreDto {
    private String name;

    public GenreDto() {
    }

    public GenreDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenreDto genreDto = (GenreDto) o;
        return Objects.equals(name, genreDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "GenreDto{name='" + name + '\'' + '}';
    }
}
