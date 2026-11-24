package ca.qc.bdeb.sim.tpanesanasss;

import java.util.Random;

/**
 * Petites méthodes pour tirer des nombres au hasard.
 * Le jeu en a besoin à plein d'endroits : ça évite de répéter les mêmes calculs.
 */
public class Hasard {
    private static final Random random = new Random();

    private Hasard() {}

    /**
     * @return un nombre à virgule au hasard entre min et max
     */
    public static double entre(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }


    public static int entierEntre(int min, int max) {
        return random.nextInt(min, max + 1);
    }


    public static int signe() {
        return random.nextBoolean() ? 1 : -1;
    }
}