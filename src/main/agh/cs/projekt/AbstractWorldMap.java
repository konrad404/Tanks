package agh.cs.projekt;

import java.util.HashMap;
import java.util.Map;


abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    private final MapVisualizer image = new MapVisualizer(this);
    public int mapHeight;
    public int mapWidth;
    protected final Map<Vector2d, Animal> animalsMap = new HashMap<>();

    @Override
    abstract public boolean canMoveTo(Vector2d position);

    @Override
    public void place(Animal animal){};

    @Override
    public boolean isOccupied(Vector2d position) {
        if (objectAt(position) != null) return true;
        else return false;
    }
    @Override
    abstract public Object objectAt(Vector2d position);

    abstract public String toString();
    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition){
        animalsMap.put(newPosition, animalsMap.remove(oldPosition));
    }
}
