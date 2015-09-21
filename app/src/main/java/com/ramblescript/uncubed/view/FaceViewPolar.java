package com.ramblescript.uncubed.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.ramblescript.uncubed.Utils.Coords;
import com.ramblescript.uncubed.model.Face;

/**
 * Created by dmitri on 11/09/15.
 */
public class FaceViewPolar extends FaceView {

    public FaceViewPolar(Face model, int color){
        super(model, color);

        shape.reset();
        this.setRect(x, y, w, h, (float) rotation);
    }

    @Override
    /**
     * @param x defines rotation about the origin poing. A.k.a. the Theta component.
     * @param y defines distant from origin, a.k.a. the radius component
     * @param w defines the width of the rectangle, in degrees
     * @param h defines the height of the rectangle as the radius component
     * @param r defines the rotation of the rectangle about its center
     *
     */
    public FaceView setRect(float x, float y, float w, float h, float r){
        shape.reset();

        Coords center = new Coords(0, 0);
        float radAngle = w;

        Coords coords = null;
        Coords lastCoords = null;

        double xoffset = x;///180*Math.PI;

        for(int i = 0; i<=4; i++){
            double a = i * Math.PI/2;
            double theta = w * Math.sin(a) + xoffset;
            double radius = h * Math.cos(a) + y;


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

        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) this.rotation);
        canvas.drawPath(shape, this.paint);

        if(null != components) for (int i = 0, j = components.length; i<j; i++) {
            components[i].draw(canvas);
        }

        if(model != null && model.isSelected())
            paint.setAlpha(255);
        else paint.setAlpha(88);


        Paint textPaint = new Paint();
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(20);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.restore();
    }

    @Override
    public void checkSelection(double x, double y) {
        
    }
}