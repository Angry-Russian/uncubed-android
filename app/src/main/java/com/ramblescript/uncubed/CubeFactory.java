package com.ramblescript.uncubed;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.view.FaceView;

/**
 * Created by Dmitri on 6/12/2015.
 */
public abstract class CubeFactory {

    private static float radius = (float)Math.sqrt(64*64+64*64);

    public static FaceView createCube(int d, int div, String disposition){

        FaceView face = new FaceView(new Face(), 0);

        FaceView[] components = new FaceView[d*(d-1)];

        for(int i = 0; i<components.length; i++){
            components[i] = new FaceView(null, 0x33000000 + (int) Math.ceil(0xFFFFFF * Math.random()));
        }

        switch(disposition){
            // set positions and rotations according to a pattern
            // ... sounds like a Strategy :O
            case "lotus" :
                face.setRect(300, 300, 0, 0, 0);
                for(int i = 0; i<components.length; i++){
                    float deg = 360 / d;
                    float rad = deg / 180 * (float)Math.PI;
                    float doffset = deg * (float)(Math.floor(i/d)%2) / 2;
                    float roffset = doffset / 180 * (float)Math.PI;

                    float ix = radius * (float)Math.cos(rad*i + roffset) * (float)(1+Math.floor(i/d));
                    float iy = radius * (float)Math.sin(rad*i + roffset) * (float)(1+Math.floor(i/d));
                    float iw = 128;
                    float ih = 128;
                    components[i].setRect(ix, iy, iw, ih, deg*i + doffset + 45);
                }
                break;
            case "tower" : break;
            case "box" : break;
        }

        face.setComponents(components);

        return face;
    }
}
