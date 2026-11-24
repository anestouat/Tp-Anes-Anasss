package ca.qc.bdeb.sim.tpanesanasss;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public abstract class ObjetDuJeu {
    protected Point2D position;
    protected Point2D velocite = Point2D.ZERO;
    protected Point2D acceleration = Point2D.ZERO;
    protected Point2D taille;
    protected Image image;

    public ObjetDuJeu(Point2D position, Point2D taille, Image image) {
        this.position = position;
        this.taille = taille;
        this.image = image;
    }


    public void update(double deltaTemps) {
        updatePhysique(deltaTemps);
    }


    protected void updatePhysique(double deltaTemps) {
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));
    }

    public void draw(GraphicsContext context, Camera camera) {
        Point2D ecran = camera.coordoEcran(position);
        context.drawImage(image, ecran.getX(), ecran.getY(), taille.getX(), taille.getY());
    }

    public void drawDebug(GraphicsContext context, Camera camera) {
        Point2D ecran = camera.coordoEcran(position);
        context.setStroke(Color.YELLOW);
        context.setLineWidth(1);
        context.strokeRect(ecran.getX(), ecran.getY(), taille.getX(), taille.getY());
    }


    public boolean enCollisionAvec(ObjetDuJeu autre) {
        boolean pasDeCollision = this.getDroite() < autre.getGauche()
                || autre.getDroite() < this.getGauche()
                || this.getBas() < autre.getHaut()
                || autre.getBas() < this.getHaut();
        return !pasDeCollision;
    }

    protected static double oscillation(double amplitude, double periode, double temps) {
        return amplitude * Math.sin(2 * Math.PI * temps / periode);
    }


    public double getHaut() {
        return position.getY();
    }

    public double getBas() {
        return position.getY() + taille.getY();
    }

    public double getGauche() {
        return position.getX();
    }

    public double getDroite() {
        return position.getX() + taille.getX();
    }

    public Point2D getCentre() {
        return position.add(taille.multiply(0.5));
    }

    public Point2D getAcceleration() {
        return acceleration;
    }
}