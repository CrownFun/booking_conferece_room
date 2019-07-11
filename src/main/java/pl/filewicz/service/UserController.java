package pl.filewicz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.filewicz.api.UserDto;
import pl.filewicz.exceptions.DuplicateLoginException;
import pl.filewicz.mapper.UserMapper;
import pl.filewicz.model.User;
import pl.filewicz.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// duplicateLoginException

@Service
public class UserController {


    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createNewUser(User user) {

        Optional<User> userByLogin = userRepository.findByLogin(user.getLogin());

        userByLogin.ifPresent(user1 -> {
            throw new DuplicateLoginException();
        });

        userRepository.save(user);

        return UserMapper.toDto(user);

    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }


    public Optional<User> getUser(String login) {
        return userRepository.findByLogin(login);
    }

    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }

    public void updateUser(String login, User user) {
        Optional<User> userByLogin = userRepository.findByLogin(login);
        userByLogin.ifPresent(user2 -> {
                    user2.setName(user.getName());
                    user2.setSurname(user.getSurname());
                    user2.setPassword(user.getPassword());
                    user2.setLogin(user.getLogin());
                    userRepository.save(user2);
                }
        );


    }


}
