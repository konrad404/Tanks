package agh.cs.lab1;

import agh.cs.lab1.*;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
public class MovesTest {
    @Test
    public void orientation(){
        AbstractWorldMap map = new RectangularMap(4,4);
        Animal test = new Animal(map);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.RIGHT);
        String a = ("S");
        assertEquals(test.toString(), a);
    }

    @Test
    public void map(){
        AbstractWorldMap map = new RectangularMap(4,4);
        Animal test = new Animal(map);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.LEFT);
        String a = ("N");
        assertEquals(test.toString(), a);
    }

    @Test
    public void chain(){
        AbstractWorldMap map = new RectangularMap(4,4);
        Animal test = new Animal(map);
        boolean flag= true;
        try {
            String[] tab = {"fo", "l", "b", "backw", "lefta", "f", "right", "right"};
            MoveDirection[] moves = OptionsParser.parse(tab);
            for (int i = 0; i < moves.length; i++) {
                test.move(moves[i]);
            }
        } catch (IllegalArgumentException e) {
            flag = false;
        }
        assertEquals(flag, false);
    }

}
