package agh.cs.projekt;

import java.util.HashMap;
import java.util.Map;


abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    private final MapVisualizer image = new MapVisualizer(this);
    abstract protected Vector2d lowerLeft();
    abstract protected Vector2d upperRight();
    protected final Map<Vector2d, Animal> animalsMap = new HashMap<>();

    @Override
    abstract public boolean canMoveTo(Vector2d position);
    @Override
    public boolean place(Animal animal){
        if (canMoveTo(animal.getPosition())){
            animalsMap.put(animal.getPosition(), animal);
            return true;
        }
        else throw new IllegalArgumentException(animal.getPosition().toString() + " place is occupied");
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        if (objectAt(position) != null) return true;
        else return false;
    }
    @Override
    abstract public Object objectAt(Vector2d position);

    public String toString(){
        String mapa = image.draw(lowerLeft(), upperRight());
        return mapa;
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        animalsMap.put(newPosition, animalsMap.remove(oldPosition));
    }
}
