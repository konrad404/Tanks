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
    private int grasscount;
    private int childrenAmount;



    public Spectator(int beginners, int startingEnergy){
        grasscount =0;
        animalNumber=0;
        avgEnergy = 0;
        sumEnergy=beginners*startingEnergy;
        avgLifeTime =0;
        sumLifeTime=0;
        deathCount=0;
        childrenAmount = 0;

    }

    public void placeGrass(int placed){
        grasscount += placed;
    }

    public void birth(Animal animal){
//        childrenAmount+=2;
        animalNumber++;
        if (genotypes.containsKey(animal.gene)){
            genotypes.put(animal.gene, genotypes.remove(animal.gene)+1);
        }
        else{
            genotypes.put(animal.gene, 1);
        }
    }

    public void death(Animal animal, int lostEnergy){
//        childrenAmount-=animal.childrenNumber;
//        childrenAmount-=2;
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
        grasscount -= grassAmount;
    }

    public String toString(){
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

        float avgChildrenAmount = (float) childrenAmount/(2*animalsAlive);


        String gen = "";
        for(int i =0; i< mostoftengenome.length; i++)
            gen += mostoftengenome[i];
//        System.out.println(gen);

        return ("Animals Alive: " + animalsAlive +
                "\nGrass Amount: " + grasscount+
                "\nMain Genome: " + gen+
                "\nAverage Energy: " + avgEnergy +
                "\nAverage Life Time: " + avgLifeTime+
                "\nAverage Children Amount: " + avgChildrenAmount);
    }
}
