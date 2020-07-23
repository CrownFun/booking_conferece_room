package pl.filewicz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import pl.filewicz.model.Room;
import pl.filewicz.model.User;
import pl.filewicz.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// tu trzeba zamienic na mockMvc, Rest Assured albo test rest template mozna zastoswac po uruchominiu na serwerze.
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfulConferenceRoomSpringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestfulConferenceRoomSpringApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    private final String rootUrl = "http://localhost:8080/api";

    @Test
    public void testCreateNewUser() {
        User user = new User("Thomas", "Burton", "tbarton", "uncracable");
        user.setAdminPassword("q1w2e3r4");
        ResponseEntity<User> postResponse = testRestTemplate.postForEntity(rootUrl + "/users", user, User.class);
        HttpStatus createdCode = HttpStatus.CREATED;
        assertEquals(postResponse.getStatusCode(), createdCode);
        assertNotNull(postResponse);

    }

    @Test
    public void testGetAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(rootUrl + "/users",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response);
    }

    @Test
    public void testUpdateUser() {

        User user = new User("Anne", "Green", "agreen", "ExampleTestPass");
        user.setAdminPassword("q1w2e3r4");
        testRestTemplate.postForEntity(rootUrl + "/users", user, User.class);
        User userFound = testRestTemplate.getForObject(rootUrl + "/users/agreen", User.class);
        userFound.setName("Ellen");
        userFound.setPassword(user.getPassword());
        userFound.setAdminPassword("q1w2e3r4");
        testRestTemplate.put(rootUrl + "/users/agreen", userFound);
        User updatedUserFound = testRestTemplate.getForObject(rootUrl + "/users/agreen", User.class);
        assertEquals(userFound.getName(), updatedUserFound.getName());

    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setAdminPassword("q1w2e3r4");
        testRestTemplate.delete(rootUrl + "/users/agreen", user);

        try {
            testRestTemplate.getForObject(rootUrl + "/users/agreen", User.class);
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void testCreateNewRoom() {
        Room room = new Room("Example Room", "10th floor", 15, true, "33-22-11-22");
        room.setAdminPassword("q1w2e3r4");
        ResponseEntity<Room> postResponse = testRestTemplate.postForEntity(rootUrl + "/rooms", room, Room.class);
        HttpStatus createdStatus = HttpStatus.CREATED;
        assertEquals(createdStatus, postResponse.getStatusCode());
        assertNotNull(postResponse);
    }


    @Test
    public void testGetAllRooms() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(rootUrl + "/rooms",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response);
    }

    @Test
    public void testUpdateRoom() {
        Room room = new Room("Coffe Room", "12th floor", 26, false, "33-22-11-99");
        room.setAdminPassword("q1w2e3r4");
        testRestTemplate.postForEntity(rootUrl + "/rooms", room, Room.class);
        Room roomFound = testRestTemplate.getForObject(rootUrl + "/rooms/Coffe Room", Room.class);
        roomFound.setAdminPassword("q1w2e3r4");
        roomFound.setLocation_description("2nd floor");
        testRestTemplate.put(rootUrl + "/rooms/Coffe", roomFound);
        Room updatedRoomFound = testRestTemplate.getForObject(rootUrl + "/rooms/Coffe Room", Room.class);
        assertEquals(roomFound.getLocation_description(), updatedRoomFound.getLocation_description());

    }

    @Test
    public void testDeleteRoom() {
        Room room = new Room();
        room.setAdminPassword("q1w2e3r4");
        testRestTemplate.delete(rootUrl + "/rooms/Coffe", room);
        try {
            testRestTemplate.getForObject(rootUrl + "/rooms/Coffe", Room.class);
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
