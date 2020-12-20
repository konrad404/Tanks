package agh.cs.projekt;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Optional;


public class Visualizer {

    private final float tileSizeX;
    private final float tileSizeY;
    private final int w;
    private final int h;

    private final int xTiles;
    private final int yTiles;
    private final Text statistics;
    public boolean paused;
    public boolean onGoing;
    private final SimulationEngine engine;
    private final Tile[][] grid;
    private final Text targeted;
    private Vector2d targetPosition;
    private Button startTracking;
    private Pane root;
    private Button pauseButton;


    public Visualizer(int width, int height, int xTiles, int yTiles, SimulationEngine engine){
        this.w = width;
        this.h = height;
        this.xTiles = xTiles;
        this.yTiles = yTiles;
        this.tileSizeX = (float) w/xTiles;
        this.tileSizeY = (float) h/yTiles;
        this.grid = new Tile[xTiles][yTiles];
        this.statistics = new Text("");
        this.targeted = new Text("");
        this.paused = false;
        this.engine = engine;
        this.onGoing = true;
    }

    public Parent createContent(){
        root = new Pane();
        root.setPrefSize(w,h);
        for(int y =0; y< yTiles;y++){
            for(int x =0; x< xTiles; x++){
                Tile tile = new Tile(x,y,tileSizeX,tileSizeY, this);
                grid[x][y]= tile;
                root.getChildren().add(tile);
            }
        }

        this.pauseButton = new Button("Pause/Continue");
        this.pauseButton.setTranslateX(w+40);
        this.pauseButton.setTranslateY(40);
        this.pauseButton.setOnAction(event -> changePause());
        root.getChildren().add(pauseButton);

        this.statistics.setTranslateX(w+40);
        this.statistics.setTranslateY(80);
        root.getChildren().add(this.statistics);

        this.targeted.setTranslateX(w+40);
        this.targeted.setTranslateY(220);
        root.getChildren().add(this.targeted);

        this.startTracking = new Button("TRACK");
        this.startTracking.setTranslateX(w+40);
        this.startTracking.setTranslateY(260);
        this.startTracking.setOnAction(event -> track());
        this.startTracking.setVisible(false);
        root.getChildren().add(this.startTracking);

        Button mainGenomes = new Button("Main Genome");
        mainGenomes.setTranslateX(w+ 180);
        mainGenomes.setTranslateY(40);
        mainGenomes.setOnAction(event -> engine.targetMainGenome());
        root.getChildren().add(mainGenomes);

        Button stopAndShow = new Button("      STOP &\nShow Statisticks");
        stopAndShow.setTranslateX(w+ 300);
        stopAndShow.setTranslateY(40);
        stopAndShow.setOnAction(event -> stopAndGiveStatistics());
        root.getChildren().add(stopAndShow);

        return root;
    }

    private void changePause(){
        if (!this.paused) {
            this.paused = true;
        }
        else {
            this.paused = false;
            this.startTracking.setVisible(false);
            this.targeted.setVisible(false);
        }
    }

    public void stopAndGiveStatistics(){
        this.engine.endOfSymulation();
        onGoing = false;
    }

    public void changeColor(int x, int y, Color color){
        grid[x][y].changeColor(color);
    }

    public void target(Vector2d position){
        if(engine.map.objectAt(position) instanceof Animal) {
            this.targeted.setText("pozycja zwierzęcia: " + position.toString());
            this.targeted.setVisible(true);
            this.targetPosition = position;
            this.startTracking.setVisible(true);
        }
    }

    public void track(){
        final int[] agesToTrack = {-1};
        TextInputDialog question = new TextInputDialog();
        question.setHeaderText("Please enter the number of ages");
        Optional<String> res = question.showAndWait();
        res.ifPresent(age -> agesToTrack[0] = Integer.parseInt(age));
        engine.target(targetPosition, agesToTrack[0]);

    }

    public void addStatistics(String statistics, boolean summarize){
        this.statistics.setText(statistics);
        if(summarize) changePause();
    }



}
