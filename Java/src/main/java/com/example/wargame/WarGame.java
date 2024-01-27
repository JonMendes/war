package com.example.wargame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class WarGame extends Application {
    private ArrayList<String> territories;
    private ArrayList<Player> players;
    private Player currentPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeTerritories();
        initializePlayers();

        primaryStage.setTitle("War Game");
        primaryStage.setScene(createGameScene());
        primaryStage.show();
    }

    private void initializeTerritories() {
        territories = new ArrayList<>();
        // Adicione os territórios do jogo War
        Collections.addAll(territories, "North America", "South America", "Europe", "Africa", "Asia", "Australia");
        Collections.shuffle(territories);
    }

    private void initializePlayers() {
        players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        // Adicione mais jogadores conforme necessário
        currentPlayer = players.get(0);
    }

    private Scene createGameScene() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Button drawButton = new Button("Draw Territory");
        drawButton.setOnAction(event -> drawTerritory());

        grid.add(drawButton, 0, 0);

        return new Scene(grid, 300, 200);
    }

    private void drawTerritory() {
        if (!territories.isEmpty()) {
            String drawnTerritory = territories.remove(0);
            showInformation("Territory Drawn", "Player: " + currentPlayer.getName() + "\nTerritory: " + drawnTerritory);
            // Lógica adicional aqui (distribuir exércitos, verificar vitória, etc.)
            switchPlayer();
        } else {
            showInformation("Game Over", "All territories have been drawn.");
        }
    }

    private void switchPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        currentPlayer = players.get(nextIndex);
    }

    private void showInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

