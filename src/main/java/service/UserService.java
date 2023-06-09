package service;

import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api")

public class UserService {
    private Users createdUser;
    private Users usersById;
    private List<Users> allUsers = new ArrayList<>();
    private final static Logger userLog = LoggerFactory.getLogger(Users.class);
    @PostMapping("/users")
    public Users createUser(@RequestBody Users user) {
        // логика создания пользователя
        createdUser = user;
        allUsers.add(user);
        userLog.info("User создан: {}", user);
        return createdUser;
    }

    @PutMapping("/users/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        // логика обновления пользователя
        usersById = getUserById(id);
        if (usersById != null) {
            usersById.setName(updatedUser.getName());
            usersById.setEmail(updatedUser.getEmail());
            userLog.info("User обновлен: {}", usersById);
            return usersById;
        } else {
            userLog.error("User с id {} не обнаружен", id);
            return null;
        }
    }

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        // логика получения списка всех пользователей
        userLog.info("Все users получены");
        return allUsers;
    }

    @GetMapping("/users/{id}")
    public Users getUserById(@PathVariable Long id) {
        // логика получения пользователя по id
        usersById = allUsers.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        if (usersById != null) {
            userLog.info("User получен по id {}: {}", id, usersById);
            return usersById;
        } else {
            userLog.error("User с id {} не обнаружен", id);
            return null;
        }
    }
}
