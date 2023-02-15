package ru.my.cinema.repository;

import ru.my.cinema.model.User;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * UserRepository описывает поведение хранилища пользователей.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.02.2023
 */
public interface UserRepository {
    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);
}
