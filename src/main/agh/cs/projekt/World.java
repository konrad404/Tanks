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




public class World  extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Visualizer visualizer = new Visualizer(400,400, 20,20);
        JungleMap map = new JungleMap(20,20, (float) 0.5);
        SimulationEngine engine = new SimulationEngine(map,visualizer,0,50,20,2);
        Scene scene = new Scene(visualizer.createContent(),900,600);
        stage.setScene(scene);
        stage.show();
        engine.day();
        new Thread (() ->{
            for (int i = 0; i < 1000; i++) {
                engine.day();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
