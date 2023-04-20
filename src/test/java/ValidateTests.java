 import controller.FilmController;
 import controller.UserController;
 import exceptions.ValidationException;
 import model.Film;
 import model.User;
 import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

    public class ValidateTests {
        private final FilmController filmController = new FilmController();
        private final UserController userController = new UserController();

        @Test
        public void testFilmRightCreation() {
            Film film = new Film(1, "Film1", "Корректная дата релиза",
                    LocalDate.of(2222, 12, 2), 22);
            assertEquals(film, filmController.create(film));
        }

        @Test
        public void testFilmInCorrectDateRelease() {
            Film film = new Film(1, "Film2", "Некорректная дата релиза",
                    LocalDate.of(1000, 11, 1), 11);
            assertThrows(ValidationException.class, () -> filmController.create(film));
        }

        @Test
        public void testUserValidateRightCreation() {
            User user = new User(1, "yandex@ya.ru", "yandex", "Test",
                    LocalDate.of(2000, 1, 1));
            assertEquals(user, userController.create(user));
        }

        @Test
        public void testUserWithoutName() {
            User user = new User(1, "yandex@ya.ru", "yandex", "",
                    LocalDate.of(2000, 1, 1));
            userController.create(user);
            assertEquals(user.getName(), user.getLogin());
        }
    }

