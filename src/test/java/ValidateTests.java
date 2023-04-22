 import org.example.controller.FilmController;
 import org.example.controller.UserController;
 import org.example.exceptions.ValidationException;
 import org.example.model.Film;
 import org.example.model.User;
 import org.testng.annotations.Test;

import java.time.LocalDate;

 import static org.testng.Assert.assertThrows;
 import static org.testng.AssertJUnit.assertEquals;


    public class ValidateTests {
        private final FilmController filmController = new FilmController();
        private final UserController userController = new UserController();

        @Test
        public void testFilmRightCreation() {
            Film film = new Film(12, "Film1", "Корректная дата релиза",
                    LocalDate.of(2222, 12, 2), 22);
           assertEquals(film, filmController.create(film));
        }

       @Test
        public void testFilmInCorrectDateRelease() {
            Film film = new Film(2, "Film2", "Некорректная дата релиза",
                    LocalDate.of(1000, 11, 1), 11);
            assertThrows(ValidationException.class, () -> filmController.create(film));
        }

        @Test
        public void testUserValidateRightCreation() {
            User user = new User(3, "yandex@ya.ru", "yandex", "Test",
                    LocalDate.of(2000, 1, 1));
            assertEquals(user, userController.create(user));
        }

       @Test
        public void testUserWithoutName() {
            User user = new User( 33, "yandex@ya.ru", "", "yandex",
                    LocalDate.of(2000, 1, 1));
            userController.create(user);
            assertEquals(user.getName(), user.getLogin());
        }
    }

