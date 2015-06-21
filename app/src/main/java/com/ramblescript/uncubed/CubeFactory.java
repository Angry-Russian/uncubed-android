package com.ramblescript.uncubed;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.view.FaceView;
import com.ramblescript.uncubed.view.UC_Drawable;
import android.graphics.Color;

/**
 * Created by Dmitri on 6/12/2015.
 */
public abstract class CubeFactory {

    public static FaceView createCube(int d, int div, float side, String disposition){

        FaceView face = new FaceView(new Face(), 0).setRect(300, 300, 0, 0, 0);

        FaceView[] components = new FaceView[d*(d-1)];

        for(int i = 0, j = components.length; i<j; i++)
            components[i] = new FaceView(null, Color.HSVToColor(255, new float[]{i * 360 / j, 1, 0.5f}));

        switch(disposition){
            // set positions and rotations according to a pattern
            // ... sounds like a Strategy :O
            case "lotus" :

                float radius = (float)Math.sqrt(side*side*2);
                for(int i = 0; i<components.length; i++){
                    FaceView cubeFace = components[i];
                    float deg = 360 / d;
                    float rad = deg / 180 * (float)Math.PI;
                    float doffset = deg * (float)(Math.floor(i/d)%2) / 2 - 30;
                    float roffset = doffset / 180 * (float)Math.PI;

                    float ix = radius * (float) Math.cos(rad*i + roffset) * (float)(.5+Math.floor(i/d));
                    float iy = radius * (float) Math.sin(rad*i + roffset) * (float)(.5+Math.floor(i/d));
                    float iw = side;
                    float ih = iw;

                    cubeFace.setRect(ix, iy, iw, ih, deg * i + doffset - 45);
                    FaceView[] cubeFaceComponents = new FaceView[div*div];

                    float jw = (iw-2)/div-2;
                    float jh = (ih-2)/div-2;
                    int color =  cubeFace.getColor();
                    for(int j = 0, k=cubeFaceComponents.length; j<k; j++){
                        FaceView faceTile = new FaceView(null,color).setRect(2+(2+jw) * (j%div) + jw/2, 2+(2+jh) * (int)Math.floor(j / div) + jh/2, jw, jh, 0);
                        cubeFaceComponents[j] = faceTile;
                    }
                    cubeFace.setColor(64, 128, 128, 128);
                    cubeFace.setComponents(cubeFaceComponents);
                }
                break;
            case "tower" : break;
            case "box" : break;
        }

        face.setComponents(components);

        return face;
    }
}
