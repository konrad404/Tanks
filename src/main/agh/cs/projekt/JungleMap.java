package agh.cs.projekt;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

import java.util.*;

public class JungleMap extends AbstractWorldMap {
    private ListMultimap <Vector2d, Animal> animalJungleMap = ArrayListMultimap.create();
    public Map<Vector2d, Grass> grassFieldsMap = new HashMap<>();
    private final Vector2d left_down_corner= new Vector2d(0,0);
    private final Vector2d right_up_corner;
    private final Vector2d jungle_left_down_corner;
    private final Vector2d jungle_right_up_corner;
    private final int jungleHeight;
    private final int jungleWidth;
    private final MapVisualizer image = new MapVisualizer(this);

//    n jako liczba roslin na poczatku mapy
    public JungleMap(int mapHeight, int mapWidth, float jungleRatio){
        jungleHeight = (int) (mapHeight*jungleRatio);
        jungleWidth = (int) (mapWidth*jungleRatio);
        this.mapHeight= mapHeight;
        this.mapWidth = mapWidth;
        right_up_corner = new Vector2d (mapWidth-1,mapHeight-1);
        jungle_left_down_corner = new Vector2d((mapWidth-jungleWidth)/2,(mapHeight-jungleHeight)/2);
        jungle_right_up_corner = new Vector2d((mapWidth-jungleWidth)/2+jungleWidth-1, (mapHeight-jungleHeight)/2+jungleHeight-1);
    }

//funkcje mająca na celu późniejsze dodawanie pojedyńczej trawy w i poza junglą
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
        if (y>=jungle_left_down_corner.y &&  y <=jungle_right_up_corner.y &&
                x>=jungle_left_down_corner.x && x<=jungle_right_up_corner.x ) {
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
//        System.out.println("miejsca poza junglą: " + placesOutOfJungle);
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

//        jeśli nie to wywołujemy funkcję dodania pojedyńczej trawy na losowe miejsce
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

//        jeśli nie to wywołujemy funkcję dodania pojedyńczej trawy na losowe miejsce
        else if(placesOutOfJungle >1)
            placeGrassOutOfJungle();
        else placed --;
        return placed;
    }

    public boolean isGrassAt(Vector2d position){
        return grassFieldsMap.containsKey(position) == true;
    }

    public void removeGrass(Vector2d position){
        Grass dead = grassFieldsMap.remove(position);
        dead = null;
    }

//    public Vector2d getRightUpCorner(){
//        return right_up_corner;
//    }

    public Vector2d findBirthPlace(Vector2d position) {
        Vector2d newPosition = position;
//      sprawdzanie czy dokoła jest jakieś wolne miejsce
        boolean flag = false;
        for (int i = 0; i < 8; i++) {
            newPosition = position.goInDirection(i,mapHeight,mapWidth);
            if (!isOccupied(newPosition)) flag = true;
        }

        int direction = new Random().nextInt(8);
//        jeśli jest wolne miejsce (chociaż jedno to wybieramy je losowo)
        if (flag){
            while (isOccupied(position.goInDirection(direction, mapHeight, mapWidth)))
                direction = new Random().nextInt(8);
        }
        return (position.goInDirection(direction,mapHeight,mapWidth));
    }

    public Vector2d getRightUpCorner(){
        return right_up_corner;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

//    zamien na void
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
    public String toString(){
        String mapa = image.draw(left_down_corner,right_up_corner);
        return mapa;
    }

    public ListMultimap <Vector2d, Animal> getAnimalsMap (Vector2d position){
        return animalJungleMap;
    }

}
