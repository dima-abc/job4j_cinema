package ru.my.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.my.cinema.model.Film;
import ru.my.cinema.model.FilmSession;
import ru.my.cinema.model.dto.FilmDto;
import ru.my.cinema.service.FilmService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FilmController TEST.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 08.03.2023
 */
public class FilmControllerTest {
    private FilmService filmService;
    private FilmController filmController;

    @BeforeEach
    public void initService() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    public void whenRequestGetAllFilmThenReturnFilmListPage() {
        var filmDto1 = new FilmDto();
        filmDto1.setId(1);
        var filmDto2 = new FilmDto();
        filmDto2.setId(2);
        var expectedAllFilmDto = List.of(filmDto1, filmDto2);
        when(filmService.getAllFilmDto()).thenReturn(expectedAllFilmDto);

        var model = new ConcurrentModel();
        var actualPage = filmController.getAllFilm(model);
        var actualFilmsDto = model.getAttribute("allFilmDto");

        assertThat(actualFilmsDto).usingRecursiveComparison().isEqualTo(expectedAllFilmDto);
        assertThat(actualPage).isEqualTo("films/list");
    }

    @Test
    public void whenRequestGetFilmByIdThenReturnFilmOnePage() {
        int id = 1;
        var film = new Film(1, "name", "description", 1, 1, 1, 1, 1);
        var filmDto = new FilmDto.Builder()
                .buildId(film.getId())
                .buildName(film.getName())
                .buildDescription(film.getDescription())
                .buildYear(film.getYear())
                .buildGenre("genre")
                .buildMinimalAge(film.getMinimalAge())
                .buildDurationInMinutes(film.getDurationInMinutes())
                .buildFileId(film.getFileId())
                .build();
        when(filmService.getFilmDtoById(id)).thenReturn(Optional.of(filmDto));

        var model = new ConcurrentModel();
        var actualPage = filmController.getFilm(model, id);
        var actualFilmDto = model.getAttribute("filmDto");

        assertThat(actualFilmDto).usingRecursiveComparison().isEqualTo(filmDto);
        assertThat(actualPage).isEqualTo("films/one");
    }

    @Test
    public void whenRequestGetFilmThenReturnErrorPageAndSameMessage() {
        var expectMessage = "Фильм не найден.";
        when(filmService.getFilmDtoById(anyInt())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var actualPage = filmController.getFilm(model, anyInt());
        var actualMessage = model.getAttribute("message");

        assertThat(actualMessage).isEqualTo(expectMessage);
        assertThat(actualPage).isEqualTo("statuses/errors/404");
    }
}