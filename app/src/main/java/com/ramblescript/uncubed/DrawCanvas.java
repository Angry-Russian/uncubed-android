package com.ramblescript.uncubed;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.ramblescript.uncubed.Utils.Coords;
import com.ramblescript.uncubed.model.Neighbor;
import com.ramblescript.uncubed.view.FaceView;
import com.ramblescript.uncubed.view.UC_Interactive;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Dmitri on 18/5/15.
 */
public class DrawCanvas extends View {

    private long lastTick = 0;
    private long tick = 0;

    private Paint paint = new Paint();

	private FaceView cube;

	public DrawCanvas(Context context, AttributeSet attrs){
		super(context, attrs);

        paint.setColor(0xFFe7e7e7);
        paint.setStyle(Paint.Style.FILL);

        UncubedGame.setPreferredDimentions(4);
        UncubedGame.setPreferredDivisions(3);

		cube = UncubedGame.getInstance().setRect(getWidth()/2, getHeight()/2, 0, 0, 0);

		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		cube.setRect(w/2, h/2, 0, 0, 0);
	}

	@Override
	protected void onDraw(Canvas c){
        lastTick = tick;

        c.drawRect(c.getClipBounds(), this.paint);

        tick = System.currentTimeMillis();
        cube.update(tick - lastTick);
        cube.draw(c);
        invalidate();
	}

    private int primaryPID = -1;
    private int secondaryPID = -1;
    private double lastDistance = -1;
	@Override
	public boolean onTouchEvent(MotionEvent e){
		switch(e.getAction()){
			case MotionEvent.ACTION_DOWN:
                cube.deselect();
                cube.checkSelection(e.getX(), e.getY());
                //cube.showUI;
                invalidate();
                break;

			case MotionEvent.ACTION_UP:
                cube.deselect();
                //cube.hideUI;
				break;

			case MotionEvent.ACTION_MOVE:
                if (e.getPointerCount() > 1) {
                    // scale & rotate
                    float dx = e.getX(secondaryPID) - e.getX(primaryPID);
                    float dy = e.getY(secondaryPID) - e.getY(primaryPID);
                    double distance = Math.sqrt(dx*dx+dy*dy);

                    if(lastDistance == -1) ;lastDistance = distance;

                    cube.setScale(lastDistance/distance);
                }else {
                    // interact normally
                    cube.deselect();
                    cube.checkSelection(e.getX(), e.getY());
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // starts scaling & rotating
                if(e.getPointerCount() == 2) {
                    primaryPID = e.getPointerId(0);
                    secondaryPID = e.getPointerId(1);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // stops scaling & rotating
                primaryPID = -1;
                secondaryPID = -1;
                lastDistance = -1;
                break;
		}

		return true;
	}
}
