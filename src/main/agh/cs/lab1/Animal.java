package agh.cs.lab1;

public class Animal {
    private final IWorldMap map;
    private Vector2d position;
    public Animal(IWorldMap map1) {
        map = map1;
        position = new Vector2d(2,2);
    }
    public Animal(IWorldMap map1, Vector2d initialPosition){
        map = map1;
        position = initialPosition;
    }
    private MapDirection orientation = MapDirection.NORTH;

    public Vector2d getPosition(){
        return position;
    }

    public String toString(){
        String answer = (orientation.toString());
        return answer;
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
                Vector2d move = position.add(orientation.toUnitVector());
                if (map.canMoveTo(move))
                    position = move;
                break;
            }
            case BACKWARD: {
                Vector2d move = position.substract(orientation.toUnitVector());
                if (map.canMoveTo(move))
                    position = move;
                break;
            }
            default: break;
        }
    }



}
