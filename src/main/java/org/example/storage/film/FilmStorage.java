package org.example.storage.film;

import org.example.model.Film;

import java.util.ArrayList;


public interface FilmStorage {

    ArrayList<Film> getAll();

    Film add(Film film);

    Film update(Film film);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    ArrayList<Film> getMostPopularFilms(int count);

    Film getFilmById(int filmId);
}
