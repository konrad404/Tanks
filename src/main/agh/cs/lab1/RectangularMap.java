package agh.cs.lab1;


import java.util.*;

public class RectangularMap extends AbstractWorldMap implements IWorldMap {
    int width;
    int height;
    protected Vector2d right_up_corner;
    protected Vector2d left_down_corner = new Vector2d(0,0);
    public RectangularMap(int w, int h) {
        width = w;
        height = h;
        right_up_corner = new Vector2d(width, height);
    }

    public List<Animal> animals = new ArrayList<>();
    public HashMap<Vector2d, Animal> animals2 = new HashMap<>();
    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!position.precedes(right_up_corner)) {
            return false;
        }
        if (!position.follows(left_down_corner)) {
            return false;
        }
        for(int i =0; i< animals.size(); i++){
            if (position.equals(animals.get(i).getPosition())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())) {
            animals.add(animal);
            animals2.put(animal.getPosition(), animal);
            return true;
        }
        else throw new IllegalArgumentException("x="+ animal.getPosition().x +" y="+ animal.getPosition().y + " place is occupied");
    }


    @Override
    public Object objectAt(Vector2d position) {
        if (animals2.containsKey(position)) {
            if (animals2.get(position).getPosition().equals(position))
                return animals2.get(position);
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
    @Override
    protected List<Animal> getanimals(){
        return animals;
    }
    @Override
    protected HashMap<Vector2d, Animal> getanimals2(){
        return animals2;
    }

}
