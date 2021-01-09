package agh.cs.projekt;

import java.io.*;
import java.util.*;

public class Spectator {
    private final Map<Genotype,Integer> genotypes = new HashMap<>();
    private int animalNumber;
    private int sumEnergy;
    private float avgEnergy;
    private float avgLifeTime;
    private int sumLifeTime;
    private int deathCount;
    private int grassCount;
    private boolean deathOfTheChosenOne;
    private int targetedChildren;
    private int targetedDescendant;
    private int dateOfDeath;
    public Genotype mainGenome; // public?
    private int mainStatistics_SumAnimalsAlive;
    private int mainStatistics_SumGrassAmount;
    private final  Map<Genotype, Integer> mainStatistics_AvgMainGenome = new HashMap<>();
    private int mainStatistics_SumEnergy;
    private int mainStatistics_SumLifeTime;
    private int mainStatistics_SumChildrenAmount;
    private final int beginners;


    public Spectator(int beginners, int startingEnergy){
        this.beginners = beginners;
        grassCount =0;
        animalNumber=0;
        avgEnergy = 0;
        sumEnergy=beginners*startingEnergy;
        avgLifeTime =0;
        sumLifeTime=0;
        deathCount=0;
        deathOfTheChosenOne = false;
        targetedChildren=0;
        targetedDescendant=0;
        dateOfDeath =0;
        mainGenome = new Genotype(new int[32]);
//        ==========================
        mainStatistics_SumAnimalsAlive =0;
        mainStatistics_SumGrassAmount=0;
        mainStatistics_SumEnergy =0;
//      następna zmienna jest tym samym co sumlifetime jednak przechowuje ją dwukrotnie
//      aby zmienna wypisywana potem w końcowych statystykach miała początek nazwy mainSttatistics...
        mainStatistics_SumLifeTime=0;
        mainStatistics_SumChildrenAmount =0;
    }

    public void placeGrass(int placed){
        grassCount += placed;
    }

    public void birth(Animal animal){
//      nie zwiększamy liczby zwierząt na mapie dla pionierów
        if (!(animalNumber <= beginners))
            mainStatistics_SumChildrenAmount+=2;
        animalNumber++;
        if (genotypes.containsKey(animal.gene)){
            genotypes.put(animal.gene, genotypes.remove(animal.gene)+1);
        }
        else{
            genotypes.put(animal.gene, 1);
        }

        if (animal.type == AnimalType.CHILDREN){
            targetedDescendant++;
            targetedChildren++;
        }
        else if(animal.type == AnimalType.DESCENDANT)
            targetedDescendant++;
    }

    public void death(Animal animal, int lostEnergy, int age){
        deathCount++;
        sumLifeTime+=animal.getAge();
        sumEnergy-=lostEnergy;
        if(genotypes.get(animal.gene) == 1){
            genotypes.remove(animal.gene);
        }
        else{
            genotypes.replace(animal.gene, genotypes.get(animal.gene)-1);
        }
        if (animal.type == AnimalType.TARGETED) {
            deathOfTheChosenOne = true;
            dateOfDeath = age;
        }
    }

    public  void run(int moveEnergy){

        int animalsAlive = animalNumber-deathCount;
        sumEnergy -= animalsAlive*moveEnergy;
    }

    public void eating(int grassAmount, int grassEnergy, int spoiledEnergy){
        sumEnergy += (grassAmount*grassEnergy-spoiledEnergy);
        grassCount -= grassAmount;
    }

    private Genotype mainGenome(){
        int count=0;
        int maxAmount=0;
        Genotype mostoftengenome= null;
        for(Genotype genome: genotypes.keySet()){
            if(genotypes.get(genome) >maxAmount){
                maxAmount = genotypes.get(genome);
                count=1;
            }
            else if(genotypes.get(genome) == maxAmount){
                count++;
            }
        }
        if(count >1){
            ArrayList<Genotype> possibleMainGenome = new ArrayList<>();
            for(Genotype genome: genotypes.keySet()){
                if(genotypes.get(genome) == maxAmount) possibleMainGenome.add(genome);
            }
            mostoftengenome = possibleMainGenome.get(new Random().nextInt(possibleMainGenome.size()));
        }

        else{
            for(Genotype genome: genotypes.keySet())
                if(genotypes.get(genome) == maxAmount) mostoftengenome = genome;
        }
        return mostoftengenome;
    }

    public void showEndStatistics(int age){
        Genotype bestGenome = null;
        boolean anyChosen=false;
        for(Genotype gene: mainStatistics_AvgMainGenome.keySet()){
            if(!anyChosen){
                bestGenome = gene;
                anyChosen = true;
            }
            else if(mainStatistics_AvgMainGenome.get(gene) > mainStatistics_AvgMainGenome.get(bestGenome))
                bestGenome = gene;
        }

        String bestGene = "";
        if(bestGenome != null){
                for (int i = 0; i < bestGenome.genotype.length; i++)
                bestGene += bestGenome.genotype[i];
        }

        float avgAnimalNumber = (float) mainStatistics_SumAnimalsAlive/age;
        float avgGrassNumber = (float) mainStatistics_SumGrassAmount/age;
        float avgEnergy = (float) mainStatistics_SumEnergy/age;
        float avgLifeTime = 0;
        if(deathCount >0){
            avgLifeTime = (float) mainStatistics_SumLifeTime / deathCount;
        }
        float avgChildrenNumber = (float) mainStatistics_SumChildrenAmount/animalNumber;


        String information = ("Average Animal Number: " + avgAnimalNumber+
                        "\nAverage Grass Number: " + avgGrassNumber+
                        "\nMost Dominating Genotype: " + bestGene+
                        "\nAverage Energy: " + avgEnergy+
                        "\nAverage Life Time: " + avgLifeTime+
                        "\nAverage Children Number: " + avgChildrenNumber);



        try {
            File stats = new File("statistics.txt");
            if (stats.createNewFile()) {
                System.out.println("File created: " + stats.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("statistics.txt");
            myWriter.write(information);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

    public String toString(int sumChildAmount, int age, boolean summarize){ // odradzam toString z parametrami
        int animalsAlive = animalNumber-deathCount;

        if(sumLifeTime >0){
            avgLifeTime = (float) sumLifeTime / deathCount;
        }
        avgEnergy = (float) sumEnergy/animalsAlive;

        float avgChildrenAmount =0;
        if(animalsAlive != 0) avgChildrenAmount = (float) sumChildAmount/animalsAlive;


//        System.out.println(gen);}
        String toAdd = "";

        if(summarize){
            toAdd+=("\nStatistics of targeted animal");
            if(deathOfTheChosenOne)
                toAdd += ("\nAge of death " + dateOfDeath);
            else
                toAdd += ("\nStill ALive");
            int first = targetedChildren;
            int second = targetedDescendant;
            toAdd +=("\nChildren Amount: "+ first+
                    "\nDescendant Amount: "+ second);
            this.deathOfTheChosenOne = false;
            targetedChildren =0;
            targetedDescendant=0;
        }

//      Aktualizowanie końcowych statystyk;

        mainStatistics_SumAnimalsAlive +=animalsAlive;
        mainStatistics_SumGrassAmount+= grassCount;
        mainStatistics_SumEnergy +=avgEnergy;
        mainStatistics_SumChildrenAmount +=avgChildrenAmount;
        mainStatistics_SumLifeTime = sumLifeTime;

        mainGenome = mainGenome();
        String gen = "";

        if (mainGenome != null) {
            for (int i = 0; i < mainGenome.genotype.length; i++)
                gen += mainGenome.genotype[i];
            if(mainStatistics_AvgMainGenome.containsKey(mainGenome))
                mainStatistics_AvgMainGenome.put(mainGenome,mainStatistics_AvgMainGenome.remove(mainGenome)+1);
            else
                mainStatistics_AvgMainGenome.put(mainGenome,1);
        }


        return ("Age: " + age+
                "\nAnimals Alive: " + animalsAlive +
                "\nGrass Amount: " + grassCount +
                "\nMain Genome: " + gen+
                "\nAverage Energy: " + avgEnergy +
                "\nAverage Life Time: " + avgLifeTime+
                "\nAverage Children Amount: " + avgChildrenAmount+ toAdd);
    }
}
