package agh.cs.projekt2_Tanks;

import java.util.*;

public class Battliefield{
//  lewy dolny róg to zawsze (0,0)
    private final agh.cs.projekt2_Tanks.Vector2d right_up_corner;
    private final Map<agh.cs.projekt2_Tanks.Vector2d, Tank> tankMap = new HashMap<>();
    private final Map<agh.cs.projekt2_Tanks.Vector2d, Obstacle> obstacleMap = new HashMap<>();
    private final Map<agh.cs.projekt2_Tanks.Vector2d, PowerUp> powerUpMap = new HashMap<>();


    public Battliefield(int mapHeight, int mapWidth){
        right_up_corner = new agh.cs.projekt2_Tanks.Vector2d(mapWidth-1,mapHeight-1);
    }

    public agh.cs.projekt2_Tanks.Vector2d getUpperRightCorner(){
        return right_up_corner;
    }

    public void placeAbstractObject(AbstractObject object){
        if (object instanceof Tank) {
            tankMap.put(((Tank) object).getPosition(), (Tank) object);
        }
        else if (object instanceof Obstacle){
            obstacleMap.put(((Obstacle) object).getPosition(), (Obstacle) object);
        }
    }

    public void placePowerUp(PowerUp powerUp){
        powerUpMap.put(powerUp.getPosition(), powerUp);
    }

    public void tankPositionChanged(Tank tank, agh.cs.projekt2_Tanks.Vector2d oldPosition){
        tankMap.remove(oldPosition);
        tankMap.put(tank.getPosition(),tank);
    }

    public void disappearAbstractObject(AbstractObject object){
        if (object instanceof Tank){
            tankMap.remove(((Tank) object).getPosition());
        }
        else if (object instanceof Obstacle){
            obstacleMap.remove(((Obstacle) object).getPosition());
        }
    }

    public void removePowerUp (PowerUp powerUp){
        powerUpMap.remove(powerUp.getPosition());
    }

    public List<Tank> getTanks(){
        return Collections.unmodifiableList(new ArrayList<>(tankMap.values()));
    }


    public PowerUp powerUpAt(agh.cs.projekt2_Tanks.Vector2d position){
        if(powerUpMap.containsKey(position))
            return powerUpMap.get(position);
        else return null;
    }

    public AbstractObject abstractObjectAt(agh.cs.projekt2_Tanks.Vector2d position) {
        if (obstacleMap.containsKey(position))
            return obstacleMap.get(position);
        else if (tankMap.containsKey(position))
            return tankMap.get(position);
        return null;
    }

    public boolean isAbstractObjectAt(agh.cs.projekt2_Tanks.Vector2d position){
        return (abstractObjectAt(position) != null);
    }

    public boolean anyPlaceForEnemy(Vector2d playerPosition){
        Vector2d position;
        for(int x = 0; x<= right_up_corner.x; x++)
            for(int y = 0; y<= right_up_corner.y; y++){
                position = new Vector2d(x,y);
                if(!isAbstractObjectAt(position) && position.isFarEnough(playerPosition))
                    return true;
            }
        return false;
    }

    public boolean anyPlaceForObstacle(){
        Vector2d position;
        for(int x = 0; x<= right_up_corner.x; x++)
            for(int y = 0; y<= right_up_corner.y; y++){
                position = new Vector2d(x,y);
                if(!isAbstractObjectAt(position))
                    return true;
            }
        return  false;
    }


}
