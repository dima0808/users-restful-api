package com.company.usersrestfulapi.api.service.impl;

import com.company.usersrestfulapi.api.dao.UserRepository;
import com.company.usersrestfulapi.api.dto.UserDto;
import com.company.usersrestfulapi.api.entity.User;
import com.company.usersrestfulapi.api.service.UserException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

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
    void getAllUsers_ReturnsListOfUsers() {

        User user1 = new User();
        User user2 = new User();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        Assertions.assertThat(users).hasSize(2);
        Assertions.assertThat(users).contains(user1, user2);
    }

    @Test
    void getUsersByBirthDateRange_ReturnsListOfUsers() {

        LocalDate fromDate = LocalDate.of(1990, 6, 11);
        LocalDate toDate = LocalDate.of(1995, 9, 25);
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findByBirthDateBetween(fromDate, toDate)).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getUsersByBirthDateRange(fromDate, toDate);

        Assertions.assertThat(users).hasSize(2);
        Assertions.assertThat(users).contains(user1, user2);
    }

    @Test
    void getUser_ReturnsUser_WhenUserExists() {

        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUser(userId);

        Assertions.assertThat(result).isEqualTo(user);
    }

    @Test
    void getUser_ThrowsException_WhenUserDoesNotExist() {

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.getUser(userId))
                .isInstanceOf(UserException.class)
                .hasMessage("user with id " + userId + " not found");
    }

    @Test
    void createUser_ReturnsCreatedUser() {

        User createdUser = new User();
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(createdUser);

        User result = userService.createUser(userDto);

        Assertions.assertThat(result).isEqualTo(createdUser);
    }

    @Test
    void createUser_ThrowsException_WhenEmailExists() {

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        Assertions.assertThatThrownBy(() -> userService.createUser(userDto))
                .isInstanceOf(UserException.class)
                .hasMessage("email " + userDto.getEmail() + " already exists");
    }

    @Test
    void updateUserFields_PassingAllFields_ReturnsUpdatedUser() {

        User existingUser = new User();
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUserFields(userId, userDto);

        Assertions.assertThat(result).isEqualTo(existingUser);
        Assertions.assertThat(result.getEmail()).isEqualTo(userDto.getEmail());
        Assertions.assertThat(result.getFirstName()).isEqualTo(userDto.getFirstName());
        Assertions.assertThat(result.getSecondName()).isEqualTo(userDto.getSecondName());
        Assertions.assertThat(result.getBirthDate()).isEqualTo(userDto.getBirthDate());
        Assertions.assertThat(result.getAddress()).isEqualTo(userDto.getAddress());
        Assertions.assertThat(result.getPhoneNumber()).isEqualTo(userDto.getPhoneNumber());
    }

    @Test
    void updateUserFields_ThrowsException_WhenEmailExists() {

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        Assertions.assertThatThrownBy(() -> userService.updateUserFields(userId, userDto))
                .isInstanceOf(UserException.class)
                .hasMessage("email " + userDto.getEmail() + " already exists");
    }

    @Test
    void updateUser_ReturnsUpdatedUser() {

        User existingUser = new User();
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(userId, userDto);

        Assertions.assertThat(result).isEqualTo(existingUser);
        Assertions.assertThat(result.getEmail()).isEqualTo(userDto.getEmail());
        Assertions.assertThat(result.getFirstName()).isEqualTo(userDto.getFirstName());
        Assertions.assertThat(result.getSecondName()).isEqualTo(userDto.getSecondName());
        Assertions.assertThat(result.getBirthDate()).isEqualTo(userDto.getBirthDate());
        Assertions.assertThat(result.getAddress()).isEqualTo(userDto.getAddress());
        Assertions.assertThat(result.getPhoneNumber()).isEqualTo(userDto.getPhoneNumber());
    }

    @Test
    void updateUser_ThrowsException_WhenEmailExists() {

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        Assertions.assertThatThrownBy(() -> userService.updateUser(userId, userDto))
                .isInstanceOf(UserException.class)
                .hasMessage("email " + userDto.getEmail() + " already exists");
    }

    @Test
    void deleteUser_DeletesUser_WhenUserExists() {

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_ThrowsException_WhenUserDoesNotExist() {

        when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> userService.deleteUser(userId))
                .isInstanceOf(UserException.class)
                .hasMessage("user with id " + userId + " not found");
    }

    @Test
    void validateAge_ThrowsException_WhenAgeIsInvalid() {

        int age = -10;

        Assertions.assertThatThrownBy(() -> userService.validateAge(age, 18))
                .isInstanceOf(UserException.class)
                .hasMessage("invalid birth date");
    }

    @Test
    void validateAge_ThrowsException_WhenAgeIsLessThanMinimum() {

        int age = 10;

        Assertions.assertThatThrownBy(() -> userService.validateAge(age, 18))
                .isInstanceOf(UserException.class)
                .hasMessage("user is under the legal age (18)");
    }
}
