package ru.my.cinema.service;

import org.springframework.stereotype.Service;
import ru.my.cinema.model.FilmSession;
import ru.my.cinema.model.Genre;
import ru.my.cinema.model.dto.SessionDto;
import ru.my.cinema.model.dto.SessionFilmGenreHallDto;
import ru.my.cinema.repository.*;

import java.time.LocalTime;
import java.util.Collection;
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
    public Optional<SessionDto> getSessionDtoById(int filmSessionId) {
        return Optional.empty();
    }

    @Override
    public Collection<SessionDto> getSessionDtoByFilmId(int filmId) {
        return filmSessionRepository.getFilmSessionByFilmId(filmId)
                .stream()
                .map(this::getDtoByFilmSession)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SessionDto> getAllSessionDtoSortedByStarTime(LocalTime timeNow) {

        return null;
    }

    /**
     * Вернуть все доступные сеансы. Сортировка по времени начала.
     *
     * @return
     */
    @Override
    public Collection<SessionDto> getAllSessionDto() {
        return filmSessionRepository.getAllFilmSession()
                .stream()
                .map(this::getDtoByFilmSession)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SessionFilmGenreHallDto> getSessionHallDtoBySessionId(int sessionId) {
        var session = filmSessionRepository.getFilmSessionById(sessionId);
        if (session.isEmpty()) {
            return Optional.empty();
        }
        var film = filmRepository.getFilmById(session.get().getFilmId());
        if (film.isEmpty()) {
            return Optional.empty();
        }
        var hall = hallRepository.getHallById(session.get().getHallId());
        if (hall.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new SessionFilmGenreHallDto(
                session.get().getId(),
                session.get().getStartTime(),
                session.get().getPrice(),
                film.get().getName(),
                film.get().getMinimalAge(),
                hall.get().getId(),
                hall.get().getName(),
                hall.get().getRowCount(),
                hall.get().getPlaceCount()
        ));
    }

    /**
     * Создание SessionDto из FilmSessions
     *
     * @param filmSession FilmSession
     * @return SessionDto
     */
    private Optional<SessionDto> getDtoByFilmSession(FilmSession filmSession) {
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
                new SessionDto(
                        filmSession.getId(),
                        filmSession.getStartTime().toLocalTime(),
                        film.get().getFileId(),
                        film.get().getName(),
                        genre.get().getName(),
                        film.get().getMinimalAge(),
                        hall.get().getId(),
                        hall.get().getName()
                )
        );
    }
}
