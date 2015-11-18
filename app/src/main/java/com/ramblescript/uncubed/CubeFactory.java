package com.ramblescript.uncubed;

import com.ramblescript.uncubed.Utils.AbstractFaceViewPlacementStrategy;
import com.ramblescript.uncubed.Utils.BoxFaceViewPlacementStrategy;
import com.ramblescript.uncubed.Utils.RadialFaceViewPlacementStrategy;
import com.ramblescript.uncubed.view.Cube;
import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.model.FaceAdapter;
import com.ramblescript.uncubed.view.FaceView;

/**
 * Created by Dmitri on 6/12/2015.
 */
public abstract class CubeFactory {

    /**
     *
     * @param dimensions dimentions, a normal cube has 3
     * @param divisions divisions per side, a normal cube has 3 or 5
     * @param side length of one side
     * @param disposition placement and type of faces
     * @return FaceView holding the cube
     */
	public static FaceView createCube(int dimensions, int divisions, float side, String disposition){

		FaceView cube = new Cube(new Face(0), 0).setRect(300, 300, 0, 0, 0);


		FaceView[] components = new FaceView[dimensions * (dimensions - 1)];
		FaceView[][] componentFaces = new FaceView[dimensions * (dimensions - 1)][divisions * divisions];

        AbstractFaceViewPlacementStrategy placementStrategy = null;
        switch(disposition) {
            // set positions and rotations according to a pattern
            // ... sounds like a Strategy :O
            case "box": placementStrategy = new BoxFaceViewPlacementStrategy(); break;
            case "lotus" : placementStrategy = new RadialFaceViewPlacementStrategy(); break;
            case "tower" : break;
		}

        if(placementStrategy!=null) placementStrategy.execute(components, componentFaces, dimensions, divisions, side);
        else return null;

        // once everything is created and positioned, connect it all together
        for(int i = 0, j = components.length; i<j; i++){
            int layer = (int)Math.floor(i/ dimensions);

            Face f = components[i].getModel();
            Face up;
            Face left;
            int offsetV = 0;
            int offsetH = 0;

            if (layer == 0){// if this is the innermost layer
                left = components[(i+1)% dimensions].getModel();
                up = components[(i+ dimensions -1)% dimensions].getModel();
                offsetV = 1;
                offsetH = -1;
            }else{
                left = components[((i+1)% dimensions) + (layer-1)* dimensions].getModel();
                up = components[(i- dimensions)].getModel();
            }

            f.setNeighbor(3, new FaceAdapter(up, offsetV));
            up.setNeighbor(1, new FaceAdapter(f, -offsetV));
            f.setNeighbor(2, new FaceAdapter(left, offsetH));
            left.setNeighbor(0, new FaceAdapter(f, -offsetV));

            FaceView[] cFaces = componentFaces[i];
            for(int k = 0, l = cFaces.length; k<l; k++){

                Face thisFace = cFaces[k].getModel();
                if(k< divisions){
                    // connect upward-edge neighbor: previous layer
                    if(layer< dimensions -2) { // unless you're in the last layer, connect blah
                        Face upward = componentFaces[(i + dimensions) % j][divisions * divisions - divisions + k].getModel();
                        thisFace.setNeighbor(3, new FaceAdapter(upward, 0));
                        upward.setNeighbor(1, new FaceAdapter(thisFace, 0));

                    }else{ // otherwise, connect to neighboring face and mod direction
                        Face upward = componentFaces[((i+1)% dimensions) + layer* dimensions][k* divisions].getModel();
                        thisFace.setNeighbor(3, new FaceAdapter(upward, 1));
                        upward.setNeighbor(2, new FaceAdapter(thisFace, -1));
                    }

                }else{ // you're inside the face
                    // connect upward-local neighbor
                    Face upward = cFaces[k- divisions].getModel();
                    thisFace.setNeighbor(3, new FaceAdapter(upward, 0));
                    upward.setNeighbor(1, new FaceAdapter(thisFace, 0));
                }

                if(k% divisions == divisions -1){
                    if(layer == 0) {
                        Face rightward = componentFaces[(i + 1) % dimensions][(int) Math.floor(k / divisions) + divisions * divisions - divisions].getModel();
                        rightward.setNeighbor(1, new FaceAdapter(thisFace, 1));
                        thisFace.setNeighbor(0, new FaceAdapter(rightward, -1));

                    }else{
                        Face rightward = componentFaces[(layer-1)* dimensions +((i+1)% dimensions)][k- divisions +1].getModel();
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
		cube.setComponents(components);

		return cube;
	}
}
