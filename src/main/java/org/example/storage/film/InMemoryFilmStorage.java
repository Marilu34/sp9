package org.example.storage.film;

import lombok.Data;
import lombok.Getter;
import org.example.exceptions.ValidationException;
import org.example.model.Film;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
public class InMemoryFilmStorage implements FilmStorage {
    private static final LocalDate FIRST_FILM_RELEASE = LocalDate.of(1895, 12, 28);
    private static final Map<Integer, Film> films = new HashMap<>();
    private static int nextId = 0;

    @Override
    public ArrayList<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film add(Film film) {
        validate(film);
        film.setId(setNextId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        films.replace(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilmById(int filmId) {
        return films.get(filmId);
    }

    @Override
    public void addLike(int filmId, int userId) {
        var film = films.get(filmId);
        film.getUserIdLikes().add(userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        var film = films.get(filmId);
        film.getUserIdLikes().remove(userId);
    }

    @Override
    public ArrayList<Film> getMostPopularFilms(int count) {
        return films.values().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private int compare(Film f0, Film f1) {
        int result = 0;
        if (f0.getUserIdLikes().size() > f1.getUserIdLikes().size()) {
            result = -1;
        }
        return result;
    }

    private int setNextId() {
        return ++nextId;
    }

    // Temporary methods. Will be deleted after we will have real db in project

    public static void setStartId0() {
        nextId = 0;
    }

    public static void clearDb() {
        films.clear();
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE)) {
            throw new ValidationException("Дата выпуска Film недействительна");
        }
        if (film.getName().isBlank() || film.getName() == null) {
            throw new ValidationException("Имя Film не может быть пустым");
        }
        if (film.getDuration() <= 0 || film.getDuration() > 200) {
            throw new ValidationException("Продолжительность Film не может быть отрицательным");
        }
        if (film.getDescription() == null || film.getDescription().isBlank() || film.getDescription().length() > 200) {
            throw new ValidationException("Описание Film не может быть больше 200 символов");
        }
    }
}
