package agh.cs.lab1;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
public class MapInternalTests {
    @Test
    public void integralTest1(){
        String[] args = {"l", "r", "r", "f", "f", "f", "l", "f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map1 = new RectangularMap(10,5);
        IWorldMap map2 = new RectangularMap(10,5);
        map1.place( new Animal(map1));
        map1.place(new Animal(map1, new Vector2d(1,2)));
        map1.run(directions);

        Animal cat = new Animal(map2, new Vector2d(2,3));
        Animal dog = new Animal(map2, new Vector2d(3,2));
        cat.move(MoveDirection.LEFT);
        dog.move(MoveDirection.RIGHT);
        map2.place(cat);
        map2.place(dog);
        assertEquals(map1.toString(), map2.toString());
    }
    @Test
    public void integralTest2_place(){
        String[] args = {"l", "r", "r", "f", "f", "f", "l", "f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map1 = new RectangularMap(10,5);
        IWorldMap map2 = new RectangularMap(10,5);
        map1.place( new Animal(map1));
        map1.run(directions);
        assertEquals((map1.place(new Animal(map1, new Vector2d(5,3)))), false);
    }
    @Test
    public void integralTest3_moveto(){
        String[] args = {"l", "r", "r", "f", "f", "f", "r", "l", "r"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map1 = new RectangularMap(10,5);
        IWorldMap map2 = new RectangularMap(10,5);
        map1.place( new Animal(map1));
        map1.place(new Animal(map1, new Vector2d(1,2)));
        map1.run(directions);
        assertEquals(map1.canMoveTo(new Vector2d(2,3)), false);
    }
}
