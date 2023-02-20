package ru.my.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.my.cinema.service.FilmService;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FilmController controller для передачи модели Film в вид.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 20.02.2023
 */
@Controller
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public String getAllFilm(Model model) {
        model.addAttribute("fileLogoId", IndexController.LOGO);
        model.addAttribute("allFilmDto", filmService.getAllFilm());
        return "films/list";
    }
}
