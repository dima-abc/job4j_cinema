package ru.my.cinema.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.my.cinema.configuration.DatasourceConfiguration;
import ru.my.cinema.model.File;
import ru.my.cinema.model.dto.FileDto;
import ru.my.cinema.repository.Sql2oFileRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * SimpleFileService TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 15.02.2023
 */
class SimpleFileServiceTest {
    private static String pathFile;
    private static String nameFile;
    private static File file;
    private static FileDto fileDto;
    private static Sql2o sql2o;
    private static Sql2oFileRepository sql2oFileRepository;
    private static SimpleFileService simpleFileService;

    @BeforeAll
    public static void initRepository() throws Exception {
        var properties = new Properties();
        try (var inputStream = SimpleFileServiceTest.class
                .getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var fileDirectory = properties.getProperty("test.directory");
        var testFile = properties.getProperty("test.file");

        pathFile = fileDirectory + "/" + testFile;
        nameFile = testFile;
        file = new File(0, nameFile, pathFile);

        fileDto = new FileDto(testFile, Files.readAllBytes(Path.of(pathFile)));
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        simpleFileService = new SimpleFileService(sql2oFileRepository, fileDirectory);

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
    public void whenServiceFileGetByIdThenReturnFileDto() {
        Optional<FileDto> actualFileDto = simpleFileService.getFileById(file.getId());
        assertThat(actualFileDto).usingRecursiveComparison().isEqualTo(Optional.of(fileDto));
    }

    @Test
    public void whenServiceFileGetByIdZeroThenReturnEmpty() {
        Optional<FileDto> actualFileDto = simpleFileService.getFileById(0);
        assertThat(actualFileDto).isEmpty();
    }
}