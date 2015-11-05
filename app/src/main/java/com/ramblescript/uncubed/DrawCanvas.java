package com.ramblescript.uncubed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.ramblescript.uncubed.view.FaceView;

/**
 * Created by Dmitri on 18/5/15.
 */
public class DrawCanvas extends View {

	private FaceView cube;

	public DrawCanvas(Context context, AttributeSet attrs){
		super(context, attrs);

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
        cube.draw(c);
        invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e){
		switch(e.getAction()){
			case MotionEvent.ACTION_DOWN:
				break;

			case MotionEvent.ACTION_UP:
				break;

			case MotionEvent.ACTION_MOVE:
				break;
		}

		cube.deselect();
		cube.checkSelection(e.getX(), e.getY());
		invalidate();
		return true;
	}
}
