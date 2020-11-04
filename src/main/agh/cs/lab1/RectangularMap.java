package agh.cs.lab1;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RectangularMap extends AbstractWorldMap implements IWorldMap {
    int width;
    int height;
    public RectangularMap(int x, int y) {
        width = x;
        height = y;
    }
    public List<Animal> animals = new ArrayList<>();
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
        if(canMoveTo(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        else return false;
    }


    @Override
    public Object objectAt(Vector2d position) {
        for(int i =0; i< animals.size(); i++){
            if (position.equals(animals.get(i).getPosition())) return animals.get(i);
        }
        return null;
    }

    @Override
    protected Vector2d lowwerLeft() {
        return new Vector2d(0,0);
    }

    @Override
    protected Vector2d upperRight() {
        return new Vector2d(width,height);
    }
    @Override
    protected List<Animal> getanimals(){
        return animals;
    }

}
