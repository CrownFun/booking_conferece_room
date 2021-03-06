package pl.filewicz.mapper;

import pl.filewicz.dto.UserDto;
import pl.filewicz.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        return new UserDto(user.getName(), user.getSurname(), user.getLogin());
    }
}
