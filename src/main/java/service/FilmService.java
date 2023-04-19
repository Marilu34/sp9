package service;

import lombok.extern.slf4j.Slf4j;
import model.Films;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Slf4j
public class FilmService {
    Films createdFilm;
    List<Films> allFilms;
    Films FilmById;
    private final static Logger filmLog = LoggerFactory.getLogger(Films.class);
    @PostMapping("/films")
    public Films addFilm(@RequestBody Films film) {
        try {
            // логика создания фильма
            filmLog.info("Film {} created successfully", film.getName());
            return createdFilm;
        } catch (Exception e) {
            filmLog.error("Failed to create film. Reason: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/films/{id}")
    public Films updateFilm(@PathVariable Long id, @RequestBody Films updatedFilm) {
        try {
            // логика обновления фильма
            filmLog.info("Film {} updated successfully", updatedFilm.getName());
            return updatedFilm;
        } catch (Exception e) {
            filmLog.error("Failed to update film. Reason: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/films")
    public List<Films> getAllFilms() {
        try {
            // логика получения всех фильмов
            filmLog.info("All films retrieved successfully");
            return allFilms;
        } catch (Exception e) {
            filmLog.error("Failed to retrieve all films. Reason: {}", e.getMessage());
            throw e;
        }
    }

    public Films getFilmById(@PathVariable Long id) {
        try {
            // логика получения фильма по id
            filmLog.info("Film with id {} retrieved successfully", id);
            return FilmById;
        } catch (Exception e) {
            filmLog.error("Failed to retrieve film with id {}. Reason: {}", id, e.getMessage());
            throw e;
        }
    }
}
