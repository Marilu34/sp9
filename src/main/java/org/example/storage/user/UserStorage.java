package org.example.storage.user;


import org.example.model.User;

import java.util.Collection;

public interface UserStorage {

    User createUser(User user);

    Collection<User> getUsers();

    User updateUsers(User user);

    User getUsersById(Integer userId);

    void deleteUsers();
}
