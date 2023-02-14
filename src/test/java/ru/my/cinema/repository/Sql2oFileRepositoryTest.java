package ru.my.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.my.cinema.configuration.DatasourceConfiguration;
import ru.my.cinema.model.File;
import ru.my.cinema.model.dto.FileDto;

import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Sql2oFileRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.02.2023
 */
class Sql2oFileRepositoryTest {

    private static Sql2oFileRepository sql2oFileRepository;

    private static File file = new File(-1, "testName", "testPath");

    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepository() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFileRepositoryTest.class
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

        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                            "INSERT INTO files(name, path) VALUES (:name, :path)",
                            true)
                    .addParameter("name", file.getName())
                    .addParameter("path", file.getPath());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            file.setId(generatedId);
        }
    }

    @AfterAll
    public static void clearFiles() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "DELETE FROM files");
            query.executeUpdate();
        }
    }

    @Test
    public void whenGetFileByIdThenReturnFileOptional() {
        Optional<File> actualFile = sql2oFileRepository.getFindById(file.getId());
        Optional<File> expectedFile = Optional.of(file);
        assertThat(actualFile).usingRecursiveComparison().isEqualTo(expectedFile);
    }
}