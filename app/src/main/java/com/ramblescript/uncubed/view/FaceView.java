package com.ramblescript.uncubed.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.ramblescript.uncubed.model.Face;
import com.ramblescript.uncubed.model.Neighbor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Dmitri on 6/12/2015.
 */
public class FaceView implements UC_Drawable{

	public int id = -1;

	protected UC_Drawable[] components;

	protected Paint paint = new Paint();
	protected Path shape = new Path();

	protected Paint debugPaint = new Paint();
	protected Path debugShape = new Path();

	protected Face parent;
	protected double rotation = 0;
	protected float x = 0;
	protected float y = 0;
	protected float w = 32;
	protected float h = 32;
	protected double scale = 1;

	private double sqrt2 = Math.sqrt(2);

	protected Face model = null;

	public void setModel(Face model){this.model = model;}
	public Face getModel(){return model;}

	public FaceView(Face model, int color){
		this.model = model;

		paint.setColor(color);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(20);
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);

		debugPaint.setColor(color % 0x1000000 + 0xFF000000);
		debugPaint.setAntiAlias(true);
		debugPaint.setStrokeWidth(2);
		debugPaint.setStyle(Paint.Style.STROKE);
		debugPaint.setStrokeJoin(Paint.Join.ROUND);
		debugPaint.setStrokeCap(Paint.Cap.ROUND);

		shape.addRect(0, 0, 128, 128, Path.Direction.CW);
		debugShape.addRect(0, 0, 128, 128, Path.Direction.CW);
	}

	public void draw(Canvas canvas) {
		canvas.save();

		if(x != 0 || y != 0)    canvas.translate(x, y);
		if(rotation != 0)       canvas.rotate((float)rotation);
		if(w > 0 || h > 0)      canvas.translate(-w / 2, -h / 2);
		if(scale !=1)           canvas.scale((float)scale, (float)scale);

		//paint.setShadowLayer(16, 0, 0, paint.getColor());
		if(model != null && model.isSelected())
			paint.setAlpha(255);
		else paint.setAlpha(128);

		canvas.drawPath(shape, paint);

		if(null != components) for (int i = 0, j = components.length; i<j; i++) {
			components[i].draw(canvas);
		}


		Paint textPaint = new Paint();
		textPaint.setColor(0xFFFFFFFF);
		textPaint.setAntiAlias(true);
		textPaint.setStrokeWidth(20);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setStrokeJoin(Paint.Join.ROUND);
		textPaint.setStrokeCap(Paint.Cap.ROUND);

        // debugging, uncomment to show face numbers
		canvas.translate((int) w / 2, (int) h / 2 + 16);
		canvas.scale(3, 3);
		if(this.model != null) canvas.drawText("" + id, 0, 0, textPaint);//*/
		canvas.restore();
	}

	public void setComponents(UC_Drawable[] components){this.components = components;}
	public UC_Drawable[] getComponents(){return components;}

	public FaceView setRect(float x, float y, float w, float h, float rotation){
		this.x = x; this.y = y; this.w = w; this.h = h; this.rotation = rotation;

		shape = new Path();
		shape.addRect(0, 0, w, h, Path.Direction.CW);

		debugShape = new Path();
		debugShape.addRect(0, 0, 128, 128, Path.Direction.CW);

		return this;
	}

	public FaceView setColor(int color){
		paint.setColor(color);
		return this;
	}

	public FaceView setColor(int a, int r, int g, int b){
		paint.setARGB(a, r, g, b);
		return this;
	}

	public int getColor(){return paint.getColor();}

	public FaceView setRotation(float rad) {
		this.rotation = rad;
		return this;
	}
	public double getRotation(){return this.rotation;}

	public void checkSelection(double x, double y){

		// get relative coords
		double dx = x - this.x;
		double dy = y - this.y;

		// calculate polar radians and distance, compensate for rotation
		double r = this.rotation/180*Math.PI;
		double rad = Math.atan2(dy, dx);
		double d = Math.sqrt(dx*dx + dy*dy);

		// compensate for rotation and use as new values
		dx = Math.cos(rad-r) * d + this.w/2;
		dy = Math.sin(rad-r) * d + this.h/2;

		boolean selected = Math.abs(dx - this.w/2) < this.w/2 + .5
						&& Math.abs(dy - this.h/2) < this.h/2 + .5;

		if(model != null){
			if(selected) model.select();
			ArrayList<Neighbor> selection = model.getLoop(0);
            selection.addAll(model.getLoop(1));
			Iterator<Neighbor> si = selection.listIterator();
			if(selected) while(si.hasNext()){
				Neighbor s = si.next();
				s.select();
			}
		}

		if(components != null) for(int i = 0, j = components.length; i<j; i++){
			if(selected || w == 0 || h == 0) components[i].checkSelection(dx, dy);
		}
	}

	public void deselect() {
		model.select(false);
		if(components != null) for(int i = 0, j = components.length; i<j; i++){
			components[i].deselect();
		}
	}
}
