package ru.my.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.my.cinema.model.User;
import ru.my.cinema.model.dto.UserDto;
import ru.my.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * UserController TEST.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.03.2023
 */
public class UserControllerTest {
    private UserService userService;
    private UserController userController;
    private HttpServletRequest httpServletRequest;
    private HttpSession httpSession;

    @BeforeEach
    public void initService() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        httpServletRequest = mock(HttpServletRequest.class);
        httpSession = mock(HttpSession.class);
    }

    @Test
    public void whenRequestGetRegisterPageThenReturnRegisterPage() {
        var view = userController.getRegisterPage();
        assertThat(view).isEqualTo("users/register");
    }

    @Test
    public void whenRequestPostRegisterUserThenReturnIndexPage() {
        var user = new User(1, "name", "mail", "pass");
        var userDto = new UserDto(user.getId(), user.getFullName(), user.getEmail());
        var userCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userCaptor.capture())).thenReturn(Optional.of(userDto));
        when(httpServletRequest.getSession()).thenReturn(httpSession);

        var model = new ConcurrentModel();
        var view = userController.registerUser(model, user, httpServletRequest);
        var actualUser = userCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(user);
        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whenRequestPostRegisterUserThenReturnErrorsPageAndSameMessage() {
        var expectMessage = "Пользователь с такой почтой уже существует";
        var expectGuestUserDto = new UserDto(0, "Гость", "");
        var user = new User(1, "name", "mail", "pass");
        when(userService.save(user)).thenReturn(Optional.empty());
        when(httpServletRequest.getSession()).thenReturn(httpSession);

        var model = new ConcurrentModel();
        var view = userController.registerUser(model, user, httpServletRequest);
        var actualMessage = model.getAttribute("message");
        var actualGuestUserDto = model.getAttribute("user");

        assertThat(actualMessage).isEqualTo(expectMessage);
        assertThat(actualGuestUserDto).usingRecursiveComparison().isEqualTo(expectGuestUserDto);
        assertThat(view).isEqualTo("statuses/errors/404");
    }

    @Test
    public void whenRequestGetLoginPageThenReturnLoginPage() {
        var view = userController.getLoginPage();

        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenRequestGetLoginUserThenRedirectIndexPage() {
        var user = new User(1, "name", "mail", "pass");
        var userDto = new UserDto(user.getId(), user.getFullName(), user.getEmail());
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.of(userDto));
        when(httpServletRequest.getSession()).thenReturn(httpSession);

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, httpServletRequest);

        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whenRequestPostLoginUserThenReturnUsersLoginPageAndSameUser() {
        var user = new User(1, "name", "mail", "pass");
        var expectMessage = "Почта или пароль введены неверно";
        var expectGuestUserDto = new UserDto(0, "Гость", "");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.empty());
        when(httpServletRequest.getSession()).thenReturn(httpSession);

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, httpServletRequest);
        var actualMessage = model.getAttribute("error");
        var actualGuestUserDto = model.getAttribute("user");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualMessage).isEqualTo(expectMessage);
        assertThat(actualGuestUserDto).usingRecursiveComparison().isEqualTo(expectGuestUserDto);
    }

    @Test
    public void whenRequestLogoutThenRedirectUsersLoginPage() {
        var view = userController.logout(httpSession);

        assertThat(view).isEqualTo("redirect:/users/login");
    }
}