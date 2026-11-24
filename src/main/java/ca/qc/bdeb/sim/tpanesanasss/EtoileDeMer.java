package ca.qc.bdeb.sim.tpanesanasss;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;


public class EtoileDeMer extends Projectile {
    private static final Image IMAGE = new Image("etoile.png");
    private static final double VITESSE = 800;

    public EtoileDeMer(Point2D centre) {
        super(centre, 36, 35, IMAGE);
        velocite = new Point2D(VITESSE, 0);
    }
}