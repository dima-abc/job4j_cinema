package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.User;
import ru.my.cinema.model.dto.UserDto;
import ru.my.cinema.repository.UserRepository;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * SimpleUserService реализация бизнес логики для модели User.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.03.2023
 */
@Service
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDto getUserDtoByUser(User user) {
        return new UserDto(user.getId(),
                user.getFullName(),
                user.getPassword());
    }

    @Override
    public Optional<UserDto> save(User user) {
        var userOptional = userRepository.save(user);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(getUserDtoByUser(userOptional.get()));
    }

    @Override
    public Optional<UserDto> findByEmailAndPassword(String email, String password) {
        var userOptional = userRepository.findByEmailAndPassword(email, password);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(getUserDtoByUser(userOptional.get()));
    }
}
