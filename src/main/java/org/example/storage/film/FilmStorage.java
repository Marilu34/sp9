package org.example.storage.film;

import org.example.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film createFilm(Film film);

    Collection<Film> getAllFilms();

    Film updateFilm(Film film);

    Film getFilmById(Integer filmId);

    void deleteAllFilms();
}