package com.ramblescript.uncubed;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.model.FaceAdapter;
import com.ramblescript.uncubed.view.FaceView;
import com.ramblescript.uncubed.view.UC_Drawable;
import android.graphics.Color;

/**
 * Created by Dmitri on 6/12/2015.
 */
public abstract class CubeFactory {

	public static FaceView createCube(int d, int div, float side, String disposition){

		FaceView face = new FaceView(new Face(0), 0).setRect(300, 300, 0, 0, 0);

		FaceView[] components = new FaceView[d*(d-1)];
		Face[] faces = new Face[d*(d-1)];

		// create main faces and views
		for(int i = 0, j = components.length; i<j; i++) {
			Face f = new Face(4);
			faces[i] = f;

			FaceView fv = new FaceView(f, Color.HSVToColor(255, new float[]{i * 360 / j, 1, 0.5f}));
			fv.id = i;
			components[i] = fv;
		}
		switch(disposition){
			// set positions and rotations according to a pattern
			// ... sounds like a Strategy :O
			case "lotus" :

				float radius = (float)Math.sqrt(side*side*2);
				for(int i = 0; i<components.length; i++){

					FaceView cubeFace = components[i];

					float deg = 360 / d;
					float rad = deg / 180 * (float)Math.PI;

					float doffset = deg * (float)(Math.floor(i/d)) / 2 - 30;
					float roffset = doffset / 180 * (float)Math.PI;

					float ix = radius * (float)(Math.cos(rad*i + roffset) * (.5+Math.floor(i/d)));
					float iy = radius * (float)(Math.sin(rad*i + roffset) * (.5+Math.floor(i/d)));
					float iw = side;
					float ih = iw;

					cubeFace.setRect(ix, iy, iw, ih, deg * i + doffset - 45);
					FaceView[] cubeFaceComponents = new FaceView[div*div];

					int color =  cubeFace.getColor();
					float jw = (iw-2)/div-2;
					float jh = (ih-2)/div-2;

					// create face tiles
					for(int j = 0, k=cubeFaceComponents.length; j<k; j++){
						FaceView faceTile = new FaceView(null,color).setRect(2+(2+jw) * (j%div) + jw/2, 2+(2+jh) * (int)Math.floor(j / div) + jh/2, jw, jh, 0);
						faceTile.id = j;
						cubeFaceComponents[j] = faceTile;
					}
					cubeFace.setColor(64, 128, 128, 128);
					cubeFace.setComponents(cubeFaceComponents);
				}

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

					//f.setNeighbor(3, new FaceAdapter(up, 0));

				}
				break;
			case "tower" : break;
			case "box" : break;
		}

		face.setComponents(components);

		return face;
	}
}
