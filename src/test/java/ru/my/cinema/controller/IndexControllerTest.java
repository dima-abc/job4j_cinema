package ru.my.cinema.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * IndexController TEST.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 08.03.2023
 */
public class IndexControllerTest {
    @Test
    public void whenRequestGetIndexPageThenReturnIndexPage() {
        var indexController = new IndexController();

        var actualPage = indexController.getIndex();

        assertThat(actualPage).isEqualTo("index");
    }
}