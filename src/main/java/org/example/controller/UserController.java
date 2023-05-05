package org.example.controller;

import org.example.model.User;
import org.example.storage.user.InMemoryUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
   InMemoryUserStorage userStorage;

    public UserController() {

    }
    @Autowired
    public UserController(UserService userService, InMemoryUserStorage userStorage) {
        this.userService = userService;
      this.userStorage = userStorage;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userStorage.createUser(user);
    }


    @GetMapping
    public Collection<User> getAllUsers() {
        return userStorage.getUsers();
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userStorage.updateUsers(user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Integer userId) {
        return userStorage.getUsersById(userId);
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userStorage.deleteUsers();
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        userStorage.deleteUsersById(userId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable("userId") Integer userId,
                          @PathVariable("friendId") Integer friendId) {
        userService.addFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable("userId") Integer userId) {
        return userService.getFriendList(userId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable("userId") Integer userId,
                             @PathVariable("friendId") Integer friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends/common/{friendsId}")
    public List<User> getCommonFriends(@PathVariable Integer userId,
                                       @PathVariable Integer friendsId) {
        Set<Integer> idFriends = getUserById(userId).getFriendsId();
                Set<Integer> friendFriendsId = getUserById(userId).getFriendsId();
        idFriends.retainAll(getUserById(userId).getFriendsId());

        return friendFriendsId.stream().
                map(friendId -> userStorage.getUsersById(friendId)).collect(Collectors.toList());
    }
}