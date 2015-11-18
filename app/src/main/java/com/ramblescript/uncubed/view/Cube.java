package com.ramblescript.uncubed.view;

import android.graphics.Canvas;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.model.Neighbor;

import java.util.ArrayList;

/**
 * Created by Dmitri on 11/17/2015.
 */
public class Cube extends FaceView {


    private boolean showDirUI = false;

    private int divisions;
    private int dimensions;

    public int getDivisions() {
        return divisions;
    }
    public void setDivisions(int divisions) {
        this.divisions = divisions;
    }
    public int getDimensions() {
        return dimensions;
    }
    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public Cube(Face model, int color) {
        super(model, color);
    }

    @Override
    public void draw(Canvas c){
        super.draw(c);
    }

    @Override
    public void update(long deltaTime){
        super.update(deltaTime);
    }

    public void shift(float x, float y, int dir){
        ArrayList<Neighbor> loop = getSelected(x, y, dir);

        int l;
        if(loop != null) l = loop.size();
        else return;

        if(l > 0){
            int[] colors = new int[l];

            for(int i = 0; i<l; i++){
                Neighbor nextTile = loop.get((i + divisions)%l);
                colors[i] = nextTile.getColor();
            }

            for(int i = 0; i<l; i++){
                loop.get(i).setColor(colors[i]);
            }
        }
    }
}
