package pl.filewicz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.filewicz.dto.UserDto;
import pl.filewicz.mapper.UserMapper;
import pl.filewicz.model.User;
import pl.filewicz.service.UserController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserRestControllerImpl implements UserRestController{

    private final UserController userController;

    @GetMapping
    public List<UserDto> getUsers() {
        return userController.getAllUsers();
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        Optional<User> user = userController.getUser(login);
        return user.map(value -> ResponseEntity.ok(UserMapper.toDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody User user) {
        UserDto userSaved = userController.createNewUser(user);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{login}")
//                .buildAndExpand(userSaved.getLogin())
//                .toUri();
        return new ResponseEntity<>(userSaved,HttpStatus.CREATED);
//        return ResponseEntity.created(location).body(userSaved);
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
