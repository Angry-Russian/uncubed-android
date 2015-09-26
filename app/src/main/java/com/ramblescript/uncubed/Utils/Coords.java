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
        double tx, ty;
        if(center == null){
            tx = Math.sin(theta) * radius;
            ty = Math.cos(theta) * radius;
        }else {
            tx = center.x + Math.sin(theta) * radius;
            ty = center.y + Math.cos(theta) * radius;
        }
        return new Coords((float)tx, (float)ty);
    }

    public static Coords polarToCartesian(Coords center, double theta, double radius){
        double tx, ty;
        if(center == null){
            tx = Math.sin(theta) * radius;
            ty = Math.cos(theta) * radius;
        }else {
            tx = center.x + Math.sin(theta) * radius;
            ty = center.y + Math.cos(theta) * radius;
        }
        return new Coords((float)tx, (float)ty);
    }

    public static Coords cartToPolar(Coords center, double x, double y){
        double theta, radius;

        if(center == null){
            theta = Math.atan2(y, x);
            radius = Math.sqrt(x*x+y*y);
        }else{
            theta = Math.atan2(y - center.y, x - center.x);
            radius = Math.sqrt(x*x+y*y);
        }
        return new Coords(theta, radius);
    };
}
