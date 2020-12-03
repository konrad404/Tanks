package agh.cs.projekt;

import agh.cs.lab1.GrassField;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.Random;

public class World {
    public static void main(String[] args){

            JungleMap map = new JungleMap(10,4,4,2);
            Vector2d[] positions = { new Vector2d(3,3), new Vector2d(3,3) };
            SimulationEngine engine = new SimulationEngine(map, positions, 4, 2, 4);
            System.out.println(map.toString());
//            engine.show();
//            System.out.println(map.grassFieldsMap.size());
//            System.out.println();
            engine.eating();
//            engine.show();
//            System.out.println(map.grassFieldsMap.size());
//            System.out.println();
            engine.copulations();
//            System.out.println(map.toString());
//            engine.show();
            engine.run();
//            System.out.println();
//            engine.show();
            engine.eating();
            engine.show();
            System.out.println(map.toString());
    }

}
