package pl.filewicz.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String name;
    private String surname;
    private String login;

    public UserDto(String name, String surname, String login) {
        this.name = name;
        this.surname = surname;
        this.login = login;
    }
}
