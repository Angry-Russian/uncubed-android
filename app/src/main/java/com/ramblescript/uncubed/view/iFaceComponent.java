package com.ramblescript.uncubed.view;


import com.ramblescript.uncubed.model.Neighbor;

import java.util.ArrayList;

/**
 * Created by Dmitri on 24/5/15.
 */
public interface iFaceComponent extends iInteractive{

    ArrayList<Neighbor> getSelected(double x, double y, int direction);
    void deselect();

    iFaceComponent setRect(float x, float y, float w, float h, double r);
}
