package com.ramblescript.uncubed;

import android.graphics.Color;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.model.FaceAdapter;
import com.ramblescript.uncubed.view.FaceView;

/**
 * Created by dmitri on 20/09/15.
 */
public class BoxFaceViewPlacementStrategy extends AbstractFaceViewPlacementStrategy {

    @Override
    public void execute(FaceView[] components, FaceView[][] componentFaces, int d, int div, float side) {

        // create main faces and views
        for (int i = 0, j = components.length; i < j; i++) {
            Face f = new Face(4);
            FaceView fv = new FaceView(f, Color.HSVToColor(255, new float[]{i * 360 / j, 1, 0.5f}));
            fv.id = i;
            components[i] = fv;
        }

        float radius = (float) Math.sqrt(side * side * 2);
        float layerRadius = (float) side;

        for (int i = 0; i < components.length; i++) {

            FaceView cubeFace = components[i];

            float deg = 360 / d;
            float rad = deg / 180 * (float) Math.PI;

            float doffset = deg * (float) (Math.floor(i / d)) / 2 - 30;
            float roffset = doffset / 180 * (float) Math.PI;

            float layerSize = (float) Math.floor(i / d) * side;
            float ix = (radius * 0.5f + layerSize) * (float) (Math.cos(rad * i + roffset));
            float iy = (radius * 0.5f + layerSize) * (float) (Math.sin(rad * i + roffset));
            float iw = side;
            float ih = iw;

            cubeFace.setRect(ix, iy, iw, ih, deg * i + doffset - 45 + 180);
            FaceView[] cubeFaceComponents = new FaceView[div * div];

            float jw = (iw - 2) / div - 2;
            float jh = (ih - 2) / div - 2;

            // create face tiles
            for (int j = 0, k = cubeFaceComponents.length; j < k; j++) {
                Face f = new Face(4);
                FaceView faceTile = new FaceView(f, cubeFace.getColor()).setRect(2 + (2 + jw) * (j % div) + jw / 2, 2 + (2 + jh) * (int) Math.floor(j / div) + jh / 2, jw, jh, 0);
                faceTile.id = j;
                cubeFaceComponents[j] = faceTile;
            }

            cubeFace.setColor(64, 200, 200, 220);
            cubeFace.setComponents(cubeFaceComponents);
            componentFaces[i] = cubeFaceComponents;
        }
    }
}
