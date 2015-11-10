package com.ramblescript.uncubed.view;

import android.graphics.Canvas;

import com.ramblescript.uncubed.Utils.Rect;
import com.ramblescript.uncubed.model.Neighbor;

import java.util.ArrayList;

/**
 * Created by Dmitri on 24/5/15.
 */
public interface UC_Interactive {
    void draw(Canvas canvas);
    void update(long deltaTime);

    void checkSelection(double x, double y);
    ArrayList<Neighbor> getSelected(double x, double y, int direction);
    void deselect();

    void animate(Animator animator, boolean override);
    UC_Interactive setRect(float x, float y, float w, float h, double r);
    Rect getRect();
}
