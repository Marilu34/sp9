package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;

    @Email(message = "Email should be format \"xxx@mm.com\"")
    @NotNull(message = "Email should not be empty")
    private String email;

    @NotBlank
    @NotNull
    private String login;

    @Nullable
    private String name;

    @Past
    private LocalDate birthday;

    @JsonIgnore
    @Getter
    @Setter
    private Set<Integer> friends = new HashSet<>();
}
