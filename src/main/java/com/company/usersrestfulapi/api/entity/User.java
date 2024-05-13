package com.company.usersrestfulapi.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // required

    @Column(nullable = false)
    private String firstName; // required

    @Column(nullable = false)
    private String secondName; // required

    @Column(nullable = false)
    private LocalDate birthDate; // required

    private String address;

    private String phoneNumber;
}
