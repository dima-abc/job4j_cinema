package ru.my.cinema.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.my.cinema.controller.IndexController;
import ru.my.cinema.model.dto.UserDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.03.2023
 */
@Component
@Order(2)
public class SessionFilter extends HttpFilter {

    /**
     * Метод проверяет наличие пользователя в сессии.
     * При отсутствии добавляет пользователя гость в request.
     *
     * @param session HttpSession
     * @param request HttpServletRequest
     */
    private void addUserToSession(HttpSession session, HttpServletRequest request) {
        var user = (UserDto) session.getAttribute("user");
        if (user == null) {
            user = new UserDto();
            user.setFullName("Гость");
        }
        request.setAttribute("fileLogoId", IndexController.LOGO);
        request.setAttribute("user", user);
    }

    /**
     * Фильтр 2 в цепочке фильтров.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var session = request.getSession();
        addUserToSession(session, request);
        chain.doFilter(request, response);
    }
}
