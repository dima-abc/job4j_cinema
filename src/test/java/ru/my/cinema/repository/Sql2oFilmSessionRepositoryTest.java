package ru.my.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.my.cinema.configuration.DatasourceConfiguration;
import ru.my.cinema.model.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Sql2oFilmSession TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 16.02.2023
 */
class Sql2oFilmSessionRepositoryTest {
    private static Genre genre = new Genre(0, "genre");
    private static File file = new File(0, "nameFile", "pathFile");
    private static Film film = new Film(0, "name1", "description1",
            2001, 0, 18, 180, 0);
    private static Hall hall = new Hall(0, "hall1", 3, 3, "descriptionHall");

    private static FilmSession filmSession1 = new FilmSession(0, 0, 0,
            LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.SECONDS),
            LocalDateTime.now().plusDays(10).truncatedTo(ChronoUnit.SECONDS),
            1000);
    private static FilmSession filmSession2 = new FilmSession(0, 0, 0,
            LocalDateTime.now().minusDays(10).truncatedTo(ChronoUnit.SECONDS),
            LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.SECONDS),
            2000);
    private static Sql2o sql2o;
    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void initFilmSessionRepository() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepositoryTest.class
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

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);

        try (var connection = sql2o.open()) {
            var clearQuery = connection.createQuery("DELETE FROM film_sessions");
            clearQuery.executeUpdate();

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

            film.setFileId(file.getId());
            film.setGenreId(genre.getId());

            var sqlInsertFilms = """
                    INSERT INTO films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) 
                    VALUES (:name, :description, :year, :genreId, :minimalAge, :durationInMinutes, :fileId)
                    """;
            var queryFilms = connection.createQuery(sqlInsertFilms, true);
            queryFilms.addParameter("name", film.getName());
            queryFilms.addParameter("description", film.getDescription());
            queryFilms.addParameter("year", film.getYear());
            queryFilms.addParameter("genreId", film.getGenreId());
            queryFilms.addParameter("minimalAge", film.getMinimalAge());
            queryFilms.addParameter("durationInMinutes", film.getDurationInMinutes());
            queryFilms.addParameter("fileId", film.getFileId());
            int filmId1 = queryFilms.executeUpdate().getKey(Integer.class);
            film.setId(filmId1);

            var sqlInsertHall = """
                    INSERT INTO halls(name, row_count, place_count, description) 
                    VALUES (:name, :rowCount, :placeCount, :description)
                    """;
            var queryHall = connection.createQuery(sqlInsertHall, true);
            queryHall.addParameter("name", hall.getName());
            queryHall.addParameter("rowCount", hall.getRowCount());
            queryHall.addParameter("placeCount", hall.getPlaceCount());
            queryHall.addParameter("description", hall.getDescription());
            int generateIdHall = queryHall.executeUpdate().getKey(Integer.class);
            hall.setId(generateIdHall);

            filmSession1.setFilmId(film.getId());
            filmSession1.setHallId(hall.getId());
            filmSession2.setFilmId(film.getId());
            filmSession2.setHallId(hall.getId());

            var sqlInsertSessions = """
                    INSERT INTO film_sessions(film_id, hall_id, start_time, end_time, price) 
                    VALUES (:filmId, :hallId, :startTime, :endTime, :price)
                    """;
            var queryFilmSession = connection.createQuery(sqlInsertSessions, true);
            queryFilmSession.addParameter("filmId", filmSession1.getFilmId());
            queryFilmSession.addParameter("hallId", filmSession1.getHallId());
            queryFilmSession.addParameter("startTime", filmSession1.getStartTime());
            queryFilmSession.addParameter("endTime", filmSession1.getEndTime());
            queryFilmSession.addParameter("price", filmSession1.getPrice());
            int generateId1 = queryFilmSession.executeUpdate().getKey(Integer.class);
            filmSession1.setId(generateId1);

            queryFilmSession.addParameter("filmId", filmSession2.getFilmId());
            queryFilmSession.addParameter("hallId", filmSession2.getHallId());
            queryFilmSession.addParameter("startTime", filmSession2.getStartTime());
            queryFilmSession.addParameter("endTime", filmSession2.getEndTime());
            queryFilmSession.addParameter("price", filmSession2.getPrice());
            int generateId2 = queryFilmSession.executeUpdate().getKey(Integer.class);
            filmSession2.setId(generateId2);
        }
    }

    @AfterAll
    public static void clearFilmSession() {
        try (var connection = sql2o.open()) {
            var queryClearFilmSession = connection.createQuery("DELETE FROM film_sessions");
            queryClearFilmSession.executeUpdate();

            var queryClearFilm = connection.createQuery("DELETE FROM films");
            queryClearFilm.executeUpdate();

            var queryClearFile = connection.createQuery("DELETE FROM files");
            queryClearFile.executeUpdate();

            var queryClearGenre = connection.createQuery("DELETE FROM genres");
            queryClearGenre.executeUpdate();

            var queryClearHall = connection.createQuery("DELETE FROM halls");
            queryClearHall.executeUpdate();
        }
    }

    @Test
    void whenGetFilmSessionById1ThenReturnSession1Optional() {
        var actualFilmSession = sql2oFilmSessionRepository.getFilmSessionById(filmSession1.getId());
        var expected = Optional.of(filmSession1);
        assertThat(actualFilmSession).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenGetFilmSessionById2ThenReturnSession2Optional() {
        var actualFilmSession = sql2oFilmSessionRepository.getFilmSessionById(filmSession2.getId());
        var expected = Optional.of(filmSession2);
        assertThat(actualFilmSession).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenGetFilmSessionByIdZeroThenReturnEmpty() {
        var actualFilmSession = sql2oFilmSessionRepository.getFilmSessionById(0);
        assertThat(actualFilmSession).isEmpty();
    }

    @Test
    void whenGetAllFilmSessionThenReturnListSession1Session2() {
        var actualListSession = sql2oFilmSessionRepository.getAllFilmSession();
        var expected = List.of(filmSession1, filmSession2);
        assertThat(actualListSession).usingRecursiveComparison().isEqualTo(expected);
    }
}