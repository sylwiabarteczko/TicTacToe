# TicTacToe – Backend

A Spring Boot web application for playing Tic Tac Toe. Supports human vs human and human vs AI game modes using the OpenRouter API.

## Architecture

The project consists of three modules:

- `logic` – game logic (board, players, game state)
- `web` – web layer (REST API, database, Kafka)
- `TicTacToeReportingService` – separate service collecting statistics from Kafka events

The `web` module uses the `logic` module for game logic and state management. Game state is serialized to JSON and persisted in PostgreSQL.

## Requirements

- Java 21
- Maven
- Docker (for PostgreSQL, Kafka, Zookeeper)
- OpenRouter API key *(optional – required only for AI game mode)*

## Setup

### 1. Start infrastructure (Docker)
```bash
cd TicTacToe-Infrastructure
docker-compose up -d
```

Starts:
- PostgreSQL on port `5434`
- Kafka on port `9092`
- Zookeeper on port `2181`

### 2. Configure application.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5434/tic-tac-toe
spring.datasource.username=tic-tac-toe
spring.datasource.password="your password"

spring.kafka.bootstrap-servers=localhost:9092

# Optional – required only for AI game mode
# openrouter.key-path=/path/to/openrouter.key
```

**To enable AI mode:**
1. Create an account at [openrouter.ai](https://openrouter.ai) and generate an API key
2. Save the key to a file, e.g. `openrouter.key` (this file is gitignored)
3. Set the path in `application.properties`:
```properties
openrouter.key-path=/your/path/to/openrouter.key
```

If `openrouter.key-path` is not set, the app starts normally but AI game mode will be unavailable.

### 3. Run the application
```bash
cd web
mvn spring-boot:run
```

Available at: `http://localhost:8080`

### 4. (Optional) Run ReportingService
```bash
cd TicTacToeReportingService
mvn spring-boot:run
```

Available at: `http://localhost:8081`

## Game modes

| Mode | Description | Requirements |
|------|-------------|--------------|
| Human vs Human | Two players on one device | None |
| Human vs AI | Player vs AI (OpenRouter) | OpenRouter API key |

## Kafka events

The app publishes events to the `tictactoe.events` topic:

| Event | When |
|-------|------|
| `USER_REGISTERED` | User registers |
| `GAME_CREATED` | New game is created |
| `MOVE_MADE` | A move is made (by player or AI) |
| `GAME_FINISHED` | Game ends |

## Screenshots

<p align="center">
  <br/><em>Login page</em>
  <img src="https://raw.githubusercontent.com/sylwiabarteczko/TicTacToe/auto-ai/web/Login.png" width="50%" />
</p>

<p align="center">
  <br/><em>Registration page</em>
  <img src="https://raw.githubusercontent.com/sylwiabarteczko/TicTacToe/auto-ai/web/Register.png" width="50%" />
</p>

<p align="center">
  <br/><em>Game board</em>
  <img src="https://raw.githubusercontent.com/sylwiabarteczko/TicTacToe/auto-ai/web/Game.png" width="50%" />
</p>