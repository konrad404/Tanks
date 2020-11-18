package agh.cs.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimulationEngine implements IEngine {

    private final MoveDirection[] directions;
    private final List<Animal> animals = new ArrayList<>();
    public SimulationEngine(MoveDirection[] directions, AbstractWorldMap map, Vector2d[] positions){
        this.directions = directions;
        for (int i=0 ; i< positions.length ; i++){
            Animal animal = new Animal(map,positions[i]);
            if (map.place(animal)){
                animals.add(animal);
                animal.addObserver(map);
            }
            else{
                throw new IllegalArgumentException(animal.getPosition().toString() + " place is occupied");
            }
        }
    }

    @Override
    public void run() {
        if (animals.size() != 0) {
            for (int i =0; i< directions.length; i++){
                Animal animal = animals.get(i%animals.size());
                animal.move(directions[i]);
            }
        }
    }
}
