package ru.giftcards.giftcards_back.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.giftcards.giftcards_back.auth.dto.RegisterUserDto;
import ru.giftcards.giftcards_back.auth.entity.Role;
import ru.giftcards.giftcards_back.auth.entity.Status;
import ru.giftcards.giftcards_back.auth.entity.User;
import ru.giftcards.giftcards_back.auth.errors.UserAlreadyExistAuthenticationException;
import ru.giftcards.giftcards_back.auth.repository.RoleRepository;
import ru.giftcards.giftcards_back.auth.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterUserDto regUser) {

        User userFromDb = userRepository.findByUsername(regUser.getUsername());
        if(userFromDb != null){
            throw new UserAlreadyExistAuthenticationException("Такой пользователь уже существует");
        }
        userFromDb = userRepository.findByEmail(regUser.getEmail());
        if(userFromDb != null){
            throw new UserAlreadyExistAuthenticationException("Почта занята");
        }

        User user = new User();

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setUsername(regUser.getUsername());
        user.setEmail(regUser.getEmail());
        user.setPassword(passwordEncoder.encode(regUser.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
    }
}
