package agh.cs.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


abstract class AbstractWorldMap implements IWorldMap {

    abstract protected List<Animal> getanimals();

    abstract protected Vector2d lowwerLeft();
    abstract protected Vector2d upperRight();
    @Override
    abstract public boolean canMoveTo(Vector2d position);
    @Override
    abstract public boolean place(Animal animal);
    @Override
    public boolean isOccupied(Vector2d position) {
        if (objectAt(position) != null) return true;
        else return false;
    }
    @Override
    abstract public Object objectAt(Vector2d position);

    @Override
    public void run(MoveDirection[] directions) {
        if (getanimals().size() != 0)
            for( int i=0 ; i< directions.length; i++){
                getanimals().get(i%(getanimals().size())).move(directions[i]);
            }
    }

    public String toString(){
        MapVisualizer obraz = new MapVisualizer(this);
        String mapa = obraz.draw(lowwerLeft(), upperRight());
        return mapa;
    }
}
