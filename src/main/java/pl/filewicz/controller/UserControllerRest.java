package pl.filewicz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.filewicz.api.UserDto;
import pl.filewicz.mapper.UserMapper;
import pl.filewicz.service.UserController;
import pl.filewicz.model.User;

import java.net.URI;
import java.util.List;
import java.util.Optional;


// requestparam vs requestbody vs pathvariable

@RestController
@RequestMapping("api/users")
public class UserControllerRest {


    private UserController userController;

    @Autowired
    public UserControllerRest(UserController userController) {
        this.userController = userController;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getUsers() {
        return userController.getAllUsers();
    }

    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        Optional<User> user = userController.getUser(login);
        return user.map(value -> ResponseEntity.ok(UserMapper.toDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> saveUser(@RequestBody User user) {
        UserDto userSaved = userController.createNewUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{login}")
                .buildAndExpand(userSaved.getLogin())
                .toUri();
        return ResponseEntity.created(location).body(userSaved);

    }

    @DeleteMapping("/{login}")
    public void deleteUser(@PathVariable String login, @RequestBody User user) {
        userController.deleteUser(login, user);
    }

    @PutMapping("/{login}")
    public void updateUser(@PathVariable String login, @RequestBody User user) {
        if (!login.equals(user.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The updated object must have a login that matches the login in the resource path");
        }
        userController.updateUser(login, user);
    }


}
