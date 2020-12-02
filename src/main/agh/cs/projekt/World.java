package agh.cs.projekt;

import agh.cs.lab1.GrassField;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.Random;

public class World {
//    static void run (MoveDirection[] instructions){
//        for (MoveDirection move: instructions) {
//            switch(move){
//                case FORWARD:
//                    System.out.println("do przodu");
//                    break;
//                case BACKWARD:
//                    System.out.println("do tyłu");
//                    break;
//                case RIGHT:
//                    System.out.println("w prawo");
//                    break;
//                case LEFT:
//                    System.out.println("w lewo");
//                    break;
//            }
//        }
//        }
//    static MoveDirection[] change(String[] words)
//        {
//            MoveDirection[] directions= new MoveDirection[words.length];
//            for (int i=0; i<words.length; i++) {
//                String word = words[i];
//                switch (word)
//                {
//                    case "f":
//                        directions[i]= MoveDirection.FORWARD;
//                        break;
//                    case "b":
//                        directions[i]= MoveDirection.BACKWARD;
//                        break;
//                    case "r":
//                        directions[i]= MoveDirection.RIGHT;
//                        break;
//                    case "l":
//                        directions[i]= MoveDirection.LEFT;
//                        break;
//                    default:
//                        break;
//                }
//
//            }
//            return directions;
//        }
    public static void main(String[] args){

            String[] tab = {"f", "l", "b", "left", "f", "right"};
            MoveDirection[] directions = new OptionsParser().parse(tab);
            JungleMap map = new JungleMap(10,4,4,2);
            Vector2d[] positions = { new Vector2d(3,3), new Vector2d(3,3) };
            SimulationEngine engine = new SimulationEngine(map, positions, 4, 2);
//            System.out.println(map.toString());
//            engine.show();
//            System.out.println(map.grassFieldsMap.size());
//            System.out.println();
            engine.eating();
//            engine.show();
//            System.out.println(map.grassFieldsMap.size());
//            System.out.println();
            engine.run();
            engine.eating();
//            engine.show();
//            System.out.println(map.grassFieldsMap.size());
            System.out.println(map.toString());
    }

}
