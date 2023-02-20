package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.FilmSession;
import ru.my.cinema.model.Genre;
import ru.my.cinema.model.dto.FilmSessionDto;
import ru.my.cinema.repository.*;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 20.02.2023
 */
@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final HallRepository hallRepository;
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository,
                                    HallRepository hallRepository,
                                    FilmRepository filmRepository,
                                    GenreRepository genreRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.hallRepository = hallRepository;
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<FilmSessionDto> getFilmSessionDtoById(int filmSessionId) {
        return Optional.empty();
    }

    @Override
    public Collection<FilmSessionDto> getFilmSessionDtoByFilmId(int filmId) {
        return null;
    }

    @Override
    public Collection<FilmSessionDto> getAllFilmSessionDtoSortedByStarTime(LocalTime timeNow) {

        return null;
    }

    @Override
    public Collection<FilmSessionDto> getAllFilmSessionDto() {
        return filmSessionRepository.getAllFilmSession()
                .stream().map(fs -> getFilmSessionDtoByFilmSession(fs).get())
                .sorted(Comparator.comparing(FilmSessionDto::getStartTime))
                .collect(Collectors.toList());
    }

    /**
     * Создание FilmSessionDto из FilmSessions
     *
     * @param filmSession FilmSession
     * @return FilmSessionDto
     */
    private Optional<FilmSessionDto> getFilmSessionDtoByFilmSession(FilmSession filmSession) {
        var film = filmRepository.getFilmById(filmSession.getFilmId());
        if (film.isEmpty()) {
            return Optional.empty();
        }
        var genre = genreRepository.getGenreById(film.get().getGenreId());
        if (genre.isEmpty()) {
            genre = Optional.of(new Genre(0, ""));
        }
        var hall = hallRepository.getHallById(filmSession.getHallId());
        if (hall.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new FilmSessionDto(
                        filmSession.getId(),
                        filmSession.getStartTime().toLocalTime(),
                        film.get().getName(),
                        genre.get().getName(),
                        film.get().getMinimalAge(),
                        hall.get().getId(),
                        hall.get().getName()
                )
        );
    }
}
