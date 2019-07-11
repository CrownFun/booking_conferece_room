package pl.filewicz.mapper;

import org.springframework.stereotype.Service;
import pl.filewicz.api.UserDto;
import pl.filewicz.model.User;


public class UserMapper {

    public static UserDto toDto(User user) {
        return new UserDto(user.getName(), user.getSurname(), user.getLogin());
    }


}
