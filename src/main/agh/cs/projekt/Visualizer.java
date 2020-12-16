package agh.cs.projekt;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;


public class Visualizer {

    private final float tileSizeX;
    private final float tileSizeY;
    private final int w;
    private final int h;

    private final int xTiles;
    private final int yTiles;


    public Tile[][] grid;

    public Visualizer(int width, int height, int xTiles, int yTiles){
        this.w = width;
        this.h = height;
        this.xTiles = xTiles;
        this.yTiles = yTiles;
        this.tileSizeX = (float) w/xTiles;
        this.tileSizeY = (float) h/yTiles;
        this.grid = new Tile[xTiles][yTiles];
    }

    public Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(w,h);
//        System.out.println("yTiles: " + yTiles);
//        System.out.println("xTiles: " + xTiles);
        for(int y =0; y< yTiles;y++){
            for(int x =0; x< xTiles; x++){
//                System.out.println("x: " + x + " y " +y);
                Tile tile = new Tile(x,y,tileSizeX,tileSizeY);
                grid[x][y]= tile;
                root.getChildren().add(tile);
            }
        }
        return root;
    }



    public void changeColor(int x, int y, Color color){
        grid[x][y].changeColor(color);
    }


//    public show()


}
