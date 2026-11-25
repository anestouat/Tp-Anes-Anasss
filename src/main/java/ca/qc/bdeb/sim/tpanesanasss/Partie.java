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
    // ----- État de la partie -----
    private boolean enPause = false;
    private boolean modeDebug = false;     // touche D


    public void update(double deltaTemps) {
        gererTouchesSpeciales();

        if (enPause) {
            return; // rien ne bouge
        }

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

    private void gererTouchesSpeciales() {
        if (Input.vientDEtreAppuyee(KeyCode.P)) {
            enPause = !enPause;
        }
        if (Input.vientDEtreAppuyee(KeyCode.D)) {
            modeDebug = !modeDebug;
        }
    }

    private ArrayList<ObjetDuJeu> objetsAvecCollisions() {
        ArrayList<ObjetDuJeu> objets = new ArrayList<>();
        objets.addAll(projectiles);
        objets.add(gontrand);
        return objets;
    }
    public void draw(GraphicsContext context) {
        // Fond
        context.setFill(couleurFond);
        context.fillRect(0, 0, MainJavaFx.LARGEUR, MainJavaFx.HAUTEUR);

        // Objets du jeu
        for (ObjetDuJeu objet : objetsAvecCollisions()) {
            objet.draw(context, camera);
        }

        // Débogage
        if (modeDebug) {
            dessinerDebug(context);
        }

        // Interface par-dessus le jeu
        dessinerBarreDeVie(context);
        if (enPause) {
            // Rectangle noir à moitié transparent pour assombrir l'écran
            context.setFill(Color.rgb(0, 0, 0, 0.5));
            context.fillRect(0, 0, MainJavaFx.LARGEUR, MainJavaFx.HAUTEUR);
            dessinerTexteAuCentre(context, "PAUSE", Color.WHITE);
        }
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
    private void dessinerTexteAuCentre(GraphicsContext context, String texte, Color couleur) {
        context.setFill(couleur);
        context.setFont(Font.font(50));
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.fillText(texte, MainJavaFx.LARGEUR / 2, MainJavaFx.HAUTEUR / 2);
    }

    private void dessinerDebug(GraphicsContext context) {
        for (ObjetDuJeu objet : objetsAvecCollisions()) {
            objet.drawDebug(context, camera);
        }

        // Ligne verticale à 20% de l'écran
        double xLigne = MainJavaFx.LARGEUR / 5;
        context.setStroke(Color.YELLOW);
        context.strokeLine(xLigne, 0, xLigne, MainJavaFx.HAUTEUR);

        // Infos sous la barre de vie
        double pourcentage = gontrand.getGauche() / LARGEUR_NIVEAU * 100;
        context.setFill(Color.WHITE);
        context.setFont(Font.font(13));
        context.setTextAlign(TextAlignment.LEFT);
        context.setTextBaseline(VPos.TOP);
        context.fillText("Poissons : " + poissons.size(), 10, 40);
        context.fillText("Projectiles : " + projectiles.size(), 10, 56);
        context.fillText(String.format("Position : %.1f %%", pourcentage), 10, 72);
    }
}