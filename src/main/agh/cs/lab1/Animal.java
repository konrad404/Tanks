package agh.cs.lab1;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);

    public String toString(){
        String answer = (position.toString() + " " + orientation.toString());
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
                Vector2d tmp = position.add(orientation.toUnitVector());
                if (tmp.x >= 0 && tmp.x<=4 && tmp.y>=0 && tmp.y <=4) position = tmp;
                break;
            }
            case BACKWARD: {
                Vector2d tmp = position.substract(orientation.toUnitVector());
                if (tmp.x >= 0 && tmp.x<=4 && tmp.y>=0 && tmp.y <=4)
                    position = tmp;
                break;
            }
            default: break;
        }
    }



}
