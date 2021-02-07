package agh.cs.projekt2_Tanks;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShellSimulator {
    private final ListMultimap<Vector2d, Shell> shellMap =  ArrayListMultimap.create();
    private final ListMultimap<Vector2d, Shell> shellMapAfterMove =  ArrayListMultimap.create();
    private final Battliefield battliefield;
    private final Map<Vector2d, Integer> damageMap = new HashMap<>();
    private final Vector2d rightUpCorner;


    ShellSimulator(Battliefield battliefield, Vector2d rightUpCorner){
        this.battliefield = battliefield;
        this.rightUpCorner = rightUpCorner;
    }

    public void placeNewShell(Shell shell){
        shellMap.put(shell.getPosition(), shell);
    }

    private void moveArmourPiercingShell(Shell shell){
        Vector2d newPosition;
        for (int i=0; i<shell.getMovement();i++){
            newPosition = shell.getPosition().positionAfterMove(shell.getDirection());
            if (!newPosition.isInBattlefield(rightUpCorner)) {
                return;
            } else {
                if (damageMap.containsKey(newPosition)) {
                    int damage = damageMap.remove(newPosition);
                    damageMap.put(newPosition, damage+shell.getShellDamage());
                }
                else
                    damageMap.put(newPosition, shell.getShellDamage());
                shell.move(newPosition);
            }
        }
        shellMapAfterMove.put(shell.getPosition(), shell);
    }

    private boolean isBouncing(Vector2d position){
        AbstractObject abstractObject = battliefield.abstractObjectAt(position);
        return (abstractObject instanceof Obstacle || !position.isInBattlefield(rightUpCorner));
    }

    private void moveBouncingShell(Shell shell){
        Vector2d newPosition;
        AbstractObject collisionObject;
        for (int i=0;i<shell.getMovement();i++){
            newPosition = shell.getPosition().positionAfterMove(shell.getDirection());
            collisionObject = battliefield.abstractObjectAt(newPosition);
            if(isBouncing(newPosition)){
                if(collisionObject instanceof Obstacle)
                    damageMap.put(newPosition, shell.getShellDamage());
                for(int j =0; j<4; j++){
                    shell.turn(Turn.Left);
                }
                newPosition = shell.getPosition();
            }
//            w przypadku strzelenia odbijającym pociskiem w ściane obok któej się stoji:
            collisionObject = battliefield.abstractObjectAt(newPosition);
            if(collisionObject instanceof Tank){
                damageMap.put(newPosition,shell.getShellDamage());
                return;
            }
            shell.move(newPosition);
        }
        shellMapAfterMove.put(shell.getPosition(), shell);
    }

    private void moveOrdinaryShell(Shell shell){
        Vector2d newPosition;
        for (int i=0; i< shell.getMovement();i++) {
            newPosition = shell.getPosition().positionAfterMove(shell.getDirection());
            if (!newPosition.isInBattlefield(rightUpCorner)) {
                return;
            }
            else{
                if (battliefield.isAbstractObjectAt(newPosition)){
                    if (damageMap.containsKey(newPosition)) {
                        int damage = damageMap.remove(newPosition);
                        damageMap.put(newPosition, damage+shell.getShellDamage());
                    }
                    else
                        damageMap.put(newPosition, shell.getShellDamage());
                    return;
                }
                else {
                    shell.move(newPosition);
                }
            }
        }
        shellMapAfterMove.put(shell.getPosition(), shell);
    }

    public Map<Vector2d, Integer> moveShells(){
        damageMap.clear();
        shellMapAfterMove.clear();

        for (Shell shell : shellMap.values()){
            if (shell.isArmourPiercing()){
                moveArmourPiercingShell(shell);
            }
            else if(shell.isBouncing()){
                moveBouncingShell(shell);
            }
            else {
                moveOrdinaryShell(shell);
            }
        }
        shellMap.clear();
        for (Shell shell : shellMapAfterMove.values()) {
            shellMap.put(shell.getPosition(),shell);
        }
        return  Collections.unmodifiableMap(damageMap);
    }

    public boolean isShellAtPlace(Vector2d position){
        return shellMap.containsKey(position);
    }

    public void destroyShell(Shell shell){
        shellMap.remove(shell.getPosition(), shell);
    }

    public Shell shellAtPlace(Vector2d position){
        ArrayList <Shell> shells = new ArrayList<>(shellMap.get(position));
        if (shells.size() ==0)
            return null;
        return shells.get(0);
    }
}
