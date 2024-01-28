package com.example.wargame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class WarGame extends Application {
    private ArrayList<String> territories;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Canvas canvas;
    private Label playerInfoLabel;
    private int armiesPerTurn = 5;

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
        Collections.addAll(territories, "North America", "South America", "Europe", "Africa", "Asia", "Australia");
        Collections.shuffle(territories);
    }

    private void initializePlayers() {
        players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        currentPlayer = players.get(0);
    }

    private Scene createGameScene() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        canvas = new Canvas(400, 300);
        grid.add(canvas, 0, 0);

        playerInfoLabel = new Label("Current Player: " + currentPlayer.getName() + "\nArmies: 0");
        grid.add(playerInfoLabel, 0, 1);

        Button drawButton = new Button("Draw Territory");
        drawButton.setOnAction(event -> drawTerritory());

        grid.add(drawButton, 0, 2);

        // Desenha o mapa inicial
        drawMap();

        return new Scene(grid, 600, 400);
    }

    private void drawMap() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Desenha territórios com formas geométricas (exemplo)
        gc.setFill(Color.BLUE);
        gc.fillRect(50, 50, 100, 50);
        gc.setFill(Color.GREEN);
        gc.fillRect(200, 150, 120, 80);

        // Desenha linhas para representar conexões entre territórios
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(100, 75, 220, 190);

        // Adicione mais desenhos conforme necessário
    }

    private void drawTerritory() {
        if (!territories.isEmpty()) {
            String drawnTerritory = territories.remove(0);
            currentPlayer.addArmies(armiesPerTurn);
            showInformation("Territory Drawn", "Player: " + currentPlayer.getName() +
                    "\nTerritory: " + drawnTerritory +
                    "\nArmies: " + armiesPerTurn);
            switchPlayer();
            drawMap(); // Atualiza o mapa após desenhar um novo território
        } else {
            showInformation("Game Over", "All territories have been drawn.");
            // Adicione lógica adicional para determinar o vencedor
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
    private int armies;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getArmies() {
        return armies;
    }

    public void addArmies(int numArmies) {
        armies += numArmies;
    }
}
