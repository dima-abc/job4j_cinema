package ru.my.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.my.cinema.service.HallService;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.02.2023
 */
@Controller
@RequestMapping("/halls")
public class HallController {
    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping("/{sessionId}")
    public String getHallBySession(Model model, @PathVariable int sessionId) {
        var hallDto = hallService.getHallDtoBySessionId(sessionId);
        if (hallDto.isEmpty()) {
            model.addAttribute("fileLogoId", IndexController.LOGO);
            model.addAttribute("message", "Сеансы с выбранным фильмом не найдены.");
            return "statuses/errors/404";
        }
        model.addAttribute("hallDto", hallDto.get());
        return "halls/hallSession";
    }
}
