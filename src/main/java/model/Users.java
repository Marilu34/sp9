package model;


import lombok.Data;

import java.time.LocalDate;

@Data
public class Users {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}