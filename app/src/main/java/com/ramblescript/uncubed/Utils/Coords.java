package com.ramblescript.uncubed.Utils;

/**
 * Created by dmitri on 11/09/15.
 */
public class Coords {
    public double x = 0;
    public double y = 0;

    public Coords(double x, double y){
        this.x = x; this.y = y;
    }

    public static Coords polarToCartesian(Coords center, float theta, float radius){
        double tx = center.x + Math.sin(theta) * radius;
        double ty = center.y + Math.cos(theta) * radius;

        return new Coords((float)tx, (float)ty);
    }

    public static Coords polarToCartesian(Coords center, double theta, double radius){
        double tx = center.x + Math.sin(theta) * radius;
        double ty = center.y + Math.cos(theta) * radius;

        return new Coords((float)tx, (float)ty);
    }
}
