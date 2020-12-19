package agh.cs.projekt;


import javafx.application.Application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;


public class World  extends Application{

    @Override
    public void start(Stage stage1) throws Exception {
//        Visualizer visualizer = new Visualizer(400,400, 20,20, null);

        JSONParser jsonParser = new JSONParser();
        JSONObject startData = (JSONObject) jsonParser.parse(new FileReader("./src/sources/StartData.json"));

        int mapHeight = Integer.parseInt(startData.get("mapHeight").toString());
        int mapWidth =Integer.parseInt(startData.get("mapWidth").toString());
        float ratio = Float.parseFloat(startData.get("ratio").toString());
        int beginners =Integer.parseInt(startData.get("beginners").toString());;
        int startingEnergy =Integer.parseInt(startData.get("startingEnergy").toString());;
        int oneGrassEnergy =Integer.parseInt(startData.get("oneGrassEnergy").toString());;
        int moveEnergy =Integer.parseInt(startData.get("moveEnergy").toString());;
        int stageHeight = 600;
        int stageWidth = 900;




        JungleMap map1 = new JungleMap(mapHeight,mapWidth, ratio);
        SimulationEngine engine1 = new SimulationEngine(map1,beginners,startingEnergy,oneGrassEnergy,moveEnergy);
        Scene scene = new Scene(engine1.visualizer.createContent(),stageWidth,stageHeight);
        stage1.setScene(scene);
        stage1.show();

        Stage stage2 = new Stage();
        JungleMap map2 = new JungleMap(mapHeight,mapWidth, ratio);
        SimulationEngine engine2 = new SimulationEngine(map2,beginners,startingEnergy,oneGrassEnergy,moveEnergy);
        Scene scene2 = new Scene(engine2.visualizer.createContent(),stageWidth,stageHeight);
        stage2.setScene(scene2);
        stage2.show();

        engine1.simulate();
        engine2.simulate();
    }


    public static void main(String[] args){

//            JungleMap map = new JungleMap(4,4, (float) 0.5);
//            Visualizer visualizer = new Visualizer(400,400, 40,40);
//            Parent a = visualizer.createContent();
//            SimulationEngine engine = new SimulationEngine(map,visualizer, 8, 20, 8,  2);
//            System.out.println(map.toString());
//            for (int i=0; i< 10000;i++) {
//                    engine.day();
//                    System.out.println(map.toString());
////                System.out.println("dzień: "+ i);
//            }
        launch(args);
//        System.out.println("koniec???");
    }


}
