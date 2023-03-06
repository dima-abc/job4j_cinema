package ru.my.cinema.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * AuthorizationFilter фильтр 1 в цепочке фильтров.
 * Выполняет контроль доступа к ресурсу.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.03.2023
 */
@Component
@Order(1)
public class AuthorizationFilter extends HttpFilter {

    private boolean isAlwaysPermitted(String uri) {
        return uri.startsWith("/tickets");
    }

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        var uri = request.getRequestURI();
        if (!isAlwaysPermitted(uri)) {
            chain.doFilter(request, response);
            return;
        }
        var userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            var loginPageUrl = request.getContextPath() + "/users/login";
            response.sendRedirect(loginPageUrl);
            return;
        }
        chain.doFilter(request, response);
    }
}
