package agh.cs.projekt2_Tanks;


import javafx.stage.Stage;

import java.util.*;

public class SimulationEngine {
    private final Battliefield battliefield;
    private final Tank player;
    private final Random random = new Random();
    private final Vector2d rightUpCorner;
    private final ShellSimulator shellSimulator;
    private int tankTimer =0;
    private final int timeToPlaceTank;
    private int obstacleTimer = 0;
    private final int timeToPlaceObstacle = 5;
    private final int obstacleHealth =5;
    private int powerUpTimer =0;
    private final int timeToPlacePowerUp = 3;
    public final Visualizer visualizer;
    private final int width;
    private final int height;
    private int playerMoves =0;
    private List<Tank> tanks;
    private int points =0;
    private final boolean enemiesWithPowerUps;
    private final int enemyHealth;


    SimulationEngine(Battliefield battliefield, int stageWidth, int stageHeight, Stage stage, int difficultyLevel, boolean enemiesWithPowerUps){
        this.battliefield =battliefield;
        int playerHealth;
        if(difficultyLevel == 3)
            playerHealth = 6;
        else if(difficultyLevel == 2)
            playerHealth = 8;
        else
            playerHealth = 10;
        int startX = battliefield.getUpperRightCorner().x/2;
        int startY = battliefield.getUpperRightCorner().y/2;
        Vector2d startPosition = new Vector2d(startX,startY);
        this.player = new Tank(playerHealth, 0,startPosition,battliefield, this);
        battliefield.placeAbstractObject(player);
        rightUpCorner = battliefield.getUpperRightCorner();
        shellSimulator = new ShellSimulator(battliefield, battliefield.getUpperRightCorner());
        this.visualizer=new Visualizer(700,700,stageWidth,stageHeight,this, stage);
        this.width = stageWidth;
        this.height = stageHeight;
        this.enemiesWithPowerUps = enemiesWithPowerUps;
        if(difficultyLevel == 3)
            enemyHealth = 2;
        else
            enemyHealth =1;
        if(difficultyLevel == 1)
            timeToPlaceTank = 7;
        else
            timeToPlaceTank = 5;
    }

    private void placeOpponent(){
        if(battliefield.anyPlaceForEnemy(player.getPosition())) {
            Vector2d startPosition;
            do {
                int startX = random.nextInt(battliefield.getUpperRightCorner().x + 1);
                int startY = random.nextInt(battliefield.getUpperRightCorner().y + 1);
                startPosition = new Vector2d(startX, startY);
            } while (!startPosition.isFarEnough(player.getPosition()) || battliefield.isAbstractObjectAt(startPosition));
            Tank tank;
            if (enemiesWithPowerUps) {
                PowerUp powerUp;
                do {
                    powerUp = new PowerUp(startPosition, 1, battliefield);
                } while (powerUp.getMode() == PowerUpsName.Immortality);
                tank = new Tank(enemyHealth, 0, startPosition, battliefield, this, powerUp);
                powerUp.disappear();
            } else {
                tank = new Tank(enemyHealth, 0, startPosition, battliefield, this);
            }
            battliefield.placeAbstractObject(tank);
        }
    }

    private void placeObstacle(){
        if(battliefield.anyPlaceForObstacle()){
            Vector2d startPosition;
            do {
                int startX = random.nextInt(battliefield.getUpperRightCorner().x + 1);
                int startY = random.nextInt(battliefield.getUpperRightCorner().y + 1);
                startPosition = new Vector2d(startX, startY);
            } while (battliefield.isAbstractObjectAt(startPosition));
            Obstacle obstacle = new Obstacle(startPosition, obstacleHealth, battliefield);
            battliefield.placeAbstractObject(obstacle);
        }
    }

    private void placePowerUp(){
        int startX = random.nextInt(battliefield.getUpperRightCorner().x + 1);
        int startY = random.nextInt(battliefield.getUpperRightCorner().y + 1);
        Vector2d startPosition = new Vector2d(startX, startY);
        PowerUp powerUp = new PowerUp(startPosition, 8,battliefield);
        battliefield.placePowerUp(powerUp);
    }

    private void moveEnemyTanks(){
        tanks = battliefield.getTanks();
        Vector2d newPosition;
        Shell shell;
        for (Tank tank : tanks){
            if (tank.getPosition() != player.getPosition()) {
                for (int i = 0; i < tank.getMoveNumber(); i++) {
                    int directionToPlayer = tank.getPosition().getDirectionToPlayer(player.getPosition(), rightUpCorner);
                    while (tank.getDirection() != directionToPlayer) {
                        tank.turn(Turn.Left);
                    }
                    int choice = random.nextInt(2);
                    if (choice == 0) {
//                      ruch w stronę gracza
                        newPosition = tank.getPosition().positionAfterMove(tank.getDirection());
                        if (!battliefield.isAbstractObjectAt(newPosition) &&
                                newPosition.isInBattlefield(rightUpCorner)) {
                            tank.move(newPosition);
                            PowerUp powerUp = battliefield.powerUpAt(tank.getPosition());
                            if (powerUp != null) {
                                tank.pickUpUpgrade(powerUp);
                                powerUp.disappear();
                                tank.useUpgrade(powerUp.getMode());
                            }
                            if (shellSimulator.isShellAtPlace(tank.getPosition())) {
                                shell = shellSimulator.shellAtPlace(tank.getPosition());
                                if ((shell.getDirection() + 4) % 8 == tank.getDirection()) {
                                    tank.getDamage(shell.getShellDamage());
                                    if(tank.getHealth()<=0)
                                        points++;
                                    shellSimulator.destroyShell(shell);
                                }
                            }
                        }
                    }
                    else {
//                  strzał do gracza
                        shell = tank.getShell();
                        shellSimulator.placeNewShell(shell);
                    }
                }
            }
            tank.reducePowerUpsDuraction();
        }
    }

    private void moveShells(){
        Map<Vector2d, Integer> damageMap = shellSimulator.moveShells();
        for (Vector2d position : damageMap.keySet() )
        {
            AbstractObject damagedObject = battliefield.abstractObjectAt(position);
            if(damagedObject !=  null){
                damagedObject.getDamage(damageMap.get(position));
                if(damagedObject instanceof Tank){
                    if (((Tank) damagedObject).getHealth() <=0 && !damagedObject.equals(player))
                        points++;
                }
            }
        }
    }

    public void simulate(){
        day();
    }

    protected void movePlayer(){
        Vector2d positionAfterMove = player.getPosition().positionAfterMove(player.getDirection());
        if(!battliefield.isAbstractObjectAt(positionAfterMove) && positionAfterMove.isInBattlefield(rightUpCorner)) {
            player.move(positionAfterMove);
            PowerUp powerUp = battliefield.powerUpAt(player.getPosition());
            if (powerUp != null) {
                player.pickUpUpgrade(powerUp);
                powerUp.disappear();
                if(powerUp.getMode() != PowerUpsName.BonusHealth){
                    visualizer.pickUpgrade(powerUp);
                }
            }
            if(shellSimulator.isShellAtPlace(player.getPosition())){
                Shell shell = shellSimulator.shellAtPlace(player.getPosition());
                if((shell.getDirection()+4)%8 == player.getDirection()) {
                    player.getDamage(shell.getShellDamage());
                    shellSimulator.destroyShell(shell);
                }
            }
            playerMoves ++;
            refreshMap();
            if(player.getHealth()<=0)
                endTheGame();
            if(playerMoves == player.getMoveNumber()){
                day();
                playerMoves =0;
            }
        }
    }

    protected void turnPlayer(Turn side){
        player.turn(side);
        visualizer.placeObject(player.getPosition(),true);
    }

    protected void shoot(){
        Shell shell = player.getShell();
        shellSimulator.placeNewShell(shell);
        playerMoves++;
        if(playerMoves == player.getMoveNumber()){
            day();
            playerMoves =0;
        }
    }

    protected Battliefield getBattliefield(){
        return battliefield;
    }

    protected ShellSimulator getShellSimulator(){
        return shellSimulator;
    }

    protected boolean usePowerUp(PowerUpsName powerUpsName){
        return player.useUpgrade(powerUpsName);
    }

    protected void removePowerup(PowerUp powerUp){
        visualizer.endUpgradeDuration(powerUp.getMode());
    }

    public void refreshMap(){
        Vector2d position;
        for(int y =0; y< height;y++) {
            for (int x = 0; x < width; x++) {
                position = new Vector2d(x, y);
                visualizer.placeObject(position, position.equals(player.getPosition()));
            }
        }
    }

    private void day(){
        if(random.nextInt(tankTimer+1) ==0){
            placeOpponent();
            tankTimer = timeToPlaceTank;
        }
        if(random.nextInt(obstacleTimer+1) ==0){
            placeObstacle();
            obstacleTimer = timeToPlaceObstacle;
        }
        if(random.nextInt(powerUpTimer+1) ==0){
            placePowerUp();
            powerUpTimer = timeToPlacePowerUp;
        }
        tankTimer --;
        obstacleTimer --;
        powerUpTimer --;

        moveEnemyTanks();

        moveShells();

        tanks = battliefield.getTanks();
        if (tanks.size() == 1){
            placeOpponent();
            tankTimer = timeToPlaceTank;
        }

        PowerUp powerUp;
        Vector2d position;
        for(int y =0; y< height;y++) {
            for (int x = 0; x < width; x++) {
                position = new Vector2d(x, y);
                visualizer.placeObject(position, position.equals(player.getPosition()));
                powerUp = battliefield.powerUpAt(position);
                if(powerUp != null)
                    powerUp.missDay();
            }
        }
        if(player.getHealth()<=0)
            endTheGame();
        visualizer.setPoints(points);
    }

    private void endTheGame(){
        visualizer.endGame(points);
    }


}
