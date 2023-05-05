import org.example.storage.film.InMemoryFilmStorage;

import org.example.exceptions.ValidationException;
import org.example.model.Film;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FilmTests {
    private final InMemoryFilmStorage filmController = new InMemoryFilmStorage();

    @Test
    public void testFilmRightCreation() {
        Film film = new Film(12, "Film1", "Корректная дата релиза",
                LocalDate.of(2222, 12, 2), 22, new HashSet<>());
        assertEquals(film, filmController.createFilm(film));
    }

    @Test
    public void testFilmInCorrectDateRelease() {
        Film film = new Film(2, "Film2", "Некорректная дата релиза",
                LocalDate.of(1000, 11, 1), 11, new HashSet<>());
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }
}

