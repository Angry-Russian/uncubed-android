package com.ramblescript.uncubed;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

/**
 * Created by Dmitri on 24/5/15.
 */
public class Face implements UC_Drawable {

    private Paint paint = new Paint();
    private Path shape = new Path();

    private float rotation = 0;
    private float x = 0;
    private float y = 0;
    private float w = 32;
    private float h = 32;
    private float scale = 1;

    private UC_Drawable[] components;

    public Face(int color){
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        shape.addRect(0, 0, 128, 128, Path.Direction.CW);
    }

    public Face(int color, UC_Drawable[] components){
        this(color);
        this.components = components;
    }

    public void draw(Canvas canvas) {
        canvas.save();

        canvas.translate(x + w/2, y + h/2);
        canvas.rotate(rotation);
        canvas.translate(-w/2, -h/2);
        canvas.scale(scale, scale);

        //paint.setShadowLayer(16, 0, 0, paint.getColor());
        canvas.drawPath(shape, paint);

        if(null != components) for (int i = 0, j = components.length; i<j; i++) {
            components[i].draw(canvas);
        }
        canvas.restore();
    }

    public Face setRect(float x, float y, float w, float h, float rotation){
        this.x = x; this.y = y; this.w = w; this.h = h; this.rotation = rotation;
        shape = new Path();
        shape.addRect(0, 0, w, h, Path.Direction.CW);
        shape.addRect(w/2, h/2, 16, 16, Path.Direction.CW);
        return this;
    }

    public Face setColor(int color){
        paint.setColor(color);
        return this;
    }

    public Face setColor(int a, int r, int g, int b){
        paint.setARGB(a, r, g, b);
        return this;
    }

    public Face setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }
}
