package agh.cs.lab1;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
public class MapSingleTests {
    @Test
    public void grassfield_place(){
        IWorldMap map = new GrassField(10);
        map.place(new Animal(map));
        assertEquals(map.place(new Animal(map)), false);
    }
    @Test
    public void RectangularMap_place(){
        IWorldMap map = new RectangularMap(4,4);
        assertEquals(map.place(new Animal(map, new Vector2d(4,5))), false);
    }
    @Test
    public void RectangularMap_place2(){
        IWorldMap map = new RectangularMap(4,4);
        map.place(new Animal(map, new Vector2d(4,4)));
        assertEquals(map.place(new Animal(map, new Vector2d(4,4))), false);
    }
    @Test
    public void grassfield_isOccupied(){
        IWorldMap map = new GrassField(10);
        map.place(new Animal(map));
        assertEquals(map.isOccupied(new Vector2d(2,2)), true);
    }
    @Test
    public void RectangularMap_isOccupied2(){
        IWorldMap map = new RectangularMap(4,4);
        map.place(new Animal(map));
        assertEquals(map.isOccupied(new Vector2d(2,2)), true);
    }
//    @Test
//    public void grassfield_isOccupied2(){
//        IWorldMap map = new GrassField(10);
//        map.place(new Animal(map));
//        for(int i =0; i< map.grassfields.size(); i++){
//            if (new Vector2d(2,2).equals(map.listToString().get(i).getPosition())) return map.grassfields.get(i);
//        }
//        System.out.println(map.toString());
//        assertEquals(map.isOccupied(new Vector2d(2,3)), false);
//    }
    @ Test
    public void grassfield_canMoveTo(){
        IWorldMap map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(1,2)));
        assertEquals(map.canMoveTo(new Vector2d(1,2)), false);
    }
    @ Test
    public void RectangularMap_canMoveTo(){
        IWorldMap map = new RectangularMap(4,4);
        map.place(new Animal(map, new Vector2d(1,2)));
        assertEquals(map.canMoveTo(new Vector2d(1,2)), false);
    }
    @ Test
    public void grassfield_canMoveTo2(){
        IWorldMap map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(1,2)));
        assertEquals(map.canMoveTo(new Vector2d(2,2)), true);
    }
    @ Test
    public void RectangularMap_canMoveTo2(){
        IWorldMap map = new RectangularMap(4,4);
        assertEquals(map.canMoveTo(new Vector2d(5,2)), false);
    }

}
