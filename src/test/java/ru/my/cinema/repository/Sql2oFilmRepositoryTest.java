package ru.my.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.my.cinema.configuration.DatasourceConfiguration;
import ru.my.cinema.model.File;
import ru.my.cinema.model.Film;
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
 * Sql2oFilmRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.02.2023
 */
class Sql2oFilmRepositoryTest {
    private static Genre genre = new Genre(0, "genre");
    private static File file = new File(0, "nameFile1", "pathFile1");
    private static Film film1 = new Film(0, "name1", "description1",
            2001, 0, 18, 180, 0);
    private static Film film2 = new Film(0, "name2", "description2",
            2002, 0, 19, 120, 0);
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

            var queryInsertFile = connection
                    .createQuery("INSERT INTO files(name, path) values (:name, :path)", true)
                    .addParameter("name", file.getName())
                    .addParameter("path", file.getPath());
            int generateIdFile = queryInsertFile.executeUpdate().getKey(Integer.class);
            file.setId(generateIdFile);

            var queryInsertGenre = connection
                    .createQuery("INSERT INTO genres(name) values (:name)", true)
                    .addParameter("name", genre.getName());
            int generateIdGenre = queryInsertGenre.executeUpdate().getKey(Integer.class);
            genre.setId(generateIdGenre);

            film1.setFileId(file.getId());
            film1.setGenreId(genre.getId());
            film2.setFileId(file.getId());
            film2.setGenreId(genre.getId());

            var sql = """
                    INSERT INTO films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) 
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

            query.addParameter("name", film2.getName());
            query.addParameter("description", film2.getDescription());
            query.addParameter("year", film2.getYear());
            query.addParameter("genreId", film2.getGenreId());
            query.addParameter("minimalAge", film2.getMinimalAge());
            query.addParameter("durationInMinutes", film2.getDurationInMinutes());
            query.addParameter("fileId", film2.getFileId());
            int filmId2 = query.executeUpdate().getKey(Integer.class);
            film2.setId(filmId2);
        }
    }

    @AfterAll
    public static void clearFilms() {
        try (var connection = sql2o.open()) {
            var queryClearFilm = connection.createQuery("DELETE FROM films");
            queryClearFilm.executeUpdate();

            var queryClearFile = connection.createQuery("DELETE FROM files");
            queryClearFile.executeUpdate();

            var queryClearGenre = connection.createQuery("DELETE FROM genres");
            queryClearGenre.executeUpdate();
        }
    }

    @Test
    void whenGetFilmByIdFilm1ThenReturnFilm1Optional() {
        var actualFilm = sql2oFilmRepository.getFilmById(film1.getId());
        var expectedFilm = Optional.of(film1);
        assertThat(actualFilm).usingRecursiveComparison().isEqualTo(expectedFilm);
    }

    @Test
    void whenGetFilmByIdFilm2ThenReturnFilm2Optional() {
        var actualFilm = sql2oFilmRepository.getFilmById(film2.getId());
        var expectedFilm = Optional.of(film2);
        assertThat(actualFilm).usingRecursiveComparison().isEqualTo(expectedFilm);
    }

    @Test
    void whenGetFilmByIdZeroThenReturnEmpty() {
        var actual = sql2oFilmRepository.getFilmById(0);
        assertThat(actual).isEmpty();
    }

    @Test
    void whenGetAllFilmThenReturnListOfFilm1Film2() {
        var actualFilms = sql2oFilmRepository.getAllFilm();
        var expectedFilms = List.of(film1, film2);
        assertThat(actualFilms).usingRecursiveComparison().isEqualTo(expectedFilms);
    }
}