package ca.qc.bdeb.sim.tpanesanasss;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Gontrand extends ObjetDuJeu {
    public static final int VIES_MAX = 4;
    private static final double LARGEUR = 102, HAUTEUR = 90;
    private static final double ACCELERATION = 1000;      // px/s²
    private static final double VITESSE_MAX = 300;        // px/s
    private static final double DUREE_INVINCIBLE = 2;     // secondes
    private static final double DUREE_CLIGNOTEMENT = 0.25;

    private static final Image IMAGE_NORMALE = new Image("gontrand.png");
    private static final Image IMAGE_AVANCE = new Image("gontrand-avant.png");
    private static final Image IMAGE_OUTCH = new Image("gontrand-outch.png");

    private int vies = VIES_MAX;
    private double tempsDepuisDernierCoup = DUREE_INVINCIBLE; // pas invincible au départ

    public Gontrand() {
        super(Point2D.ZERO, new Point2D(LARGEUR, HAUTEUR), IMAGE_NORMALE);
        recommencerAGauche();
    }

    @Override
    public void update(double deltaTemps) {
        // 1. Les flèches changent l'accélération (même méthode pour les 2 axes : pas de copier-coller)
        double ax = accelerationSurUnAxe(
                Input.isKeyPressed(KeyCode.LEFT), Input.isKeyPressed(KeyCode.RIGHT),
                velocite.getX(), deltaTemps);
        double ay = accelerationSurUnAxe(
                Input.isKeyPressed(KeyCode.UP), Input.isKeyPressed(KeyCode.DOWN),
                velocite.getY(), deltaTemps);
        acceleration = new Point2D(ax, ay);

        // 2. Physique normale (méthode de ObjetDuJeu)
        updatePhysique(deltaTemps);

        // 3. Vitesse maximale de 300 px/s dans chaque direction
        velocite = new Point2D(
                Math.clamp(velocite.getX(), -VITESSE_MAX, VITESSE_MAX),
                Math.clamp(velocite.getY(), -VITESSE_MAX, VITESSE_MAX));

        // 4. Les chronomètres avancent
        tempsDepuisDernierCoup += deltaTemps;

        choisirImage();
    }

    private double accelerationSurUnAxe(boolean versMoins, boolean versPlus,
                                        double vitesse, double deltaTemps) {
        if (versMoins) {
            return -ACCELERATION;
        }
        if (versPlus) {
            return ACCELERATION;
        }
        double freinage = Math.min(ACCELERATION, Math.abs(vitesse) / deltaTemps);
        return -Math.signum(vitesse) * freinage;
    }

    private void choisirImage() {
        if (estInvincible()) {
            image = IMAGE_OUTCH;
        } else if (velocite.magnitude() > 1) { // il bouge (on ignore les vitesses minuscules)
            image = IMAGE_AVANCE;
        } else {
            image = IMAGE_NORMALE;
        }
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        // Clignotement : 0.25s visible, 0.25s invisible, ...
        int numeroIntervalle = (int) (tempsDepuisDernierCoup / DUREE_CLIGNOTEMENT);
        boolean cache = estInvincible() && numeroIntervalle % 2 == 1;

        if (!cache) {
            super.draw(context, camera);
        }
    }
    public void garderDansLaZone(double xMin, double xMax) {
        double x = Math.clamp(position.getX(), xMin, xMax - taille.getX());
        double y = Math.clamp(position.getY(), 0, MainJavaFx.HAUTEUR - taille.getY());

        // S'il frappe un bord, sa vitesse dans cette direction tombe à 0
        double vx = (x == position.getX()) ? velocite.getX() : 0;
        double vy = (y == position.getY()) ? velocite.getY() : 0;

        position = new Point2D(x, y);
        velocite = new Point2D(vx, vy);
    }

    public void recommencerAGauche() {
        position = new Point2D(0, (MainJavaFx.HAUTEUR - HAUTEUR) / 2);
        velocite = Point2D.ZERO;
        acceleration = Point2D.ZERO;
    }

    // ----- Vies -----

    public void perdreVie() {
        vies--;
        tempsDepuisDernierCoup = 0;
    }

    public boolean estInvincible() {
        return tempsDepuisDernierCoup < DUREE_INVINCIBLE;
    }

    public void remettreToutesLesVies() {
        vies = VIES_MAX;
    }

    public int getVies() {
        return vies;
    }
}