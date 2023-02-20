package ru.my.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.my.cinema.service.SimpleFilmSessionService;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 20.02.2023
 */
@Controller
@RequestMapping("/sessions")
public class FilmSessionController {
    private final SimpleFilmSessionService filmSessionService;

    public FilmSessionController(SimpleFilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping
    public String getAllFilmSession(Model model) {
        model.addAttribute("fileLogoId", IndexController.LOGO);
        model.addAttribute("filmSessionsDtoS", filmSessionService.getAllFilmSessionDto());
        return "sessions/list";
    }
}
