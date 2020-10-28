package agh.cs.lab1;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RectangularMap implements IWorldMap {
    int width;
    int height;
    public RectangularMap(int x, int y) {
        width = x;
        height = y;
    }
    public List<Animal> animals = new ArrayList<Animal>();
    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!position.precedes(new Vector2d(width, height))) return false;
        if (!position.follows(new Vector2d(0, 0))) return false;
        for(int i =0; i< animals.size(); i++){
            if (position.equals(animals.get(i).getPosition())) return false;
        }
        return true;
    }

    @Override
    public boolean place(Animal animal) {
        if (!animal.getPosition().precedes(new Vector2d(width, height))) return false;
        if (!animal.getPosition().follows(new Vector2d(0, 0))) return false;
        for(int i =0; i< animals.size(); i++){
            if (animal.getPosition().equals(animals.get(i).getPosition())) return false;
        }
        animals.add(animal);
        return true;
    }

    @Override
    public void run(MoveDirection[] directions) {
        if (animals.size() != 0)
        for( int i=0 ; i< directions.length; i++){
            animals.get(i%(animals.size())).move(directions[i]);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for(int i =0; i< animals.size(); i++){
            if (position.equals(animals.get(i).getPosition())) return true;
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(int i =0; i< animals.size(); i++){
            if (position.equals(animals.get(i).getPosition())) return animals.get(i);
        }
        return null;
    }
    public String toString(){
        MapVisualizer obraz = new MapVisualizer(this);
        String mapa = obraz.draw(new Vector2d(0,0), new Vector2d(width, height));
        return mapa;
    }
}
