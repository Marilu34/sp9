package org.example.service;

import org.example.model.Film;
import org.example.storage.film.FilmStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage){
        this.filmStorage = filmStorage;
    }

    public ArrayList<Film> getAll(){
        return filmStorage.getAll();
    }

    public Film add(Film film){
        return filmStorage.add(film);
    }

    public Film update(Film film){
        return filmStorage.update(film);
    }

    public void addLike(int filmId, int userId) {
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId){
        filmStorage.deleteLike(filmId, userId);
    }

    public ArrayList<Film> getMostPopularFilms(int count) {
        return filmStorage.getMostPopularFilms(count);
    }

    public Film getFilmById(int filmId) {
        return filmStorage.getFilmById(filmId);
    }
}