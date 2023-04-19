package model;


import lombok.Data;

import java.time.LocalDate;

@Data
public class Films {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}