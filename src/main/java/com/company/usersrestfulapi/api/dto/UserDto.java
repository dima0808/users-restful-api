package com.company.usersrestfulapi.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    @Email(message = "invalid email")
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "first name is required")
    private String firstName;
    @NotBlank(message = "second name is required")
    private String secondName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "birth date is required")
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

    public int findAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}
