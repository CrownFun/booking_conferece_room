package pl.filewicz.api;


public class UserDto {

    private String name;

    private String surname;

    private String login;

    public UserDto() {
    }

    public UserDto(String name, String surname, String login) {
        this.name = name;
        this.surname = surname;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
