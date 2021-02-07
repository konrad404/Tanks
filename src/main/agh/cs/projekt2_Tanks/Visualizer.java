package agh.cs.projekt2_Tanks;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Visualizer {

    private final float tileSizeX;
    private final float tileSizeY;
    private final int w;
    private final int h;
    private final int xTiles;
    private final int yTiles;
    private final SimulationEngine engine;
    private final Tile[][] grid;
    private Pane root;
    private final Canvas canvas;
    private final Battliefield battliefield;
    private final ShellSimulator shellSimulator;
    private Text playerHealth = new Text("");
    private Text points = new Text("points: "+0);
    private final Stage stage;


    public Visualizer(int width, int height, int xTiles, int yTiles, agh.cs.projekt2_Tanks.SimulationEngine engine, Stage stage){
        this.stage = stage;
        this.w = width;
        this.h = height;
        this.xTiles = xTiles;
//       dodatkowy wiersz na ulepszenia i statystyki:
        this.yTiles = yTiles+1;
        this.tileSizeX = (float) w/this.xTiles;
        this.tileSizeY = (float) h/this.yTiles;
        this.grid = new Tile[this.xTiles][this.yTiles];
        this.engine = engine;
        canvas = new Canvas(w,h);
        this.battliefield = engine.getBattliefield();
        this.shellSimulator = engine.getShellSimulator();
        for(int y =0; y< this.yTiles;y++){
            for(int x =0; x< this.xTiles; x++){
                agh.cs.projekt2_Tanks.Tile tile = new agh.cs.projekt2_Tanks.Tile(x,y,tileSizeX,tileSizeY,canvas,w,h);
                grid[x][y]= tile;
            }
        }
        for(PowerUpsName powerUp : PowerUpsName.values()){
            if(powerUp != PowerUpsName.BonusHealth){
                int i = getNumberOfUpgrade(powerUp);
                grid[i][this.yTiles-1].setText((i - 1) + "\n  " + powerUp.toString());
            }
        }


    }

    public Parent createContent(){
        root = new Pane();
        root.setPrefSize(w,h);
        root.getChildren().add(canvas);

        root.addEventFilter(KeyEvent.KEY_PRESSED, (key)->{
            switch (key.getCode()){
                case W -> engine.movePlayer();
                case A -> engine.turnPlayer(Turn.Left);
                case D -> engine.turnPlayer(Turn.Right);
                case SPACE -> engine.shoot();
                case DIGIT1 -> useUpgrade(PowerUpsName.DoubleMove);
                case DIGIT2 -> useUpgrade(PowerUpsName.DoubleSpeedShell);
                case DIGIT3 -> useUpgrade(PowerUpsName.ArmourPiercingShell);
                case DIGIT4 -> useUpgrade(PowerUpsName.BouncingShell);
                case DIGIT5 -> useUpgrade(PowerUpsName.Immortality);
            }
        });

        Button workButton = new Button("");
        workButton.setTranslateX(w+40);
        workButton.setTranslateY(40);
        root.getChildren().add(workButton);

        playerHealth.setTranslateX(5);
        playerHealth.setTranslateY(20);
        root.getChildren().add(playerHealth);

        points.setTranslateX(w-70);
        points.setTranslateY(20);
        root.getChildren().add(points);

        return root;
    }

    public void placeObject(Vector2d positionToChange, boolean isPlayer){
        String path;
        int rotation;
        String text;
        AbstractObject abstractObject = battliefield.abstractObjectAt(positionToChange);
        PowerUp powerUp = battliefield.powerUpAt(positionToChange);
        if (abstractObject instanceof Tank){
            rotation = ((Tank) abstractObject).getDirection();
            if (isPlayer){
                path = "/agh/cs/photos/player.png";
                grid[positionToChange.x][positionToChange.y].setPhoto(path,rotation);
                playerHealth.setText("Lives: " + ((Tank) abstractObject).getHealth());
            }
            else {
                path = "/agh/cs/photos/enemyTank.png";
                grid[positionToChange.x][positionToChange.y].setPhoto(path, rotation);
            }
        }
        else if(abstractObject instanceof Obstacle){
            path = "/agh/cs/photos/obstacle.jpg";
            grid[positionToChange.x][positionToChange.y].setPhoto(path, 0);
        }
        else if(powerUp != null){
            text = powerUp.toString();
            grid[positionToChange.x][positionToChange.y].setText(text);
        }
        else if(shellSimulator.isShellAtPlace(positionToChange)){
            Shell shell = shellSimulator.shellAtPlace(positionToChange);
            if (shell.isArmourPiercing())
                path = "/agh/cs/photos/armourPiercingBullet.png";
            else if (shell.isBouncing())
                path = "/agh/cs/photos/bouncingBullet.png";
            else
                path = "/agh/cs/photos/classicBullet.png";
            rotation = (shell.getDirection()+2)%8;
            grid[positionToChange.x][positionToChange.y].setPhoto(path, rotation);
        }
        else{
            grid[positionToChange.x][positionToChange.y].clear();
        }

    }

    private int getNumberOfUpgrade(PowerUpsName powerUpName){
        int i=2;
        for(PowerUpsName powerUp : PowerUpsName.values()){
            if(powerUp == powerUpName) return i;
            i++;
        }
        return 0;
    }

    public void pickUpgrade(PowerUp upgrade){
        int number = getNumberOfUpgrade(upgrade.getMode());
        grid[number][yTiles-1].setColour(Color.GREEN);
    }

    public void useUpgrade(PowerUpsName upgradeMode){
        if(engine.usePowerUp(upgradeMode)){
            int number = getNumberOfUpgrade(upgradeMode);
            grid[number][yTiles-1].setColour(Color.LIGHTSALMON);
        }
    }

    public void endUpgradeDuration(PowerUpsName upgradeMode){
        int number = getNumberOfUpgrade(upgradeMode);
        grid[number][yTiles-1].setColour(Color.WHITE);
    }

    public void setPoints(int points){
        this.points.setText("points: " + points);
    }

    public void endGame(int points){
        Alert end = new Alert(Alert.AlertType.INFORMATION);
        end.setTitle("Koniec gry");
        end.setHeaderText("Twój wynik to: " + points);
        end.showAndWait();
        stage.close();
    }

}
