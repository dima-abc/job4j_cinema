package ru.my.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.my.cinema.configuration.DatasourceConfiguration;
import ru.my.cinema.model.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Sql2oTicketRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
class Sql2oTicketRepositoryTest {
    private static Genre genre = new Genre(0, "genre");
    private static File file = new File(0, "nameFile", "pathFile");
    private static Film film = new Film(0, "name1", "description1",
            2001, 0, 18, 180, 0);
    private static Hall hall = new Hall(0, "hall1", 3, 3, "descriptionHall");

    private static FilmSession filmSession = new FilmSession(0, 0, 0,
            LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.SECONDS),
            LocalDateTime.now().plusDays(10).truncatedTo(ChronoUnit.SECONDS),
            1000);
    private static User user = new User(0, "FIO", "mail@mail.ru", "qwerty");
    private static Sql2o sql2o;
    private static Sql2oUserRepository sql2oUserRepository;
    private static Sql2oTicketRepository sql2oTicketRepository;

    @BeforeAll
    public static void initTicketRepository() throws Exception {
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
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
        sql2oUserRepository.save(user);

        try (var connection = sql2o.open()) {
            var clearQuery = connection.createQuery("DELETE FROM tickets");
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
            int filmId = queryFilms.executeUpdate().getKey(Integer.class);
            film.setId(filmId);

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

            filmSession.setFilmId(film.getId());
            filmSession.setHallId(hall.getId());

            var sqlInsertSessions = """
                    INSERT INTO film_sessions(film_id, hall_id, start_time, end_time, price) 
                    VALUES (:filmId, :hallId, :startTime, :endTime, :price)
                    """;
            var queryFilmSession = connection.createQuery(sqlInsertSessions, true);
            queryFilmSession.addParameter("filmId", filmSession.getFilmId());
            queryFilmSession.addParameter("hallId", filmSession.getHallId());
            queryFilmSession.addParameter("startTime", filmSession.getStartTime());
            queryFilmSession.addParameter("endTime", filmSession.getEndTime());
            queryFilmSession.addParameter("price", filmSession.getPrice());
            int generateIdFilmSession = queryFilmSession.executeUpdate().getKey(Integer.class);
            filmSession.setId(generateIdFilmSession);
        }
    }

    @AfterAll
    public static void clearTable() {
        try (var connection = sql2o.open()) {
            var queryClearUsers = connection.createQuery("DELETE FROM users");
            queryClearUsers.executeUpdate();

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

    @AfterEach
    public void clearTickets() {
        try (var connection = sql2o.open()) {
            var queryClearTickets = connection.createQuery("DELETE FROM tickets");
            queryClearTickets.executeUpdate();
        }
    }


    @Test
    void whenSaveTicketThenReturnOptionalTicket() {
        var ticket = new Ticket(0, filmSession.getId(), 3, 3, user.getId());
        var actualTicket = sql2oTicketRepository.save(ticket);
        var expectedTicket = Optional.of(ticket);

        assertThat(actualTicket).usingRecursiveComparison().isEqualTo(expectedTicket);
    }

    @Test
    void whenSaveTwoTicketWhetEqualsThenReturnEmpty() {
        var ticket = new Ticket(0, filmSession.getId(), 3, 3, user.getId());
        sql2oTicketRepository.save(ticket);
        ticket.setUserId(0);
        var actualTicketEmpty = sql2oTicketRepository.save(ticket);

        assertThat(actualTicketEmpty).isEmpty();
    }

    @Test
    void whenGetTicketByUserIdThenReturnCollectionTickets() {
        var ticket1 = new Ticket(0, filmSession.getId(), 3, 3, user.getId());
        var ticket2 = new Ticket(0, filmSession.getId(), 2, 5, user.getId());
        sql2oTicketRepository.save(ticket1);
        sql2oTicketRepository.save(ticket2);

        var actualTicketsByUser = sql2oTicketRepository.getTicketByUserId(user.getId());
        var expectedTicketByUser = List.of(ticket1, ticket2);

        assertThat(actualTicketsByUser).usingRecursiveComparison().isEqualTo(expectedTicketByUser);
    }

    @Test
    void whenGetTicketByUserIdZeroThenReturnCollectionEmpty() {
        var ticket1 = new Ticket(0, filmSession.getId(), 3, 3, user.getId());
        var ticket2 = new Ticket(0, filmSession.getId(), 2, 5, user.getId());
        sql2oTicketRepository.save(ticket1);
        sql2oTicketRepository.save(ticket2);

        var actualTicketsByUser = sql2oTicketRepository.getTicketByUserId(0);

        assertThat(actualTicketsByUser).isEqualTo(Collections.emptyList());
    }
}