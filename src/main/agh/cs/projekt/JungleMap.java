package agh.cs.projekt;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.*;

public class JungleMap implements IWorldMap, IPositionChangeObserver {
    private final ListMultimap <Vector2d, Animal> animalJungleMap = ArrayListMultimap.create();
    private final Map<Vector2d, Grass> grassFieldsMap = new HashMap<>();
//  prawy dolny róg to zawsze (0,0)
    private final Vector2d right_up_corner;
    private final Vector2d jungle_left_down_corner;
    private final Vector2d jungle_right_up_corner;
    private final int jungleHeight;
    private final int jungleWidth;
    private final int mapHeight;
    private final int mapWidth;

    public JungleMap(int mapHeight, int mapWidth, float jungleRatio){
        jungleHeight = (int) (mapHeight*jungleRatio);
        jungleWidth = (int) (mapWidth*jungleRatio);
        this.mapHeight= mapHeight;
        this.mapWidth = mapWidth;
        right_up_corner = new Vector2d (mapWidth-1,mapHeight-1);
        jungle_left_down_corner = new Vector2d((mapWidth-jungleWidth)/2,(mapHeight-jungleHeight)/2);
        jungle_right_up_corner = new Vector2d((mapWidth-jungleWidth)/2+jungleWidth-1, (mapHeight-jungleHeight)/2+jungleHeight-1);
    }

    public void placeGrassInJungle(){
        int x = Math.abs(jungle_left_down_corner.x+(new Random().nextInt(jungleWidth)));
        int y = Math.abs(jungle_left_down_corner.y+(new Random().nextInt(jungleHeight)));
        Vector2d position = new Vector2d(x,y);
//        System.out.println(position.toString());
        if (objectAt(position)==null) {
            Grass grass = new Grass(position);
            grassFieldsMap.put(position, grass);
        }
        else placeGrassInJungle();
    }

    public void placeGrassOutOfJungle(){
        int x = Math.abs((new Random().nextInt(mapWidth)));
        int y = Math.abs((new Random().nextInt(mapHeight)));
        Vector2d position = new Vector2d(x,y);
        if(position.precedes(jungle_right_up_corner) && position.follows(jungle_left_down_corner)){
            placeGrassOutOfJungle();
        }
        else if(objectAt(position)==null) {
            Grass grass = new Grass(position);
            grassFieldsMap.put(position, grass);
        }
        else placeGrassOutOfJungle();
    }

    private int emptyPlacesInJungle(){
        int count =0;
        for (int i =jungle_left_down_corner.x;i<=jungle_right_up_corner.x;i++){
            for (int j =jungle_left_down_corner.y;j<=jungle_right_up_corner.y;j++){
                if (objectAt(new Vector2d(i,j)) == null) count ++;
            }
        }
        return count;
    }

    private int emptyPlacesOutOfJungle(){
        int count =0;
        for (int i =0;i< mapWidth;i++){
            for (int j =0;j<mapHeight;j++){
                Vector2d position = new Vector2d(i,j);
                if (!(position.follows(jungle_left_down_corner) && position.precedes(jungle_right_up_corner))) {
                    if (objectAt(position) == null) count++;
                }
            }
        }
        return count;
    }

    public int placeGrasses() {
        int placed =2;
        int placesInJungle = emptyPlacesInJungle();
        int placesOutOfJungle = emptyPlacesOutOfJungle();
//      jeśli jest tylko jedno wolne miejsce to na nim stawiamy, bez losowania
        if (placesInJungle == 1){
            for (int i =jungle_left_down_corner.x;i<=jungle_right_up_corner.x;i++){
                for (int j =jungle_left_down_corner.y;j<=jungle_right_up_corner.y;j++){
                    Vector2d position = new Vector2d(i,j);
                    if (objectAt(position) == null) {
                        Grass grass = new Grass(position);
                        grassFieldsMap.put(position, grass);
                    }
                }
            }
        }

//        jeśli jest więcej miejsc to wywołujemy funkcję dodania pojedyńczej trawy na losowe miejsce
        else if(placesInJungle > 1)
            placeGrassInJungle();
        else placed --;

//      jeśli jest tylko jedno wolne miejsce to na nim stawiamy, bez losowania
        if (placesOutOfJungle == 1) {
            for (int i = 0; i < mapWidth; i++) {
                for (int j = 0; j < mapHeight; j++) {
                    Vector2d position = new Vector2d(i, j);
                    if (!(position.follows(jungle_left_down_corner) && position.precedes(jungle_right_up_corner))) {
                        if (objectAt(position) == null) {
                            Grass grass = new Grass(position);
                            grassFieldsMap.put(position, grass);
                        }
                    }
                }
            }
        }

//        jeśli jest więcej to wywołujemy funkcję dodania pojedyńczej trawy na losowe miejsce
        else if(placesOutOfJungle >1)
            placeGrassOutOfJungle();
        else placed --;
        return placed;
    }

    public boolean isGrassAt(Vector2d position){
        return grassFieldsMap.containsKey(position);
    }

    public void removeGrass(Vector2d position){
        grassFieldsMap.remove(position);
    }


    public Vector2d findBirthPlace(Vector2d position) {
        Vector2d newPosition;
//      sprawdzanie czy dokoła jest jakieś wolne miejsce
        boolean anyEmptyPlace = false;
        for (int i = 0; i < 8; i++) {
            newPosition = position.goInDirection(i,mapHeight,mapWidth);
            if (!isOccupied(newPosition)) anyEmptyPlace = true;
        }

        int direction = new Random().nextInt(8);
//        jeśli jest wolne miejsce (chociaż jedno to wybieramy je losowo)
        if (anyEmptyPlace){
            while (isOccupied(position.goInDirection(direction, mapHeight, mapWidth)))
                direction = new Random().nextInt(8);
        }
        return (position.goInDirection(direction,mapHeight,mapWidth));
    }

    public Vector2d getRightUpCorner(){
        return right_up_corner;
    }


    @Override
    public void place(Animal animal){
            animalJungleMap.put(animal.getPosition(), animal);
        }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition){
        animalJungleMap.remove(oldPosition, animal);
        animalJungleMap.put(newPosition, animal);
    }

    @Override
    public void death(Animal animal, Vector2d position){
        animalJungleMap.remove(position, animal);
    }



    @Override
    public Object objectAt(Vector2d position) {
        if(animalJungleMap.containsKey(position)){
            return animalJungleMap.get(position).get(0);
        }
        if (grassFieldsMap.containsKey(position)) return grassFieldsMap.get(position);
        return null;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }


}
