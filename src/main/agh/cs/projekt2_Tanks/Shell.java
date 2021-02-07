package agh.cs.projekt2_Tanks;


public class Shell {
    private  int movement;
    private  boolean bouncing = false;
    private  boolean armourPiercing = false;
    private int direction;
    private Vector2d position;

    public Shell(PowerUpsName upgrade, int movement, int direction, Vector2d position){
        this.movement = movement;
        if (upgrade == PowerUpsName.BouncingShell) bouncing = true;
        else if(upgrade == PowerUpsName.ArmourPiercingShell) armourPiercing = true;
        this.direction = direction;
        this.position = position;
    }

    public void move(Vector2d newPosition){
        position = newPosition;
    }

    public void turn(Turn turn){
        if (turn == Turn.Left)
            direction +=7;
        if (turn == Turn.Right)
            direction++;
        direction = direction%8;
    }

    public int getShellDamage(){
        if ( armourPiercing) return 2;
        else return 1;
    }

    public Vector2d getPosition(){ return position; }

    public int getDirection(){
        return direction;
    }

    public boolean isBouncing(){
        return bouncing;
    }

    public boolean isArmourPiercing(){
        return armourPiercing;
    }

    public int getMovement(){
        return movement;
    }

    public String toString(){
        return (position.toString() + " " + direction);
    }

}
