package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.Hall;
import ru.my.cinema.repository.HallRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * SimpleHallService
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.02.2023
 */
@Service
public class SimpleHallService implements HallService {
    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Optional<Hall> getHallById(int hallId) {
        return Optional.empty();
    }

    @Override
    public Collection<Hall> getAllHall() {
        return null;
    }
}
