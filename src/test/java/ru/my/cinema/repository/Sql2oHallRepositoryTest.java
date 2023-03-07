package ru.my.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.my.cinema.configuration.DatasourceConfiguration;
import ru.my.cinema.model.Hall;

import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Sql2oHallRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
class Sql2oHallRepositoryTest {
    private static Hall hall1 = new Hall(0, "hall1", 5, 5, "description1");
    private static Hall hall2 = new Hall(0, "hall2", 3, 3, "description2");
    private static Sql2o sql2o;
    private static Sql2oHallRepository sql2oHallRepository;

    @BeforeAll
    public static void initHallRepository() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepository.class
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
        sql2oHallRepository = new Sql2oHallRepository(sql2o);

        try (var connection = sql2o.open()) {
            var queryClearHall = connection.createQuery("DELETE FROM halls");
            queryClearHall.executeUpdate();

            var sqlInsertHall = """
                    INSERT INTO halls(name, row_count, place_count, description) 
                    VALUES (:name, :rowCount, :placeCount, :description)
                    """;
            var queryInsertHall = connection.createQuery(sqlInsertHall, true);

            queryInsertHall.addParameter("name", hall1.getName());
            queryInsertHall.addParameter("rowCount", hall1.getRowCount());
            queryInsertHall.addParameter("placeCount", hall1.getPlaceCount());
            queryInsertHall.addParameter("description", hall1.getDescription());
            int generateIdHell1 = queryInsertHall.executeUpdate().getKey(Integer.class);
            hall1.setId(generateIdHell1);

            queryInsertHall.addParameter("name", hall2.getName());
            queryInsertHall.addParameter("rowCount", hall2.getRowCount());
            queryInsertHall.addParameter("placeCount", hall2.getPlaceCount());
            queryInsertHall.addParameter("description", hall2.getDescription());
            int generateIdHell2 = queryInsertHall.executeUpdate().getKey(Integer.class);
            hall2.setId(generateIdHell2);
        }
    }

    @AfterAll
    public static void clearHalls() {
        try (var connection = sql2o.open()) {
            var queryClearHall = connection.createQuery("DELETE FROM halls");
            queryClearHall.executeUpdate();
        }
    }

    @Test
    void whenGetHallByIdThenReturnHell1Optional() {
        var actualHall = sql2oHallRepository.getHallById(hall1.getId());
        var expectedHall = Optional.of(hall1);
        assertThat(actualHall).usingRecursiveComparison().isEqualTo(expectedHall);
    }

    @Test
    void whenGetHallByIdThenReturnHell2Optional() {
        var actualHall = sql2oHallRepository.getHallById(hall2.getId());
        var expectedHall = Optional.of(hall2);
        assertThat(actualHall).usingRecursiveComparison().isEqualTo(expectedHall);
    }

    @Test
    void whenGetHallByIdZeroThenReturnOptionalEmpty() {
        var actualHall = sql2oHallRepository.getHallById(0);
        assertThat(actualHall).isEmpty();
    }
}