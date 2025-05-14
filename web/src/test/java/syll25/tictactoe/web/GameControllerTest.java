package syll25.tictactoe.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import syll25.tictactoe.web.model.MoveResponseDTO;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String sessionCookie;
    private String username;
    private String password;

    private String createURL(String path) {
        return "http://localhost:" + port + path;
    }

    @BeforeEach
    void registerAndLogin() {
        username = "testUser_" + System.currentTimeMillis();
        password = "testPass_" + System.currentTimeMillis();

        MultiValueMap<String, String> registerForm = new LinkedMultiValueMap<>();
        registerForm.add("username", username);
        registerForm.add("password", password);

        restTemplate.postForEntity(createURL("/user/register"), registerForm, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> loginRequest = new HttpEntity<>(registerForm, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/user/login"),
                HttpMethod.POST,
                loginRequest,
                String.class
        );

        sessionCookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        assertThat(sessionCookie).isNotNull();
    }


    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionCookie);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    @Test
    void startNewGame_shouldRedirectToGame() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("player1Name", "Gracz1");
        form.add("player2Name", "Gracz2");
        form.add("boardSize", "3");

        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/game/start"),
                HttpMethod.POST,
                new HttpEntity<>(form, getAuthHeaders()),
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        assertThat(response.getHeaders().getLocation().getPath()).matches("/game/\\d+");
    }

    @Test
    void makeMove_shouldReturnUpdatedState() {
        Long gameId = createNewGame();

        MultiValueMap<String, String> moveParams = new LinkedMultiValueMap<>();
        moveParams.add("gameId", gameId.toString());
        moveParams.add("row", "0");
        moveParams.add("col", "0");

        ResponseEntity<MoveResponseDTO> response = restTemplate.exchange(
                createURL("/game/move"),
                HttpMethod.POST,
                new HttpEntity<>(moveParams, getAuthHeaders()),
                MoveResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        MoveResponseDTO body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getStateDTO().getBoard()[0][0]).isNotBlank();
    }

    @Test
    void getGameState_shouldReturnCurrentState() {
        Long gameId = createNewGame();

        ResponseEntity<MoveResponseDTO> response = restTemplate.exchange(
                createURL("/game/" + gameId + "/state"),
                HttpMethod.GET,
                new HttpEntity<>(null, getAuthHeaders()),
                MoveResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStateDTO().getBoard()).isNotNull();
    }

    private Long createNewGame() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("player1Name", "Gracz1");
        form.add("player2Name", "Gracz2");
        form.add("boardSize", "3");

        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/game/start"),
                HttpMethod.POST,
                new HttpEntity<>(form, getAuthHeaders()),
                String.class
        );

        URI location = response.getHeaders().getLocation();
        assertThat(location).isNotNull();

        return Long.parseLong(location.getPath().replace("/game/", ""));
    }
}
