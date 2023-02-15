package ru.my.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.my.cinema.configuration.DatasourceConfiguration;
import ru.my.cinema.model.Film;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Sql2oFilmRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.02.2023
 */
class Sql2oFilmRepositoryTest {
    private static Film film1 = new Film(0, "name1", "description1",
            2001, 1, 18, 180, 1);
    private static Film film2 = new Film(0, "name2", "description2",
            2002, 2, 19, 120, 2);
    private static Sql2o sql2o;
    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    public static void initRepository() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oGenreRepositoryTest.class
                .getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);

        try (var connection = sql2o.open()) {
            var queryClear = connection.createQuery("DELETE FROM films");
            queryClear.executeUpdate();
            var sql = """
                    INSERT INTO films(name, description, year, genre_id, minimal_age, duration_in_minutes) 
                    VALUES (:name, :description, :year, :genreId, :minimalAge, :durationInMinutes, :fileId)
                    """;
            var query = connection.createQuery(sql, true);
            query.addParameter("name", film1.getName());
            query.addParameter("description", film1.getDescription());
            query.addParameter("year", film1.getYear());
            query.addParameter("genreId", film1.getGenreId());
            query.addParameter("minimalAge", film1.getMinimalAge());
            query.addParameter("durationInMinutes", film1.getDurationInMinutes());
            query.addParameter("fileId", film1.getFileId());
            int filmId1 = query.executeUpdate().getKey(Integer.class);
            film1.setId(filmId1);
        }
    }

    @Test
    void getFilmById() {
    }

    @Test
    void getAllFilm() {
    }
}