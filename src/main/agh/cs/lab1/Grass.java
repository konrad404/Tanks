package agh.cs.lab1;

public class Grass {
    Vector2d position;
    public Grass(Vector2d place){
        position = place;
    }
    public Vector2d getPosition(){
        return position;
    }
    public String toString(){
        return "*";
    }
}
