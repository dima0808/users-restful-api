package com.company.usersrestfulapi.api.dao;

import com.company.usersrestfulapi.api.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void init() {
        user1 = User.builder()
                .email("alex@outlook.com")
                .firstName("Alex")
                .secondName("Smith")
                .birthDate( LocalDate.of(1994, 9, 10))
                .address("Goofy address 12")
                .phoneNumber("+1234567890").build();

        user2 = User.builder()
                .email("john@gmail.com")
                .firstName("John")
                .secondName("Brown")
                .birthDate(LocalDate.of(1997, 5, 15))
                .address("Goofy address 17")
                .phoneNumber("+1234567890").build();

        user3 = User.builder()
                .email("liam@gmail.com")
                .firstName("Liam")
                .secondName("Erikson")
                .birthDate(LocalDate.of(2003, 1, 2))
                .address("Goofy address 21")
                .phoneNumber("+1234567890").build();
    }

    @Test
    void save_SavesUserCorrectly() {

        userRepository.save(user1);

        Optional<User> result = userRepository.findById(user1.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(user1);
    }

    @Test
    void findAll_ReturnsAllUsers() {

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        List<User> users = userRepository.findAll();

        Assertions.assertThat(users).containsExactlyInAnyOrder(user1, user2, user3);
    }

    @Test
    void findByBirthDateBetween_ReturnsCorrectUsers() {

        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        List<User> users = userRepository.findByBirthDateBetween(fromDate, toDate);

        Assertions.assertThat(users).containsExactly(user1, user2);
    }

    @Test
    void existsByEmail_ReturnsTrue_WhenEmailExists() {

        userRepository.save(user1);

        boolean result = userRepository.existsByEmail(user1.getEmail());

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void existsByEmail_ReturnsFalse_WhenEmailDoesNotExist() {

        boolean result = userRepository.existsByEmail("foo@gmail.com");

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void existsById_ReturnsTrue_WhenIdExists() {

        userRepository.save(user1);

        boolean result = userRepository.existsById(user1.getId());

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void existsById_ReturnsFalse_WhenIdDoesNotExist() {

        boolean result = userRepository.existsById(1L);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void findById_ReturnsCorrectUser_WhenIdExists() {

        userRepository.save(user1);
        Optional<User> result = userRepository.findById(user1.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(user1);
    }

    @Test
    void findById_ReturnsEmptyOptional_WhenIdDoesNotExist() {

        Optional<User> result = userRepository.findById(1L);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void deleteById_DeletesUserCorrectly() {

        userRepository.save(user1);
        userRepository.deleteById(user1.getId());

        Optional<User> result = userRepository.findById(user1.getId());
        Assertions.assertThat(result).isEmpty();
    }
}
