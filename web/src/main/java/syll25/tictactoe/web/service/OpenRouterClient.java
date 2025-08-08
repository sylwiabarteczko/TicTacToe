package syll25.tictactoe.web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class OpenRouterClient {

    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private final String apiKey;

    public OpenRouterClient() {
        try {
            Path keyPath = Path.of("/Users/sylwiabarteczko/Desktop/Pliki/TicTacToe/openrouter.key");
            this.apiKey = Files.readString(keyPath).trim();
        } catch (IOException e) {
            throw new RuntimeException("Cannot read OpenRouter API key", e);
        }
    }

    public int[] getBestMove(String[][] board, char aiSymbol, char opponentSymbol) {
        String prompt = buildPrompt(board, aiSymbol, opponentSymbol);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", prompt
        );

        Map<String, Object> body = Map.of(
                "model", "mistral",
                "messages", List.of(message)
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(OPENROUTER_API_URL, request, Map.class);

        String content = ((Map)((List)((Map) response.getBody().get("choices")).get(0)).get(Integer.parseInt("message"))).get("content").toString();
        return parseMove(content);
    }

    private String buildPrompt(String[][] board, char aiSymbol, char opponentSymbol) {
        StringBuilder sb = new StringBuilder("You are the AI playing Tic Tac Toe as '" + aiSymbol + "'.\n");
        sb.append("Here is the board:\n");
        for (String[] row : board) {
            for (String cell : row) {
                sb.append(cell == null ? "." : cell).append(" ");
            }
            sb.append("\n");
        }
        sb.append("Give your move as: row,col (e.g. 1,2)");
        return sb.toString();
    }

    private int[] parseMove(String content) {
        String[] parts = content.replaceAll("[^0-9,]", "").split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid move format from AI: " + content);
        }

        int row = Integer.parseInt(parts[0].trim());
        int col = Integer.parseInt(parts[1].trim());

        return new int[]{row, col};
    }

}
