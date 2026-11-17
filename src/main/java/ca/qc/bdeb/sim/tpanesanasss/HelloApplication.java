package ca.qc.bdeb.sim.tpanesanasss;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HBox h = new HBox();
       VBox v = new VBox();
       HBox hh = new HBox();
        Scene scene = new Scene(v, 320, 240);
        System.out.println("messi");
        stage.setTitle("Hello!");
        System.out.println();
        stage.setScene(scene);
        stage.show();
    }
}
