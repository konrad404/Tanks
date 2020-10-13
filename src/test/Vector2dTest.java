import agh.cs.lab1.Vector2d;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class Vector2dTest {
    @Test
    public void to_String(){
        Vector2d tmp = new Vector2d(1,2);
        assertEquals(tmp.toString(), "(1,2)");
    }
    @Test
    public void Precedes(){
        Vector2d tmp1 = new Vector2d(1,3);
        Vector2d tmp2 = new Vector2d(1,4);
        assertEquals(tmp1.precedes(tmp2), true);
    }
    @Test
    public void Precedes2(){
        Vector2d tmp1 = new Vector2d(1,3);
        Vector2d tmp2 = new Vector2d(1,4);
        assertEquals(tmp2.precedes(tmp1), false);
    }
    @Test
    public void Fallows(){
        Vector2d tmp1 = new Vector2d(1,3);
        Vector2d tmp2 = new Vector2d(1,4);
        assertEquals(tmp1.precedes(tmp2), true);
    }
    @Test
    public void Fallows2(){
        Vector2d tmp1 = new Vector2d(1,3);
        Vector2d tmp2 = new Vector2d(1,4);
        assertEquals(tmp2.precedes(tmp1), false);
    }
    @Test
    public void UpperRight(){
        Vector2d tmp1 = new Vector2d(2,3);
        Vector2d tmp2 = new Vector2d(1,4);
        Vector2d tmp3 = new Vector2d(2,4);
        assertEquals(tmp1.upperRight(tmp2), tmp3 );
    }
    @Test
    public void LowwerLeft(){
        Vector2d tmp1 = new Vector2d(2,3);
        Vector2d tmp2 = new Vector2d(1,4);
        Vector2d tmp3 = new Vector2d(1,3);
        assertEquals(tmp1.lowwerLeft(tmp2), tmp3 );
    }
    @Test
    public void Add(){
        Vector2d tmp1 = new Vector2d(2,3);
        Vector2d tmp2 = new Vector2d(1,-4);
        Vector2d tmp3 = new Vector2d(3,-1);
        assertEquals(tmp1.add(tmp2), tmp3 );
    }
    @Test
    public void Substract(){
        Vector2d tmp1 = new Vector2d(2,3);
        Vector2d tmp2 = new Vector2d(1,-4);
        Vector2d tmp3 = new Vector2d(1,7);
        assertEquals(tmp1.substract(tmp2), tmp3 );
    }
    @Test
    public void Opposite(){
        Vector2d tmp1 = new Vector2d(2,-3);
        Vector2d tmp2 = new Vector2d(-2,3);
        assertEquals(tmp1.opposite(), tmp2 );
    }
    @Test
    public void Equals(){
        Vector2d tmp1 = new Vector2d(2,-3);
        Vector2d tmp2 = new Vector2d(-2,3);
        assertEquals(tmp1.equals(tmp2), false );
    }
    @Test
    public void Equals2(){
        Vector2d tmp1 = new Vector2d(2,-3);
        Vector2d tmp2 = new Vector2d(2,-3);
        assertEquals(tmp1.equals(tmp2), true );
    }
    @Test
    public void Equals3(){
        Vector2d tmp1 = new Vector2d(2,-3);
        int x =0;
        assertEquals(tmp1.equals(x), false );
    }
}
