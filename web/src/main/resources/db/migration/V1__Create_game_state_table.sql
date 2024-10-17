CREATE TABLE game_states (
    id SERIAL PRIMARY KEY,
    player1_name VARCHAR(255) NOT NULL,
    player2_name VARCHAR(255) NOT NULL,
    board_state TEXT NOT NULL,
    current_player VARCHAR(255) NOT NULL,
    game_over BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
