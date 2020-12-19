package agh.cs.projekt;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    private int x, y;
    private Rectangle border;
    private Text text = new Text();
    private Visualizer visualizer;

    public Tile(int x, int y, float tileSizeX, float  tileSizeY, Visualizer visualizer){
        this.visualizer = visualizer;
        this.x=x;
        this.y=y;
        this.border = new Rectangle(tileSizeX-2,tileSizeY-2);
        border.setStroke(Color.LIGHTGRAY);
        border.setFill(Color.LIGHTGRAY);

        getChildren().add(border);

        setTranslateX(x* tileSizeX);
        setTranslateY(y*tileSizeY);

        setOnMouseClicked(event -> target());
    }

    public void target(){
        Vector2d position = new Vector2d(x,y);
        visualizer.target(position);
//        visualizer.engine.target(position);
    }

    public void changeColor(Color color){
        border.setFill(color);
    }
}
