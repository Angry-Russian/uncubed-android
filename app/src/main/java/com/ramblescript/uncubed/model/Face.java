package com.ramblescript.uncubed.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.ramblescript.uncubed.view.UC_Drawable;

/**
 * Created by Dmitri on 24/5/15.
 */
public class Face {

	private Face[] neighbors;
	private Face[] components;

	private int directionOffset = 0;

	public Face(){

	}

	public Face getNeighbor(int direction){
		Face res = null;
		if(neighbors != null|| neighbors.length > 0){

			direction += directionOffset;
			if(direction < 0) direction = (direction % neighbors.length) + direction;
			direction = direction % neighbors.length;

			res = neighbors[direction];
		}
		return res;
	}

	public void setNeighbor(int direction, Face neighbor){
		if(neighbors != null && neighbors.length > direction)
			neighbors[(direction + directionOffset)%neighbors.length] = neighbor;
		else Log.e("Face", "Trying to set neighbor outside of array range");
	}

	public Face[] getComponents(){return components;}
	public void setComponents(){this.components = components;}
}
