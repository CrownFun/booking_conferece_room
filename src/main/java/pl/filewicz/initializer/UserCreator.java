package pl.filewicz.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.filewicz.model.User;
import pl.filewicz.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class UserCreator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        userRepository.save(new User("John", "Smith", "jsmith", passwordEncoder.encode("qwerty")));
        userRepository.save(new User("Jane", "Doe", "jdoe", passwordEncoder.encode("mySecret")));
    }
}
