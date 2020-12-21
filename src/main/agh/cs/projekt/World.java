package agh.cs.projekt;


import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class World  extends Application{

    @Override
    public void start(Stage stage1) throws Exception {

        JSONParser jsonParser = new JSONParser();
        JSONObject startData = (JSONObject) jsonParser.parse(new FileReader("./src/sources/StartData.json"));

        int mapHeight = Integer.parseInt(startData.get("mapHeight").toString());
        int mapWidth =Integer.parseInt(startData.get("mapWidth").toString());
        float ratio = Float.parseFloat(startData.get("ratio").toString());
        int beginners =Integer.parseInt(startData.get("beginners").toString());
        int startingEnergy =Integer.parseInt(startData.get("startingEnergy").toString());
        int oneGrassEnergy =Integer.parseInt(startData.get("oneGrassEnergy").toString());
        int moveEnergy =Integer.parseInt(startData.get("moveEnergy").toString());
        int mapNumber = Integer.parseInt(startData.get("mapNumber").toString());
        int stageHeight = 400;
        int stageWidth = 810;


        JungleMap map1 = new JungleMap(mapHeight,mapWidth, ratio);
        SimulationEngine engine1 = new SimulationEngine(map1,beginners,startingEnergy,oneGrassEnergy,moveEnergy);
        Scene scene = new Scene(engine1.visualizer.createContent(),stageWidth,stageHeight);
        stage1.setScene(scene);
        stage1.setX(0);
        stage1.show();


        engine1.simulate();
//      jeśli jest taka potrzeba dodajemy drugi stage oraz drugą mapę
//      wybór pomiedzy 1 lub dwoma mapami podejmujemy w pliku json
        if(mapNumber ==2){
            Stage stage2 = new Stage();
            JungleMap map2 = new JungleMap(mapHeight, mapWidth, ratio);
            SimulationEngine engine2 = new SimulationEngine(map2, beginners, startingEnergy, oneGrassEnergy, moveEnergy);
            Scene scene2 = new Scene(engine2.visualizer.createContent(), stageWidth, stageHeight);
            stage2.setScene(scene2);
            stage2.setX(400);
            stage2.show();
            engine2.simulate();
        }
    }


    public static void main(String[] args){

        launch(args);


    }


}
