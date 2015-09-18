package com.ramblescript.uncubed.Utils;

/**
 * Created by dmitri on 11/09/15.
 */
public class Coords {
    public float x = 0;
    public float y = 0;

    public Coords(float x, float y){
        this.x = x; this.y = y;
    }

    public static Coords polarToCartesian(Coords center, float theta, float radius){
        float x = center.x + (float) Math.sin(theta) * radius;
        float y = center.y + (float) Math.cos(theta) * radius;

        return new Coords(x, y);
    }
}
