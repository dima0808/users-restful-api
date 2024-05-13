    package com.company.usersrestfulapi.api.service.impl;

    import com.company.usersrestfulapi.api.dao.UserRepository;
    import com.company.usersrestfulapi.api.dto.UserDto;
    import com.company.usersrestfulapi.api.entity.User;
    import com.company.usersrestfulapi.api.service.UserException;
    import com.company.usersrestfulapi.api.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.util.List;

    @Service
    public class UserServiceImpl implements UserService {

        @Value("${user.minAge}")
        private int minAge;

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
        public List<User> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate) {
            return userRepository.findByBirthDateBetween(fromDate, toDate);
        }

        @Override
        public User getUser(Long id) {
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserException("user with id " + id + " not found"));
        }

        @Override
        public User createUser(UserDto userDto) {

            validateAge(userDto.findAge(), minAge);

            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new UserException("email " + userDto.getEmail() + " already exists");
            }

            User user = User.builder()
                    .email(userDto.getEmail())
                    .firstName(userDto.getFirstName())
                    .secondName(userDto.getSecondName())
                    .birthDate(userDto.getBirthDate())
                    .address(userDto.getAddress())
                    .phoneNumber(userDto.getPhoneNumber())
                    .build();

            return userRepository.save(user);
        }

        @Override
        public User updateUserFields(Long id, UserDto userDto) {

            validateAge(userDto.findAge(), minAge);

            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new UserException("email " + userDto.getEmail() + " already exists");
            }

            User existingUser = getUser(id);
            if (userDto.getEmail() != null) existingUser.setEmail(userDto.getEmail());
            if (userDto.getFirstName() != null) existingUser.setFirstName(userDto.getFirstName());
            if (userDto.getSecondName() != null) existingUser.setSecondName(userDto.getSecondName());
            if (userDto.getBirthDate() != null) existingUser.setBirthDate(userDto.getBirthDate());
            if (userDto.getAddress() != null) existingUser.setAddress(userDto.getAddress());
            if (userDto.getPhoneNumber() != null) existingUser.setPhoneNumber(userDto.getPhoneNumber());

            return userRepository.save(existingUser);
        }

        @Override
        public User updateUser(Long id, UserDto userDto) {

            validateAge(userDto.findAge(), minAge);

            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new UserException("email " + userDto.getEmail() + " already exists");
            }

            User existingUser = getUser(id);
            existingUser.setEmail(userDto.getEmail());
            existingUser.setFirstName(userDto.getFirstName());
            existingUser.setSecondName(userDto.getSecondName());
            existingUser.setBirthDate(userDto.getBirthDate());
            existingUser.setAddress(userDto.getAddress());
            existingUser.setPhoneNumber(userDto.getPhoneNumber());

            return userRepository.save(existingUser);
        }

        @Override
        public void deleteUser(Long id) {
            if (!userRepository.existsById(id)) {
                throw new UserException("user with id " + id + " not found");
            }
            userRepository.deleteById(id);
        }

        public void validateAge(int age, int minAge) {
            if (age < 0 || age > 120) {
                throw new UserException("invalid birth date");
            } else if (age < minAge) {
                throw new UserException("user is under the legal age (" + minAge + ")");
            }
        }
    }
