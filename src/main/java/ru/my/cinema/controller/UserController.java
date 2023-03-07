package ru.my.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.my.cinema.model.User;
import ru.my.cinema.model.dto.UserDto;
import ru.my.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * UserController контроллер авторизации и регистрации пользователей.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.03.2023
 */
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(Model model,
                               @ModelAttribute User user,
                               HttpServletRequest request) {
        var saveUserDtoOptional = userService.save(user);
        var session = request.getSession();
        if (saveUserDtoOptional.isEmpty()) {
            model.addAttribute("user", new UserDto(0, "Гость", ""));
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "statuses/errors/404";
        }
        session.setAttribute("user", saveUserDtoOptional.get());
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user,
                            Model model,
                            HttpServletRequest request) {
        var userDtoOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        var session = request.getSession();
        if (userDtoOptional.isEmpty()) {
            model.addAttribute("user", new UserDto(0, "Гость", ""));
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        session.setAttribute("user", userDtoOptional.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
