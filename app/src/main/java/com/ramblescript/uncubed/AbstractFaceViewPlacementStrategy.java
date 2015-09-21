package com.ramblescript.uncubed;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.view.FaceView;

/**
 * Created by dmitri on 20/09/15.
 */
abstract public class AbstractFaceViewPlacementStrategy {
    abstract public void execute(FaceView[] components, FaceView[][] componentFaces, int d, int div, float side);
}
