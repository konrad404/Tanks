package agh.cs.projekt2_Tanks;

import java.util.Random;

public class PowerUp {
    private final Vector2d position;
    private final PowerUpsName mode;
    private int timeLeft;
    private final Battliefield map;
    private static final Random random = new Random();
    private int durationLeft;
    private boolean active = false;
    private boolean used = false;

    public PowerUp(Vector2d position, int lifeTime, Battliefield map){
        this.position = position;
        this.mode = PowerUpsName.values()[random.nextInt(PowerUpsName.values().length)];
        timeLeft = lifeTime;
        this.map = map;
        durationLeft = this.startDuration();
    }

    private int startDuration(){
        return switch (mode) {
            case ArmourPiercingShell -> 4;
            case DoubleSpeedShell -> 5;
            case BouncingShell -> 3;
            case Immortality -> 2;
            case BonusHealth -> 0;
            case DoubleMove -> 4;
        };
    }

    public void activate(){
        active = true;
    }

    public boolean isActive(){
        return active;
    }

    public void reduceDuration(){
        durationLeft --;
        if (durationLeft ==0)
            used = true;
    }

    public boolean isUsed(){
        return used;
    }

    public PowerUpsName getMode(){
        return mode;
    }

    public Vector2d getPosition(){
        return position;
    }

    public void missDay(){
        timeLeft--;
        if (timeLeft == 0) this.disappear();
    }

    public void disappear(){
        map.removePowerUp(this);
    }

    public String toString(){
        return mode.toString();
    }
}
