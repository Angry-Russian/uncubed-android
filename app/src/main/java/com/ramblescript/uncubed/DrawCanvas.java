package com.ramblescript.uncubed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import android.graphics.Canvas;
import android.view.MotionEvent;

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
        lastTick = tick;
        tick = System.currentTimeMillis();
        cube.update(tick - lastTick);
        cube.draw(c);
        invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e){
		switch(e.getAction()){
			case MotionEvent.ACTION_DOWN:
                cube.deselect();
                cube.checkSelection(e.getX(), e.getY());


                ArrayList<Neighbor> loop = cube.getSelected(e.getX(), e.getY(), 2);

                int l = loop.size();
                if(loop != null && l > 0){
                    int[] colors = new int[l];

                    for(int i = 0; i<l; i++){
                        Neighbor nextTile = loop.get((i+1)%l);
                        colors[i] = nextTile.getColor();
                    }

                    for(int i = 0; i<l; i++){
                        loop.get(i).setColor(0xFF0000FF);
                    }

                    invalidate();
                }
                break;

			case MotionEvent.ACTION_UP:
				break;

			case MotionEvent.ACTION_MOVE:
				break;
		}

		return true;
	}
}
