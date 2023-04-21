package org.example.controller;


import org.example.exceptions.IDException;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;//не може начинаться с нуля

    private int generateId() {
        return id++;
    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            log.debug("User с id:{}", user.getId());
            throw new IDException("id занят");
        }
        user.setId(generateId());
        validate(user);
        users.put(user.getId(), user);
        log.info("Создан User с id:{}", user.getId());
        return user;
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("У User с id:{} нет имени", user.getId());
        }
    }

    @GetMapping("/getall")
    public Collection<User> getAll() {
        return users.values();
    }

    @PutMapping("/put")
    public User put(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.debug("User с id:{} не найден", user.getId());
        } else {
            validate(user);
            users.put(user.getId(), user);
            log.info("User с id:{} update", user.getId());
        }
        return user;
    }
}