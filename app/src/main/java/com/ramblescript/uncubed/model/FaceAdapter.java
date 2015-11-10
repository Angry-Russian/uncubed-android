package com.ramblescript.uncubed.model;

import com.ramblescript.uncubed.model.Face;

import java.util.ArrayList;

/**
 * Created by Dmitri on 21/6/15.
 */
public class FaceAdapter implements Neighbor{

	private static Neighbor[] transpose(Neighbor[] neighbors){
		int side = (int) (Math.ceil(Math.sqrt(neighbors.length)));
		Neighbor[] ncopy = new Neighbor[side * side];

		for(int i = 0, l = neighbors.length; i<l; i++){
			int x = i%side;
			int y = (int) Math.floor(i/side);
			ncopy[y + x * side] = neighbors[i];
		}

		return ncopy;
	}
	private static Neighbor[] flip(Neighbor[] neighbors, boolean vertical){
		int side = (int) (Math.ceil(Math.sqrt(neighbors.length)));
		Neighbor[] ncopy = new Neighbor[side * side];

		for(int i = 0, l = neighbors.length; i<l; i++){
			int x = i%side;
			int y = (int) Math.floor(i/side);
			if(vertical)
				ncopy[x + (side-y-1)*side] = neighbors[i];
			else
				ncopy[(side-x-1) + y*side] = neighbors[1];
		}

		return ncopy;
	}
	private static Neighbor[] flip(Neighbor[] neighbors){
		return flip(neighbors, false);
	}

	private static Neighbor[] rotate(Neighbor[] neighbors, boolean CCW){
		return flip(transpose(neighbors), CCW);
	}


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

	public Neighbor[] getComponents() {
		Neighbor[] neighbors = target.getNeighbors();
		for(int i = 0, j = Math.abs(directionOffset); i<j; i++){
			neighbors = FaceAdapter.rotate(neighbors, directionOffset<0);
		}
		return neighbors;
	}

	@Override
	public ArrayList<Neighbor> getLoop(int dir) { return target.getLoop(dir + directionOffset); }

	@Override
	public ArrayList<Neighbor> getLoop(int dir, ArrayList<Neighbor> loop) { return target.getLoop(dir + directionOffset, loop); }

	public void setNeighbor(int dir, Neighbor face){ target.setNeighbor(dir + directionOffset, face); }

	public void select(){
		target.select();
	}

	public void select(boolean s){
		target.select(s);
	}

    @Override
    public void setColor(int color) {
        target.setColor(color);
    }

    @Override
    public int getColor() {
        return target.getColor();
    }
}
