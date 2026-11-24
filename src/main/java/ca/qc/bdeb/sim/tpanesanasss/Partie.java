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


public class Partie {
    public static final double LARGEUR_NIVEAU = 8 * MainJavaFx.LARGEUR;
    private static final double LARGEUR_BARRE_VIE = 150;


    private final Gontrand gontrand = new Gontrand();
    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    private Camera camera = new Camera();


    private Color couleurFond = Color.hsb(Hasard.entre(190, 270), 0.84, 1.0);



    public void update(double deltaTemps) {

        gontrand.update(deltaTemps);
        gontrand.garderDansLaZone(camera.getX(), LARGEUR_NIVEAU);
        camera.suivre(gontrand);


        if (Input.isKeyPressed(KeyCode.SPACE) && gontrand.peutTirer()) {
            projectiles.addAll(gontrand.tirer());
        }

        for (Projectile projectile : projectiles) {
            projectile.update(deltaTemps);
        }


        projectiles.removeIf(projectile -> camera.estHorsEcran(projectile));
    }


    private ArrayList<ObjetDuJeu> objetsAvecCollisions() {
        ArrayList<ObjetDuJeu> objets = new ArrayList<>();
        objets.addAll(projectiles);
        objets.add(gontrand);
        return objets;
    }

    public void draw(GraphicsContext context) {

        context.setFill(couleurFond);
        context.fillRect(0, 0, MainJavaFx.LARGEUR, MainJavaFx.HAUTEUR);


        for (ObjetDuJeu objet : objetsAvecCollisions()) {
            objet.draw(context, camera);
        }


        dessinerBarreDeVie(context);
    }


    private void dessinerBarreDeVie(GraphicsContext context) {
        double x = 10, y = 10, hauteur = 20;
        double largeurRemplie = LARGEUR_BARRE_VIE * gontrand.getVies() / Gontrand.VIES_MAX;

        context.setFill(Color.WHITE);
        context.fillRect(x, y, largeurRemplie, hauteur);
        context.setStroke(Color.WHITE);
        context.setLineWidth(2);
        context.strokeRect(x, y, LARGEUR_BARRE_VIE, hauteur);


        Image icone = gontrand.getTypeProjectile().getIcone();
        double hauteurIcone = 26;
        double largeurIcone = icone.getWidth() / icone.getHeight() * hauteurIcone;
        context.drawImage(icone, x + LARGEUR_BARRE_VIE + 10, y - 3, largeurIcone, hauteurIcone);
    }
}