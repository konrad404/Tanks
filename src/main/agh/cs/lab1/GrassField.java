package agh.cs.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GrassField extends AbstractWorldMap {
    private List<Animal> animals = new ArrayList<>();
    private List<Grass> grassfields = new ArrayList<>();
    private Vector2d left_down_corner= new Vector2d(0,0);
    private Vector2d right_up_corner= new Vector2d(0,0);
    boolean flag = true;
    public GrassField(int n){
        for (int i=0; i<n; i++){
            int x = Math.abs((new Random().nextInt())) % (int)Math.sqrt(10*n);
            int y = Math.abs((new Random().nextInt())) % (int)Math.sqrt(10*n);
            Grass grass = new Grass(new Vector2d(x,y));
            boolean occupied = false;
            for(int j =0; j< grassfields.size(); j++){
                if (grass.equals(grassfields.get(j))) occupied = true;
            }
            if (!occupied) {
                // poniżej aktualizowanie wymiarów mapy do późniejszego wypisania
                if (flag){
                    left_down_corner = grass.position;
                    right_up_corner = grass.position;
                    flag = false;
                }
                else{
                    if (x < left_down_corner.x) left_down_corner = new Vector2d(x, left_down_corner.y);
                    else if (x > right_up_corner.x) right_up_corner = new Vector2d(x, right_up_corner.y);
                    if (y<left_down_corner.y) left_down_corner = new Vector2d(left_down_corner.x, y);
                    else if (y > right_up_corner.y) right_up_corner = new Vector2d(right_up_corner.x, y);
                }
                grassfields.add(grass);
            }
        }
    }



    @Override
    public boolean canMoveTo(Vector2d position) {
        for(int i =0; i< animals.size(); i++){
            if (position.equals(animals.get(i).getPosition())) return false;
        }
        return true;
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
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
        for(int i =0; i< grassfields.size(); i++){
            if (position.equals(grassfields.get(i).getPosition())) return grassfields.get(i);
        }
        return null;
    }
    @Override
    protected Vector2d lowwerLeft() {
        for (int i =0 ; i < animals.size(); i++){
            if (animals.get(i).getPosition().x < left_down_corner.x) left_down_corner = new Vector2d(animals.get(i).getPosition().x, left_down_corner.y);
            if (animals.get(i).getPosition().y<left_down_corner.y) left_down_corner = new Vector2d(left_down_corner.x, animals.get(i).getPosition().y);
        }
        return left_down_corner;
    }

    @Override
    protected Vector2d upperRight() {
        for (int i =0 ; i < animals.size(); i++){
            if (animals.get(i).getPosition().x > right_up_corner.x) right_up_corner = new Vector2d(animals.get(i).getPosition().x, right_up_corner.y);
            if (animals.get(i).getPosition().y>right_up_corner.y) right_up_corner = new Vector2d(right_up_corner.x, animals.get(i).getPosition().y);
        }
        return right_up_corner;
    }
    @Override
    protected List<Animal> getanimals(){
        return animals;
    }
}
