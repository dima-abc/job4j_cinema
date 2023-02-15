package ru.my.cinema.repository;

import ru.my.cinema.model.Hall;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * HallRepository interface описывает поведение хранилища кинозалов
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.02.2023
 */
public interface HallRepository {
    Optional<Hall> getHallById(int hallId);

    Collection<Hall> getAllHall();
}
