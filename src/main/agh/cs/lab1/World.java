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
        String[] tab = {"f","l","b","backw", "left", "f","right"};
        Animal pupil = new Animal();
        System.out.println(pupil);
        for (MoveDirection move : OptionsParser.parse(args)) {
            pupil.move(move);
        }
        System.out.println(pupil.toString());
    }

}
