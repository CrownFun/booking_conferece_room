package pl.filewicz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filewicz.api.UserDto;
import pl.filewicz.exceptions.*;
import pl.filewicz.mapper.UserMapper;
import pl.filewicz.model.User;
import pl.filewicz.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@PropertySource("classpath:application.properties")
public class UserController {


    private UserRepository userRepository;
    private Environment environment;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, Environment environment, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.environment = environment;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createNewUser(User user) {
        checkAdminPassword(user);
        if (user.getPassword().length() < 6) {
            throw new UserPasswordRequirementsException();
        }
        Optional<User> userByLogin = userRepository.findByLogin(user.getLogin());
        userByLogin.ifPresent(user1 -> {
            throw new DuplicateUserException();
        });
        try {
            String passwordHash = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordHash);
            userRepository.save(user);
        } catch (Exception e) {
            throw new CreateFormFormatException();
        }
        return UserMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public Optional<User> getUser(String login) {
        return userRepository.findByLogin(login);
    }

    public void deleteUser(String login, User user) {
        checkAdminPassword(user);
        Optional<User> userByLogin = userRepository.findByLogin(login);
        userByLogin.ifPresent(user1 -> userRepository.deleteById(user1.getId()));
    }

    public void updateUser(String login, User user) {
        checkAdminPassword(user);
        Optional<User> userByLogin = userRepository.findByLogin(login);
        userByLogin.ifPresentOrElse(user2 -> {
                    user2.setName(user.getName());
                    user2.setSurname(user.getSurname());
                    user2.setPassword(user.getPassword());
                    user2.setLogin(user.getLogin());
                    userRepository.save(user2);
                }, () -> {
                    throw new UserNotFoundException();
                }
        );
    }


    private boolean adminPasswordValidate(User user) {
        boolean isValid = false;
        String adminPass = environment.getProperty("messageFileProperty");
        if (user.getAdminPassword() == null || !user.getAdminPassword().equals(adminPass)) {
            isValid = true;
        }
        return isValid;
    }


    private void checkAdminPassword(User user) {
        if (adminPasswordValidate(user)) {
            throw new AdministratorSecurityException();
        }
    }
}
