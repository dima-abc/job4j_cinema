package ru.my.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.my.cinema.model.File;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Sql2oFileRepository хранилище в базе данных модели File
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.02.2023
 */
@Repository
public class Sql2oFileRepository implements FileRepository {
    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<File> getFindById(int idFile) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM files WHERE id = :idFile");
                    query.addParameter("idFile", idFile);
            var file = query.executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }
}
