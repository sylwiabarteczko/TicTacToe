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

    public OpenRouterClient(@org.springframework.beans.factory.annotation.Value("${openrouter.key-path}") String keyPath) {
        try {
            this.apiKey = java.nio.file.Files.readString(java.nio.file.Path.of(keyPath)).trim();
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Cannot read OpenRouter API key from: " + keyPath, e);
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
                "model", "mistralai/mistral-7b-instruct",
                "messages", List.of(message)
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(OPENROUTER_API_URL, request, Map.class);

        Map rBody = response.getBody();
        ArrayList choices = (ArrayList) rBody.get("choices");
        Map choice = (Map) choices.get(0);
        LinkedHashMap messages = (LinkedHashMap) choice.get("message");

        String content = (String) messages.get("content");

        return parseMove(content);
    }

    private String buildPrompt(String[][] board, char aiSymbol, char opponentSymbol) {
        int n = board.length;
        StringBuilder sb = new StringBuilder("You are the AI playing Tic Tac Toe as '")
                .append(aiSymbol).append("'.\n");
        sb.append("Board (use '.' for empty):\n");
        for (String[] row : board) {
            for (String cell : row) sb.append(cell == null ? "." : cell).append(" ");
            sb.append("\n");
        }
        sb.append("The board is ").append(n).append("x").append(n).append(".\n");
        sb.append("Rows and columns are 0-based (0..").append(n - 1).append(").\n");
        sb.append("Return EXACTLY one move in the form: row,col  (e.g. 0,2)\n");
        return sb.toString();
    }


    private int[] parseMove(String content) {
        java.util.regex.Matcher m =
                java.util.regex.Pattern.compile("\\b(\\d+)\\s*,\\s*(\\d+)\\b").matcher(content);
        if (!m.find()) {
            throw new IllegalArgumentException("Invalid move format from AI: " + content);
        }
        int row = Integer.parseInt(m.group(1));
        int col = Integer.parseInt(m.group(2));
        return new int[]{row, col};
    }

}
