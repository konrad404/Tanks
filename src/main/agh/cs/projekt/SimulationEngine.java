package agh.cs.projekt;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class SimulationEngine implements IEngine {

    protected final Spectator spectator;
    protected JungleMap map;
    private final int oneGrassEnergy;
    private final int copulationEnergy;
    private final int moveEnergy;
    public final Visualizer visualizer;
    private boolean isTracking;
    private int age;
    private int ageToSummarize;
    private final int beginners;
    private final int height;
    private final int width;
    private final ListMultimap<Vector2d, Animal> animalsMap = ArrayListMultimap.create();

    public SimulationEngine(JungleMap map, int beginners, int startingEnergy, int oneGrassEnergy,
                            int moveEnergy){
        this.height = map.getRightUpCorner().y+1;
        this.width = map.getRightUpCorner().x+1;
        this.visualizer = new Visualizer(400,400,width,height,this);
        this.spectator = new Spectator(beginners, startingEnergy);
        this.moveEnergy = moveEnergy;
        this.map = map;
        this.oneGrassEnergy = oneGrassEnergy;
        this.copulationEnergy = startingEnergy/2;
        this.isTracking = false;
        this.age =1;
        this.ageToSummarize =0;
//      zmniejszamy liczbe początkowych zwierząt jeżeli jest ich więcej niż miejsc na mapie
        this.beginners = Math.min(beginners, this.height * this.width);
//      dodawanie naszych pionierów
        for (int i=0 ; i< this.beginners ; i++){
            int[] gene = new int[32];
            for (int j =0; j<gene.length; j++){
                int genome = new Random().nextInt(8);
                gene[j] = genome;
            }
            Vector2d position = new Vector2d(new Random().nextInt(width), new Random().nextInt(height));
            while(map.isOccupied(position))
                position = new Vector2d(new Random().nextInt(width), new Random().nextInt(height));
            Animal animal = new Animal(map,position, startingEnergy,new Genotype(gene), moveEnergy, null, null, AnimalType.ORDINARY);
            map.place(animal);
            animalsMap.put(position,animal);
            animal.addObserver(map);
            spectator.birth(animal);

        }
    }

    @Override
    public void run() {
        ArrayList<Animal> animals = new ArrayList<>(animalsMap.values());
        for (Animal animal : animals) {
            Vector2d oldPosition = animal.getPosition();
            int startEnergy = animal.energy;
            animal.move();
            if (animal.energy <= 0) {
//              kiedy zwierze umrze podczas ruchu:
                animalsMap.remove(oldPosition, animal);
                spectator.death(animal, startEnergy, age);
                continue;
            }
            Vector2d newPosition = animal.getPosition();
            animalsMap.remove(oldPosition, animal);
            animalsMap.put(newPosition, animal);
        }
            spectator.run(moveEnergy);
    }

    public void eating() {
        int spoiledEnergy =0;
        int grassCount=0;
        for (Vector2d position : animalsMap.keySet()) {
            if (map.isGrassAt(position)) {
                grassCount++;
                int maxEnergy = 0;
                int count=0;
//              pobieramy listę zwierząt na danym miejscu
//              i wyszukujemy zwierzęta o najwyższej energii
                ArrayList<Animal> animals = new ArrayList<>(animalsMap.get(position));
                for (Animal animal : animals) {
                    if (animal.energy > maxEnergy) {
                        maxEnergy = animal.energy;
                        count = 1;
                    } else if (animal.energy == maxEnergy) count++;
                }
                int bonusEnergy = oneGrassEnergy/count;
                spoiledEnergy += (oneGrassEnergy - bonusEnergy*count);
                for (Animal animal : animals) {
                    if (animal.energy == maxEnergy) animal.eat(bonusEnergy);
                }
                map.removeGrass(position);
            }
        }
        spectator.eating(grassCount,oneGrassEnergy, spoiledEnergy);
    }

    public void copulations(){
//      lista dzieci do dodania na koniec metody:
        ArrayList<Animal> children = new ArrayList<>();
        for (Vector2d position: animalsMap.keySet()){
            if (animalsMap.get(position).size()>1){
                ArrayList<Animal> animals = new ArrayList<>(animalsMap.get(position));
//              ponieżej wyszukujemy 2 zwierzęta o najwyższej energii na każdym miejscu
                int maxEnergy =0;
                int count =0;
                for (Animal animal : animals) {
                    if (animal.energy > maxEnergy) {
                        maxEnergy = animal.energy;
                        count = 0;
                    } else if (animal.energy == maxEnergy) count++;
                }
                int id1=0;
                int id2=0;
//              jeśli zwierząt o najwyższej energii jest więcej niż 1:
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
//              w przeciwnym przypadku:
                else{
                    int secondMaxEnergy=0;
                    int secondCount =0;
                    for(int i =0; i< animals.size();i++)
                        if(animals.get(i).energy == maxEnergy) id1 = i;
                    for (Animal animal : animals) {
                        if (animal.energy > secondMaxEnergy && animal.energy < maxEnergy) {
                            secondMaxEnergy = animal.energy;
                            secondCount = 0;
                        } else if (animal.energy == secondMaxEnergy) secondCount++;
                    }
//                  jesli zwierząt u drugiej najwyższej eneergi jest więcej niż 1 to wybieramy spośród nich losowo
                    if (secondCount >1){
                        ArrayList<Integer> possibleParentId = new ArrayList<>();
                        for(int i =0; i< animals.size();i++)
                            if(animals.get(i).energy == secondMaxEnergy) possibleParentId.add(i);
                        int nr1 = new Random().nextInt(possibleParentId.size());
                        id2 = possibleParentId.get(nr1);
                    }
                    else{
                        for(int i =0; i< animals.size();i++)
                            if(animals.get(i).energy == secondMaxEnergy) id2 = i;
                    }
                }

                Animal parent1 = animals.get(id1);
                Animal parent2 = animals.get(id2);
//              jeśli energia któregokolwiek z parentów jest za mała dziecko nie rodzi się
                if(parent1.energy<copulationEnergy || parent2.energy < copulationEnergy) continue;
//               poniżej tworzenie genu dziecka
                int cut1 = 1 + new Random().nextInt(29);
                int cut2 = cut1 + 1 + new Random().nextInt((30-cut1));
                int [] gene = new int[32];
                for (int i =0; i<=cut1;i++) gene[i] = parent1.gene.genotype[i];
                for (int i =cut1+1; i<=cut2;i++) gene[i] = parent2.gene.genotype[i];
                for (int i = cut2+1;i<32;i++) gene[i] = parent1.gene.genotype[i];
//               energia dziecka to suma oddanych energi jego rodziców (1/4)

                int energy = parent1.giveBirth()+parent2.giveBirth();
                Vector2d birthPosition = map.findBirthPlace(position);
                AnimalType type = AnimalType.ORDINARY;
                if(parent1.type == AnimalType.TARGETED || parent2.type == AnimalType.TARGETED)
                    type = AnimalType.CHILDREN;
                else if(parent1.type != AnimalType.ORDINARY || parent2.type != AnimalType.ORDINARY)
                    type = AnimalType.DESCENDANT;
                Animal child = new Animal(map,birthPosition, energy , new Genotype(gene), moveEnergy, parent1, parent2, type);
                children.add(child);
            }
        }
        for (Animal child : children) {
            Vector2d place = child.getPosition();
            spectator.birth(child);
            map.place(child);
            animalsMap.put(place, child);
            child.addObserver(map);

        }
    }

    public void target(Vector2d position, int n){
        if (!this.isTracking && animalsMap.keySet().contains(position)) {
            this.ageToSummarize = age+n;
            ArrayList<Animal> animals = new ArrayList<>(animalsMap.get(position));
            int maxEnergy = 0;
            for(Animal animal: animals)
                if (animal.energy > maxEnergy)
                    maxEnergy = animal.energy;
            ArrayList<Animal> alphas = new ArrayList<>();
            for(Animal animal: animals)
                if(animal.energy == maxEnergy)
                    alphas.add(animal);
            Animal chosen = alphas.get(new Random().nextInt(alphas.size()));
            chosen.changeType(AnimalType.TARGETED);
            this.isTracking = true;
        }
    }

    public void targetMainGenome(){
        Genotype main = spectator.mainGenome;
        for(Animal animal: animalsMap.values()){
            if(animal.gene.equals(main)){
                int x = animal.getPosition().x;
                int y = animal.getPosition().y;
                visualizer.changeColor(x,y,Color.MEDIUMAQUAMARINE);
            }
        }
    }

    public void endOfSymulation(){
        spectator.showEndStatistics(age);
    }

    public void simulate(){
        new Thread (() ->{
            while (!(this.visualizer.paused) && this.visualizer.onGoing) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                day();
                while(this.visualizer.paused){
                    System.out.print("");
                }
            }
        }).start();
    }

    public void day(){
//        System.out.println("dzień dobry");
        int newGrasses = map.placeGrasses();

        spectator.placeGrass(newGrasses);

        run();

        eating();

        copulations();

        for(int x =0; x<width;x++){
            for(int y =0; y<height;y++){
                if(map.isGrassAt(new Vector2d(x,y))) {
                    visualizer.changeColor(x, y, Color.GREEN);
                }
                else {
                    visualizer.changeColor(x, y, Color.LIGHTGRAY);
                }
            }
        }
        for(Vector2d position: animalsMap.keySet()){
            int x = position.x;
            int y = position.y;
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

        int sumChildAmount =0;
        for(Animal animal : animalsMap.values()) sumChildAmount+=animal.childrenNumber;

        String statistics = spectator.toString(sumChildAmount, this.age, (ageToSummarize ==age));

//      jesli epoka w której się znajdujemy jest epoką do której mieliśmy monitorować wybrane zwierzę
//      to przestajemy je obserwować i wypisujemy jego dane
        if(age == ageToSummarize){
            for(Animal animal: animalsMap.values()) animal.changeType(AnimalType.ORDINARY);
            isTracking = false;
        }

        visualizer.addStatistics(statistics, (ageToSummarize == age));

        this.age++;

    }
}
