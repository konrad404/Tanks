package agh.cs.projekt2_Tanks;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;

public class Tile extends StackPane {
    private final int x, y;
    private final float tileSizeX, tileSizeY;
    private final GraphicsContext border;
    private Image image;
    private final int w,h;
    private String text ="";

    public Tile(int x, int y, float tileSizeX, float  tileSizeY, Canvas canvas,int w,int h){
        this.x=x;
        this.y=y+1;
        this.tileSizeX =tileSizeX;
        this.tileSizeY = tileSizeY;
        this.border = canvas.getGraphicsContext2D();
        this.w = w;
        this.h = h;
        border.setStroke(Color.LIGHTGRAY);
        border.setFill(Color.WHITE);
    }


    public void setPhoto(String path, int rotation){
        image = new Image(path);
        ImageView iv = new ImageView(image);
        iv.setRotate(180 + (45*rotation));
        image = iv.snapshot(null,null);
        border.drawImage(image, x*tileSizeX,h-y*tileSizeY,tileSizeX,tileSizeY);
        setSquere();
    }

    public void setText(String text){
        this.text = text;
        clear();
        border.setFill(Color.DARKBLUE);
        border.fillText(this.text,x*tileSizeX+5,h-y*tileSizeY+15);
        border.setFill(Color.WHITE);
    }

    public void setColour(Color colour){
        border.setFill(colour);
        border.fillRect(x*tileSizeX,h-y*tileSizeY,tileSizeX,tileSizeY);
        setText(this.text);
    }

    public void setSquere(){
        border.strokeRect(x*tileSizeX,h-y*tileSizeY,tileSizeX,tileSizeY);
    }

    public void clear(){
        border.clearRect(x*tileSizeX,h-y*tileSizeY,tileSizeX,tileSizeY);
        border.fillRect(x*tileSizeX,h-y*tileSizeY,tileSizeX,tileSizeY);
        setSquere();
    }
}
