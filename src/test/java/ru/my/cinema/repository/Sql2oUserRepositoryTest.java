package ru.my.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.my.cinema.configuration.DatasourceConfiguration;
import ru.my.cinema.model.User;

import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * Sql2oUserRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.02.2023
 */
class Sql2oUserRepositoryTest {
    private static Sql2o sql2o;
    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initUserRepository() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class
                .getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }

        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var connection = new DatasourceConfiguration();
        var datasource = connection.connectionPool(url, username, password);
        sql2o = connection.databaseClient(datasource);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUser() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users");
            query.executeUpdate();
        }
    }

    @Test
    public void whenSaveUserThenReturnUserOptional() {
        var user = new User(0, "FIO", "mail@mail.ru", "123456");
        var actualUser = sql2oUserRepository.save(user);
        var expectUser = Optional.of(user);

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectUser);
    }

    @Test
    public void whenSaveTwoUserEqualEmailThenReturnEmpty() {
        var user1 = new User(0, "FIO", "mail@mail.ru", "123456");
        var user2 = new User(0, "FFF", "mail@mail.ru", "1");
        sql2oUserRepository.save(user1);
        var actualEmpty = sql2oUserRepository.save(user2);

        assertThat(actualEmpty).isEmpty();
    }

    @Test
    public void whenSaveUserWhetFindByMailAndPasswordThenReturnOptionalUser() {
        var user = new User(0, "FIO", "mail@mail.ru", "123456");
        var saveUserOptional = sql2oUserRepository.save(user);
        var actualUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        var expectedUser = Optional.of(user);

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(saveUserOptional);
    }

    @Test
    public void whenFindByMailAndPasswordThenReturnEmpty() {
        var user = new User(0, "FIO", "mail@mail.ru", "123456");
        var actualEmpty = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        assertThat(actualEmpty).isEmpty();
    }

    @Test
    public void whenFindByMailActualAndPasswordErrorThenReturnEmpty() {
        var user = new User(0, "FIO", "mail@mail.ru", "123456");
        sql2oUserRepository.save(user);
        var actualEmpty = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), "error");

        assertThat(actualEmpty).isEmpty();
    }

    @Test
    public void whenFindByMailErrorAndPasswordActualThenReturnEmpty() {
        var user = new User(0, "FIO", "mail@mail.ru", "123456");
        sql2oUserRepository.save(user);
        var actualEmpty = sql2oUserRepository.findByEmailAndPassword("error", user.getPassword());

        assertThat(actualEmpty).isEmpty();
    }
}