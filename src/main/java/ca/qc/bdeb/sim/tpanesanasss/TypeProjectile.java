package ca.qc.bdeb.sim.tpanesanasss;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.ArrayList;


public enum TypeProjectile {
    ETOILE("etoile.png");

    // Petite image affichée en haut à gauche, à côté de la barre de vie
    private final Image icone;

    TypeProjectile(String fichierIcone) {
        this.icone = new Image(fichierIcone);
    }

    public Image getIcone() {
        return icone;
    }


    public ArrayList<Projectile> creer(Point2D centre) {
        ArrayList<Projectile> projectiles = new ArrayList<>();

        switch (this) {
            case ETOILE -> projectiles.add(new EtoileDeMer(centre));
        }

        return projectiles;
    }
}