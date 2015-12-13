package com.ramblescript.uncubed.view;

import android.graphics.Canvas;

/**
 * Created by dmitri on 02/12/15.
 */
public interface iInteractive {

    void draw(Canvas canvas);
    void update(long deltaTime);
    void animate(Animator animator, boolean override);

    void checkSelection(double x, double y);
}
