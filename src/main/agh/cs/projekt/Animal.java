package agh.cs.projekt;

import javafx.scene.paint.Color;

import java.util.*;

public class Animal {
    private AbstractWorldMap map;
    private Vector2d position;
    public int age;
    public int energy;
    public Genotype gene;
    private int direction =new Random().nextInt(8);
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private int moveEnergy;
    public int childrenNumber;
    public AnimalType type;
    private Animal parent1;
    private Animal parent2;

    public Animal(AbstractWorldMap map, Vector2d initialPosition, int energy, Genotype gene, int moveEnergy,
                  Animal parent1, Animal parent2, AnimalType type){
        this.age =0;
        this.map=map;
        this.energy=energy;
        this.gene = gene;
        this.moveEnergy = moveEnergy;
        position = initialPosition;
        this.type =type;
        this.childrenNumber =0;
        this.parent1 = parent1;
        this.parent2 =parent2;
    }



    public Vector2d getPosition(){
        return position;
    }

    public String toString(){
        return String.valueOf(direction);
    }

//    zmienić żeby nie było przekazywanego zwierzęcia do zwierzęcia
    public void move(Animal animal){
        int turnId = new Random().nextInt(32);
        direction +=  gene.gene[turnId];
        direction %= 8;
        Vector2d newPosition = position.goInDirection(direction,map.mapHeight, map.mapWidth);
//        System.out.println("z: " + position.toString()+ " do: " + newPosition.toString());
        map.positionChanged(animal,position,newPosition);
        position = newPosition;
        energy -= moveEnergy;
        if (energy <=0) {
            map.death(animal, position);
            if (parent1 != null)
                parent1.childrenNumber--;
            if(parent2 != null)
                parent2.childrenNumber--;
        }
        else this.age++;
    }

    public void eat(int bonusenergy){
        this.energy += bonusenergy;
    }

    public int giveBirth(){
        int energyloss = energy/4;
        energy -=energyloss;
        childrenNumber++;
        return energyloss;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void changeType(AnimalType newType){
        this.type = newType;
    }

    public Color getcolor(){
        if (energy<20) return Color.LIGHTCORAL;
        else if (energy < 50) return Color.BROWN;
        else return Color.RED;
    }




}
