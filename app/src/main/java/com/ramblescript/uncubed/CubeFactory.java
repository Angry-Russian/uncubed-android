package com.ramblescript.uncubed;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.model.FaceAdapter;
import com.ramblescript.uncubed.model.Neighbor;
import com.ramblescript.uncubed.view.FaceView;
import com.ramblescript.uncubed.view.FaceViewPolar;
import com.ramblescript.uncubed.view.UC_Drawable;
import android.graphics.Color;

/**
 * Created by Dmitri on 6/12/2015.
 */
public abstract class CubeFactory {

    /**
     *
     * @param d dimentions, a normal cube has 3
     * @param div divisions per side, a normal cube has 3 or 5
     * @param side length of one side
     * @param disposition placement and type of faces
     * @return FaceView holding the cube
     */
	public static FaceView createCube(int d, int div, float side, String disposition){

		FaceView face = new FaceView(new Face(0), 0).setRect(300, 300, 0, 0, 0);

		Face[] faces = new Face[d*(d-1)];
		FaceView[] components = new FaceView[d*d-d];
		FaceView[][] componentFaces = new FaceView[d*d-d][div*div];


        AbstractFaceViewPlacementStrategy placementStrategy = null;
        switch(disposition) {
            // set positions and rotations according to a pattern
            // ... sounds like a Strategy :O
            case "box": placementStrategy = new BoxFaceViewPlacementStrategy(); break;
            case "lotus" : placementStrategy = new RadialFaceViewPlacementStrategy(); break;
            case "tower" : break;
		}

        if(placementStrategy!=null) placementStrategy.execute(components, componentFaces, d, div, 200);

        // once everything is created and positioned, connect it all together
        int layer = 0;
        for(int i = 0, j = components.length; i<j; i++){
            layer = (int)Math.floor(i/d);

            Face f = components[i].getModel();
            Face up;
            Face left;
            int offsetV = 0;
            int offsetH = 0;

            if (layer == 0){// if this is the innermost layer
                left = components[(i+1)%d].getModel();
                up = components[(i+d-1)%d].getModel();
                offsetV = 1;
                offsetH = -1;
            }else{
                left = components[((i+1)%d) + (layer-1)*d].getModel();
                up = components[(i-d)].getModel();
            }

            f.setNeighbor(3, new FaceAdapter(up, offsetV));
            up.setNeighbor(1, new FaceAdapter(f, -offsetV));
            f.setNeighbor(2, new FaceAdapter(left, offsetH));
            left.setNeighbor(0, new FaceAdapter(f, -offsetV));

            FaceView[] cFaces = componentFaces[i];
            for(int k = 0, l = cFaces.length; k<l; k++){

                Face thisFace = cFaces[k].getModel();
                if(k<div){
                    // connect upward-edge neighbor: previous layer
                    if(layer<d-2) { // unless you're in the last layer, connect blah
                        Face upward = componentFaces[(i + d) % j][div * div - div + k].getModel();
                        thisFace.setNeighbor(3, new FaceAdapter(upward, 0));
                        upward.setNeighbor(1, new FaceAdapter(thisFace, 0));

                    }else{ // otherwise, connect to neighboring face and mod direction
                        Face upward = componentFaces[((i+1)%d) + layer*d][k*div].getModel();
                        thisFace.setNeighbor(3, new FaceAdapter(upward, 1));
                        upward.setNeighbor(2, new FaceAdapter(thisFace, -1));
                    }

                }else{ // you're inside the face
                    // connect upward-local neighbor
                    Face upward = cFaces[k-div].getModel();
                    thisFace.setNeighbor(3, new FaceAdapter(upward, 0));
                    upward.setNeighbor(1, new FaceAdapter(thisFace, 0));
                }

                if(k%div == div-1){
                    if(layer == 0) {
                        Face rightward = componentFaces[(i + 1) % d][(int) Math.floor(k / div) + div * div - div].getModel();
                        rightward.setNeighbor(1, new FaceAdapter(thisFace, 1));
                        thisFace.setNeighbor(0, new FaceAdapter(rightward, -1));

                    }else{
                        Face rightward = componentFaces[(layer-1)*d+((i+1)%d)][k-div+1].getModel();
                        rightward.setNeighbor(2, new FaceAdapter(thisFace, 0));
                        thisFace.setNeighbor(0, new FaceAdapter(rightward, 0));
                    }
                }else {
                    Face rightward = cFaces[k+1].getModel();
                    rightward.setNeighbor(2, new FaceAdapter(thisFace, 0));
                    thisFace.setNeighbor(0, new FaceAdapter(rightward, 0));
                }
            }
        }
		face.setComponents(components);

		return face;
	}
}
