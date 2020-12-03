package agh.cs.projekt;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements IEngine {

    private JungleMap map;
    private int oneGrassEnergy;
    private int copulationEnergy;
    private ListMultimap<Vector2d, Animal> animalsMap = ArrayListMultimap.create();
    public SimulationEngine(JungleMap map, Vector2d[] positions, int startingEnergy, int oneGrassEnergy,
                            int copulationEnergy){
        this.map = map;
        this.oneGrassEnergy = oneGrassEnergy;
        this.copulationEnergy = copulationEnergy;
        for (int i=0 ; i< positions.length ; i++){
            int[] gene = new int[32];
            for (int j =0; j<gene.length; j++){
                int genome = new Random().nextInt(8);
                gene[j] = genome;
            }
            Animal animal = new Animal(map,positions[i], startingEnergy,gene);
            if (map.place(animal)){
                animalsMap.put(positions[i],animal);
                animal.addObserver(map);
            }
            else{
                throw new IllegalArgumentException(animal.getPosition().toString() + " place is occupied");
            }
        }
    }

    @Override
    public void run() {
        ArrayList<Animal> animals = new ArrayList<>(animalsMap.values());
            for (int i =0; i< animals.size(); i++){
                Vector2d oldPosition = animals.get(i).getPosition();
                Animal animal = animals.get(i);
                animal.move(animal);
                Vector2d newPosition = animals.get(i).getPosition();
                animalsMap.remove(oldPosition, animal);
                animalsMap.put(newPosition, animal);
            }
    }

    public void eating() {
        for (Vector2d position : animalsMap.keySet()) {
            if (map.isGrassAt(position)) {
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
    }

    public void copulations(){
        for (Vector2d position: animalsMap.keySet()){
            if (animalsMap.get(position).size()>1){
                ArrayList<Animal> animals = new ArrayList<>(animalsMap.get(position));
//                ponieżej wyszukujemy 2 zwierzęta o najwyższej energii na każdym miejscu
                int id1=0;
                for (int i =1; i< animals.size(); i++){
                    if (animals.get(i).energy>animals.get(id1).energy) id1 =i;
                }
                int id2 = (id1+1)%animals.size();
                for(int i =0;i<animals.size();i++){
                    if(i != id1 && animals.get(i).energy > animals.get(id2).energy) id2 =i;
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

                Animal child = new Animal(map,map.findBirthPlace(position), energy , gene);
                if (map.place(child)){
                    animalsMap.put(child.getPosition(),child);
                    child.addObserver(map);
                }
            }
        }
    }

//    do usnunięcia
    public void show(){
        for (Vector2d position : animalsMap.keySet()){
            ArrayList<Animal> animals = new ArrayList<>(animalsMap.get(position));
            for (int i =0; i< animals.size(); i++){
                System.out.println(animals.get(i).getPosition().toString() + " " + animals.get(i).energy);
                for (int j =0; j < animals.get(i).gene.length; j++){
                    System.out.print(animals.get(i).gene[j]);
                }
                System.out.println();
            }
        }
    }

}
