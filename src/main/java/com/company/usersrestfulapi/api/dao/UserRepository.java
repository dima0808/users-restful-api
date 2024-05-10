package com.company.usersrestfulapi.api.dao;

import com.company.usersrestfulapi.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
