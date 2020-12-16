package agh.cs.projekt;

import java.util.*;

public class Spectator {
    private Map<int[],Integer> genotypes = new HashMap<>();
    private int animalNumber;
    private int sumEnergy;
    private double avgEnergy;
    private double avgLifeTime;
    private int sumLifeTime;
    private int deathCount;



    public Spectator(int beginners, int startingEnergy){
        animalNumber=0;
        avgEnergy = 0;
        sumEnergy=beginners*startingEnergy;
        avgLifeTime =0;
        sumLifeTime=0;
        deathCount=0;
    }

    public void birth(Animal animal){
        animalNumber++;
        if (genotypes.containsKey(animal.gene)){
            genotypes.put(animal.gene, genotypes.remove(animal.gene)+1);
        }
        else{
            genotypes.put(animal.gene, 1);
        }
    }

    public void death(Animal animal, int lostEnergy){
        deathCount++;
//        System.out.println("wiek śmierci: "+ animal.age);
        sumLifeTime+=animal.age;
        sumEnergy-=lostEnergy;
        if(genotypes.get(animal.gene) == 1){
            genotypes.remove(animal.gene);
        }
        else{
            genotypes.replace(animal.gene, genotypes.get(animal.gene)-1);
        }
    }

    public  void run(int moveEnergy){

        int animalsAlive = animalNumber-deathCount;
        sumEnergy -= animalsAlive*moveEnergy;
    }

    public void eating(int grassAmount, int grassEnergy){
        sumEnergy += grassAmount*grassEnergy;
    }

    public void present(){
        int animalsAlive = animalNumber-deathCount;
        if(sumLifeTime >0){
            avgLifeTime = (float) sumLifeTime / deathCount;
        }
        avgEnergy = (float) sumEnergy/animalsAlive;
//        znajdywanie najczęściej występującego genomu:
        int count=0;
        int maxAmount=0;
        int[] mostoftengenome ={};
        for(int[] genome: genotypes.keySet()){
            if(genotypes.get(genome) >maxAmount){
                maxAmount = genotypes.get(genome);
                count=1;
            }
            else if(genotypes.get(genome) == maxAmount){
                count++;
            }
        }
        if(count >1){
            ArrayList<int[]> potencialGenome = new ArrayList<>();
            for(int[] genome: genotypes.keySet()){
                if(genotypes.get(genome) == maxAmount) potencialGenome.add(genome);
            }
            mostoftengenome = potencialGenome.get(new Random().nextInt(potencialGenome.size()));
        }
        else{
            for(int[] genome: genotypes.keySet())
                if(genotypes.get(genome) == maxAmount) mostoftengenome = genome;
        }

//        System.out.println("łączna energia: " + sumEnergy);
        System.out.print("najczęściej występujący genom: ");
        for(int i: mostoftengenome) System.out.print(i);
        System.out.println(" ilość: " + maxAmount);
//        System.out.println("suma lat: "+ sumLifeTime);
        System.out.println("żyjące zwierzta: " + animalsAlive + " srednia energia: " + avgEnergy);
        System.out.println("zwierzęta które umarły: " + deathCount+" średnia długość życia zwierząt (które umarły): " + avgLifeTime);
    }
}
