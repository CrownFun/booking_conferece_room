package pl.filewicz.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.filewicz.exceptions.UserNotFoundException;
import pl.filewicz.model.User;
import pl.filewicz.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        PasswordEncoder encoder = passwordEncoder();
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return new org.springframework.security.core.userdetails.User(
                user.get().getLogin(),
                encoder.encode(user.get().getPassword()),
                new HashSet<>());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
