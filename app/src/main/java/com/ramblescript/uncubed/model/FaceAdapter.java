package com.ramblescript.uncubed.model;

import com.ramblescript.uncubed.model.Face;

/**
 * Created by Dmitri on 21/6/15.
 */
public class FaceAdapter implements Neighbor{
	private Face target;
	private int directionOffset = 0;

	public FaceAdapter(Face face){
		target = face;
	}

	public FaceAdapter(Face face, int directionOffset){
		this(face);
		this.directionOffset = directionOffset;
	}

	public Neighbor getNeighbor(int dir){
		return target.getNeighbor(dir + directionOffset);
	}

	public void setNeighbor(int dir, Neighbor face){
		target.setNeighbor(dir + directionOffset, face);
	}
}
