package controller;

import model.Films;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<Films> addFilm(@RequestBody Films film) {
        Films createdFilm = filmService.addFilm(film);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Films> updateFilm(@PathVariable Long id, @RequestBody Films updatedFilm) {
        Films film = filmService.getFilmById(id);
        if (film == null) {
            return ResponseEntity.notFound().build();
        }
        updatedFilm.setId(id);
        Films savedFilm = filmService.updateFilm(id,updatedFilm);
        return ResponseEntity.ok(savedFilm);
    }

    @GetMapping
    public ResponseEntity<List<Films>> getAllFilms() {
        List<Films> films = filmService.getAllFilms();
        return ResponseEntity.ok(films);
    }
}
