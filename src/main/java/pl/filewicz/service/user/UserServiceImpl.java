package pl.filewicz.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filewicz.dto.UserDto;
import pl.filewicz.exceptions.*;
import pl.filewicz.mapper.UserMapper;
import pl.filewicz.model.User;
import pl.filewicz.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Environment environment;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createNewUser(User user) {
        checkAdminPassword(user);
        if (user.getPassword().length() < 6) {
            throw new UserPasswordRequirementsException();
        }
        Optional<User> userByLogin = userRepository.findByLogin(user.getLogin());
        userByLogin.ifPresent(user1 -> {
            throw new DuplicateUserException(user1.getLogin());
        });
        try {
            String passwordHash = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordHash);
            userRepository.save(user);
        } catch (Exception e) {
            throw new CreateFormFormatException(user.getPassword());
        }
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUser(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void deleteUser(String login, User user) {
        checkAdminPassword(user);
        Optional<User> userByLogin = userRepository.findByLogin(login);
        userByLogin.ifPresent(user1 -> userRepository.deleteById(user1.getId()));
    }

    @Override
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
                    throw new UserNotFoundException(login);
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
