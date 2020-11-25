package agh.cs.projekt;

public enum MoveDirection {
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT;
    public String toString(){
        switch(this){
            case FORWARD: return "przód";
            case BACKWARD: return "tył";
            case RIGHT: return "prawo";
            case LEFT: return "lewo";
            default: return null;
        }
    }
}
