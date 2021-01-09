package agh.cs.projekt; // przydałby się jakiś podział na pakiety
// plik konfiguracyjny to nie source, tylko resource, więc nie powinien w ogóle być w katalogu src

import javafx.scene.paint.Color;

import java.util.*;

public class Animal {
    private final JungleMap map;
    private Vector2d position;
    private int age;
    private int energy;
    public final Genotype gene;
    private int direction = new Random().nextInt(8); // kierunek jako int?
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private final int moveEnergy;
    public int childrenNumber;  // public?
    public AnimalType type; // czy to na pewno cecha zwierzęcia?
    private final Animal parent1;
    private final Animal parent2;
    private final int mapHeight;
    private final int mapWidth;

    public Animal(JungleMap map, Vector2d initialPosition, int energy, Genotype gene, int moveEnergy,
                  Animal parent1, Animal parent2, AnimalType type) {
        this.age = 0;
        this.map = map;
        this.energy = energy;
        this.gene = gene;
        this.moveEnergy = moveEnergy;
        this.position = initialPosition;
        this.type = type;
        this.childrenNumber = 0;
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.mapHeight = map.getRightUpCorner().y + 1;
        this.mapWidth = map.getRightUpCorner().x + 1;

    }


    public Vector2d getPosition() {
        return position;
    }


    //    zmienić żeby nie było przekazywanego zwierzęcia do zwierzęcia
    public void move() {
        int turnId = new Random().nextInt(32);  // nowy obiekt co wywołanie
        direction += gene.genotype[turnId];
        direction %= 8;
        Vector2d newPosition = position.goInDirection(direction, mapHeight, mapWidth);
        map.positionChanged(this, position, newPosition);
        position = newPosition;
        energy -= moveEnergy;
        if (energy <= 0) {
            map.death(this, position);
            if (parent1 != null)
                parent1.childrenNumber--;
            if (parent2 != null)
                parent2.childrenNumber--;
        } else this.age++;
    }

    public void eat(int bonusenergy) {
        this.energy += bonusenergy;
    }

    public int giveBirth() {
        int energyloss = energy / 4;
        energy -= energyloss;
        childrenNumber++;
        return energyloss;
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void changeType(AnimalType newType) {
        this.type = newType;
    }

    public Color getcolor() {    // GUI powinno być oddzielone od logiki aplikacji
        if (energy < 20) return Color.LIGHTCORAL;
        else if (energy < 50) return Color.BROWN;
        else return Color.RED;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }
}
