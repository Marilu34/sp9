package org.example.storage.user;


import org.example.model.User;

import java.util.ArrayList;

import java.util.Set;

public interface UserStorage {

    ArrayList<User> getAll();

    User add(User user);

    User update(User user);

    Set<String> getExistingEmails();

    User getUser(int userId);

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    void deleteUser(User user);

    ArrayList<User> getFriends(int userId);

    ArrayList<User> getCommonFriends(int userId, int userIdToCompare);
}