package ru.my.cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Запуск приложения.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.02.2023
 */
@SpringBootApplication
public class CinemaApp {
    private static final Logger LOG = LoggerFactory.getLogger(CinemaApp.class);
    private static final String START_PAGE = "http://localhost:8080/index";

    public static void main(String[] args) {
        SpringApplication.run(CinemaApp.class, args);
        LOG.info("Go to: {}", START_PAGE);
    }
}
