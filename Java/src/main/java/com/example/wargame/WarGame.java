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
        //gc.setFill(Color.BLUE);
        //gc.fillRect(50, 50, 100, 50);
        //gc.setFill(Color.GREEN);
        //gc.fillRect(200, 150, 120, 80);

        // Desenha linhas para representar conexões entre territórios
        //gc.setStroke(Color.BLACK);
        //gc.setLineWidth(2);
        //gc.strokeLine(100, 75, 220, 190);


        //America do Norte
        //Alasca
        gc.setFill(Color.YELLOW);
        gc.fillRect(40, 40, 30, 30);
        //Mackenzie
        gc.setFill(Color.BROWN);
        gc.fillRect(70, 40, 30, 30);
        //Vancouver
        gc.setFill(Color.LIGHTGOLDENRODYELLOW);
        gc.fillRect(55, 70, 30, 30);
        //Ottawa
        gc.setFill(Color.YELLOW);
        gc.fillRect(85, 70, 30, 30);
        //Labrador
        gc.setFill(Color.YELLOWGREEN);
        gc.fillRect(115, 70, 30, 30);
        //Groelandia
        gc.setFill(Color.YELLOW);
        gc.fillRect(120, 20, 30, 30);
        //California
        gc.setFill(Color.BROWN);
        gc.fillRect(70, 100, 30, 30);
        //Nova York
        gc.setFill(Color.GOLD);
        gc.fillRect(100, 100, 30, 30);
        //México
        gc.setFill(Color.YELLOW);
        gc.fillRect(90, 130, 30, 30);

        //America do Sul
        //Venezuela
        gc.setFill(Color.RED);
        gc.fillRect(120, 160, 30, 30);
        //Peru
        gc.setFill(Color.DARKRED);
        gc.fillRect(105, 190, 30, 30);
        //Argentina
        gc.setFill(Color.MEDIUMVIOLETRED);
        gc.fillRect(105, 220, 30, 30);
        //Brasil
        gc.setFill(Color.ORANGERED);
        gc.fillRect(135, 190, 30, 50);

        //Europa
        //Islandia
        gc.setFill(Color.BLUE);
        gc.fillRect(170, 50, 10, 10);
        //Suecia
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(210, 30, 30, 30);
        //Inglaterra
        gc.setFill(Color.CADETBLUE);
        gc.fillRect(190, 60, 20, 20);
        //Alemanha
        gc.setFill(Color.BLUE);
        gc.fillRect(210, 60, 10, 20);
        //Polonia
        gc.setFill(Color.DODGERBLUE);
        gc.fillRect(220, 60, 20, 20);
        //Moscou
        gc.setFill(Color.BLUEVIOLET);
        gc.fillRect(240, 30, 30, 40);

        //Africa
        //Argelia
        gc.setFill(Color.PINK);
        gc.fillRect(210, 130, 30, 30);
        //Egito
        gc.setFill(Color.DEEPPINK);
        gc.fillRect(240, 120, 30, 20);
        //Sudao
        gc.setFill(Color.HOTPINK);
        gc.fillRect(240, 140, 30, 20);
        //Congo
        gc.setFill(Color.DEEPPINK);
        gc.fillRect(220, 160, 30, 30);
        //Africa do Sul
        gc.setFill(Color.PINK);
        gc.fillRect(230, 190, 30, 30);
        //Madagascar
        gc.setFill(Color.HOTPINK);
        gc.fillRect(260, 170, 10, 10);

        //Asia
        //Omsk
        gc.setFill(Color.GREEN);
        gc.fillRect(270, 30, 30, 30);
        //Arai
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(270, 60, 30, 30);
        //Oriente Medio
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(270, 90, 40, 30);
        //India
        gc.setFill(Color.GREEN);
        gc.fillRect(300, 90, 30, 30);
        //Vietna
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(330, 90, 15, 35);
        //China
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(300, 60, 60, 30);
        //Dudinka
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(300, 20, 15, 40);
        //Mongolia
        gc.setFill(Color.GREEN);
        gc.fillRect(315, 45, 45, 15);
        //Tachita
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(315, 30, 45, 15);
        //Siberia
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(315, 0, 45, 30);
        //Vladivostok
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(360, 30, 50, 50);
        //Japão
        gc.setFill(Color.GREEN);
        gc.fillRect(370, 90, 10, 30);

        //Oceania
        //Sumatra
        gc.setFill(Color.ORANGE);
        gc.fillRect(320, 160, 10, 30);
        //Borneu
        gc.setFill(Color.ORANGERED);
        gc.fillRect(340, 160, 20, 20);
        //Nova Guine
        gc.setFill(Color.DARKORANGE);
        gc.fillRect(370, 170, 30, 10);
        //Australia
        gc.setFill(Color.ORANGE);
        gc.fillRect(340, 200, 40, 40);

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
