package ru.my.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.my.cinema.model.Ticket;
import ru.my.cinema.service.TicketService;

import java.util.Optional;

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
    public String createTicket(@ModelAttribute Ticket ticket, Model model) {
        var ticketOptional = Optional.empty();
        if (ticketOptional.isEmpty()) {
            model.addAttribute("message", "Не удалось приобрести билет на заданное место. Вероятно оно уже занято.");
            return "errors/404";
        }
        model.addAttribute("message", "Билет куплен успешно");
        return "errors/200";
    }
}
