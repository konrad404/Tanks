package agh.cs.projekt;

import java.util.*;

public class JungleMap extends AbstractWorldMap {
    private List<Animal> animals = new ArrayList<>();
    public List<Grass> grassfields = new ArrayList<>();
    public Map<Vector2d, Grass> grassFieldsMap = new HashMap<>();
    private Vector2d left_down_corner= new Vector2d(0,0);
    private Vector2d right_up_corner;
    private Vector2d jungle_left_down_corner;
    private Vector2d jungle_right_up_corner;
    int mapHeight;
    int mapWidth;
    int jungleHeight;
    int jungleWidth;
//    n jako liczba roslin na poczatku mapy
    public JungleMap(int n, int mapHeight, int mapWidth, int jungleHeight, int jungleWidth){
        right_up_corner = new Vector2d (mapWidth,mapHeight);
        jungle_left_down_corner = new Vector2d((mapWidth-jungleWidth)/2,(mapHeight-jungleHeight)/2);
        jungle_right_up_corner = new Vector2d((mapWidth-jungleWidth)/2+jungleWidth-1, (mapHeight-jungleHeight)/2+jungleHeight-1);
        placeGrass(n);
    }
//funkcja mająca na celu późniejsze dodawanie trawy na koniec dnia
    public void placeGrass(int n){
        int jungleN;
        int notjungleN= n/2;
        if (n%2 == 0)  jungleN=n/2;
        else  jungleN=n/2+1;
        for (int i =0; i<jungleN; i++){
            int x = Math.abs(jungle_left_down_corner.x+(new Random().nextInt(jungleWidth)));
            int y = Math.abs(jungle_left_down_corner.y+(new Random().nextInt(jungleHeight)));
            Vector2d position = new Vector2d(x,y);
            if (!(grassAt(position)==null)) {
                Grass grass = new Grass(position);
                grassfields.add(grass);
                grassFieldsMap.put(position, grass);
            }
            else i--;
        }
        for (int i =0; i<notjungleN; i++){
            int x = Math.abs((new Random().nextInt(mapWidth)));
            int y = Math.abs((new Random().nextInt(mapHeight)));
            if (y>=jungle_left_down_corner.y &&  y <=jungle_right_up_corner.y &&
                    x>=jungle_left_down_corner.x && x<=jungle_right_up_corner.x ) {
                i--;
                continue;
            }
            Vector2d position = new Vector2d(x,y);
            if (!(grassAt(position)==null)) {
                Grass grass = new Grass(position);
                grassfields.add(grass);
                grassFieldsMap.put(position, grass);
            }
            else i--;
        }
    }
    public Object grassAt(Vector2d position) {
        if (grassFieldsMap.containsKey(position)) return grassFieldsMap.get(position);
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        for(int i =0; i< animals.size(); i++){
            if (position.equals(animals.get(i).getPosition())) return false;
        }
        return true;
    }


    @Override
    public Object objectAt(Vector2d position) {
        if (animalsMap.containsKey(position)) {
            if (animalsMap.get(position).getPosition().equals(position))
                return animalsMap.get(position);
        }
        for(int i =0; i< grassfields.size(); i++){
            if (position.equals(grassfields.get(i).getPosition())) return grassfields.get(i);
        }
        return null;
    }
    @Override
    protected Vector2d lowerLeft() {
        for (int i =0 ; i < animals.size(); i++){
            left_down_corner = animals.get(i).getPosition().lowerLeft(left_down_corner);
        }
        return left_down_corner;
    }

    @Override
    protected Vector2d upperRight() {
        for (int i =0 ; i < animals.size(); i++){
            right_up_corner = animals.get(i).getPosition().upperRight(right_up_corner);
        }
        return right_up_corner;
    }

}
