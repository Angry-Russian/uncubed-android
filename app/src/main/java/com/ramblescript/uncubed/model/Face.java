package com.ramblescript.uncubed.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.ramblescript.uncubed.view.UC_Drawable;

/**
 *
 * Created by Dmitri on 24/5/15.
 */
public class Face implements Neighbor{
	private boolean selected = false;
	private int n = 4;
	private Neighbor[] neighbors;

	public Face(int n){
		this.n = n;
		neighbors = new Neighbor[n];
	}

	public Neighbor getNeighbor(int direction){
		Neighbor res = null;
		if(neighbors != null|| neighbors.length > 0){
			if(direction < 0) direction = (direction % neighbors.length) + direction;
			direction = direction % neighbors.length;

			res = neighbors[direction];
		}
		return res;
	}

	public void setNeighbor(int direction, Neighbor neighbor){
		if(neighbors != null && neighbors.length > direction)
			neighbors[direction%neighbors.length] = neighbor;
		else Log.e("Face", "Trying to set neighbor outside of array range");
	}

	public boolean isSelected(){
		return this.selected;
	}

	public void select(){
		this.selected = true;
	}

	public void select(boolean s){
		this.selected = s;
	}

	public boolean isHomogeneous(){
		return true;
	}
}
