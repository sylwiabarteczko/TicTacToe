package syll25.tictactoe.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import syll25.tictactoe.web.repository.UserRepository;
import syll25.tictactoe.web.model.User;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void register(String username, String rawPassword) {

        if (!isValidPassword(rawPassword)) {
            throw new IllegalArgumentException("Password does not meet required rules.");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, hashedPassword);
        userRepository.save(user);
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

        boolean minLength   = password.length() >= 8;
        boolean hasLower    = password.matches(".*[a-z].*");
        boolean hasUpper    = password.matches(".*[A-Z].*");
        boolean hasDigit    = password.matches(".*\\d.*");
        boolean hasSpecial  = password.matches(".*[^a-zA-Z0-9].*");

        return minLength && hasLower && hasUpper && hasDigit && hasSpecial;
    }

}