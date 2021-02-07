package agh.cs.projekt2_Tanks;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class World1 extends Application{

    @Override
    public void start(Stage stage1) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject startData = (JSONObject) jsonParser.parse(new FileReader("./data/TankData.json"));
        int mapHeight = Integer.parseInt(startData.get("mapHeight").toString());;
        int mapWidth =Integer.parseInt(startData.get("mapWidth").toString());
        int difficultyLevel = Integer.parseInt(startData.get("difficultyLevel").toString());
        boolean enemiesWithPowerUps = Boolean.parseBoolean(startData.get("enemiesWithPowerups").toString());
        Battliefield battliefield = new Battliefield(mapHeight,mapWidth);
        SimulationEngine simulationEngine = new SimulationEngine(battliefield,mapWidth,mapHeight, stage1, difficultyLevel, enemiesWithPowerUps);
        Scene scene = new Scene(simulationEngine.visualizer.createContent(), 700, 700);
        stage1.setScene(scene);
        stage1.show();
        simulationEngine.simulate();
    }


    public static void main(String[] args){

        launch(args);
    }


}
