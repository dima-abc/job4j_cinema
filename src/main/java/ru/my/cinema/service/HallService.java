package ru.my.cinema.service;

import ru.my.cinema.model.Hall;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * HallService interface описывает поведение бизнес логики обработки модели Hall.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
public interface HallService {
    public Optional<Hall> getHallById(int hallId);

    public Collection<Hall> getAllHall();
}
