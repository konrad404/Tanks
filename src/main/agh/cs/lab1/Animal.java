package agh.cs.lab1;

import java.util.ArrayList;
import java.util.List;

public class Animal {
    private AbstractWorldMap map;
    private Vector2d position;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    public Animal(AbstractWorldMap map) {
        this.map = map;
        position = new Vector2d(2,2);
    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition){
        this.map=map;
        position = initialPosition;
    }
    private MapDirection orientation = MapDirection.NORTH;

    public Vector2d getPosition(){
        return position;
    }

    public String toString(){
        return orientation.toString();
    }
    public void move(MoveDirection direction){
        switch(direction){
            case LEFT: {
                orientation = orientation.previous();
                break;
            }
            case RIGHT: {
                orientation = orientation.next();
                break;
            }
            case FORWARD: {
                Vector2d newposition = position.add(orientation.toUnitVector());
                if (map.canMoveTo(newposition)) {
                    map.positionChanged(position,newposition);
                    position = newposition;
                }
                break;
            }
            case BACKWARD: {
                Vector2d newposition = position.subtract(orientation.toUnitVector());
                if (map.canMoveTo(newposition))
                    map.positionChanged(position,newposition);
                position = newposition;
                break;
            }
            default: break;
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    void removeObserver(IPositionChangeObserver observer){
        for (int i =0;i< observers.size();i++){
            if (observers.get(i).equals(observer)){
                observers.remove(i);
                break;
            }
        }
    }


}
