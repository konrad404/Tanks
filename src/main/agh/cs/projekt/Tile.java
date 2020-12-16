package agh.cs.projekt;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    private int x, y;
    private Rectangle border;
    private Text text = new Text();

    public Tile(int x, int y, float tileSizeX, float  tileSizeY){
        this.x=x;
        this.y=y;
        this.border = new Rectangle(tileSizeX-2,tileSizeY-2);
        border.setStroke(Color.LIGHTGRAY);
        border.setFill(Color.LIGHTGRAY);

        getChildren().add(border);

        setTranslateX(x* tileSizeX);
        setTranslateY(y*tileSizeY);


    }
    public void changeColor(Color color){
//            System.out.println("from color: "+ border.getFill());
        border.setFill(color);
//            System.out.println("to color: "+ border.getFill());
    }
}
