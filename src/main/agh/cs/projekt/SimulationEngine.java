package agh.cs.projekt;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements IEngine {

    private Spectator spectator;
    private JungleMap map;
    private int oneGrassEnergy;
    private int copulationEnergy;
    private int moveEnergy;
    private Visualizer visualizer;
    private ListMultimap<Vector2d, Animal> animalsMap = ArrayListMultimap.create();

    public SimulationEngine(JungleMap map,Visualizer visualizer, int beginners, int startingEnergy, int oneGrassEnergy,
                            int moveEnergy){
        this.spectator = new Spectator(beginners, startingEnergy);
        this.moveEnergy = moveEnergy;
        this.map = map;
        this.visualizer=visualizer;
        this.oneGrassEnergy = oneGrassEnergy;
        this.copulationEnergy = startingEnergy/2;
        for (int i=0 ; i< beginners ; i++){
            int[] gene = new int[32];
            for (int j =0; j<gene.length; j++){
                int genome = new Random().nextInt(8);
                gene[j] = genome;
            }
            Vector2d position = new Vector2d(new Random().nextInt(map.mapWidth), new Random().nextInt(map.mapHeight));
            while(map.isOccupied(position))
                position = new Vector2d(new Random().nextInt(map.mapWidth), new Random().nextInt(map.mapHeight));
            Animal animal = new Animal(map,position, startingEnergy,gene, moveEnergy, null, null);
            map.place(animal);
            animalsMap.put(position,animal);
            animal.addObserver(map);
            spectator.birth(animal);

        }
    }

    @Override
    public void run() {
        ArrayList<Animal> animals = new ArrayList<>(animalsMap.values());
            for (int i =0; i< animals.size(); i++){
                Vector2d oldPosition = animals.get(i).getPosition();
                Animal animal = animals.get(i);
                int startEnergy = animal.energy;
                animal.move(animal);
                if (animal.energy <=0) {
                    animalsMap.remove(oldPosition, animal);
//                    System.out.println("umiera ");
                    spectator.death(animal,startEnergy);
                    continue;
                }
                Vector2d newPosition = animals.get(i).getPosition();
                animalsMap.remove(oldPosition, animal);
                animalsMap.put(newPosition, animal);
            }
            spectator.run(moveEnergy);
    }

    public void eating() {
        int grassCount=0;
        for (Vector2d position : animalsMap.keySet()) {
            if (map.isGrassAt(position)) {
                grassCount++;
                int maxEnergy = 0;
                int count=0;
//              pobieramy listę zwierząt na danym miejscu
//              i wyszukujemy zwierzęta o najwyższej energii
                ArrayList<Animal> animals = new ArrayList<>(animalsMap.get(position));
                for (int i =0; i< animals.size(); i++){
                    if(animals.get(i).energy > maxEnergy){
                        maxEnergy = animals.get(i).energy;
                        count =1;
                    }
                    else if(animals.get(i).energy == maxEnergy) count++;
                }
                int bonusEnergy = oneGrassEnergy/count;
                for (int i =0; i< animals.size(); i++){
                    if(animals.get(i).energy == maxEnergy) animals.get(i).eat(bonusEnergy);
                }
                map.removeGrass(position);
            }
        }
        spectator.eating(grassCount,oneGrassEnergy);
    }

    public void copulations(){
        ArrayList<Animal> children = new ArrayList<>();
        ArrayList<Vector2d> childrenPositions = new ArrayList<>();
        for (Vector2d position: animalsMap.keySet()){
            if (animalsMap.get(position).size()>1){
                ArrayList<Animal> animals = new ArrayList<>(animalsMap.get(position));
//              ponieżej wyszukujemy 2 zwierzęta o najwyższej energii na każdym miejscu
                int maxEnergy =0;
                int count =0;
                for(int i =0; i< animals.size();i++){
                    if (animals.get(i).energy > maxEnergy){
                        maxEnergy = animals.get(i).energy;
                        count =0;
                    }
                    else if( animals.get(i).energy == maxEnergy) count++;
                }
                int id1=0;
                int id2=0;
                if (count >1){
                    ArrayList<Integer> parents = new ArrayList<>();
                    for(int i =0; i< animals.size();i++)
                        if(animals.get(i).energy == maxEnergy) parents.add(i);
                    int nr1 = new Random().nextInt(parents.size());
                    int nr2 = new Random().nextInt(parents.size());
                    while(nr1 == nr2) nr2 = new Random().nextInt(parents.size());
                    id1 = parents.get(nr1);
                    id2 = parents.get(nr2);
                }

                else{
                    int secondMaxEnergy=0;
                    int secondCount =0;
                    for(int i =0; i< animals.size();i++)
                        if(animals.get(i).energy == maxEnergy) id1 = i;
                    for(int i =0; i< animals.size();i++){
                        if (animals.get(i).energy > secondMaxEnergy && animals.get(i).energy < maxEnergy){
                            secondMaxEnergy = animals.get(i).energy;
                            secondCount =0;
                        }
                        else if( animals.get(i).energy == secondMaxEnergy) secondCount++;
                    }
                    if (secondCount >1){
                        ArrayList<Integer> parents = new ArrayList<>();
                        for(int i =0; i< animals.size();i++)
                            if(animals.get(i).energy == secondMaxEnergy) parents.add(i);
                        int nr1 = new Random().nextInt(parents.size());
                        id2 = parents.get(nr1);
                    }
                    else{
                        for(int i =0; i< animals.size();i++)
                            if(animals.get(i).energy == secondMaxEnergy) id2 = i;
                    }
                }

                Animal parent1 = animals.get(id1);
                Animal parent2 = animals.get(id2);
                if(parent1.energy<copulationEnergy || parent2.energy < copulationEnergy) continue;
//               poniżej tworzenie genu dziecka
                int cut1 = 1 + new Random().nextInt(29);
                int cut2 = cut1 + 1 + new Random().nextInt((30-cut1));
                int [] gene = new int[32];
                for (int i =0; i<=cut1;i++) gene[i] = parent1.gene[i];
                for (int i =cut1+1; i<=cut2;i++) gene[i] = parent2.gene[i];
                for (int i = cut2+1;i<32;i++) gene[i] = parent1.gene[i];
//               energia dziecka to suma oddanych energi jego rodziców (1/4)

                int energy = parent1.giveBirth()+parent2.giveBirth();
                Vector2d birthPosition = map.findBirthPlace(position);
                Animal child = new Animal(map,birthPosition, energy , gene, moveEnergy, parent1, parent2);
                children.add(child);
                childrenPositions.add(birthPosition);
            }
        }
        for(int childNr =0; childNr<children.size();childNr++){
            Animal child = children.get(childNr);
            Vector2d place = childrenPositions.get(childNr);
            spectator.birth(child);
            map.place(child);
            animalsMap.put(place,child);
            child.addObserver(map);

        }
    }

//    potrzebna???
//    public void cleaning(){
//
//    }

    public void day(){
//        System.out.println("dzień dobry");
        int newGrasses = map.placeGrasses();

        spectator.placeGrass(newGrasses);

        run();
//        System.out.println("pobiegane");
        eating();
//        System.out.println("pojedzone");
        copulations();
//        System.out.println("po...");
//        System.out.println("liczba zwierząt: "+ animalsMap.size());
//        for(Animal animal : animalsMap.values()) System.out.println("energia: " + animal.energy);
//        spectator.present();
//        Vector2d rightUpCorner = map.getRightUpCorner();
//        System.out.println("róg:  " + rightUpCorner.toString());
        for(int x =0; x<20;x++){
            for(int y =0; y<20;y++){
//                System.out.println("x " + x + " y " + y);
                if(map.isGrassAt(new Vector2d(x,y))) {
                    visualizer.changeColor(x, y, Color.GREEN);
//                    System.out.println("x " + x + " y " + y);
                }
                else {
                    visualizer.changeColor(x, y, Color.LIGHTGRAY);
//                    System.out.println("x " + x + " y " + y);
                }
            }
        }

        for(Vector2d position: animalsMap.keySet()){
            int x = position.x;
            int y = position.y;
//            System.out.println("x: " +x + " y: " + y);
            ArrayList<Animal> animals = new ArrayList<>(animalsMap.get(position));
            if(animalsMap.get(position).size() >1) {
                int maxEnergy =0;
                int id =0;
                for(int i=0; i< animals.size();i++){
                    if (animals.get(i).energy>maxEnergy){
                        maxEnergy = animals.get(i).energy;
                        id =i;
                    }
                }
                visualizer.changeColor(x, y, animals.get(id).getcolor());
            }
            else
                visualizer.changeColor(x,y,animals.get(0).getcolor());
        }
        visualizer.addStatistics(spectator.toString());

//        System.out.println("dobranoc");

    }


}
