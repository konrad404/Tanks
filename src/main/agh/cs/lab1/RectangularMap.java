package agh.cs.lab1;


import java.util.*;

public class RectangularMap extends AbstractWorldMap implements IWorldMap {
    protected Vector2d right_up_corner;
    protected Vector2d left_down_corner = new Vector2d(0,0);
    public RectangularMap(int width, int height) {
        right_up_corner = new Vector2d(width-1, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!position.precedes(right_up_corner)) {
            return false;
        }
        if (!position.follows(left_down_corner)) {
            return false;
        }
            if (isOccupied(position)) {
                return false;
            }
        return true;
    }
    @Override
    public Object objectAt(Vector2d position) {
        if (animalsMap.containsKey(position)) {
                return animalsMap.get(position);
        }
        return null;
    }

    @Override
    protected Vector2d lowerLeft() {
        return left_down_corner;
    }

    @Override
    protected Vector2d upperRight() {
        return right_up_corner;
    }

}
