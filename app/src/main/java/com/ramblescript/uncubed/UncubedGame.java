package com.ramblescript.uncubed;

import com.ramblescript.uncubed.view.FaceView;

/**
 * Created by dmitri on 18/09/15.
 */
abstract class UncubedGame {
    private static FaceView instance = null;
    private static int preferredDimentions = 3;
    private static int preferredDivisions = 3;

    public static int getPreferredDimentions() {
        return preferredDimentions;
    }

    public static void setPreferredDimentions(int preferredDimentions) {
        UncubedGame.preferredDimentions = preferredDimentions;
    }

    public static int getPreferredDivisions() {
        return preferredDivisions;
    }

    public static void setPreferredDivisions(int preferredDivisions) {
        UncubedGame.preferredDivisions = preferredDivisions;
    }


    public static FaceView getInstance() {
        if(instance == null)
            instance = CubeFactory.createCube(3, 3, 256, "box");

        return instance;
    }

    public static void destroyInstance(){
        instance = null;
    }

    public static FaceView getNewInstance(){
        destroyInstance();
        return getInstance();
    }

}
