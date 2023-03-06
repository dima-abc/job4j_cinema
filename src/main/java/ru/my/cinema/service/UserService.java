package ru.my.cinema.service;

import ru.my.cinema.model.User;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * UserService interface описывает поведение бизнес логики обработки модели User.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
public interface UserService {
    Optional<User> save(User user);
    Optional<User> findByEmailAndPassword(String email, String password);
}
