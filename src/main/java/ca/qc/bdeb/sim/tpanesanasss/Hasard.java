package ca.qc.bdeb.sim.tpanesanasss;

import java.util.Random;


public class Hasard {
    private static final Random random = new Random();

    private Hasard() {}


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