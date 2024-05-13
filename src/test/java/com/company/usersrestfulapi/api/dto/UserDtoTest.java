package com.company.usersrestfulapi.api.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UserDtoTest {

    @Test
    void findAge_ReturnsCorrectAge() {

        LocalDate birthDate = LocalDate.of(2005, 8, 8);
        UserDto userDto = new UserDto("test@gmail.com", "Dmytro", "Mamchenko", birthDate, "8 Foo St City", "+1234567890");
        int age = userDto.findAge();

        Assertions.assertThat(age).isEqualTo(18);
    }
}
