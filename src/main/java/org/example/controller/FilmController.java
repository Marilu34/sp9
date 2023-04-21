package org.example.controller;

import org.example.exceptions.IDException;
import org.example.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer id = 1;
    private static final LocalDate FIRST_FILM_RELEASE = LocalDate.of(1895, 12, 28);

    private Integer setId() {
        return id++;
    }

    @PostMapping("/filmcreate")
    public Film create(@RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            log.debug("Film id:{}", film.getId());
            throw new IDException("Id already use");
        }
        film.setId(setId());
        validate(film);
        films.put(film.getId(), film);
        log.info("film with id:{} create", film.getId());
        return film;
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE)) {
            log.debug("film not valid release date:{}", film.getReleaseDate());
            throw new ValidationException("Release date not valid");
        }
    }

    @GetMapping("/filmgetall")
    public Collection<Film> getAll() {
        return films.values();
    }

    @PutMapping("/filmput")
    public Film put(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.debug("Film id:{}", film.getId());
            throw new IDException("Id not found");
        }
        validate(film);
        films.put(film.getId(), film);
        log.info("Film with id:{} update", film.getId());
        return film;
    }

}