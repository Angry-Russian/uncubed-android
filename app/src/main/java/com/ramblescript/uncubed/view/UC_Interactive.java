package com.ramblescript.uncubed.view;

import android.graphics.Canvas;

import com.ramblescript.uncubed.Utils.Rect;

/**
 * Created by Dmitri on 24/5/15.
 */
public interface UC_Interactive {
    void draw(Canvas canvas);
    
    void checkSelection(double x, double y);
    void deselect();

    void animate(Animator animator, boolean override);
    UC_Interactive setRect(float x, float y, float w, float h, double r);
    Rect getRect();
}
