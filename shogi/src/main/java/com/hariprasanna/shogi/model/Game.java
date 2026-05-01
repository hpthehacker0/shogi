package com.hariprasanna.shogi.model;

import com.hariprasanna.shogi.engine.PlayerColor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // Is it Black's turn or White's turn?
    @Enumerated(EnumType.STRING)
    private PlayerColor currentTurn;

    public PlayerColor getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(PlayerColor currentTurn) {
        this.currentTurn = currentTurn;
    }

    // e.g., "WAITING", "IN_PROGRESS", "CHECKMATE", "RESIGNED"
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // This is where the magic happens! We will store the entire
    // ShogiBoard grid and Komadai as a giant JSON string here.
    @Column(columnDefinition = "TEXT")
    private String boardStateJson;

    public String getBoardStateJson() {
        return boardStateJson;
    }

    public void setBoardStateJson(String boardStateJson) {
        this.boardStateJson = boardStateJson;
    }

    // Add this to your Game.java class
    @ElementCollection
    private List<String> moveLogs = new ArrayList<>();

    // Make sure to add the Getter and Setter!
    public List<String> getMoveLogs() { return moveLogs; }
    public void setMoveLogs(List<String> moveLogs) { this.moveLogs = moveLogs; }

    // --- Constructors ---
    public Game() {
        // JPA requires a no-args constructor!
    }



}