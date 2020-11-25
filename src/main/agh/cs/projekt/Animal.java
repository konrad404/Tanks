package agh.cs.projekt;

import java.util.*;

public class Animal {
    private AbstractWorldMap map;
    private Vector2d position;
    private int energy;
    private int[] gene = new int[32];
    private int direction =0;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    public Animal(AbstractWorldMap map) {
        this.map = map;
        position = new Vector2d(2,2);
    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition){
        this.map=map;
        position = initialPosition;
    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition, int energy, int[] gene){
        this.map=map;
        this.energy=energy;
        this.gene = gene;
        position = initialPosition;
    }

    public int getEnergy(){return energy;}

    public Vector2d getPosition(){
        return position;
    }

    public String toString(){
        return String.valueOf(direction);
    }
    public void move(){
        int turnId = new Random().nextInt(32);
        direction +=  gene[turnId];
        direction %= 8;
        switch (direction){
            case 0:{
                Vector2d newPosition = position.add(new Vector2d(0,1));
                map.positionChanged(position,newPosition);
                position = newPosition;
            }
            case 1:{
                Vector2d newPosition = position.add(new Vector2d(1,1));
                map.positionChanged(position,newPosition);
                position = newPosition;
            }
            case 2:{
                Vector2d newPosition = position.add(new Vector2d(1,0));
                map.positionChanged(position,newPosition);
                position = newPosition;
            }
            case 3:{
                Vector2d newPosition = position.add(new Vector2d(1,-1));
                map.positionChanged(position,newPosition);
                position = newPosition;
            }
            case 4:{
                Vector2d newPosition = position.add(new Vector2d(0,-1));
                map.positionChanged(position,newPosition);
                position = newPosition;
            }
            case 5:{
                Vector2d newPosition = position.add(new Vector2d(-1,-1));
                map.positionChanged(position,newPosition);
                position = newPosition;
            }
            case 6:{
                Vector2d newPosition = position.add(new Vector2d(-1,0));
                map.positionChanged(position,newPosition);
                position = newPosition;
            }
            case 7:{
                Vector2d newPosition = position.add(new Vector2d(-1,1));
                map.positionChanged(position,newPosition);
                position = newPosition;
            }

        }
    }
    public void eat(int bonusenergy){
        this.energy += bonusenergy;
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
