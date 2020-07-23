package pl.filewicz.service.user;

import pl.filewicz.dto.UserDto;
import pl.filewicz.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto createNewUser(User user);

    List<UserDto> getAllUsers();

    Optional<User> getUser(String login);

    void deleteUser(String login, User user);

    void updateUser(String login, User user);

}
