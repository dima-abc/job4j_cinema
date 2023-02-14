package ru.my.cinema.repository;

import ru.my.cinema.model.File;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FileRepository интерфейс описывает поведение хранилища постеров фильмов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.02.2023
 */
public interface FileRepository {

    Optional<File> getFindById(int idFile);

}
