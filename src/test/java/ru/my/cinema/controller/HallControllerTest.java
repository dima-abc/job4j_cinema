package ru.my.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.my.cinema.model.dto.HallDto;
import ru.my.cinema.service.HallService;

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
 * HallController TEST
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 08.03.2023
 */
public class HallControllerTest {
    private HallService hallService;
    private HallController hallController;

    @BeforeEach
    public void initServices() {
        hallService = mock(HallService.class);
        hallController = new HallController(hallService);
    }

    @Test
    public void whenRequestGetHallBySessionIdThenReturnHallsHallSession() {
        int sessionId = 1;
        var hallDto = new HallDto.Builder()
                .buildHallId(11)
                .buildHallName("11")
                .buildSessionId(sessionId)
                .buildFilmName("film")
                .build();
        when(hallService.getHallDtoBySessionId(sessionId)).thenReturn(Optional.of(hallDto));

        var model = new ConcurrentModel();
        var actualPage = hallController.getHallBySession(model, sessionId);
        var actualHallDto = model.getAttribute("hallDto");

        assertThat(actualPage).isEqualTo("halls/hallSession");
        assertThat(actualHallDto).usingRecursiveComparison().isEqualTo(hallDto);
    }

    @Test
    public void whenRequestGetHallBySessionIdThenReturnErrorPageAndSameMessage() {
        var expectedMessage = "Сеансы с выбранным фильмом не найдены.";
        when(hallService.getHallDtoBySessionId(anyInt())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var actualPage = hallController.getHallBySession(model, anyInt());
        var actualMessage = model.getAttribute("message");

        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat(actualPage).isEqualTo("statuses/errors/404");
    }

}