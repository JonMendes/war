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
    private ArrayList<Color> colors;
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

        canvas = new Canvas(600, 300);
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
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeLine(100+50, 40, 120+50, 30);
        gc.strokeLine(130+50, 70, 130+50, 50);
        gc.strokeLine(40+50, 55, 0, 55);
        gc.strokeLine(170+50, 50, 130+50, 30);
        gc.strokeLine(185+50, 60, 170+50, 50);
        gc.strokeLine(185+50, 60, 200+50, 80);
        gc.strokeLine(185+50, 60, 210+50, 70);
        gc.strokeLine(210+50, 115, 200+50, 80);
        gc.strokeLine(210+50, 150, 165+50, 190);
        gc.strokeLine(200+50, 80, 260+50, 120);
        gc.strokeLine(215+50, 70, 260+50, 120);
        gc.strokeLine(205+50, 80, 270+50, 105);
        gc.strokeLine(240+50, 140, 260+50, 170);
        gc.strokeLine(255+50, 190, 260+50, 180);
        gc.strokeLine(360+50, 30, 370+50, 90);
        gc.strokeLine(300+50, 60, 370+50, 90);
        gc.strokeLine(300+50, 90, 320+50, 160);
        gc.strokeLine(330+50, 90, 340+50, 160);
        gc.strokeLine(370+50, 170, 340+50, 160);
        gc.strokeLine(380+50, 180, 360+50, 200);
        gc.strokeLine(340+50, 160, 360+50, 200);
        gc.strokeLine(320+50, 170, 360+50, 200);
        gc.strokeLine(360+50, 55, 600, 55);

        gc.strokeLine(195+50, 60, 210+50, 45);


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
        gc.fillRect(185+50, 60, 15, 15);
        gc.strokeRect(185+50, 60, 15, 15);
        //Alemanha
        gc.setFill(colors.get(16));
        gc.fillRect(210+50, 60, 10, 20);
        gc.strokeRect(210+50, 60, 10, 20);
        //Espanha
        gc.setFill(colors.get(41));
        gc.fillRect(200+50, 80, 20, 10);
        gc.strokeRect(200+50, 80, 20, 10);
        //Polonia
        gc.setFill(colors.get(17));
        gc.fillRect(220+50, 60, 20, 30);
        gc.strokeRect(220+50, 60, 20, 30);
        //Moscou
        gc.setFill(colors.get(18));
        gc.fillRect(240+50, 30, 30, 60);
        gc.strokeRect(240+50, 30, 30, 60);

        //Africa
        //Argelia
        gc.setFill(colors.get(19));
        gc.fillRect(210+50, 115, 30, 45);
        gc.strokeRect(210+50, 115, 30, 45);
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
            colors.set(stateStringToInt(drawnTerritory), Color.RED);
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
                i = 40;
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
