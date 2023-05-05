package org.example.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.IDException;
import org.example.exceptions.ValidationException;
import org.example.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;//не може начинаться с нуля

    private int generateId() {
        return id++;
    }

    @Override
    public User createUser(@RequestBody @Valid User user) {
        if (users.containsValue(user)) {
            users.put(user.getId(), user);
            log.info("Пользователь с id = {} обновлен", user.getId());
        }
        validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Создан Пользователь с id:{}", user.getId());
        return user;
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new ValidationException("логин не может быть пустым");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("логин не может содержать пробелы");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            user.setEmail(user.getEmail());
            log.debug("У Пользователя с id:{} нет почты", user.getId());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("дата рождения должна быть не будущей");
        }
    }
    public User get(int userId) {
        return users.get(userId);
    }
    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }
    @Override
    public User getUsersById(Integer userId) {
        if (!users.containsKey(id)) {
            throw new IDException("Пользователь не обнаруже");
        }
        return users.get(id);
    }
    @PutMapping
    public User updateUsers(@RequestBody @Valid User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь не найден");
        } else {
            validate(user);
            users.put(user.getId(), user);
            log.info("Пользователь с id:{} update", user.getId());
        }
        return user;
    }
    @Override
    public void deleteUsers() {
        users.clear();
    }
    public void deleteUsersById(Integer userId) {
        if (!users.containsKey(userId)) {
            throw new IDException(String.format("Пользоваель с айди id:%s не обнаружен", userId));
        }
        users.remove(userId);
        log.info("Пользователь с id={} удален", users.get(userId));
    }
}