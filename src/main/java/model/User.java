package model;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Integer id;
    @Email(message = "email должно содержать символы или цифры")
    private String email;
    @NotBlank(message = "логин должен содержать символы")
    private String login;
    private String name;
    @Past(message = "дата рождения должна быть не будущей")
    @NonNull
    private LocalDate birthday;
}