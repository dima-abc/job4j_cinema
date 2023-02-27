package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.Film;
import ru.my.cinema.model.Genre;
import ru.my.cinema.model.dto.FilmDto;
import ru.my.cinema.repository.FilmRepository;
import ru.my.cinema.repository.GenreRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * SimpleFilmService реализация бизнес логики для модели Film.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 20.02.2023
 */
@Service
public class SimpleFilmService implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository filmRepository, GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<FilmDto> getFilmDtoById(int id) {
        var filmOptional = filmRepository.getFilmById(id);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        var filmDto = getFilmDtoByFilm(filmOptional.get());
        return filmDto;
    }

    @Override
    public List<FilmDto> getAllFilmDto() {
        var collectionFilm = filmRepository.getAllFilm();
        if (collectionFilm.isEmpty()) {
            return Collections.emptyList();
        }
        return collectionFilm.stream().map(f -> getFilmDtoByFilm(f).get())
                .collect(Collectors.toList());
    }

    /**
     * Преобразование DAO модели Film в DTO модель FilmDto.
     * Если жанр фильма не найден то, поле жанр в модели DTO заполнится "".
     *
     * @param film DAO Film
     * @return new FilmDto
     */
    private Optional<FilmDto> getFilmDtoByFilm(Film film) {
        var genreOptional = genreRepository.getGenreById(film.getGenreId());
        if (genreOptional.isEmpty()) {
            genreOptional = Optional.of(new Genre(0, ""));
        }
        return Optional.of(
                new FilmDto.Builder()
                        .buildId(film.getId())
                        .buildName(film.getName())
                        .buildDescription(film.getDescription())
                        .buildGenre(genreOptional.get().getName())
                        .buildYear(film.getYear())
                        .buildMinimalAge(film.getMinimalAge())
                        .buildDurationInMinutes(film.getDurationInMinutes())
                        .buildFileId(film.getFileId())
                        .build()
        );
    }
}
