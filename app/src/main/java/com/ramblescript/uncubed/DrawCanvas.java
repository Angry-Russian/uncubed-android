package com.ramblescript.uncubed;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by Dmitri on 18/5/15.
 */
public class DrawCanvas extends View {

    private UC_Drawable[] components;


    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;//*/

    public DrawCanvas(Context context, AttributeSet attrs){
        super(context, attrs);

        Log.i("123qwe", "123qwe");

        components = new UC_Drawable[6];

        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//*/

        for(int i = 0; i<components.length; i++){

            float rad = (float) (360 / components.length * (i%3) * 2  +  (float) Math.floor(i/3) * 60);
            float rx = ((float) Math.cos(rad/180*Math.PI) * 128);
            float ry = ((float) Math.sin(rad/180*Math.PI) * 128);

            components[i] = new Face( 0xFF000000 + (int) Math.ceil(Math.random() * 0xFFFFFF) )
                    .setRect(400+rx, 800+ry, 128, 128, 0)
                    .setRotation(rad + 45);
        }

        invalidate();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);//*/
    }

    @Override
    protected void onDraw(Canvas c){
        //c.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        c.drawPath(drawPath, drawPaint);

        if(null != components) for(int i = 0; i<components.length; i++){
            components[i].draw(c);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        // drawPath = new Path();
       /* switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPaint.setColor((int) (0x80000000 + 0x1000000 * Math.random()));
                drawPath.moveTo(e.getX(), e.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(e.getX(), e.getY());
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
        }//*/

        invalidate();
        return true;
    }

}
