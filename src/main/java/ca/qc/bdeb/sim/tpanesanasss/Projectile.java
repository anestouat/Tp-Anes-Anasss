package ca.qc.bdeb.sim.tpanesanasss;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;


public abstract class Projectile extends ObjetDuJeu {

    public Projectile(Point2D centre, double largeur, double hauteur, Image image) {
        super(centre.subtract(largeur / 2, hauteur / 2), new Point2D(largeur, hauteur), image);
    }
}