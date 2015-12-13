package com.ramblescript.uncubed;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.ramblescript.uncubed.view.FaceView;
import com.ramblescript.uncubed.view.UILayer;

/**
 * Created by Dmitri on 18/5/15.
 */
public class DrawCanvas extends View {

    private long lastTick = 0;
    private long tick = 0;

    private Paint paint = new Paint();

	private FaceView cube;

    ScaleGestureDetector scaler;
    UILayer shifter = null;

	public DrawCanvas(Context context, AttributeSet attrs){
		super(context, attrs);

        paint.setColor(0xFFe7e7e7);
        paint.setStyle(Paint.Style.FILL);

        UncubedGame.setPreferredDimentions(4);
        UncubedGame.setPreferredDivisions(3);

		cube = UncubedGame.getInstance().setRect(getWidth()/2, getHeight()/2, 0, 0, 0);

        scaler = new ScaleGestureDetector(context, new OnScaleGestureListener() {
            private double tmpscale;
            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
            }
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                tmpscale = cube.getScale();
                return true;
            }
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                Log.d("SCALE", "zoom ongoing, scale: " + detector.getScaleFactor());
                cube.setScale(tmpscale * detector.getScaleFactor());
                return false;
            }
        });

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

	@Override
	public boolean onTouchEvent(MotionEvent e){

        scaler.onTouchEvent(e);
        if (scaler.isInProgress()) {
            cube.deselect();
            return true;
        }

		switch(e.getAction()){
			case MotionEvent.ACTION_DOWN:
                cube.deselect();
                cube.checkSelection(e.getX(), e.getY());
                invalidate();
                break;

			case MotionEvent.ACTION_UP:
                //cube.hideUI;
				break;

			case MotionEvent.ACTION_MOVE:
                cube.deselect();
                cube.checkSelection(e.getX(), e.getY());
                break;
		}

		return true;
	}
}
