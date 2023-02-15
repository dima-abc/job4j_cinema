package ru.my.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.my.cinema.configuration.DatasourceConfiguration;
import ru.my.cinema.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Sql2oGenreRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.02.2023
 */
class Sql2oGenreRepositoryTest {
    private static Genre genre1 = new Genre(0, "genre1");
    private static Genre genre2 = new Genre(0, "genre2");
    private static Sql2o sql2o;
    private static Sql2oGenreRepository sql2oGenreRepository;

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

        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);

        try (var connection = sql2o.open()) {
            var queryClear = connection.createQuery("DELETE FROM genres");
            queryClear.executeUpdate();
            var sql = """
                    INSERT INTO genres(name) VALUES (:name)
                    """;
            var query = connection.createQuery(sql, true);
            query.addParameter("name", genre1.getName());
            int generateId1 = query.executeUpdate().getKey(Integer.class);
            genre1.setId(generateId1);
            query.addParameter("name", genre2.getName());
            int generateId2 = query.executeUpdate().getKey(Integer.class);
            genre2.setId(generateId2);
        }
    }

    @AfterAll
    public static void clearGenres() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "DELETE FROM genres");
            query.executeUpdate();
        }
    }

    @Test
    void whenGetGenreByIdGenre1ThenReturnGenre1Optional() {
        var actualGenre = sql2oGenreRepository.getGenreById(genre1.getId());
        System.err.println("****************" + actualGenre.get());
        var expectedGenre = Optional.of(genre1);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void whenGetGenreByIdZeroThenReturnEmpty() {
        var actualGenre = sql2oGenreRepository.getGenreById(0);
        var expectedGenre = Optional.of(genre1);
        assertThat(actualGenre).isEmpty();
    }

    @Test
    void whenFindAllGenreThenReturnCollectionGenre1Genre2() {
        var actualCollectionGenre = sql2oGenreRepository.findAllGenre();
        System.err.println("****************" + actualCollectionGenre.toString());
        var expectedCollectionGenre = List.of(genre1, genre2);
        assertThat(actualCollectionGenre).isEqualTo(expectedCollectionGenre);
    }
}