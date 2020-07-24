package pl.filewicz.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.filewicz.dto.UserDto;
import pl.filewicz.model.User;
import pl.filewicz.response.CustomResponse;

@Api(tags = "User Controller")
public interface UserRestController {

    @ApiOperation(value = "add new user", notes = "save new user into database")
    ResponseEntity<UserDto> getUserByLogin(@PathVariable String login);

    @ApiOperation(value = "add new user", notes = "save new user into database")
    ResponseEntity<UserDto> saveUser(@RequestBody User user);

    @ApiOperation(value = "add new user", notes = "save new user into database")
    ResponseEntity<CustomResponse> deleteUser(@PathVariable String login, @RequestBody User user);

    @ApiOperation(value = "add new user", notes = "save new user into database")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All users has been deleted"),
            @ApiResponse(code = 500, message = "Inrternal server Error"),
            @ApiResponse(code = 404, message = "Something went Wrong!")})
    ResponseEntity<CustomResponse> updateUser(@PathVariable String login, @RequestBody User user);
}
