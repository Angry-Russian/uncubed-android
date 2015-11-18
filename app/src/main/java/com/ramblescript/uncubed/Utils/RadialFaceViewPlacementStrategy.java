package com.ramblescript.uncubed.Utils;

import android.graphics.Color;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.view.FaceView;
import com.ramblescript.uncubed.view.FaceViewPolar;

/**
 * Created by dmitri on 20/09/15.
 */
public class RadialFaceViewPlacementStrategy extends AbstractFaceViewPlacementStrategy{

    public float adjustment = 1.5f;
    public void execute(FaceView[] components, FaceView[][] componentFaces, int d, int div, float side) {

        side = side/2;
        // create main faces and views
        for (int i = 0, j = components.length; i < j; i++) {
            Face f = new Face(4);

            int layer = (int) Math.floor(i/d);
            double radius = side * (adjustment + Math.floor(i/d));
            double theta = 360 / d * i + 360 / d / 2 * layer;
            double rtheta = theta/180*Math.PI;
            double width = Math.PI / d;

            FaceView fv = new FaceViewPolar(f, Color.HSVToColor(255, new float[]{i * 360 / j, 1, 0.5f}))
                    .setRect((float) (rtheta), (float) radius, (float) width, side, (float)(Math.PI/4*1));

            fv.id = i;

            components[i] = fv;
            FaceView[] cubeFaceComponents = componentFaces[i] = new FaceView[div*div];

            double stepT = width / div;
            double stepR = side / div;

            for (int k = 0, l = cubeFaceComponents.length; k < l; k++) {
                Face cf = new Face(4);
                FaceViewPolar faceTile = new FaceViewPolar(cf, fv.getColor());
                faceTile.setParent(fv);
                faceTile.setRect(
                                (float) (stepT * (k%div) - stepT * Math.floor(k/div)),
                                (float) (-stepR * (1+k%div) - stepR * Math.floor(k/div) + side),//*/
                                (float) (stepT),
                                (float) (stepR), 0);

                faceTile.id = k;
                cubeFaceComponents[k] = faceTile;
            }

            fv.setColor(128, 200, 200, 220);
            fv.setComponents(cubeFaceComponents);
            fv.deselect();
            componentFaces[i] = cubeFaceComponents;
        }
    }
}
