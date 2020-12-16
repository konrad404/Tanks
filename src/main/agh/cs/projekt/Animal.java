package agh.cs.projekt;

import java.util.*;

public class Animal {
    private AbstractWorldMap map;
    private Vector2d position;
    public int age;
    public int energy;
    public int[] gene = new int[32];
    private int direction =new Random().nextInt(8);
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private int moveEnergy;
    private int childrenNumber;

    public Animal(AbstractWorldMap map, Vector2d initialPosition, int energy, int[] gene, int moveEnergy){
        age =0;
        this.map=map;
        this.energy=energy;
        this.gene = rightGene(gene);
        this.moveEnergy = moveEnergy;
        position = initialPosition;
    }

    private int[] rightGene(int[] gene){
        int[] count = new int[8];
        for (int i =0; i< gene.length;i++){
            count[gene[i]]++;
        }
//  Dla każdego genomu którego brakuje w genie wyszukujemy miejsce możliwe do jego wstawienia
//  poprzez podmienienie wartośći innego wystepującego więcej niż raz
        for (int i =0; i< count.length;i++){
            if (count[i] ==0){
                boolean flag = true;
                while(flag){
                    int draw = new Random().nextInt(32);
                    if ( count[gene[draw]] > 1){
                        gene[i] = i;
                        count[gene[draw]] --;
                        count[i]++;
                        flag =false;
                    }
                }
            }
        }
// następnie sortujemy genom:
        Arrays.sort(gene);
        return gene;
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
        direction +=  gene[turnId];
        direction %= 8;
        Vector2d newPosition = position.goInDirection(direction,map.mapHeight, map.mapWidth);
//        System.out.println("z: " + position.toString()+ " do: " + newPosition.toString());
        map.positionChanged(animal,position,newPosition);
        position = newPosition;
        energy -= moveEnergy;
        if (energy <=0) map.death(animal, position);
        else age++;
    }

    public void eat(int bonusenergy){
        this.energy += bonusenergy;
    }

    public int giveBirth(){
        int energyloss = energy/4;
        energy -=energyloss;
        return energyloss;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }



}
