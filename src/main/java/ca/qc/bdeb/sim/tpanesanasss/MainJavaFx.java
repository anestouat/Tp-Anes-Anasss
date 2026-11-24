package ca.qc.bdeb.sim.tpanesanasss;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainJavaFx extends Application {
    public static final double LARGEUR = 900, HAUTEUR = 520;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gontrand le Flétan");
        stage.getIcons().add(new Image("gontrand.png"));
        stage.setResizable(false);
        stage.setScene(new Scene(new Pane(), LARGEUR, HAUTEUR));
        stage.show();
    }
}