package com.ramblescript.uncubed.view;

/**
 * Created by Dmitri on 10/20/2015.
 */
public abstract class Animator {
    /**
     *
     * @return true while the animation is going. False on and after last frame.
     */
    public abstract boolean visit(iFaceComponent visitor);
}