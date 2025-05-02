package syll25.tictactoe.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<Object> moveResponse;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void getNewGameTest() {
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/game/new"), String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("Start a new Tic Tac Toe Game");
    }

    @Test
    public void startNewGameTest() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("player1Name", "Sylwia");
        params.add("player2Name", "Sabina");
        params.add("boardSize", "3");
        params.add("player1Login", "loginSylwia");

        ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/game/start"), params, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(302);
        assertThat(response.getHeaders().getLocation().toString()).contains("/game/");
    }

    @Test
    public void testMakeMove() {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("player1Name", "Sylwia");
        params.add("player2Name", "Sabina");
        params.add("boardSize", "3");
        params.add("player1Login", "loginSylwia");

        ResponseEntity<String> startGameResponse = restTemplate.postForEntity(createURLWithPort("/game/start"), params, String.class);
        assertThat(startGameResponse.getStatusCodeValue()).isEqualTo(302);

        URI location = startGameResponse.getHeaders().getLocation();
        assertThat(location).isNotNull();

        String gameUrl = location.toString();
        String gameId = gameUrl.substring(gameUrl.lastIndexOf('/') + 1);

        System.out.println("Extracted gameId: " + gameId);
        assertThat(gameId).isNotBlank();

        MultiValueMap<String, String> moveParams = new LinkedMultiValueMap<>();
        moveParams.add("gameId", gameId);
        moveParams.add("row", "0");
        moveParams.add("col", "0");
        System.out.println("Move params: " + moveParams);

        ResponseEntity<String> moveResponse = restTemplate.postForEntity(createURLWithPort("/game/move"), moveParams, String.class);

        assertThat(moveResponse.getBody()).isNotNull();

        String responseBody = moveResponse.getBody();
        assertThat(responseBody).contains("\"yourTurn\":false");
        assertThat(responseBody).contains("\"currentPlayer\":\"Sabina\"");
        assertThat(responseBody).contains("Current Player");
        assertThat(responseBody).contains("disabled=\"disabled\"");
    }

    @Test
    public void testGameResult() {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("player1Name", "Sylwia");
        params.add("player2Name", "Sabina");
        params.add("boardSize", "3");

        ResponseEntity<String> startGameResponse = restTemplate.postForEntity(createURLWithPort("/game/start"), params, String.class);
        String gameUrl = startGameResponse.getHeaders().getLocation().toString();
        String gameId = gameUrl.substring(gameUrl.lastIndexOf('/') + 1);

        makeMoveAndAssert(gameId, 0, 0);
        makeMoveAndAssert(gameId, 1, 0);
        makeMoveAndAssert(gameId, 0, 1);
        makeMoveAndAssert(gameId, 1, 1);
        ResponseEntity<String> winningMoveResponse = makeMoveAndAssert(gameId, 0, 2);

        assertThat(winningMoveResponse.getStatusCodeValue()).isEqualTo(302);
        assertThat(winningMoveResponse.getHeaders().getLocation().toString()).contains("/game/gameResult/");

        ResponseEntity<String> gameResultResponse = restTemplate.getForEntity(winningMoveResponse.getHeaders().getLocation(), String.class);
        assertThat(gameResultResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(gameResultResponse.getBody()).contains("Game Result");
        assertThat(gameResultResponse.getBody()).contains("Sylwia");
    }


    private ResponseEntity<String> makeMoveAndAssert(String gameId, int row, int col) {
        MultiValueMap<String, String> moveParams = new LinkedMultiValueMap<>();
        moveParams.add("gameId", gameId);
        moveParams.add("row", String.valueOf(row));
        moveParams.add("col", String.valueOf(col));

        ResponseEntity<String> moveResponse = restTemplate.postForEntity(createURLWithPort("/game/move"), moveParams, String.class);
        assertThat(moveResponse.getStatusCodeValue()).isIn(200, 302);  // 302 mamy przekierowanie
        return moveResponse;
    }

}





