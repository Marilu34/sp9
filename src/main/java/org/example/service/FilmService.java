package org.example.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.FilmException;
import org.example.exceptions.IDException;
import org.example.model.Film;
import org.example.storage.film.InMemoryFilmStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Slf4j
public class FilmService {
    private final InMemoryFilmStorage filmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.filmStorage = inMemoryFilmStorage;
    }

    private void checkId(Integer filmId, Integer userId) {
        log.debug("check user {} check film {}", userId, filmId);
        if (filmId == null || filmId <= 0 || userId == null || userId <= 0) {
            throw new IDException(String.format("User with id:%s or film with id:%s not found", userId, filmId));
        }
        if (!filmStorage.getAllFilms().contains(filmStorage.getFilmById(filmId))) {
            throw new IDException(String.format("Film with id:%s not found", filmId));
        }
    }

    public void addLikeToFilm(Integer filmId, Integer userId) {
        checkId(filmId, userId);
        log.debug("Пользователю {} нравится фильм {}", userId, filmId);
        filmStorage.getFilmById(filmId).getUsersLike().add(userId);
    }

    public void deleteFilmLike(Integer filmId, Integer userId) {
        checkId(filmId, userId);
        if (!filmStorage.getFilmById(filmId).getUsersLike().contains(userId)) {
            log.debug("Пользователь {} убрал свой лайк с фильма {}", userId, filmId);
            throw new FilmException(String.format("Пользователь с айди id:%s не нравится фильм с айди id:%s"
                    , userId, filmId));
        }
        log.debug("Пользователь {} убрал свой лайк с фильма {}", userId, filmId);
        filmStorage.getFilmById(filmId).getUsersLike().remove(userId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getAllFilms().stream()
                .sorted((p0, p1) -> p1.getUsersLike().size() - p0.getUsersLike().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}