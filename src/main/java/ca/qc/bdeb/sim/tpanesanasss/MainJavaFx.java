package ca.qc.bdeb.sim.tpanesanasss;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
        stage.setScene(creerSceneJeu());
        stage.show();
    }

    private Scene creerSceneJeu() {
        Canvas canvas = new Canvas(LARGEUR, HAUTEUR);
        GraphicsContext context = canvas.getGraphicsContext2D();
        Pane racine = new Pane(canvas);
        Scene scene = new Scene(racine, LARGEUR, HAUTEUR);

        Partie partie = new Partie();

        AnimationTimer timer = new AnimationTimer() {
            private long dernierTemps = 0;

            @Override
            public void handle(long maintenant) {
                // Première image : on ne connaît pas encore le temps écoulé
                if (dernierTemps == 0) {
                    dernierTemps = maintenant;
                    return;
                }
                double deltaTemps = (maintenant - dernierTemps) * 1e-9;
                dernierTemps = maintenant;

                partie.update(deltaTemps);
                partie.draw(context);
            }
        };
        timer.start();

        scene.setOnKeyPressed(e -> Input.setKeyPressed(e.getCode(), true));
        scene.setOnKeyReleased(e -> Input.setKeyPressed(e.getCode(), false));

        return scene;
    }
}