package com.ramblescript.uncubed.view;

import android.graphics.Canvas;

/**
 * Created by dmitri on 02/12/15.
 */

public class UILayer implements iInteractive {
    private FaceView reference = null;
    private double x = 0;
    private double y = 0;

    /**
     * A UILayer is a drawing layer meant to be a collection of UIs that share a common coordinate space.
     * One layer is meant to group elements that are conceptually related to eachother, such as a group of arrows
     *
     */
    public UILayer(){
        // a generic UI layer
    }
    public UILayer(double x, double y){
        this.x = x;
        this.y = y;
        // a UI layer with a set coordinate value
    }
    public UILayer(FaceView reference){
        // a UI Layer centered on and following a FaceView
        this.reference = reference;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate((float) x, (float) y);
        if(reference != null) canvas.rotate((float)reference.rotation);
        // draw arrows
        canvas.restore();
    }

    @Override
    public void update(long deltaTime) {
        // update arrow positions, i.e. for pulling and snapping
        if(reference != null){
            this.x = reference.x + Math.cos(reference.rotation) * reference.w * 0.5;
            this.y = reference.y + Math.sin(reference.rotation) * reference.h * 0.5;
        }else{
            // do something
        }
        // do other things
    }

    @Override
    public void animate(Animator animator, boolean override) {
        // ... are there any animations for this?
    }

    @Override
    public void checkSelection(double x, double y) {
        // update arrow positions based on finger movement
    }
}
