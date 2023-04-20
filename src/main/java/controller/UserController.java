package controller;


import exceptions.IDException;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;//не може начинаться с нуля

    private int setId() {
        return id++;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            log.debug("User с id:{}", user.getId());
            throw new IDException("id занят");
        }
        user.setId(setId());
     validate(user);
        users.put(user.getId(), user);
        log.info("Создан User с id:{}", user.getId());
        return user;
    }

    private void validate(User user) {
        if (user.getName().isBlank() || user.getName() == null) {
            user.setName(user.getLogin());
            log.debug("У User с id:{} нет имени", user.getId());
        }
    }

    @GetMapping
    public Collection<User> getAll() {
        return users.values();
    }

    @PutMapping
    public User put( @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.debug("User с id:{}", user.getId());
            throw new IDException("Id не найден");
        }
        validate(user);
        users.put(user.getId(), user);
        log.info("User с id:{} update", user.getId());
        return user;
    }
}