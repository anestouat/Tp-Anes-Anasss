package ca.qc.bdeb.sim.tpanesanasss;

import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;


    public class Input {
        private static final Set<KeyCode> touchesAppuyees = new HashSet<>();


        private static final Set<KeyCode> nouvellesTouches = new HashSet<>();

        private Input() {}

        public static void setKeyPressed(KeyCode code, boolean appuie) {
            if (appuie) {

                if (touchesAppuyees.add(code)) {
                    nouvellesTouches.add(code);
                }
            } else {
                touchesAppuyees.remove(code);
            }
        }


        public static boolean isKeyPressed(KeyCode code) {
            return touchesAppuyees.contains(code);
        }



        public static boolean vientDEtreAppuyee(KeyCode code) {
            return nouvellesTouches.remove(code);
        }


        public static void vider() {
            touchesAppuyees.clear();
            nouvellesTouches.clear();
        }
    }

