import org.example.storage.film.InMemoryFilmStorage;

import org.example.exceptions.ValidationException;
import org.example.model.Film;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmTests {
    private final InMemoryFilmStorage filmController = new InMemoryFilmStorage();

            @Autowired
        private MockMvc mockMvc;
        private final String urlTemplate = "/films";

        @AfterEach
        public void startService() {
            InMemoryFilmStorage.setStartId0();
            InMemoryFilmStorage.clearDb();
        }
        @Test
        public Film testFilmRightCreation() {
            Film film = new Film(12, "Film1", "Корректная дата релиза",
                    LocalDate.of(2222, 12, 2), 22,new HashSet<>());
            assertEquals(film, filmController.add(film));
            return film;
        }

        @Test
        public void testFilmInCorrectDateRelease() {
            Film film = new Film(2, "Film2", "Некорректная дата релиза",
                    LocalDate.of(1000, 11, 1), 11, new HashSet<>());
            assertThrows(ValidationException.class, () -> filmController.add(film));
        }
        @Test
        public void GetAllFilms_executeRequest_ShouldReturn200Code() throws Exception {
            // Arrange
            Film film = testFilmRightCreation();
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            // Act
            mockMvc.perform(post(urlTemplate)
                    .content(jsonFilm)
                    .contentType(MediaType.APPLICATION_JSON));

            var result = mockMvc.perform(get(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON)).andReturn();

            // Assert
            var actualResponse = result.getResponse().getContentAsString();
            var actualStatusCode = result.getResponse().getStatus();

            film.setId(1);
            Assertions.assertEquals("[" + GsonConverter.convertObjectToJson(film) + "]", actualResponse);
            Assertions.assertEquals(200, actualStatusCode);
        }

        @Test
        public void CreateFilm_withAllParams_ShouldReturn200Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            // Act
            var result = mockMvc
                    .perform(post(urlTemplate)
                            .content(jsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualResponse = result.getResponse().getContentAsString();
            var actualStatusCode = result.getResponse().getStatus();

            film.setId(1);

            Assertions.assertEquals(GsonConverter.convertObjectToJson(film), actualResponse);
            Assertions.assertEquals(200, actualStatusCode);
        }

        @Test
        public void CreateFilm_whenNameIsAlreadyExist_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            film.setId(2);
            var newJsonFilm = GsonConverter.convertObjectToJson(film);

            mockMvc.perform(post(urlTemplate)
                    .content(jsonFilm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Act
            var result = mockMvc.perform(post(urlTemplate)
                            .content(newJsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(500, actualStatusCode);
        }

        @Test
        public void CreateFilm_withEmptyName_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            film.setName("");
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            // Act
            var result = mockMvc
                    .perform(post(urlTemplate)
                            .content(jsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }

        @Test
        public void CreateFilm_withNullName_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            film.setName(null);
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            // Act
            var result = mockMvc
                    .perform(post(urlTemplate)
                            .content(jsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }

        @Test
        public void CreateFilm_withTooLongDescription_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            film.setDescription("Текст — это группа предложений, которые связаны в единое целое общей темой с помощью языковых средств. Текст делится на разные части: предложения, абзацы, параграфы, главы и т. д. Дальше, когда мы будем рассказывать о его отличительных чертах, мы будем говорить именно «части», но при этом иметь в виду их все. Получай лайфхаки, статьи, видео и чек-листы по обучению на почту. ←. ... Какие существуют основные признаки текста. Теперь давайте разберёмся, какие признаки текста считаются важнейшими в русском языке. Общая тема. Все предложения в тексте, как следует из его определения, должны быть связаны друг с другом одной и той же темой. Если мы говорим о больших текстах, например, романах, тем бывает больше одной. Но и в этом случае каждая из тем всё равно относится ко всему тексту. При этом они могут раскрывать более мелкие подтемы. Рассмотрим пример текста и найдём тему, которая объединяет все предложения в нём: У меня большая семья из шести человек: я, мама, папа, старшая сестра, бабушка и дедушка. Мы живём все вместе с собакой и кошкой в большом доме в деревне");
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            // Act
            var result = mockMvc
                    .perform(post(urlTemplate)
                            .content(jsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }

        @Test
        public void CreateFilm_withFilmDateLessThanRequired_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            film.setReleaseDate(LocalDate.parse(Film.minFilmDate));
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            // Act
            var result = mockMvc
                    .perform(post(urlTemplate)
                            .content(jsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }

        @Test
        public void CreateFilm_withNegativeFilmDuration_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            film.setDuration(-1);
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            // Act
            var result = mockMvc
                    .perform(post(urlTemplate)
                            .content(jsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }

        @Test
        public void UpdateFilm_withCorrectParams_ShouldReturn200Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            film.setName("NewName");
            film.setDuration(film.getDuration() - 10);
            film.setDescription("NewDesc");
            film.setReleaseDate(LocalDate.now().minusMonths(1));
            film.setId(2);
            var newJsonFilm = GsonConverter.convertObjectToJson(film);

            mockMvc.perform(post(urlTemplate)
                    .content(jsonFilm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Act
            var result = mockMvc.perform(post(urlTemplate)
                            .content(newJsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();
            var actualResponse = result.getResponse().getContentAsString();

            Assertions.assertEquals(200, actualStatusCode);
            Assertions.assertEquals(newJsonFilm, actualResponse);
        }

        @Test
        public void UpdateFilm_withEmptyName_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            film.setName("");
            var newJsonFilm = GsonConverter.convertObjectToJson(film);

            mockMvc.perform(post(urlTemplate)
                    .content(jsonFilm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Act
            var result = mockMvc.perform(post(urlTemplate)
                            .content(newJsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }

        @Test
        public void UpdateFilm_withNullName_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            film.setName(null);
            var newJsonFilm = GsonConverter.convertObjectToJson(film);

            mockMvc.perform(post(urlTemplate)
                    .content(jsonFilm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Act
            var result = mockMvc.perform(post(urlTemplate)
                            .content(newJsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }

        @Test
        public void UpdateFilm_withTooLongDescription_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            film.setDescription("Текст — это группа предложений, которые связаны в единое целое общей темой с помощью языковых средств. Текст делится на разные части: предложения, абзацы, параграфы, главы и т. д. Дальше, когда мы будем рассказывать о его отличительных чертах, мы будем говорить именно «части», но при этом иметь в виду их все. Получай лайфхаки, статьи, видео и чек-листы по обучению на почту. ←. ... Какие существуют основные признаки текста. Теперь давайте разберёмся, какие признаки текста считаются важнейшими в русском языке. Общая тема. Все предложения в тексте, как следует из его определения, должны быть связаны друг с другом одной и той же темой. Если мы говорим о больших текстах, например, романах, тем бывает больше одной. Но и в этом случае каждая из тем всё равно относится ко всему тексту. При этом они могут раскрывать более мелкие подтемы. Рассмотрим пример текста и найдём тему, которая объединяет все предложения в нём: У меня большая семья из шести человек: я, мама, папа, старшая сестра, бабушка и дедушка. Мы живём все вместе с собакой и кошкой в большом доме в деревне");
            var newJsonFilm = GsonConverter.convertObjectToJson(film);

            mockMvc.perform(post(urlTemplate)
                    .content(jsonFilm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Act
            var result = mockMvc.perform(post(urlTemplate)
                            .content(newJsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }

        @Test
        public void UpdateFilm_withFilmDateLessThanRequires_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            film.setReleaseDate(LocalDate.parse(Film.minFilmDate));
            var newJsonFilm = GsonConverter.convertObjectToJson(film);

            mockMvc.perform(post(urlTemplate)
                    .content(jsonFilm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Act
            var result = mockMvc.perform(post(urlTemplate)
                            .content(newJsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }

        @Test
        public void UpdateFilm_withNegativeDuration_ShouldReturn400Code() throws Exception {
            // Arrange
            var film = testFilmRightCreation();
            var jsonFilm = GsonConverter.convertObjectToJson(film);

            film.setDuration(-1);
            var newJsonFilm = GsonConverter.convertObjectToJson(film);

            mockMvc.perform(post(urlTemplate)
                    .content(jsonFilm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Act
            var result = mockMvc.perform(post(urlTemplate)
                            .content(newJsonFilm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            // Assert
            var actualStatusCode = result.getResponse().getStatus();

            Assertions.assertEquals(400, actualStatusCode);
        }
}

