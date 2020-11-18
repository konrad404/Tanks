package agh.cs.lab1;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import java.lang.IllegalArgumentException;

public class MapInternalTests {
    @Test
    public void integralTest1(){
        String[] args = {"l", "r", "r", "f", "f", "f", "l", "f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        AbstractWorldMap map1 = new RectangularMap(10,5);
        AbstractWorldMap map2 = new RectangularMap(10,5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(1,2) };
        IEngine engine = new SimulationEngine(directions, map1, positions);
        engine.run();

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
        AbstractWorldMap map1 = new RectangularMap(10,5);
        Vector2d[] positions = { new Vector2d(2,2)};
        IEngine engine = new SimulationEngine(directions, map1, positions);
        engine.run();
        boolean flag = false;
        try{
            map1.place(new Animal(map1, new Vector2d(5,3)));
        }
        catch (IllegalArgumentException ex){
            flag = false;
        }
        assertEquals(flag , false);
    }
    @Test
    public void integralTest3_moveto(){
        String[] args = {"l", "r", "r", "f", "f", "f", "r", "l", "r"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        AbstractWorldMap map1 = new RectangularMap(10,5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(1,2) };
        IEngine engine = new SimulationEngine(directions, map1, positions);
        engine.run();
        boolean flag = false;
        try{
            map1.place(new Animal(map1, new Vector2d(2,3)));
        }
        catch (IllegalArgumentException ex){
            flag = false;
        }
        assertEquals(flag , false);
    }
}
