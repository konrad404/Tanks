package agh.cs.lab1;

public class World {
    static void run (Direction[] instructions){
        for (Direction move: instructions) {
            switch(move){
                case FORWARD:
                    System.out.println("do przodu");
                    break;
                case BACKWARD:
                    System.out.println("do tyłu");
                    break;
                case RIGHT:
                    System.out.println("w prawo");
                    break;
                case LEFT:
                    System.out.println("w lewo");
                    break;
            }
        }
        }
    static Direction[] change(String[] words)
        {
            Direction[] directions= new Direction[words.length];
            for (int i=0; i<words.length; i++) {
                String word = words[i];
                switch (word)
                {
                    case "f":
                        directions[i]= Direction.FORWARD;
                        break;
                    case "b":
                        directions[i]= Direction.BACKWARD;
                        break;
                    case "r":
                        directions[i]= Direction.RIGHT;
                        break;
                    case "l":
                        directions[i]= Direction.LEFT;
                        break;
                    default:
                        break;
                }

            }
            return directions;
        }
    public static void main(String[] args){
        //System.out.println("system start");
        //String[] tab = {"f", "f" , "r", "l"};
        //Direction[] directions = change(args);
        //run(directions);
        //System.out.println("system stop");
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }

}
