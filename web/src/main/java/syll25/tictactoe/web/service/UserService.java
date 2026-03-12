package syll25.tictactoe.web.service;

import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import syll25.tictactoe.web.events.EventEnvelope;
import syll25.tictactoe.web.events.EventType;
import syll25.tictactoe.web.kafka.EventPublisher;
import syll25.tictactoe.web.repository.UserRepository;
import syll25.tictactoe.web.model.User;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EventPublisher eventPublisher;

    public void register(String username, String rawPassword, int age) {

        if (!isValidPassword(rawPassword)) {
            throw new IllegalArgumentException("Password does not meet required rules.");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, hashedPassword, age);
        userRepository.save(user);

        EventEnvelope event = new EventEnvelope(
                UUID.randomUUID(),
                EventType.USER_REGISTERED,
                Instant.now(),
                1,
                Map.of("username", username, "age", age)
        );
        eventPublisher.publish(username, event);
        // tu powinien byc wrzucony event na kafke (zawiera informacje o wieku)
        //instancja event envelope, wypelniasz event type i wiek, obiekt event envelope, przekaze go do event publishera i stad zawolam
        //publishera i mu przekaze obiekt
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
    private boolean isValidPassword(String password) {

        PasswordValidator passwordValidation = new PasswordValidator(
                new LengthRule(8, 128),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Digit,1),
                new CharacterRule(new CharacterData() {
                    @Override
                    public String getErrorCode() { return "INSUFFICIENT_SPECIAL"; }
                    @Override
                    public String getCharacters() { return "!@#$%^&*()_+-=[]{}|;':\",./<>?"; }
                }, 1)
        );
        RuleResult validate = passwordValidation.validate(new PasswordData(password));

        return validate.isValid();
    }
}