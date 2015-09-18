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
    public FaceView setRect(float x, float y, float w, float h, float r){
        shape.reset();
        this.rotation = r*2;

        Coords line = null;
        Coords center = new Coords(x, y);
        float range = (float) Math.PI / 3;
        double hypothenuse = Math.sqrt(w*w+h*h);

        int steps = 90;


        line = Coords.polarToCartesian(center, range, 1);
        shape.moveTo(line.x, line.y);

        {// from point1 to point 2
            float step = range / steps;
            float variation = (float) ((hypothenuse - h) / steps);

            for(int i = 0; i<steps; i++){
                line = Coords.polarToCartesian(center, -range+i*step, h+variation*i);
                shape.lineTo(line.x, line.y);
            }
        }


        {// from point2 to point 3
            float step = range / steps;
            float variation = (float) ((hypothenuse - h) / steps);

            for(int i = 0; i<steps; i++){
                line = Coords.polarToCartesian(center, i*step, (float) hypothenuse-variation*i);
                shape.lineTo(line.x, line.y);
            }
        }

        line = Coords.polarToCartesian(center, range, h);
        shape.lineTo(line.x, line.y);

        line = Coords.polarToCartesian(center, range, 1);
        shape.lineTo(line.x, line.y);

        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) this.rotation);
        canvas.drawPath(shape, this.paint);
        canvas.restore();
        this.rotation++;
    }
/*
    @Override
    public void checkSelection(double x, double y) {

    }

    @Override
    public void deselect() {

    }*/
}