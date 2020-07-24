package pl.filewicz.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import pl.filewicz.exceptions.UserNotFoundException;
import pl.filewicz.mapper.UserMapper;
import pl.filewicz.model.User;
import pl.filewicz.response.CustomResponse;
import pl.filewicz.service.user.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestControllerImpl implements UserRestController {

    private final UserServiceImpl userController;

    @GetMapping
    public List<UserDto> getUsers() {
        return userController.getAllUsers();
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        log.info("Fetching user by login " + login);
        Optional<User> user = userController.getUser(login);
        return user.map(value -> ResponseEntity.ok(UserMapper.toDto(value)))
//                .orElseGet(() -> ResponseEntity.notFound().build());
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody User user) {
        log.info("Creating User : {}", user);
        UserDto userSaved = userController.createNewUser(user);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{login}")
//                .buildAndExpand(userSaved.getLogin())
//                .toUri();
        return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
//        return ResponseEntity.created(location).body(userSaved);
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<CustomResponse> deleteUser(@PathVariable String login, @RequestBody User user) {
        log.info("Fetching & Deleting User with name {}", login);
        userController.deleteUser(login, user);
        return new ResponseEntity<>(new CustomResponse("User " + login + " successfully deleted"), HttpStatus.OK);
    }

    @PutMapping("/{login}")
    public ResponseEntity<CustomResponse> updateUser(@PathVariable String login, @RequestBody User user) {
        log.info("Updating User with login {}", login);
        if (!login.equals(user.getLogin())) {
            throw new UserNotFoundException(login);
        }
        userController.updateUser(login, user);
        return new ResponseEntity<>(new CustomResponse("User " + login + "successfully updated"), HttpStatus.OK);
    }
}
