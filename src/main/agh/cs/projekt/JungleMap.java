package agh.cs.projekt;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

import java.util.*;

public class JungleMap extends AbstractWorldMap {
    private ListMultimap <Vector2d, Animal> animalJungleMap = ArrayListMultimap.create();
    public Map<Vector2d, Grass> grassFieldsMap = new HashMap<>();
    private Vector2d left_down_corner= new Vector2d(0,0);
    private Vector2d right_up_corner;
    private Vector2d jungle_left_down_corner;
    private Vector2d jungle_right_up_corner;
    int jungleHeight;
    int jungleWidth;
    private final MapVisualizer image = new MapVisualizer(this);

//    n jako liczba roslin na poczatku mapy
    public JungleMap(int n, int mapHeight, int mapWidth, int jungleRatio){
        jungleHeight = mapHeight/jungleRatio;
        jungleWidth = mapWidth/jungleRatio;
        this.mapHeight= mapHeight;
        this.mapWidth = mapWidth;
        right_up_corner = new Vector2d (mapWidth-1,mapHeight-1);
        jungle_left_down_corner = new Vector2d((mapWidth-jungleWidth)/2,(mapHeight-jungleHeight)/2);
        jungle_right_up_corner = new Vector2d((mapWidth-jungleWidth)/2+jungleWidth-1, (mapHeight-jungleHeight)/2+jungleHeight-1);
        placeGrass(n);
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

    public void placeGrass(int n) {

        int jungleN;
        int notjungleN = n / 2;
        if (n % 2 == 0) jungleN = n / 2;
        else jungleN = n / 2 + 1;
//        Jeśli nie ma wystarczającej liczby miejsc na nowe trawy to dodajemy trawy na
//        wszystkich wolnych miejscach (tak samo w jungli jak i po za nią)
        if (emptyPlacesInJungle() <= jungleN){
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

//        jeśli nie to wywołujemy n razy funkcję dodania pojedyńczej trawy na losowe miejsce
        else{
            for (int i =0; i< jungleN;i++) { placeGrassInJungle(); }
        }

        if (emptyPlacesOutOfJungle() <= notjungleN) {
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

        else {
            for (int i =0; i<notjungleN;i++) { placeGrassOutOfJungle(); }
        }
    }

    public boolean isGrassAt(Vector2d position){
        return grassFieldsMap.containsKey(position) == true;
    }

    public void removeGrass(Vector2d position){
        grassFieldsMap.remove(position);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

//    zamien na void
    @Override
    public boolean place(Animal animal){
            animalJungleMap.put(animal.getPosition(), animal);
            return true;
        }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition){
        animalJungleMap.remove(oldPosition, animal);
        animalJungleMap.put(newPosition, animal);
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
