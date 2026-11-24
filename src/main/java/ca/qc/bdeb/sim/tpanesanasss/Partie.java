package ca.qc.bdeb.sim.tpanesanasss;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

/**
 * Toute la logique d'une partie : niveaux, vies, création des objets, collisions,
 * pause et débogage. Le MainJavaFX appelle seulement update() et draw().
 */
public class Partie {
    public static final double LARGEUR_NIVEAU = 8 * MainJavaFx.LARGEUR;

    private final Gontrand gontrand = new Gontrand();
    private Camera camera = new Camera();


    private Color couleurFond = Color.hsb(Hasard.entre(190, 270), 0.84, 1.0);

    public void update(double deltaTemps) {
        gontrand.update(deltaTemps);
        gontrand.garderDansLaZone(camera.getX(), LARGEUR_NIVEAU);
        camera.suivre(gontrand);
    }



    public void draw(GraphicsContext context) {
        context.setFill(couleurFond);
        context.fillRect(0, 0, MainJavaFx.LARGEUR, MainJavaFx.HAUTEUR);

        gontrand.draw(context, camera);
    }
}