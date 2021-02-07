package agh.cs.projekt2_Tanks;

public enum Turn {
    Left,
    Right;

    public String toSting(){
        if (this == Right) return "RIGHT";
        else if (this == Left) return "LEFT";
        else return "";
    }
}
