package agh.cs.lab1;

import agh.cs.lab1.MapDirection;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MapDirectionTest {
    @Test
    public void next_to_north(){
        MapDirection test = MapDirection.NORTH;
        assertEquals(test.next(), MapDirection.EAST);
    }
    @Test
    public void next_to_south(){
        MapDirection test = MapDirection.SOUTH;
        assertEquals(test.next(), MapDirection.WEST);
    }
    @Test
    public void next_to_east(){
        MapDirection test = MapDirection.EAST;
        assertEquals(test.next(), MapDirection.SOUTH);
    }
    @Test
    public void next_to_west(){
        MapDirection test = MapDirection.WEST;
        assertEquals(test.next(), MapDirection.NORTH);
    }
    @Test
    public void previous_to_north(){
        MapDirection test = MapDirection.NORTH;
        assertEquals(test.previous(), MapDirection.WEST);
    }
    @Test
    public void previous_to_south(){
        MapDirection test = MapDirection.SOUTH;
        assertEquals(test.previous(), MapDirection.EAST);
    }
    @Test
    public void previous_to_east(){
        MapDirection test = MapDirection.EAST;
        assertEquals(test.previous(), MapDirection.NORTH);
    }
    @Test
    public void previous_to_west(){
        MapDirection test = MapDirection.WEST;
        assertEquals(test.previous(), MapDirection.SOUTH);
    }

}
