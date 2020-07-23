package pl.filewicz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// nowa obsługa wyjątków
// optional w service ?
// dodac dokumneatcje swagger
//dodac interfejsy @ApiOperation
//przepisac testy na MockMvc
// dodac readme
@SpringBootApplication
public class RestfulConferenceRoomSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulConferenceRoomSpringApplication.class, args);
    }
}
