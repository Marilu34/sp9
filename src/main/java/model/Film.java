package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
public class Film {
    private Integer id;
    @NotBlank(message = "Имя должно содержать символы")
    private String name;
    @NotBlank(message = "Описание должно содержать символы")
    @Size(max = 400, message = "вместимость описания до 400 символов")
    private String description;
    @Past(message = "дата выпуска не должна быть будущей")
    private LocalDate releaseDate;
    @NotNull
    @Positive(message = "продолжительность не може быть отрицательной")
    private Integer duration;
}