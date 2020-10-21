package agh.cs.lab1;

public class OptionsParser {
    public static MoveDirection[] parse (String[] tab){
        int len=0;
        String[] directions = {"forward", "f", "backward", "b", "right", "r", "left", "l"};
        for (String move: tab){
            for (String direction : directions){
                if(move.equals(direction)){
                    len++;
                    break;
                }
            }
        }
        int i=0;
        MoveDirection[] movedirections = new MoveDirection[len];
        for (String move : tab){
            switch(move){
                case "forward":
                case "f":
                    movedirections[i] = MoveDirection.FORWARD;
                    i++;
                    break;
                case "backward":
                case "b":
                    movedirections[i] = MoveDirection.BACKWARD;
                    i++;
                    break;
                case "right":
                case "r":
                    movedirections[i] = MoveDirection.RIGHT;
                    i++;
                    break;
                case "left":
                case "l":
                    movedirections[i] = MoveDirection.LEFT;
                    i++;
                    break;
            }
        }
        return movedirections;
    }
}
