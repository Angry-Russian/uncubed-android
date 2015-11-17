package com.ramblescript.uncubed.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;

import com.ramblescript.uncubed.Utils.Coords;
import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.model.Neighbor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by dmitri on 11/09/15.
 */
public class FaceViewPolar extends FaceView {

    private Region area = new Region();
    private Region clip = new Region( -4500, -4500, 9000, 9000);
    private double roll = 0;

    /**
     * A FaceView object that draws itself around its (or its parent's) origin.
     * @param model
     * @param color
     */
    public FaceViewPolar(Face model, int color){
        super(model, color);

        shape.reset();
        this.setRect(x, y, w, h, (float) rotation);
    }

    @Override
    /**
     * @param x defines rotation about the origin, from 0 to 2PI. A.k.a. the Theta component. This is the direction the face "points" in.
     * @param y defines the distance of the View's center from origin, a.k.a. the radius component. y=0 makes a figure-8-like object, as the center is on the origin.
     * @param w defines the width of the rectangle, in degrees.
     * @param h defines the height of the rectangle as the radius component
     * @param r defines the rotation of the rectangle about its center
     *
     * Note: the polygon is drawn as a cicle with only 4 points, so the width is actually the diagonal of the square, not its side.
     */
    public FaceViewPolar setRect(float x, float y, float w, float h, double r){
        shape.reset();

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.rotation = r;

        Coords center = new Coords(0, 0);
        float radAngle = w;

        Coords coords = null;
        Coords lastCoords;

        if(parent != null){
            x = parent.x + x;
            y = parent.y + y;
            /*/Coords c = Coords.cartToPolar(null, x/parent.w, y/parent.h);
            c.x -= parent.rotation;
            c = Coords.polarToCartesian(null, c.x, c.y);
            x = parent.x + (float)(c.x*parent.w);
            y = parent.y + (float)(c.y*parent.h);//*/
                                /*/
                                (float) (stepT * (k%div) - stepT * Math.floor(k/div)),
                                (float) (-stepR * (1+k%div) - stepR * Math.floor(k/div) + side),
                                //*/
            r = (float)(parent.rotation + r);
        }

        for(int i = 0; i<=4; i++){

            double a = i * Math.PI/2 + Math.PI/4;
            double theta = w * Math.sin(a+r) + x;
            double radius = h * Math.cos(a+r) + y;

            lastCoords = coords;
            coords  = new Coords(theta, radius);

            if(i==0){
                Coords corner = Coords.polarToCartesian(center, coords.x, coords.y);
                shape.moveTo((float)corner.x, (float)corner.y);
            }else{
                Coords delta = new Coords((coords.x - lastCoords.x)/120, (coords.y - lastCoords.y)/120);
                for(int j = 0; j<120; j++){
                    Coords corner = Coords.polarToCartesian(center,
                            lastCoords.x + delta.x*j,
                            lastCoords.y + delta.y*j);
                    shape.lineTo((float)corner.x, (float)corner.y);
                }
            }
        }
        area.setPath(shape, clip);
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();

        Animator animator = getAnimator();
        if(animator != null) animator.visit(this);

        int tcolor = getColor();


        setColor(tcolor);

        if(model != null && model.isSelected())
            paint.setAlpha(255);
        else paint.setAlpha(128);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawPath(shape, this.paint);


        //setColor(0xFFFFFFFF);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.85f);
        canvas.drawPath(shape, this.paint);

        setColor(tcolor);//*/

        if(null != components) for (int i = 0, j = components.length; i<j; i++) {
            components[i].draw(canvas);
        }


        canvas.restore();
    }

    @Override
    public void checkSelection(double x, double y) {

        boolean selected = area.contains((int) x, (int) y);

        ArrayList<Neighbor> selection = null;
        if(model != null){
            if(selected) model.select();
            selection = model.getLoop(0);
            selection.addAll(model.getLoop(1));
            Iterator<Neighbor> si = selection.listIterator();
            if(selected) while(si.hasNext()){
                Neighbor s = si.next();
                s.select();
            }
        }

        if(selected){
            this.model.select();
            if(components != null){
                for(int i = 0, j = components.length; i<j; i++){
                    components[i].checkSelection(x, y);
                }
            }
            setRect(this.x, this.y, w, h, (float)rotation);
        }

        return;
    }

    @Override
    public ArrayList<Neighbor> getSelected(double x, double y, int direction) {

        boolean selected = area.contains((int) x, (int) y);

        ArrayList<Neighbor> selection = new ArrayList<>();
        if(components != null && components.length > 0) {
            for (int i = 0; i < components.length; i++) {
                ArrayList<Neighbor> comps = components[i].getSelected(x, y, direction);
                if(comps != null) selection.addAll(comps);
            }
        }else if(model != null){
            if(selected)
                selection.addAll(model.getLoop(direction));
        }

        return selection;
    }
}