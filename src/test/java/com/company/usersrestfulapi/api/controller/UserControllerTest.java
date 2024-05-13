package com.company.usersrestfulapi.api.controller;

import com.company.usersrestfulapi.api.dto.UserDto;
import com.company.usersrestfulapi.api.entity.User;
import com.company.usersrestfulapi.api.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private Long userId;
    private UserDto userDto;

    @BeforeEach
    public void init() {
        userId = 1L;
        userDto = UserDto.builder()
                .email("alex@outlook.com")
                .firstName("Alex")
                .secondName("Smith")
                .birthDate( LocalDate.of(1994, 9, 10))
                .address("Goofy address 12")
                .phoneNumber("+1234567890").build();
    }

    @Test
    void getAllUsers_ReturnsAllUsers_WhenNoDateRangeSpecified() {

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers(Optional.empty(), Optional.empty());

        Assertions.assertThat(users).isEqualTo(responseEntity.getBody());
    }

    @Test
    void getAllUsers_ReturnsUsersInDateRange_WhenDateRangeSpecified() {

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        LocalDate fromDate = LocalDate.of(1999, 5, 11);
        LocalDate toDate = LocalDate.of(2004, 2, 25);
        when(userService.getUsersByBirthDateRange(fromDate, toDate)).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers(Optional.of(fromDate), Optional.of(toDate));

        Assertions.assertThat(users).isEqualTo(responseEntity.getBody());
    }

    @Test
    void getAllUsers_ThrowsException_WhenToDateIsBeforeFromDate() {

        LocalDate fromDate = LocalDate.of(2002, 6, 12);
        LocalDate toDate = LocalDate.of(1992, 11, 8);

        Assertions.assertThatThrownBy(() -> userController.getAllUsers(Optional.of(fromDate), Optional.of(toDate)))
                .isInstanceOf(InvalidRangeException.class);
    }

    @Test
    void createUser_ReturnsCreatedUser() {

        User createdUser = new User();
        when(userService.createUser(userDto)).thenReturn(createdUser);

        ResponseEntity<User> responseEntity = userController.createUser(userDto);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(createdUser);
    }

    @Test
    void updateUserFields_ReturnsUpdatedUser() {

        User updatedUser = new User();
        when(userService.updateUserFields(userId, userDto)).thenReturn(updatedUser);

        ResponseEntity<User> responseEntity = userController.updateUserFields(userId, userDto);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(updatedUser);
    }

    @Test
    void updateUser_ReturnsUpdatedUser() {

        User updatedUser = new User();
        when(userService.updateUser(userId, userDto)).thenReturn(updatedUser);

        ResponseEntity<User> responseEntity = userController.updateUser(userId, userDto);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(updatedUser);
    }

    @Test
    void deleteUser_ReturnsNoContent() {

        ResponseEntity<User> responseEntity = userController.deleteUser(userId);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }
}
