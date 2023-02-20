package ru.my.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * IndexController отображение вида главной страницы.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 20.02.2023
 */
@Controller
public class IndexController {
    public static final int LOGO = 9;
    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("fileLogoId", LOGO);
        return "index";
    }
}
