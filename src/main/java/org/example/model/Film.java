package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    private static final String minFilmDate = "1895-12-28";

    private int id;

    @NotNull(message = "Film name should not be empty")
    @NotEmpty
    private String name;

    @Size(max = 200, message = "Max value of description is 200")
    private String description;

    @Past(message = "Date of film should be after " + minFilmDate)
    private LocalDate releaseDate;

    @Positive(message = "Should be > 0")
    private int duration;

      @JsonIgnore
    @Getter
    @Setter
    private Set<Integer> userIdLikes = new HashSet<>();
}
