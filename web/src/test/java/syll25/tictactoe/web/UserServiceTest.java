package syll25.tictactoe.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import syll25.tictactoe.web.model.User;
import syll25.tictactoe.web.repository.UserRepository;
import syll25.tictactoe.web.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void passwordEncoderTest() {
        String rawPassword = "password123";
        String encodedPassword = "encoded";

        when(encoder.encode(rawPassword)).thenReturn(encodedPassword);

        userService.register("Sylwia", rawPassword);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();
        assertEquals("Sylwia", savedUser.getUsername());
        assertEquals("encoded", savedUser.getPassword());
    }

}