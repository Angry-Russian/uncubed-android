package com.ramblescript.uncubed.view;

import com.ramblescript.uncubed.view.UC_Interactive;

/**
 * Created by Dmitri on 10/20/2015.
 */
public abstract class Animator {
    /**
     *
     * @return true while the animation is going. False on and after last frame.
     */
    public abstract boolean visit(UC_Interactive visitor);
}