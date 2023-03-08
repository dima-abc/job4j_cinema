package ru.my.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import ru.my.cinema.model.Ticket;
import ru.my.cinema.model.dto.TicketDto;
import ru.my.cinema.model.dto.UserDto;
import ru.my.cinema.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * TicketController TEST.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 08.03.2023
 */
public class TicketControllerTest {
    private TicketService ticketService;
    private HttpServletRequest request;
    private TicketController ticketController;

    @BeforeEach
    public void initServices() {
        ticketService = mock(TicketService.class);
        request = mock(HttpServletRequest.class);
        ticketController = new TicketController(ticketService);
    }

    @Test
    public void whenRequestGetTicketByUserThenReturnMyTicketPage() {
        var userDto = new UserDto(1, "name", "email");
        var ticketDto1 = new TicketDto();
        ticketDto1.setSessionId(1);
        var ticketDto2 = new TicketDto();
        ticketDto2.setSessionId(3);
        var expectTickets = List.of(ticketDto1, ticketDto2);

        when(request.getAttribute("user")).thenReturn(userDto);
        when(ticketService.getTicketByUserId(userDto.getId())).thenReturn(expectTickets);

        var model = new ConcurrentModel();
        var actualPage = ticketController.getTicketByUser(model, request);
        var actualTickets = model.getAttribute("ticketsByUser");

        assertThat(actualTickets).usingRecursiveComparison().isEqualTo(expectTickets);
        assertThat(actualPage).isEqualTo("tickets/myTicket");
    }

    @Test
    public void whenRequestPostCreateTicketThenReturnStatusPage200AndSameMessage() {
        var ticketDto = new TicketDto(1, 22, "film", "hall", 2, 5);
        var userDto = new UserDto(1, "name", "email");
        var ticket = new Ticket(-1, ticketDto.getSessionId(), ticketDto.getRow(), ticketDto.getPlace(), userDto.getId());
        var ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        var expectMessage = new StringBuilder("Билет куплен успешно. ")
                .append(" Сеанс: ").append(ticketDto.getFilmName())
                .append(", ").append(ticketDto.getHallName())
                .append(", Ряд: ").append(ticketDto.getRow())
                .append(", Место: ").append(ticketDto.getPlace()).toString();

        when(request.getAttribute("user")).thenReturn(userDto);
        when(ticketService.save(ticketCaptor.capture())).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var actualPage = ticketController.createTicket(ticketDto, model, request);
        var actualMessage = model.getAttribute("message");
        var actualTicket = ticketCaptor.getValue();

        assertThat(actualPage).isEqualTo("statuses/success/200");
        assertThat(actualMessage).isEqualTo(expectMessage);
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenRequestPostCreateTicketThenReturnErrorPageAndSameMessage() {
        var ticketDto = new TicketDto(1, 22, "film", "hall", 2, 5);
        var userDto = new UserDto(1, "name", "email");
        var expectedMessage = new StringBuilder("Не удалось приобрести билет. ")
                .append(" Сеанс: ").append(ticketDto.getFilmName())
                .append(", ").append(ticketDto.getHallName())
                .append(", Ряд: ").append(ticketDto.getRow())
                .append(", Место: ").append(ticketDto.getPlace())
                .append(". Вероятно оно уже занято.").toString();
        when(request.getAttribute("user")).thenReturn(userDto);
        when(ticketService.save(any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var actualPage = ticketController.createTicket(ticketDto, model, request);
        var actualMessage = model.getAttribute("message");

        assertThat(actualPage).isEqualTo("statuses/errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}