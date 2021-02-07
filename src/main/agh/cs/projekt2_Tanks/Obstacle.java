package agh.cs.projekt2_Tanks;

public class Obstacle extends AbstractObject {
    private final Vector2d position;
    private int health;
    private final Battliefield map;

    public Obstacle(Vector2d position, int health, Battliefield map){
        this.position = position;
        this.health= health;
        this.map = map;
    }

    public Vector2d getPosition(){
        return position;
    }
    public void blowUp(){
        map.disappearAbstractObject(this);
    }

    @Override
    public void getDamage(int damage){
        health -= damage;
        if (health <=0)
            this.die();

    }

    @Override
    public void die(){
        map.disappearAbstractObject(this);
    }

    @Override
    public String toString(){
        return (position.toString() + "health: " + health);
    }
}
