package com.ramblescript.uncubed.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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

    public FaceViewPolar(Face model, int color){
        super(model, color);

        shape.reset();
        this.setRect(x, y, w, h, (float) rotation);
    }

    @Override
    /**
     * @param x defines rotation about the origin. A.k.a. the Theta component.
     * @param y defines distant from origin, a.k.a. the radius component
     * @param w defines the width of the rectangle, in degrees
     * @param h defines the height of the rectangle as the radius component
     * @param r defines the rotation of the rectangle about its center
     *
     */
    public FaceView setRect(float x, float y, float w, float h, float r){
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
        //canvas.rotate((float) this.rotation);

        int tcolor = this.getColor();
        paint.setColor(tcolor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(shape, this.paint);
        paint.setColor(0xFFFFFFFF);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.5f);
        canvas.drawPath(shape, this.paint);
        paint.setColor(tcolor);

        if(null != components) for (int i = 0, j = components.length; i<j; i++) {
            components[i].draw(canvas);
        }

        if(model != null && model.isSelected())
            paint.setAlpha(255);
        else paint.setAlpha(88);

        canvas.restore();
    }

    @Override
    public void checkSelection(double x, double y) {

        boolean selected = area.contains((int) x, (int) y);

        if(model != null){
            if(selected) model.select();
            ArrayList<Neighbor> selection = model.getLoop(0);
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
    }
}