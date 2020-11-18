package agh.cs.lab1;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class RectengularMapSingleTests {

    @Test
    public void RectangularMap_place(){
        AbstractWorldMap map = new RectangularMap(4,4);
        boolean flag= true;
        try {
            map.place(new Animal(map, new Vector2d(4,5)));
        }
        catch (IllegalArgumentException ex){
            flag = false;
        }
        assertEquals(flag , false);
    }

    @Test
    public void RectangularMap_place2(){
        AbstractWorldMap map = new RectangularMap(4,4);
        boolean flag= true;
        try {
            map.place(new Animal(map, new Vector2d(4,4)));
            map.place(new Animal(map, new Vector2d(4,4)));
        }
        catch (IllegalArgumentException ex){
            flag = false;
        }
        assertEquals(flag , false);
    }

    @Test
    public void RectangularMap_isOccupied2(){
        AbstractWorldMap map = new RectangularMap(4,4);
        map.place(new Animal(map));
        assertEquals(map.isOccupied(new Vector2d(2,2)), true);
    }

    @ Test
    public void RectangularMap_canMoveTo(){
        AbstractWorldMap map = new RectangularMap(4,4);
        map.place(new Animal(map, new Vector2d(1,2)));
        assertEquals(map.canMoveTo(new Vector2d(1,2)), false);
    }

    @ Test
    public void RectangularMap_canMoveTo2(){
        IWorldMap map = new RectangularMap(4,4);
        assertEquals(map.canMoveTo(new Vector2d(5,2)), false);
    }
}
