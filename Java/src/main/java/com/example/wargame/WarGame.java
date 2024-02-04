package com.example.wargame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Random;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.Collections;

public class WarGame extends Application {
    private ArrayList<String> territories;
    private ArrayList<Color> colors;
    private ArrayList<Integer> armies;
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
        initializePlayers(2);

        primaryStage.setTitle("War Game");
        primaryStage.setScene(createGameScene());
        primaryStage.show();
    }

    private void initializeTerritories() {
        territories = new ArrayList<>();
        Collections.addAll(territories, "Alasca","Mackenzie","Vancouver","Ottawa","Labrador","Groelandia","California","Nova York","México",
                "Venezuela","Peru","Argentina","Brasil","Islandia","Suecia","Inglaterra","Alemanha","Polonia","Moscou",
                "Argelia","Egito","Sudao","Congo","Africa do Sul","Madagascar","Omsk","Arai","Oriente Medio","India",
                "Vietna","China","Dudinka","Mongolia","Tachita","Siberia","Vladivostok","Japão","Sumatra","Borneu","Nova Guine","Australia","Espanha");
        Collections.shuffle(territories);
        colors = new ArrayList<>();
        Collections.addAll(colors,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,
                Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,
                Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,
                Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,
                Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE);
        armies = new ArrayList<>(Collections.nCopies(42, 0));;


    }

    private void initializePlayers(int playerNumber) {
        players = new ArrayList<>();
        players.add(new Player("Player 1", Color.RED));
        players.add(new Player("Player 2",Color.YELLOW));
        if(playerNumber > 2)
            players.add(new Player("Player 3",Color.GREEN));
        if(playerNumber > 3)
            players.add(new Player("Player 4",Color.BLUE));
        currentPlayer = players.get(0);
    }

    private Scene createGameScene() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        canvas = new Canvas(1000, 500);
        grid.add(canvas, 0, 0);

        playerInfoLabel = new Label("Current Player: " + currentPlayer.getName() + "\nArmies: " + currentPlayer.getArmies());
        grid.add(playerInfoLabel, 0, 1);

        Button drawButton = new Button("Draw Territories");
        drawButton.setOnAction(event -> drawTerritory());
        grid.add(drawButton, 0, 2);

        // TextFields for territories
        TextField attackingTerritoryField = new TextField();
        TextField targetTerritoryField = new TextField();
        TextField armiesToAddField = new TextField();
        TextField targetTerritoryToAddArmiesField = new TextField();

        attackingTerritoryField.setPromptText("Attacking Territory");
        targetTerritoryField.setPromptText("Target Territory");
        armiesToAddField.setPromptText("Armies to Add");
        targetTerritoryToAddArmiesField.setPromptText("Territory to Add Armies");

        grid.add(attackingTerritoryField, 0, 3);
        grid.add(targetTerritoryField, 0, 4);

        // Button for confirming attack
        Button confirmAttackButton = new Button("Confirm Attack");
        confirmAttackButton.setOnAction(event -> {
            String attacking = attackingTerritoryField.getText();
            String target = targetTerritoryField.getText();

            attackTerritory(attacking, target);

            // Use 'attacking' and 'target' as needed (e.g., print or process them)
            System.out.println("Attacking Territory: " + attacking);
            System.out.println("Target Territory: " + target);
            drawMap();
            updatePlayerInfoLabel();
        });

        grid.add(confirmAttackButton, 0, 5);

        grid.add(targetTerritoryToAddArmiesField, 0, 6);
        grid.add(armiesToAddField, 0, 7);

        // Button for confirming adding armies
        Button confirmAddArmiesButton = new Button("Confirm Add Armies");
        confirmAddArmiesButton.setOnAction(event -> {
            String territoryToAddArmies = targetTerritoryToAddArmiesField.getText();
            int armiesToAdd = Integer.parseInt(armiesToAddField.getText());
            addArmiesToTerritory(territoryToAddArmies, armiesToAdd);

            System.out.println("Territory to Add Armies: " + territoryToAddArmies);
            System.out.println("Armies to Add: " + armiesToAdd);
            drawMap();
            updatePlayerInfoLabel();
        });

        grid.add(confirmAddArmiesButton, 0, 8);

        // Button for passing
        Button passButton = new Button("Pass");
        passButton.setOnAction(event -> {
            System.out.println(currentPlayer.getName() + " has passed.");
            switchPlayer();
            currentPlayer.addArmies(Collections.frequency(colors, currentPlayer.getColor())/2);
            updatePlayerInfoLabel();
        });

        grid.add(passButton, 0, 9);

        drawMap();
        updatePlayerInfoLabel();

        return new Scene(grid, 1000, 850);
    }
    private void updatePlayerInfoLabel() {
        playerInfoLabel.setText("Current Player: " + currentPlayer.getName() + "\nArmies: " + currentPlayer.getArmies());
    }

    private void drawMap() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Desenha territórios com formas geométricas (exemplo)
        //gc.setFill(Color.BLUE);
        //gc.fillRect(50, 50, 100, 50);
        //gc.setFill(Color.GREEN);
        //gc.fillRect(200, 150, 120, 80);

        // Scaling factor
        double scaleFactor = 2.0;

        // Desenha linhas para representar conexões entre territórios
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeLine((100 + 50) * scaleFactor, (40) * scaleFactor, (120 + 50) * scaleFactor, (30) * scaleFactor);
        gc.strokeLine((130 + 50) * scaleFactor, (70) * scaleFactor, (130 + 50) * scaleFactor, (50) * scaleFactor);
        gc.strokeLine((40 + 50) * scaleFactor, (55) * scaleFactor, 0, (55) * scaleFactor);
        gc.strokeLine((170 + 50) * scaleFactor, (50) * scaleFactor, (130 + 50) * scaleFactor, (30) * scaleFactor);
        gc.strokeLine((185 + 50) * scaleFactor, (60) * scaleFactor, (170 + 50) * scaleFactor, (50) * scaleFactor);
        gc.strokeLine((185 + 50) * scaleFactor, (60) * scaleFactor, (200 + 50) * scaleFactor, (80) * scaleFactor);
        gc.strokeLine((185 + 50) * scaleFactor, (60) * scaleFactor, (210 + 50) * scaleFactor, (70) * scaleFactor);
        gc.strokeLine((210 + 50) * scaleFactor, (115) * scaleFactor, (200 + 50) * scaleFactor, (80) * scaleFactor);
        gc.strokeLine((210 + 50) * scaleFactor, (150) * scaleFactor, (165 + 50) * scaleFactor, (190) * scaleFactor);
        gc.strokeLine((200 + 50) * scaleFactor, (80) * scaleFactor, (260 + 50) * scaleFactor, (120) * scaleFactor);
        gc.strokeLine((215 + 50) * scaleFactor, (70) * scaleFactor, (260 + 50) * scaleFactor, (120) * scaleFactor);
        gc.strokeLine((205 + 50) * scaleFactor, (80) * scaleFactor, (270 + 50) * scaleFactor, (105) * scaleFactor);
        gc.strokeLine((240 + 50) * scaleFactor, (140) * scaleFactor, (260 + 50) * scaleFactor, (170) * scaleFactor);
        gc.strokeLine((255 + 50) * scaleFactor, (190) * scaleFactor, (260 + 50) * scaleFactor, (180) * scaleFactor);
        gc.strokeLine((360 + 50) * scaleFactor, (30) * scaleFactor, (370 + 50) * scaleFactor, (90) * scaleFactor);
        gc.strokeLine((300 + 50) * scaleFactor, (60) * scaleFactor, (370 + 50) * scaleFactor, (90) * scaleFactor);
        gc.strokeLine((300 + 50) * scaleFactor, (90) * scaleFactor, (320 + 50) * scaleFactor, (160) * scaleFactor);
        gc.strokeLine((330 + 50) * scaleFactor, (90) * scaleFactor, (340 + 50) * scaleFactor, (160) * scaleFactor);
        gc.strokeLine((370 + 50) * scaleFactor, (170) * scaleFactor, (340 + 50) * scaleFactor, (160) * scaleFactor);
        gc.strokeLine((380 + 50) * scaleFactor, (180) * scaleFactor, (360 + 50) * scaleFactor, (200) * scaleFactor);
        gc.strokeLine((340 + 50) * scaleFactor, (160) * scaleFactor, (360 + 50) * scaleFactor, (200) * scaleFactor);
        gc.strokeLine((320 + 50) * scaleFactor, (170) * scaleFactor, (360 + 50) * scaleFactor, (200) * scaleFactor);
        gc.strokeLine((360 + 50) * scaleFactor, (55) * scaleFactor, 600 * scaleFactor, (55) * scaleFactor);

        gc.strokeLine((195 + 50) * scaleFactor, (60) * scaleFactor, (210 + 50) * scaleFactor, (45) * scaleFactor);

// America do Norte
// Alaska
        gc.setFill(colors.get(0));
        gc.fillRect((40 + 50) * scaleFactor, (40) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((40 + 50) * scaleFactor, (40) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(0)), (55 + 50) * scaleFactor, (55) * scaleFactor);

// Mackenzie
        gc.setFill(colors.get(1));
        gc.fillRect((70 + 50) * scaleFactor, (40) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((70 + 50) * scaleFactor, (40) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(1)), (85 + 50) * scaleFactor, (55) * scaleFactor);

// Vancouver
        gc.setFill(colors.get(2));
        gc.fillRect((55 + 50) * scaleFactor, (70) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((55 + 50) * scaleFactor, (70) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(2)), (70 + 50) * scaleFactor, (85) * scaleFactor);

// Ottawa
        gc.setFill(colors.get(3));
        gc.fillRect((85 + 50) * scaleFactor, (70) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((85 + 50) * scaleFactor, (70) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(3)), (100 + 50) * scaleFactor, (85) * scaleFactor);

// Labrador
        gc.setFill(colors.get(4));
        gc.fillRect((115 + 50) * scaleFactor, (70) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((115 + 50) * scaleFactor, (70) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(4)), (130 + 50) * scaleFactor, (85) * scaleFactor);

// Groelandia
        gc.setFill(colors.get(5));
        gc.fillRect((120 + 50) * scaleFactor, (20) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((120 + 50) * scaleFactor, (20) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(5)), (135 + 50) * scaleFactor, (35) * scaleFactor);

// California
        gc.setFill(colors.get(6));
        gc.fillRect((70 + 50) * scaleFactor, (100) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((70 + 50) * scaleFactor, (100) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(6)), (85 + 50) * scaleFactor, (115) * scaleFactor);

// Nova York
        gc.setFill(colors.get(7));
        gc.fillRect((100 + 50) * scaleFactor, (100) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((100 + 50) * scaleFactor, (100) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(7)), (115 + 50) * scaleFactor, (115) * scaleFactor);

// México
        gc.setFill(colors.get(8));
        gc.fillRect((90 + 50) * scaleFactor, (130) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((90 + 50) * scaleFactor, (130) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(8)), (105 + 50) * scaleFactor, (145) * scaleFactor);

// America do Sul
// Venezuela
        gc.setFill(colors.get(9));
        gc.fillRect((120 + 50) * scaleFactor, (160) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((120 + 50) * scaleFactor, (160) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(9)), (135 + 50) * scaleFactor, (175) * scaleFactor);

// Peru
        gc.setFill(colors.get(10));
        gc.fillRect((105 + 50) * scaleFactor, (190) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((105 + 50) * scaleFactor, (190) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(10)), (120 + 50) * scaleFactor, (205) * scaleFactor);

// Argentina
        gc.setFill(colors.get(11));
        gc.fillRect((105 + 50) * scaleFactor, (220) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((105 + 50) * scaleFactor, (220) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(11)), (120 + 50) * scaleFactor, (235) * scaleFactor);

// Brasil
        gc.setFill(colors.get(12));
        gc.fillRect((135 + 50) * scaleFactor, (190) * scaleFactor, 30 * scaleFactor, 50 * scaleFactor);
        gc.strokeRect((135 + 50) * scaleFactor, (190) * scaleFactor, 30 * scaleFactor, 50 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(12)), (150 + 50) * scaleFactor, (215) * scaleFactor);

// Europa
// Islandia
        gc.setFill(colors.get(13));
        gc.fillRect((170 + 50) * scaleFactor, (50) * scaleFactor, 10 * scaleFactor, 10 * scaleFactor);
        gc.strokeRect((170 + 50) * scaleFactor, (50) * scaleFactor, 10 * scaleFactor, 10 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(13)), (175 + 50) * scaleFactor, (60) * scaleFactor);


        // Suecia
        gc.setFill(colors.get(14));
        gc.fillRect((210 + 50) * scaleFactor, (30) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((210 + 50) * scaleFactor, (30) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(14)), (225 + 50) * scaleFactor, (45) * scaleFactor);

// Inglaterra
        gc.setFill(colors.get(15));
        gc.fillRect((185 + 50) * scaleFactor, (60) * scaleFactor, 15 * scaleFactor, 15 * scaleFactor);
        gc.strokeRect((185 + 50) * scaleFactor, (60) * scaleFactor, 15 * scaleFactor, 15 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(15)), (192.5 + 50) * scaleFactor, (75) * scaleFactor);

// Alemanha
        gc.setFill(colors.get(16));
        gc.fillRect((210 + 50) * scaleFactor, (60) * scaleFactor, 10 * scaleFactor, 20 * scaleFactor);
        gc.strokeRect((210 + 50) * scaleFactor, (60) * scaleFactor, 10 * scaleFactor, 20 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(16)), (215 + 50) * scaleFactor, (75) * scaleFactor);

// Espanha
        gc.setFill(colors.get(41));
        gc.fillRect((200 + 50) * scaleFactor, (80) * scaleFactor, 20 * scaleFactor, 10 * scaleFactor);
        gc.strokeRect((200 + 50) * scaleFactor, (80) * scaleFactor, 20 * scaleFactor, 10 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(41)), (210 + 50) * scaleFactor, (90) * scaleFactor);

// Polonia
        gc.setFill(colors.get(17));
        gc.fillRect((220 + 50) * scaleFactor, (60) * scaleFactor, 20 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((220 + 50) * scaleFactor, (60) * scaleFactor, 20 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(17)), (230 + 50) * scaleFactor, (75) * scaleFactor);

// Moscou
        gc.setFill(colors.get(18));
        gc.fillRect((240 + 50) * scaleFactor, (30) * scaleFactor, 30 * scaleFactor, 60 * scaleFactor);
        gc.strokeRect((240 + 50) * scaleFactor, (30) * scaleFactor, 30 * scaleFactor, 60 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(18)), (255 + 50) * scaleFactor, (60) * scaleFactor);

// Africa
// Argelia
        gc.setFill(colors.get(19));
        gc.fillRect((210 + 50) * scaleFactor, (115) * scaleFactor, 30 * scaleFactor, 45 * scaleFactor);
        gc.strokeRect((210 + 50) * scaleFactor, (115) * scaleFactor, 30 * scaleFactor, 45 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(19)), (225 + 50) * scaleFactor, (137) * scaleFactor);

// Egito
        gc.setFill(colors.get(20));
        gc.fillRect((240 + 50) * scaleFactor, (120) * scaleFactor, 30 * scaleFactor, 20 * scaleFactor);
        gc.strokeRect((240 + 50) * scaleFactor, (120) * scaleFactor, 30 * scaleFactor, 20 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(20)), (255 + 50) * scaleFactor, (130) * scaleFactor);

// Sudao
        gc.setFill(colors.get(21));
        gc.fillRect((240 + 50) * scaleFactor, (140) * scaleFactor, 30 * scaleFactor, 20 * scaleFactor);
        gc.strokeRect((240 + 50) * scaleFactor, (140) * scaleFactor, 30 * scaleFactor, 20 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(21)), (255 + 50) * scaleFactor, (150) * scaleFactor);

// Congo
        gc.setFill(colors.get(22));
        gc.fillRect((220 + 50) * scaleFactor, (160) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((220 + 50) * scaleFactor, (160) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(22)), (235 + 50) * scaleFactor, (175) * scaleFactor);


        // Africa do Sul
        gc.setFill(colors.get(23));
        gc.fillRect((230 + 50) * scaleFactor, (190) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((230 + 50) * scaleFactor, (190) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(23)), (245 + 50) * scaleFactor, (205) * scaleFactor);

// Madagascar
        gc.setFill(colors.get(24));
        gc.fillRect((260 + 50) * scaleFactor, (170) * scaleFactor, 10 * scaleFactor, 10 * scaleFactor);
        gc.strokeRect((260 + 50) * scaleFactor, (170) * scaleFactor, 10 * scaleFactor, 10 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(24)), (265 + 50) * scaleFactor, (178) * scaleFactor);

// Omsk
        gc.setFill(colors.get(25));
        gc.fillRect((270 + 50) * scaleFactor, (30) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((270 + 50) * scaleFactor, (30) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(25)), (285 + 50) * scaleFactor, (45) * scaleFactor);

// Arai
        gc.setFill(colors.get(26));
        gc.fillRect((270 + 50) * scaleFactor, (60) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((270 + 50) * scaleFactor, (60) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(26)), (285 + 50) * scaleFactor, (75) * scaleFactor);

// Oriente Medio
        gc.setFill(colors.get(27));
        gc.fillRect((270 + 50) * scaleFactor, (90) * scaleFactor, 40 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((270 + 50) * scaleFactor, (90) * scaleFactor, 40 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(27)), (290 + 50) * scaleFactor, (105) * scaleFactor);

// India
        gc.setFill(colors.get(28));
        gc.fillRect((300 + 50) * scaleFactor, (90) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((300 + 50) * scaleFactor, (90) * scaleFactor, 30 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(28)), (315 + 50) * scaleFactor, (105) * scaleFactor);

// Vietna
        gc.setFill(colors.get(29));
        gc.fillRect((330 + 50) * scaleFactor, (90) * scaleFactor, 15 * scaleFactor, 35 * scaleFactor);
        gc.strokeRect((330 + 50) * scaleFactor, (90) * scaleFactor, 15 * scaleFactor, 35 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(29)), (337.5 + 50) * scaleFactor, (112.5) * scaleFactor);

// China
        gc.setFill(colors.get(30));
        gc.fillRect((300 + 50) * scaleFactor, (60) * scaleFactor, 60 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((300 + 50) * scaleFactor, (60) * scaleFactor, 60 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(30)), (330 + 50) * scaleFactor, (75) * scaleFactor);

// Dudinka
        gc.setFill(colors.get(31));
        gc.fillRect((300 + 50) * scaleFactor, (20) * scaleFactor, 15 * scaleFactor, 40 * scaleFactor);
        gc.strokeRect((300 + 50) * scaleFactor, (20) * scaleFactor, 15 * scaleFactor, 40 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(31)), (307.5 + 50) * scaleFactor, (40) * scaleFactor);

// Mongolia
        gc.setFill(colors.get(32));
        gc.fillRect((315 + 50) * scaleFactor, (45) * scaleFactor, 45 * scaleFactor, 15 * scaleFactor);
        gc.strokeRect((315 + 50) * scaleFactor, (45) * scaleFactor, 45 * scaleFactor, 15 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(32)), (337.5 + 50) * scaleFactor, (52.5) * scaleFactor);

// Tachita
        gc.setFill(colors.get(33));
        gc.fillRect((315 + 50) * scaleFactor, (30) * scaleFactor, 45 * scaleFactor, 15 * scaleFactor);
        gc.strokeRect((315 + 50) * scaleFactor, (30) * scaleFactor, 45 * scaleFactor, 15 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(33)), (337.5 + 50) * scaleFactor, (37.5) * scaleFactor);

// Siberia
        gc.setFill(colors.get(34));
        gc.fillRect((315 + 50) * scaleFactor, (0) * scaleFactor, 45 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((315 + 50) * scaleFactor, (0) * scaleFactor, 45 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(34)), (337.5 + 50) * scaleFactor, (15) * scaleFactor);

// Vladivostok
        gc.setFill(colors.get(35));
        gc.fillRect((360 + 50) * scaleFactor, (30) * scaleFactor, 50 * scaleFactor, 50 * scaleFactor);
        gc.strokeRect((360 + 50) * scaleFactor, (30) * scaleFactor, 50 * scaleFactor, 50 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(35)), (385 + 50) * scaleFactor, (55) * scaleFactor);

// Japão
        gc.setFill(colors.get(36));
        gc.fillRect((370 + 50) * scaleFactor, (90) * scaleFactor, 10 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((370 + 50) * scaleFactor, (90) * scaleFactor, 10 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(36)), (375 + 50) * scaleFactor, (105) * scaleFactor);

// Oceania
// Sumatra
        gc.setFill(colors.get(37));
        gc.fillRect((320 + 50) * scaleFactor, (160) * scaleFactor, 10 * scaleFactor, 30 * scaleFactor);
        gc.strokeRect((320 + 50) * scaleFactor, (160) * scaleFactor, 10 * scaleFactor, 30 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(37)), (325 + 50) * scaleFactor, (175) * scaleFactor);


        // Borneu
        gc.setFill(colors.get(38));
        gc.fillRect((340 + 50) * scaleFactor, (160) * scaleFactor, 20 * scaleFactor, 20 * scaleFactor);
        gc.strokeRect((340 + 50) * scaleFactor, (160) * scaleFactor, 20 * scaleFactor, 20 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(38)), (350 + 50) * scaleFactor, (175) * scaleFactor);

// Nova Guine
        gc.setFill(colors.get(39));
        gc.fillRect((370 + 50) * scaleFactor, (170) * scaleFactor, 30 * scaleFactor, 10 * scaleFactor);
        gc.strokeRect((370 + 50) * scaleFactor, (170) * scaleFactor, 30 * scaleFactor, 10 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(39)), (385 + 50) * scaleFactor, (175) * scaleFactor);

// Australia
        gc.setFill(colors.get(40));
        gc.fillRect((340 + 50) * scaleFactor, (200) * scaleFactor, 40 * scaleFactor, 40 * scaleFactor);
        gc.strokeRect((340 + 50) * scaleFactor, (200) * scaleFactor, 40 * scaleFactor, 40 * scaleFactor);
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(armies.get(40)), (360 + 50) * scaleFactor, (220) * scaleFactor);

// America do Norte
// Alaska
        gc.fillText("Alaska", (52 + 50) * scaleFactor, (58) * scaleFactor);

// Mackenzie
        gc.fillText("Mackenzie", (82 + 50) * scaleFactor, (58) * scaleFactor);

// Vancouver
        gc.fillText("Vancouver", (67 + 50) * scaleFactor, (88) * scaleFactor);

// Ottawa
        gc.fillText("Ottawa", (97 + 50) * scaleFactor, (88) * scaleFactor);

// Labrador
        gc.fillText("Labrador", (127 + 50) * scaleFactor, (88) * scaleFactor);

// Groelandia
        gc.fillText("Groelandia", (132 + 50) * scaleFactor, (38) * scaleFactor);

// California
        gc.fillText("California", (82 + 50) * scaleFactor, (118) * scaleFactor);

// Nova York
        gc.fillText("Nova York", (112 + 50) * scaleFactor, (118) * scaleFactor);

// México
        gc.fillText("México", (102 + 50) * scaleFactor, (148) * scaleFactor);

// America do Sul
// Venezuela
        gc.fillText("Venezuela", (132 + 50) * scaleFactor, (178) * scaleFactor);

// Peru
        gc.fillText("Peru", (117 + 50) * scaleFactor, (208) * scaleFactor);

// Argentina
        gc.fillText("Argentina", (117 + 50) * scaleFactor, (238) * scaleFactor);

// Brasil
        gc.fillText("Brasil", (147 + 50) * scaleFactor, (218) * scaleFactor);

// Europa
// Islandia
        gc.fillText("Islandia", (166 + 50) * scaleFactor, (63) * scaleFactor);

// Suecia
        gc.fillText("Suecia", (222 + 50) * scaleFactor, (48) * scaleFactor);

// Inglaterra
        gc.fillText("Inglaterra", (179.5 + 50) * scaleFactor, (78) * scaleFactor);

// Alemanha
        gc.fillText("Alemanha", (208 + 50) * scaleFactor, (73) * scaleFactor);

// Espanha
        gc.fillText("Espanha", (207 + 50) * scaleFactor, (93) * scaleFactor);

// Polonia
        gc.fillText("Polonia", (227 + 50) * scaleFactor, (78) * scaleFactor);

// Moscou
        gc.fillText("Moscou", (252 + 50) * scaleFactor, (63) * scaleFactor);

// Africa
// Argelia
        gc.fillText("Argelia", (222 + 50) * scaleFactor, (142) * scaleFactor);

// Egito
        gc.fillText("Egito", (252 + 50) * scaleFactor, (135) * scaleFactor);

// Sudao
        gc.fillText("Sudao", (252 + 50) * scaleFactor, (155) * scaleFactor);

// Congo
        gc.fillText("Congo", (232 + 50) * scaleFactor, (180) * scaleFactor);

// Africa do Sul
        gc.fillText("Africa do Sul", (242 + 50) * scaleFactor, (210) * scaleFactor);

// Madagascar
        gc.fillText("Madagascar", (262 + 50) * scaleFactor, (183) * scaleFactor);

// Omsk
        gc.fillText("Omsk", (282 + 50) * scaleFactor, (48) * scaleFactor);

// Arai
        gc.fillText("Arai", (282 + 50) * scaleFactor, (78) * scaleFactor);

// Oriente Medio
        gc.fillText("Oriente Medio", (268 + 50) * scaleFactor, (108) * scaleFactor);

// India
        gc.fillText("India", (312 + 50) * scaleFactor, (108) * scaleFactor);

// Vietna
        gc.fillText("Vietna", (334.5 + 50) * scaleFactor, (115.5) * scaleFactor);

// China
        gc.fillText("China", (327 + 50) * scaleFactor, (78) * scaleFactor);

// Dudinka
        gc.fillText("Dudinka", (304.5 + 50) * scaleFactor, (43) * scaleFactor);

// Mongolia
        gc.fillText("Mongolia", (334.5 + 50) * scaleFactor, (55.5) * scaleFactor);

// Tachita
        gc.fillText("Tachita", (334.5 + 50) * scaleFactor, (40.5) * scaleFactor);

// Siberia
        gc.fillText("Siberia", (334.5 + 50) * scaleFactor, (18) * scaleFactor);

// Vladivostok
        gc.fillText("Vladivostok", (382 + 50) * scaleFactor, (58) * scaleFactor);

// Japão
        gc.fillText("Japão", (372 + 50) * scaleFactor, (108) * scaleFactor);

// Oceania
// Sumatra
        gc.fillText("Sumatra", (322 + 50) * scaleFactor, (178) * scaleFactor);

// Borneu
        gc.fillText("Borneu", (347 + 50) * scaleFactor, (178) * scaleFactor);

// Nova Guine
        gc.fillText("Nova Guine", (382 + 50) * scaleFactor, (178) * scaleFactor);

// Australia
        gc.fillText("Australia", (357 + 50) * scaleFactor, (223) * scaleFactor);


        // Adicione mais desenhos conforme necessário
    }

    private void drawTerritory() {
        while (!territories.isEmpty()) {
            String drawnTerritory = territories.remove(0);
            armies.set(stateStringToInt(drawnTerritory),1);
            switch (currentPlayer.getName()) {
                case "Player 1" -> colors.set(stateStringToInt(drawnTerritory), Color.RED);
                case "Player 2" -> colors.set(stateStringToInt(drawnTerritory), Color.YELLOW);
                case "Player 3" -> colors.set(stateStringToInt(drawnTerritory), Color.GREEN);
                case "Player 4" -> colors.set(stateStringToInt(drawnTerritory), Color.BLUE);
            }
            switchPlayer();
            drawMap(); // Atualiza o mapa após desenhar um novo território
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

    private int stateStringToInt(String stateName)
    {
        int i = -1;
        switch (stateName) {
            case "Alasca":
                i = 0;
                break;
            case "Mackenzie":
                i = 1;
                break;
            case "Vancouver":
                i = 2;
                break;
            case "Ottawa":
                i = 3;
                break;
            case "Labrador":
                i = 4;
                break;
            case "Groelandia":
                i = 5;
                break;
            case "California":
                i = 6;
                break;
            case "Nova York":
                i = 7;
                break;
            case "México":
                i = 8;
                break;
            case "Venezuela":
                i = 9;
                break;
            case "Peru":
                i = 10;
                break;
            case "Argentina":
                i = 11;
                break;
            case "Brasil":
                i = 12;
                break;
            case "Islandia":
                i = 13;
                break;
            case "Suecia":
                i = 14;
                break;
            case "Inglaterra":
                i = 15;
                break;
            case "Alemanha":
                i = 16;
                break;
            case "Polonia":
                i = 17;
                break;
            case "Moscou":
                i = 18;
                break;
            case "Argelia":
                i = 19;
                break;
            case "Egito":
                i = 20;
                break;
            case "Sudao":
                i = 21;
                break;
            case "Congo":
                i = 22;
                break;
            case "Africa do Sul":
                i = 23;
                break;
            case "Madagascar":
                i = 24;
                break;
            case "Omsk":
                i = 25;
                break;
            case "Arai":
                i = 26;
                break;
            case "Oriente Medio":
                i = 27;
                break;
            case "India":
                i = 28;
                break;
            case "Vietna":
                i = 29;
                break;
            case "China":
                i = 30;
                break;
            case "Dudinka":
                i = 31;
                break;
            case "Mongolia":
                i = 32;
                break;
            case "Tachita":
                i = 33;
                break;
            case "Siberia":
                i = 34;
                break;
            case "Vladivostok":
                i = 35;
                break;
            case "Japão":
                i = 36;
                break;
            case "Sumatra":
                i = 37;
                break;
            case "Borneu":
                i = 38;
                break;
            case "Nova Guine":
                i = 39;
                break;
            case "Australia":
                i = 40;
                break;
            case "Espanha":
                i = 41;
                break;
        }
        return i;
    }
    private ArrayList<String> neighbours(String stateName)
    {
        ArrayList<String> r = new ArrayList<>();

        switch (stateName) {
            case "Alasca":
                Collections.addAll(r,"Mackenzie","Vancouver","Vladivostok");
                break;
            case "Mackenzie":
                Collections.addAll(r,"Alasca","Vancouver","Ottawa","Groelandia");
                break;
            case "Vancouver":
                Collections.addAll(r,"Alasca","Mackenzie","Ottawa","California");
                break;
            case "Ottawa":
                Collections.addAll(r,"Vancouver","Mackenzie","Labrador","Nova York","California");
                break;
            case "Labrador":
                Collections.addAll(r,"Groelandia","Ottawa","Nova York");
                break;
            case "Groelandia":
                Collections.addAll(r,"Labrador","Mackenzie","Islandia");
                break;
            case "California":
                Collections.addAll(r,"Nova York","México","Vancouver","Ottawa");
                break;
            case "Nova York":
                Collections.addAll(r,"California","Ottawa","Labrador");
                break;
            case "México":
                Collections.addAll(r,"California","Nova York","Venezuela");
                break;
            case "Venezuela":
                Collections.addAll(r,"México","Peru","Brasil");
                break;
            case "Peru":
                Collections.addAll(r,"Venezuela","Brasil","Argentina");
                break;
            case "Argentina":
                Collections.addAll(r,"Peru","Brasil");
                break;
            case "Brasil":
                Collections.addAll(r,"Peru","Venezuela","Argentina","Argelia");
                break;
            case "Islandia":
                Collections.addAll(r,"Groelandia","Inglaterra");
                break;
            case "Suecia":
                Collections.addAll(r,"Inglaterra","Moscou");
                break;
            case "Inglaterra":
                Collections.addAll(r,"Suecia","Alemanha","Espanha","Islandia");
                break;
            case "Alemanha":
                Collections.addAll(r,"Espanha","Polonia","Inglaterra");
                break;
            case "Polonia":
                Collections.addAll(r,"Alemanha","Moscou","Oriente Medio","Egito","Espanha");
                break;
            case "Moscou":
                Collections.addAll(r,"Polonia","Oriente Medio","Arai","Omsk","Suecia");
                break;
            case "Argelia":
                Collections.addAll(r,"Egito","Brasil","Sudao","Congo");
                break;
            case "Egito":
                Collections.addAll(r,"Polonia","Argelia","Oriente Medio","Sudao","Espanha");
                break;
            case "Sudao":
                Collections.addAll(r, "Argelia","Egito","Congo","Madagascar","Africa do Sul");
                break;
            case "Congo":
                Collections.addAll(r,"Argelia","Sudao","Africa do Sul");
                break;
            case "Africa do Sul":
                Collections.addAll(r,"Sudao","Congo","Madagascar");
                break;
            case "Madagascar":
                Collections.addAll(r,"Sudao","Africa do Sul");
                break;
            case "Omsk":
                Collections.addAll(r,"Moscou","Arai","Dudinka","China","Mongolia");
                break;
            case "Arai":
                Collections.addAll(r,"Moscou","Omsk","India","Oriente Medio","China");
                break;
            case "Oriente Medio":
                Collections.addAll(r,"Moscou","Egito","Arai","India","Polonia");
                break;
            case "India":
                Collections.addAll(r,"Arai","Oriente Medio","China","Vietna","Sumatra");
                break;
            case "Vietna":
                Collections.addAll(r,"India","China","Borneu");
                break;
            case "China":
                Collections.addAll(r,"Omsk","Arai","India","Vietna","Mongolia","Tachita","Vladivostok","Japão");
                break;
            case "Dudinka":
                Collections.addAll(r,"Omsk","Mongolia","Tachita","Siberia");
                break;
            case "Mongolia":
                Collections.addAll(r,"Omsk","China","Dudinka","Tachita");
                break;
            case "Tachita":
                Collections.addAll(r,"China","Dudinka","Mongolia","Siberia","Vladivostok");
                break;
            case "Siberia":
                Collections.addAll(r,"Dudinka","Tachita","Vladivostok");
                break;
            case "Vladivostok":
                Collections.addAll(r,"China","Tachita","Siberia","Japão","Alasca");
                break;
            case "Japão":
                Collections.addAll(r,"China","Vladivostok");
                break;
            case "Sumatra":
                Collections.addAll(r,"India","Australia");
                break;
            case "Borneu":
                Collections.addAll(r,"Vietna","Australia","Nova Guine");
                break;
            case "Nova Guine":
                Collections.addAll(r,"Borneu","Australia");
                break;
            case "Australia":
                Collections.addAll(r,"Sumatra","Borneu","Nova Guine");
                break;
            case "Espanha":
                Collections.addAll(r,"Inglaterra","Alemanha","Argelia","Polonia","Egito");
                break;
        }
        return r;
    }
    private void addArmiesToTerritory(String territory, int number)
    {
        if(currentPlayer.getColor() == colors.get(stateStringToInt(territory)))
        {
            if(currentPlayer.getArmies() >= number) {
                armies.set(stateStringToInt(territory), armies.get(stateStringToInt(territory)) + number);
                currentPlayer.addArmies(-number);
            }
            else {
                System.out.println("Not enough armies");
            }
        }
        else
        {
            System.out.println("Territory not in your possession");
        }

    }

    private void attackTerritory(String attacker, String defender)
    {
        if (colors.get(stateStringToInt(attacker)) == currentPlayer.getColor() && colors.get(stateStringToInt(defender)) != currentPlayer.getColor()
        && neighbours(attacker).contains(defender)) {
            Random random = new Random();
            ArrayList<Integer> attackDice = new ArrayList<>();
            ArrayList<Integer> defenseDice = new ArrayList<>();
            int defenseDiceN = 0;
            int attackDiceN = 0;

            if (armies.get(stateStringToInt(attacker))-1 > 0) {
                attackDiceN = 1;
                attackDice.add(random.nextInt(20) + 1);
                if (armies.get(stateStringToInt(attacker))-1 >= 2) {
                    attackDiceN = 2;
                    attackDice.add(random.nextInt(20) + 1);
                    if (armies.get(stateStringToInt(attacker))-1 >= 3) {
                        attackDiceN = 3;
                        attackDice.add(random.nextInt(20) + 1);
                    }
                }
            }
            else
            {
                System.out.println("Invalid attack");
                return;
            }

            if (armies.get(stateStringToInt(defender)) > 0) {
                defenseDiceN = 1;
                defenseDice.add(random.nextInt(20) + 1);
                if (armies.get(stateStringToInt(defender)) >= 2) {
                    defenseDiceN = 2;
                    defenseDice.add(random.nextInt(20) + 1);
                    if (armies.get(stateStringToInt(defender)) >= 3) {
                        defenseDiceN = 3;
                        defenseDice.add(random.nextInt(20) + 1);
                    }
                }
            }


            attackDice.sort(Collections.reverseOrder());
            defenseDice.sort(Collections.reverseOrder());

            System.out.println("Attacker rolls: " + attackDice);
            System.out.println("Defender rolls: " + defenseDice);


            if (attackDice.get(0) <= defenseDice.get(0))
                armies.set(stateStringToInt(attacker), armies.get(stateStringToInt(attacker)) - 1);
            else
                armies.set(stateStringToInt(defender), armies.get(stateStringToInt(defender)) - 1);

            if (defenseDiceN >= 2 && attackDiceN >= 2) {
                if (attackDice.get(1) <= defenseDice.get(1))
                    armies.set(stateStringToInt(attacker), armies.get(stateStringToInt(attacker)) - 1);
                else
                    armies.set(stateStringToInt(defender), armies.get(stateStringToInt(defender)) - 1);
            }

            if (defenseDiceN >= 3 && attackDiceN >= 3) {

                if (attackDice.get(2) <= defenseDice.get(2))
                    armies.set(stateStringToInt(attacker), armies.get(stateStringToInt(attacker)) - 1);
                else
                    armies.set(stateStringToInt(defender), armies.get(stateStringToInt(defender)) - 1);
            }

            if(armies.get(stateStringToInt(defender)) == 0)
            {
                colors.set(stateStringToInt(defender), currentPlayer.getColor());
                armies.set(stateStringToInt(defender), attackDiceN);
                armies.set(stateStringToInt(attacker), armies.get(stateStringToInt(attacker)) - attackDiceN);
            }

        }
        else{
            System.out.println("Invalid attack");
        }
    }

}

class Player {
    private String name;
    private int armies;

    private Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getArmies() {
        return armies;
    }

    public void addArmies(int numArmies) {
        armies += numArmies;
    }
}

