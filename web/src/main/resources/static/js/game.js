const body = document.querySelector('body');
const gameId = body.dataset.gameId;
const username = body.dataset.username;

function getSafeElement(id) {
    return document.getElementById(id);
}

function updateGameState(response) {
    const { stateDTO, yourTurn, currentPlayer, gameOver } = response;
    const board = stateDTO.board;
    const size = stateDTO.size;

    const table = getSafeElement("board");
    table.innerHTML = "";

    for (let row = 0; row < size; row++) {
        const tr = document.createElement("tr");

        for (let col = 0; col < size; col++) {
            const td = document.createElement("td");
            const button = document.createElement("button");

            button.textContent = board[row][col] || "";
            button.disabled = board[row][col] !== null || !yourTurn;

            button.dataset.row = row;
            button.dataset.col = col;
            button.dataset.gameId = gameId;

            button.onclick = () => submitMove(button);

            td.appendChild(button);
            tr.appendChild(td);
        }

        table.appendChild(tr);
    }

    getSafeElement("current-player").textContent = currentPlayer;
    getSafeElement("your-turn").style.display = yourTurn ? "block" : "none";
    getSafeElement("wait-turn").style.display = yourTurn ? "none" : "block";

    if (gameOver) {
        getSafeElement("game-active").style.display = "none";
        getSafeElement("game-over-message").style.display = "block";

        getSafeElement("winner-message").textContent =
            currentPlayer === "GAME_OVER"
                ? "It's a draw!"
                : currentPlayer + " wins!";
    }
}

function fetchGameState() {
    fetch(`/game/${gameId}/state`)
        .then(resp => resp.json())
        .then(updateGameState)
        .catch(err => console.error("Error", err));
}

function submitMove(button) {
    const row = button.dataset.row;
    const col = button.dataset.col;

    fetch(`/game/move?gameId=${gameId}&row=${row}&col=${col}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
    })
        .then(resp => resp.json())
        .then(updateGameState)
        .catch(err => alert("Move failed: " + err.message));
}

document.addEventListener("DOMContentLoaded", () => {
    fetchGameState();
    setInterval(fetchGameState, 2000);
});