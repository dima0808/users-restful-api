package com.company.usersrestfulapi.api.service.impl;

import com.company.usersrestfulapi.api.dao.UserRepository;
import com.company.usersrestfulapi.api.entity.User;
import com.company.usersrestfulapi.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserFields(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null); // UserNotFoundException

        if (existingUser == null) {
            return null;
        }

        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
        if (user.getSecondName() != null) existingUser.setSecondName(user.getSecondName());
        if (user.getBirthDate() != null) existingUser.setBirthDate(user.getBirthDate());
        if (user.getAddress() != null) existingUser.setAddress(user.getAddress());
        if (user.getPhoneNumber() != null) existingUser.setPhoneNumber(user.getPhoneNumber());

        return userRepository.save(existingUser);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null); // UserNotFoundException

        if (existingUser == null) {
            return null;
        }

        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setSecondName(user.getSecondName());
        existingUser.setBirthDate(user.getBirthDate());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) return; // UserNotFoundException
        userRepository.deleteById(id);
    }
}
