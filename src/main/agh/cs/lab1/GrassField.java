package agh.cs.lab1;

import java.util.*;
import java.lang.IllegalArgumentException;
import java.util.LinkedHashMap;

public class GrassField extends AbstractWorldMap {
    private List<Animal> animals = new ArrayList<>();
    public List<Grass> grassfields = new ArrayList<>();
    private Vector2d left_down_corner= new Vector2d(0,0);
    private Vector2d right_up_corner= new Vector2d(0,0);
    boolean flag = true;
    public GrassField(int n){
        for (int i=0; i<=n;){
            int x = Math.abs((new Random().nextInt())) % (int)Math.sqrt(10*n);
            int y = Math.abs((new Random().nextInt())) % (int)Math.sqrt(10*n);
            Grass grass = new Grass(new Vector2d(x,y));
            boolean occupied = false;
            for(int j =0; j< grassfields.size(); j++){
                if (grass.equals(grassfields.get(j))) {
                    occupied = true;
                    i--;
                }
            }
            if (!occupied) {
                // poniżej aktualizowanie wymiarów mapy do późniejszego wypisania
                if (flag){
                    left_down_corner = grass.position;
                    right_up_corner = grass.position;
                    flag = false;
                }
                else{
                    left_down_corner = grass.getPosition().lowerLeft(left_down_corner);
                    right_up_corner = grass.getPosition().upperRight(right_up_corner);
                }
                grassfields.add(grass);
                i+=1;
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
    public Object objectAt(Vector2d position) {
        if (animalsMap.containsKey(position)) {
            if (animalsMap.get(position).getPosition().equals(position))
                return animalsMap.get(position);
        }
        for(int i =0; i< grassfields.size(); i++){
            if (position.equals(grassfields.get(i).getPosition())) return grassfields.get(i);
        }
        return null;
    }
    @Override
    protected Vector2d lowerLeft() {
        for (int i =0 ; i < animals.size(); i++){
            left_down_corner = animals.get(i).getPosition().lowerLeft(left_down_corner);
        }
        return left_down_corner;
    }

    @Override
    protected Vector2d upperRight() {
        for (int i =0 ; i < animals.size(); i++){
            right_up_corner = animals.get(i).getPosition().upperRight(right_up_corner);
        }
        return right_up_corner;
    }

}
