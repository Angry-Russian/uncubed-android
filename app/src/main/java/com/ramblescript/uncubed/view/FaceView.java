package com.ramblescript.uncubed.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.ramblescript.uncubed.model.Face;

/**
 * Created by Dmitri on 6/12/2015.
 */
public class FaceView implements UC_Drawable{

    private UC_Drawable[] components;

    private Paint paint = new Paint();
    private Path shape = new Path();

    private Paint debugPaint = new Paint();
    private Path debugShape = new Path();

    private float rotation = 0;
    private float x = 0;
    private float y = 0;
    private float w = 32;
    private float h = 32;
    private float scale = 1;

    private Face model = null;

    public void setModel(Face model){this.model = model;}
    public Face getModel(){return model;}

    public FaceView(Face model, int color){
        this.model = model;

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        debugPaint.setColor(color % 0x1000000 + 0xFF000000);
        debugPaint.setAntiAlias(true);
        debugPaint.setStrokeWidth(2);
        debugPaint.setStyle(Paint.Style.STROKE);
        debugPaint.setStrokeJoin(Paint.Join.ROUND);
        debugPaint.setStrokeCap(Paint.Cap.ROUND);

        shape.addRect(0, 0, 128, 128, Path.Direction.CW);
        debugShape.addRect(0, 0, 128, 128, Path.Direction.CW);
    }

    public void draw(Canvas canvas) {
        canvas.save();

        canvas.translate(x, y);


        canvas.rotate(rotation);
        canvas.translate(-w / 2, -h / 2);
        canvas.scale(scale, scale);

        canvas.drawPath(debugShape, debugPaint);
        //paint.setShadowLayer(16, 0, 0, paint.getColor());
        canvas.drawPath(shape, paint);

        if(null != components) for (int i = 0, j = components.length; i<j; i++) {
            components[i].draw(canvas);
        }
        canvas.restore();
    }

    public void setComponents(UC_Drawable[] components){this.components = components;}
    public UC_Drawable[] getComponents(){return components;}

    public FaceView setRect(float x, float y, float w, float h, float rotation){
        this.x = x; this.y = y; this.w = w; this.h = h; this.rotation = rotation;

        shape = new Path();
        shape.addRect(0, 0, w, h, Path.Direction.CW);

        debugShape = new Path();
        debugShape.addRect(0, 0, 128, 128, Path.Direction.CW);

        return this;
    }

    public FaceView setColor(int color){
        paint.setColor(color);
        return this;
    }

    public FaceView setColor(int a, int r, int g, int b){
        paint.setARGB(a, r, g, b);
        return this;
    }

    public FaceView setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

}
