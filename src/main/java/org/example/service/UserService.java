package org.example.service;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.IDException;
import org.example.exceptions.UserException;
import org.example.model.User;
import org.example.storage.user.InMemoryUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Data
@Slf4j
public class UserService {
    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.userStorage = inMemoryUserStorage;
    }

    public void addFriend(Integer userId, Integer friendUserId) {
        checkUserAndFriendId(userId, friendUserId);
        if (userStorage.getUsersById(userId).getFriendsId().contains(userStorage.getUsersById(friendUserId).getId())) {
            log.debug("Пользователь с айди {} добавил в друзья Пользователя с айди {} , Пользователь Списка Друзей {}", userId, friendUserId,
                    userStorage.getUsersById(userId).getFriendsId());
            throw new UserException(String.format(
                    "Пользователь %s уж дружит с Пользователем %s", userStorage.getUsersById(userId).getName(),
                    userStorage.getUsersById(friendUserId).getName()));
        }
        if (userId == friendUserId) {
            log.debug("Пользователь {} дружит {}", userId, friendUserId);
            throw new UserException("Вы не можетет себя добавить в друзья");
        }
        log.debug("Пользователь с айди {} добавил в друзья Пользователя с айди {}", userId, friendUserId);
        userStorage.getUsersById(userId).getFriendsId().add(friendUserId);
        userStorage.getUsersById(friendUserId).getFriendsId().add(userId);
    }


    public List<User> getFriendList(Integer userId) {
        if (userId <= 0) {
            log.debug("Пользователь с айди {}", userId);
            throw new IDException(String.format("Пользователь с айди id:%s не найден", userId));
        }
        List<User> friendList = new ArrayList<>();
        for (Integer friendId : userStorage.getUsersById(userId).getFriendsId()) {
            friendList.add(userStorage.getUsersById(friendId));
        }
        log.debug("Пользоватль с айди {} в Списке Друзей {}", userId, friendList);
        return friendList;
    }

    public void deleteFriend(Integer userId, Integer friendUserId) {
        checkUserAndFriendId(userId, friendUserId);
        if (!userStorage.getUsersById(userId).getFriendsId().contains(userStorage.getUsersById(friendUserId).getId())) {
            log.debug("Пользователь {} удалил {} из Списка друзей {} ", userId, friendUserId,
                    userStorage.getUsersById(userId).getFriendsId());
            throw new UserException(String.format(
                    "Вы не друзья с Пользователем {} id:%s", userStorage.getUsersById(friendUserId).getName()
            ));
        }
        if (userId == friendUserId) {
            log.debug("Пользователь {} удалил {} из Списка друзей", userId, friendUserId);
            throw new UserException("Вы не можете себя удалить из Списка Друзей");
        }
        log.debug("Пользователь {} удалил из Списка друзей {}", userId, friendUserId);
        userStorage.getUsersById(userId).getFriendsId().remove(friendUserId);
        userStorage.getUsersById(friendUserId).getFriendsId().remove(userId);
    }

    public List<User> getListCommonFriends(Integer userId, Integer friendUserId) {
        checkUserAndFriendId(userId, friendUserId);
        List<Integer> commonListFriendsId = userStorage.getUsersById(userId).getFriendsId().stream()
                .filter(userStorage.getUsersById(friendUserId).getFriendsId()::contains)
                .collect(toList());
        List<User> mutualFriends = new ArrayList<>();
        for (Integer commonFriendsId : commonListFriendsId) {
            mutualFriends.add(userStorage.getUsersById(commonFriendsId));
        }
        log.debug("Пользователь {} с Пользователем {} имеет общих друзей из списка общих друзей {}", userId, friendUserId, mutualFriends);
        return mutualFriends;
    }

    private void checkUserAndFriendId(Integer userId, Integer friendId) {
        if (userId == null || friendId == null || userId <= 0 || friendId <= 0) {
            log.debug("Проверка дружбы Пользователя {} с Пользователем {}", userId, friendId);
            throw new IDException(String.format("Пользователь с айди id:%s или друг с айди id:%s не найден",
                    userId, friendId));
        }
    }
}