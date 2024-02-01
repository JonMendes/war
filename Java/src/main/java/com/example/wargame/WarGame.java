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
        territories = new ArrayList<>();
        Collections.addAll(territories, "Alasca","Mackenzie","Vancouver","Ottawa","Labrador","Groelandia","California","Nova York","México",
                "Venezuela","Peru","Argentina","Brasil","Islandia","Suecia","Inglaterra","Alemanha","Polonia","Moscou",
                "Argelia","Egito","Sudao","Congo","Africa do Sul","Madagascar","Omsk","Arai","Oriente Medio","India",
                "Vietna","China","Dudinka","Mongolia","Tachita","Siberia","Vladivostok","Japão","Sumatra","Borneu","Nova Guine","Australia");
        Collections.shuffle(territories);
        colors = new ArrayList<>();
        Collections.addAll(colors,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,
                Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,
                Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,
                Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,
                Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE);
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
        gc.setFill(colors.get(0));
        gc.fillRect(40+50, 40, 30, 30);
        gc.strokeRect(40+50, 40, 30, 30);
        //Mackenzie
        gc.setFill(colors.get(1));
        gc.fillRect(70+50, 40, 30, 30);
        gc.strokeRect(70+50, 40, 30, 30);
        //Vancouver
        gc.setFill(colors.get(2));
        gc.fillRect(55+50, 70, 30, 30);
        gc.strokeRect(55+50, 70, 30, 30);
        //Ottawa
        gc.setFill(colors.get(3));
        gc.fillRect(85+50, 70, 30, 30);
        gc.strokeRect(85+50, 70, 30, 30);
        //Labrador
        gc.setFill(colors.get(4));
        gc.fillRect(115+50, 70, 30, 30);
        gc.strokeRect(115+50, 70, 30, 30);
        //Groelandia
        gc.setFill(colors.get(5));
        gc.fillRect(120+50, 20, 30, 30);
        gc.strokeRect(120+50, 20, 30, 30);
        //California
        gc.setFill(colors.get(6));
        gc.fillRect(70+50, 100, 30, 30);
        gc.strokeRect(70+50, 100, 30, 30);
        //Nova York
        gc.setFill(colors.get(7));
        gc.fillRect(100+50, 100, 30, 30);
        gc.strokeRect(100+50, 100, 30, 30);
        //México
        gc.setFill(colors.get(8));
        gc.fillRect(90+50, 130, 30, 30);
        gc.strokeRect(90+50, 130, 30, 30);

        //America do Sul
        //Venezuela
        gc.setFill(colors.get(9));
        gc.fillRect(120+50, 160, 30, 30);
        gc.strokeRect(120+50, 160, 30, 30);
        //Peru
        gc.setFill(colors.get(10));
        gc.fillRect(105+50, 190, 30, 30);
        gc.strokeRect(105+50, 190, 30, 30);
        //Argentina
        gc.setFill(colors.get(11));
        gc.fillRect(105+50, 220, 30, 30);
        gc.strokeRect(105+50, 220, 30, 30);
        //Brasil
        gc.setFill(colors.get(12));
        gc.fillRect(135+50, 190, 30, 50);
        gc.strokeRect(135+50, 190, 30, 50);

        //Europa
        //Islandia
        gc.setFill(colors.get(13));
        gc.fillRect(170+50, 50, 10, 10);
        gc.strokeRect(170+50, 50, 10, 10);
        //Suecia
        gc.setFill(colors.get(14));
        gc.fillRect(210+50, 30, 30, 30);
        gc.strokeRect(210+50, 30, 30, 30);
        //Inglaterra
        gc.setFill(colors.get(15));
        gc.fillRect(190+50, 60, 20, 20);
        gc.strokeRect(190+50, 60, 20, 20);
        //Alemanha
        gc.setFill(colors.get(16));
        gc.fillRect(210+50, 60, 10, 20);
        gc.strokeRect(210+50, 60, 10, 20);
        //Polonia
        gc.setFill(colors.get(17));
        gc.fillRect(220+50, 60, 20, 20);
        gc.strokeRect(220+50, 60, 20, 20);
        //Moscou
        gc.setFill(colors.get(18));
        gc.fillRect(240+50, 30, 30, 40);
        gc.strokeRect(240+50, 30, 30, 40);

        //Africa
        //Argelia
        gc.setFill(colors.get(19));
        gc.fillRect(210+50, 130, 30, 30);
        gc.strokeRect(210+50, 130, 30, 30);
        //Egito
        gc.setFill(colors.get(20));
        gc.fillRect(240+50, 120, 30, 20);
        gc.strokeRect(240+50, 120, 30, 20);
        //Sudao
        gc.setFill(colors.get(21));
        gc.fillRect(240+50, 140, 30, 20);
        gc.strokeRect(240+50, 140, 30, 20);
        //Congo
        gc.setFill(colors.get(22));
        gc.fillRect(220+50, 160, 30, 30);
        gc.strokeRect(220+50, 160, 30, 30);
        //Africa do Sul
        gc.setFill(colors.get(23));
        gc.fillRect(230+50, 190, 30, 30);
        gc.strokeRect(230+50, 190, 30, 30);
        //Madagascar
        gc.setFill(colors.get(24));
        gc.fillRect(260+50, 170, 10, 10);
        gc.strokeRect(260+50, 170, 10, 10);

        //Asia
        //Omsk
        gc.setFill(colors.get(25));
        gc.fillRect(270+50, 30, 30, 30);
        gc.strokeRect(270+50, 30, 30, 30);
        //Arai
        gc.setFill(colors.get(26));
        gc.fillRect(270+50, 60, 30, 30);
        gc.strokeRect(270+50, 60, 30, 30);
        //Oriente Medio
        gc.setFill(colors.get(27));
        gc.fillRect(270+50, 90, 40, 30);
        gc.strokeRect(270+50, 90, 40, 30);
        //India
        gc.setFill(colors.get(28));
        gc.fillRect(300+50, 90, 30, 30);
        gc.strokeRect(300+50, 90, 30, 30);
        //Vietna
        gc.setFill(colors.get(29));
        gc.fillRect(330+50, 90, 15, 35);
        gc.strokeRect(330+50, 90, 15, 35);
        //China
        gc.setFill(colors.get(30));
        gc.fillRect(300+50, 60, 60, 30);
        gc.strokeRect(300+50, 60, 60, 30);
        //Dudinka
        gc.setFill(colors.get(31));
        gc.fillRect(300+50, 20, 15, 40);
        gc.strokeRect(300+50, 20, 15, 40);
        //Mongolia
        gc.setFill(colors.get(32));
        gc.fillRect(315+50, 45, 45, 15);
        gc.strokeRect(315+50, 45, 45, 15);
        //Tachita
        gc.setFill(colors.get(33));
        gc.fillRect(315+50, 30, 45, 15);
        gc.strokeRect(315+50, 30, 45, 15);
        //Siberia
        gc.setFill(colors.get(34));
        gc.fillRect(315+50, 0, 45, 30);
        gc.strokeRect(315+50, 0, 45, 30);
        //Vladivostok
        gc.setFill(colors.get(35));
        gc.fillRect(360+50, 30, 50, 50);
        gc.strokeRect(360+50, 30, 50, 50);
        //Japão
        gc.setFill(colors.get(36));
        gc.fillRect(370+50, 90, 10, 30);
        gc.strokeRect(370+50, 90, 10, 30);

        //Oceania
        //Sumatra
        gc.setFill(colors.get(37));
        gc.fillRect(320+50, 160, 10, 30);
        gc.strokeRect(320+50, 160, 10, 30);
        //Borneu
        gc.setFill(colors.get(38));
        gc.fillRect(340+50, 160, 20, 20);
        gc.strokeRect(340+50, 160, 20, 20);
        //Nova Guine
        gc.setFill(colors.get(39));
        gc.fillRect(370+50, 170, 30, 10);
        gc.strokeRect(370+50, 170, 30, 10);
        //Australia
        gc.setFill(colors.get(40));
        gc.fillRect(340+50, 200, 40, 40);
        gc.strokeRect(340+50, 200, 40, 40);

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
