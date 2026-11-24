package ca.qc.bdeb.sim.tpanesanasss;

import javafx.geometry.Point2D;

public class Camera {
    // Position de la caméra dans le niveau (bord gauche de ce qu'on voit)
    private double x = 0;

    public double getX() {
        return x;
    }

    public Point2D coordoEcran(Point2D positionMonde) {
        return positionMonde.subtract(x, 0);
    }

    public void suivre(Gontrand gontrand) {
        double xVoulu = gontrand.getGauche() - MainJavaFx.LARGEUR / 5;

        // Math.max : on garde la plus grande valeur, donc la caméra ne recule jamais
        x = Math.max(x, xVoulu);

        // On ne montre jamais plus loin que la fin du niveau
        x = Math.clamp(x, 0,Partie.LARGEUR_NIVEAU - MainJavaFx.LARGEUR);
    }


    public boolean estHorsEcran(ObjetDuJeu objet) {
        return objet.getDroite() < x
                || objet.getGauche() > x + MainJavaFx.LARGEUR
                || objet.getBas() < 0
                || objet.getHaut() > MainJavaFx.HAUTEUR;
    }

    public boolean estVisible(Point2D positionMonde) {
        return positionMonde.getX() >= x && positionMonde.getX() <= x + MainJavaFx.LARGEUR;
    }
}
