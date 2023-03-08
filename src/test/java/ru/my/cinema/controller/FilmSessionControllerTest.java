package ru.my.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.my.cinema.model.dto.SessionDto;
import ru.my.cinema.service.FilmSessionService;

import java.util.Collections;
import java.util.List;

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
 * FilmSessionController TEST
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 08.03.2023
 */
public class FilmSessionControllerTest {
    private FilmSessionService filmSessionService;
    private FilmSessionController filmSessionController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    @Test
    public void whenRequestGetAllFilmSessionThenReturnSessionsListPage() {
        var sessionDto1 = new SessionDto();
        sessionDto1.setId(1);
        var sessionDto2 = new SessionDto();
        sessionDto2.setId(2);
        var expectAllDto = List.of(sessionDto1, sessionDto2);
        when(filmSessionService.getAllSessionDto()).thenReturn(expectAllDto);

        var model = new ConcurrentModel();
        var actualPage = filmSessionController.getAllFilmSession(model);
        var actualAllDto = model.getAttribute("sessionsDto");

        assertThat(actualAllDto).usingRecursiveComparison().isEqualTo(expectAllDto);
        assertThat(actualPage).isEqualTo("sessions/list");
    }

    @Test
    public void whenRequestGetSessionByFilmIdThenReturnSessionsListByFilmPage() {
        int filmId = 2;
        var sessionDto1 = new SessionDto.Builder()
                .buildId(1)
                .buildFileId(filmId)
                .buildGenre("3")
                .build();
        var sessionDto2 = new SessionDto.Builder()
                .buildId(2)
                .buildFileId(filmId)
                .buildGenre("3")
                .build();
        var expectSessionsDto = List.of(sessionDto1, sessionDto2);
        when(filmSessionService.getSessionDtoByFilmId(filmId)).thenReturn(expectSessionsDto);

        var model = new ConcurrentModel();
        var actualPage = filmSessionController.getSessionByFilm(model, filmId);
        var actualFirstSessionFilmDto = model.getAttribute("sessionFilm");
        var actualSessionsFilmDto = model.getAttribute("sessionsDtoByFilm");

        assertThat(actualFirstSessionFilmDto).usingRecursiveComparison().isEqualTo(sessionDto1);
        assertThat(actualSessionsFilmDto).usingRecursiveComparison().isEqualTo(expectSessionsDto);
        assertThat(actualPage).isEqualTo("sessions/listByFilm");
    }

    @Test
    public void whenRequestGetSessionByFilmIdThenReturnErrorsPageAndSameMessage() {
        var expectedMessage = "Сеансы с выбранным фильмом не найдены.";
        when(filmSessionService.getSessionDtoByFilmId(anyInt())).thenReturn(Collections.EMPTY_LIST);

        var model = new ConcurrentModel();
        var actualPage = filmSessionController.getSessionByFilm(model, anyInt());
        var actualMessage = model.getAttribute("message");

        assertThat(actualPage).isEqualTo("statuses/errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}