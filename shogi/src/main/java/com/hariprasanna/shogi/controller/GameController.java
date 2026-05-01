package com.hariprasanna.shogi.controller;

import com.hariprasanna.shogi.dto.MoveRequestDTO;
import com.hariprasanna.shogi.engine.PlayerColor;
import com.hariprasanna.shogi.engine.ShogiBoard;
import com.hariprasanna.shogi.model.Game;
import com.hariprasanna.shogi.repository.GameRepository;
import com.hariprasanna.shogi.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;
    private final GameRepository gameRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // Spring Boot automatically passes the repository in here
    public GameController(GameService gameService, GameRepository gameRepository,SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // Endpoint 1: Start a brand new game
    @PostMapping("/start")
    public ResponseEntity<Game> startGame() throws Exception {
        Game newGame = new Game();
        newGame.setStatus("IN_PROGRESS");
        newGame.setCurrentTurn(PlayerColor.BLACK); // Black always goes first

        // Create a fresh board and serialize it
        ShogiBoard startingBoard = new ShogiBoard();
        newGame.setBoardStateJson(gameService.serializeBoard(startingBoard));

        // Save to DB and return the 200 OK response to the user
        // (You will need to add a save method in your GameService for this, or inject the Repo here)
        Game savedGame = gameRepository.save(newGame);
        return ResponseEntity.ok(savedGame);
    }

    // Endpoint 2: Submit a move
    @PostMapping("/{id}/move")
    public ResponseEntity<?> makeMove(@PathVariable Long id, @RequestBody MoveRequestDTO moveRequest) {
        try {
            Game updatedGame = gameService.processMove(id, moveRequest);
            messagingTemplate.convertAndSend("/topic/game/" + id, updatedGame);
            return ResponseEntity.ok(updatedGame);

        } catch (Exception e) {
            // If any of our State Checks throw an error, return a 400 Bad Request!
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // 🌟 NEW: Allows Player 2 to download the board state to join!
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        return gameRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}