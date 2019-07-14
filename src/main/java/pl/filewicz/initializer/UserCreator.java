package pl.filewicz.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.filewicz.model.User;
import pl.filewicz.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class UserCreator {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserCreator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        userRepository.save(new User("John", "Smith", "jsmith", passwordEncoder.encode("qwerty")));
        userRepository.save(new User("Jane", "Doe", "jdoe", passwordEncoder.encode("mySecret")));
    }


}
