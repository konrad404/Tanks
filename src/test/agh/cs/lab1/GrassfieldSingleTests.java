package agh.cs.lab1;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import java.lang.IllegalArgumentException;

public class GrassfieldSingleTests {
    @Test
    public void grassfield_place() {
        AbstractWorldMap map = new GrassField(10);
        boolean flag= true;
        try {
            map.place(new Animal(map));
            map.place(new Animal(map));
        }
        catch (IllegalArgumentException ex){
            flag = false;
        }
        assertEquals(flag , false);
    }

    @Test
    public void grassfield_isOccupied(){
        AbstractWorldMap map = new GrassField(10);
        map.place(new Animal(map));
        assertEquals(map.isOccupied(new Vector2d(2,2)), true);
    }

    @ Test
    public void grassfield_canMoveTo(){
        AbstractWorldMap map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(1,2)));
        assertEquals(map.canMoveTo(new Vector2d(1,2)), false);
    }

    @ Test
    public void grassfield_canMoveTo2(){
        AbstractWorldMap map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(1,2)));
        assertEquals(map.canMoveTo(new Vector2d(2,2)), true);
    }


}
