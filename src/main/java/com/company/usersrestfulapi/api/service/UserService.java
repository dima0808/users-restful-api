package com.company.usersrestfulapi.api.service;

import com.company.usersrestfulapi.api.dto.UserDto;
import com.company.usersrestfulapi.api.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    List<User> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate);

    User getUser(Long id);

    User createUser(UserDto userDto);

    User updateUserFields(Long id, UserDto userDto);

    User updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}
