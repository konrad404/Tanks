import agh.cs.lab1.*;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
public class MovesTest {
    @Test
    public void orientation(){
        Animal test = new Animal();
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.RIGHT);
        String a = ("(2,2) Południe");
        assertEquals(test.toString(), a);
    }
    @Test
    public void position(){
        Animal test = new Animal();
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.LEFT);
        test.move(MoveDirection.RIGHT);
        test.move(MoveDirection.FORWARD);
        test.move(MoveDirection.BACKWARD);
        test.move(MoveDirection.BACKWARD);
        test.move(MoveDirection.BACKWARD);
        String a = ("(2,4) Południe");
        assertEquals(test.toString(), a);
    }
    @Test
    public void map(){
        Animal test = new Animal();
        test.move(MoveDirection.FORWARD);
        test.move(MoveDirection.FORWARD);
        test.move(MoveDirection.FORWARD);
        test.move(MoveDirection.FORWARD);
        String a = ("(2,4) Północ");
        assertEquals(test.toString(), a);
    }
    @Test
    public void map1(){
        Animal test = new Animal();
        test.move(MoveDirection.BACKWARD);
        test.move(MoveDirection.BACKWARD);
        test.move(MoveDirection.BACKWARD);
        test.move(MoveDirection.BACKWARD);
        String a = ("(2,0) Północ");
        assertEquals(test.toString(), a);
    }
    @Test
    public void chain(){
        Animal test = new Animal();
        String[] tab = {"fo","l","b","backw", "lefta", "f","right", "right"};
        MoveDirection[] moves = OptionsParser.parse(tab);
        for (int i=0; i<moves.length; i++) {
            test.move(moves[i]);
        }
        String a = ("(2,2) Wschód");
        assertEquals(test.toString(), a);
    }

}
