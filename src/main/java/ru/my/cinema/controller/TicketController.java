package ru.my.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.my.cinema.model.Ticket;
import ru.my.cinema.model.dto.TicketDto;
import ru.my.cinema.model.dto.UserDto;
import ru.my.cinema.service.TicketService;

import javax.servlet.http.HttpServletRequest;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 02.03.2023
 */
@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/buy")
    public String createTicket(@ModelAttribute TicketDto ticketDto, Model model, HttpServletRequest request) {
        var session = request.getSession();
        var user = (UserDto) session.getAttribute("user");
        var ticketOptional = ticketService.save(
                new Ticket(-1,
                        ticketDto.getSessionId(),
                        ticketDto.getRow(),
                        ticketDto.getPlace(),
                        user.getId()));
        if (ticketOptional.isEmpty()) {
            model.addAttribute("fileLogoId", IndexController.LOGO);
            var message = new StringBuilder("Не удалось приобрести билет. ")
                    .append(" Сеанс: ").append(ticketDto.getFilmName())
                    .append(", ").append(ticketDto.getHallName())
                    .append(", Ряд: ").append(ticketDto.getRow())
                    .append(", Место: ").append(ticketDto.getPlace())
                    .append(". Вероятно оно уже занято.").toString();
            model.addAttribute("message", message);
            return "statuses/errors/404";
        }
        var message = new StringBuilder("Билет куплен успешно. ")
                .append(" Сеанс: ").append(ticketDto.getFilmName())
                .append(", ").append(ticketDto.getHallName())
                .append(", Ряд: ").append(ticketDto.getRow())
                .append(", Место: ").append(ticketDto.getPlace()).toString();
        model.addAttribute("fileLogoId", IndexController.LOGO);
        model.addAttribute("message", message);
        return "statuses/success/200";
    }
}
