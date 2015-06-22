package com.ramblescript.uncubed.model;

/**
 * Created by Dmitri on 21/6/15.
 */
public interface Neighbor {
	void setNeighbor(int dir, Neighbor n);
	Neighbor getNeighbor(int dir);
	//Neighbor[] getLoop(int dir);
}
