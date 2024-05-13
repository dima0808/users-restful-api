package com.company.usersrestfulapi.api.dao;

import com.company.usersrestfulapi.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    List<User> findByBirthDateBetween(LocalDate fromDate, LocalDate toDate);
}
