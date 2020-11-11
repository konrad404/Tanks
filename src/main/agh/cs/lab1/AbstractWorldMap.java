package agh.cs.lab1;

import java.util.*;



abstract class AbstractWorldMap implements IWorldMap {

    abstract protected List<Animal> getanimals();
    abstract protected HashMap<Vector2d, Animal> getanimals2();
    private MapVisualizer obraz = new MapVisualizer(this);
    abstract protected Vector2d lowerLeft();
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
        if (getanimals().size() != 0) {
            for (int i =0; i<getanimals().size(); i++){
                Vector2d start_position = getanimals().get(i%getanimals().size()).getPosition();
                Animal animal = getanimals2().get(start_position);
                getanimals().get(i%(getanimals().size())).move(directions[i]);
                getanimals2().remove(start_position);
                getanimals2().put(animal.getPosition(), animal);
            }
        }
    }

    public String toString(){
        String mapa = obraz.draw(lowerLeft(), upperRight());
        return mapa;
    }
}
