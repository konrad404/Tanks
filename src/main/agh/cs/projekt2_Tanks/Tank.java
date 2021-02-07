package agh.cs.projekt2_Tanks;

public class Tank extends AbstractObject{
    private PowerUp doubleMove = null;
    private PowerUp doubleSpeedShell = null;
    private PowerUp armourPiercingShell = null;
    private PowerUp bouncingShell = null;
    private PowerUp immortality = null;
    private int health;
    private int direction;
    private Vector2d position;
    private final Battliefield map;
    private int shellMovement;
    private final SimulationEngine engine;

    public Tank(int health, int direction, Vector2d position, Battliefield map, SimulationEngine engine){
        this.health = health;
        this.direction = direction%8;
        this.position = position;
        this.map = map;
        this.engine = engine;
    }

    public Tank(int health, int direction, Vector2d position, Battliefield map,SimulationEngine engine, PowerUp powerUp){
        this.health = health;
        this.direction = direction%8;
        this.position = position;
        this.map = map;
        this.engine = engine;
        pickUpUpgrade(powerUp);
        useUpgrade(powerUp.getMode());
    }

    public void turn(Turn side){
        if (side == Turn.Left){
            direction = (direction + 7) % 8;
        }
        else {
            direction = (direction + 1) % 8;
        }
    }

    public void move(Vector2d newPosition){
        Vector2d oldPosition = position;
        position = newPosition;
        map.tankPositionChanged(this, oldPosition);
    }

    @Override
    public void getDamage(int damage){
        if(immortality == null || !immortality.isActive()){
            health -= damage;
            if (health <= 0)
                this.die();
        }
    }


    public void reducePowerUpsDuraction(){
        if (doubleMove != null && doubleMove.isActive()) {
            doubleMove.reduceDuration();
            if(doubleMove.isUsed()) {
                engine.removePowerup(doubleMove);
                doubleMove = null;
            }
        }
        if (doubleSpeedShell != null && doubleSpeedShell.isActive()) {
            doubleSpeedShell.reduceDuration();
            if (doubleSpeedShell.isUsed()) {
                engine.removePowerup(doubleSpeedShell);
                doubleSpeedShell = null;
            }
        }
        if (armourPiercingShell != null && armourPiercingShell.isActive()) {
            armourPiercingShell.reduceDuration();
            if (armourPiercingShell.isUsed()) {
                engine.removePowerup(armourPiercingShell);
                armourPiercingShell = null;
            }
        }
        if (bouncingShell != null && bouncingShell.isActive()) {
            bouncingShell.reduceDuration();
            if (bouncingShell.isUsed()) {
                engine.removePowerup(bouncingShell);
                bouncingShell = null;
            }
        }
        if (immortality != null && immortality.isActive()) {
            immortality.reduceDuration();
            if(immortality.isUsed()) {
                engine.removePowerup(immortality);
                immortality = null;
            }
        }
    }

    @Override
    public void die(){
        map.disappearAbstractObject(this);
    }

    public int getMoveNumber(){
        if(doubleMove != null && doubleMove.isActive())
            return 2;
        else return 1;

    }

    public Shell getShell(){
        if(doubleSpeedShell != null && doubleSpeedShell.isActive()){
            shellMovement =2;
        }
        else shellMovement =1;
        if(armourPiercingShell!= null && armourPiercingShell.isActive()) {
            return new Shell(PowerUpsName.ArmourPiercingShell, shellMovement, direction, position);
        }
        else if (bouncingShell != null && bouncingShell.isActive()){
            return new Shell(PowerUpsName.BouncingShell, shellMovement, direction, position);
        }
        else
            return new Shell(null, shellMovement, direction, position);
    }

    public Vector2d getPosition(){
        return position;
    }

    public int getDirection(){
        return direction;
    }

    public void pickUpUpgrade (PowerUp powerUp){
        PowerUpsName upgrade = powerUp.getMode();
        switch (upgrade){
            case DoubleMove->
                doubleMove = powerUp;
            case BonusHealth->
                health++;
            case Immortality->
                immortality = powerUp;
            case BouncingShell->
                bouncingShell = powerUp;
            case DoubleSpeedShell->
                doubleSpeedShell = powerUp;
            case ArmourPiercingShell->
                armourPiercingShell = powerUp;
        }
    }

    public boolean useUpgrade(PowerUpsName powerUpsName){
        switch (powerUpsName){
            case DoubleMove-> {
                if(doubleMove != null && !doubleMove.isActive())
                    doubleMove.activate();
                else return false;
            }
            case Immortality-> {
                if(immortality != null && !immortality.isActive())
                    immortality.activate();
                else return false;
            }
            case BouncingShell-> {
                if(bouncingShell != null && !bouncingShell.isActive())
                    bouncingShell.activate();
                else return false;
            }
            case DoubleSpeedShell-> {
                if(doubleSpeedShell!= null && !doubleSpeedShell.isActive())
                    doubleSpeedShell.activate();
                else return false;
            }
            case ArmourPiercingShell-> {
                if(armourPiercingShell != null && !armourPiercingShell.isActive())
                    armourPiercingShell.activate();
                else return false;
            }
        }
        return true;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString(){
        return (position.toString() + " " +  direction + " " +  "health: " + health);
    }



}
