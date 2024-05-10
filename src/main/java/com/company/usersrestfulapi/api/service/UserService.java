package com.company.usersrestfulapi.api.service;

import com.company.usersrestfulapi.api.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User createUser(User user);

    User updateUserFields(Long id, User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}
