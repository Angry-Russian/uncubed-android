package com.ramblescript.uncubed;

import android.graphics.Color;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.view.FaceView;
import com.ramblescript.uncubed.view.FaceViewPolar;

/**
 * Created by dmitri on 20/09/15.
 */
public class RadialFaceViewPlacementStrategy extends AbstractFaceViewPlacementStrategy{
    public void execute(FaceView[] components, FaceView[][] componentFaces, int d, int div, float side) {

        // create main faces and views
        for (int i = 0, j = components.length; i < j; i++) {
            Face f = new Face(4);

            double radius = side * (1.5 + Math.floor(i * 2 / j));
            double theta = 360 / d * i + 60 * Math.floor(i * 2 / j);
            double width = Math.PI / d;

            FaceView fv = new FaceViewPolar(f, Color.HSVToColor(255, new float[]{i * 360 / j, 1, 0.5f}))
                    .setRect(0, (float) radius, (float) width, side, 0);
            fv.setRotation((float) theta);
            fv.id = i;

            components[i] = fv;
            FaceView[] cubeFaceComponents = componentFaces[i] = new FaceView[div*div];

            double stepT = width / div;
            double stepR = side / div;
            for (int k = 0, l = cubeFaceComponents.length; k < l; k++) {
                Face cf = new Face(4);
                FaceView faceTile = new FaceViewPolar(cf, fv.getColor())
                        .setRect(
                                (float) (-stepT * (k%div) + stepT * Math.floor(k/div)), // t-a/divisions * (i%divisions) + a/divisions*Math.floor(i/divisions)
                                (float) (radius+side - stepR * (1+k%div) - stepR * Math.floor(k/div)), // r-size + size/divisions*Math.floor(i%divisions) + size/divisions*Math.floor(i/divisions)+size/divisions
                                (float) (width / div),
                                (float) (side / div), 0);

                faceTile.id = k;
                cubeFaceComponents[k] = faceTile;
            }

            //fv.setColor(64, 200, 200, 220);
            fv.setComponents(cubeFaceComponents);
            componentFaces[i] = cubeFaceComponents;
        }
    }
}
