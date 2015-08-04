package com.tsc.drawing;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Todd Cherry on 8/2/2015.
 */
public class Sprite{

    private int _rotation = 0;
    private Point _velocity = new Point(0,0);
    private Matrix _m;
    private Bitmap _bm;
    private int _x = 0;
    private int _y = 0;

    //constructor
    public Sprite(int x, int y, Bitmap bm){
        _x = x;
        _y = y;
        _bm = bm;
        _m = new Matrix();
    }

    public int getX() {
        return _x;
    }
    public int getY() {
        return _y;
    }

    public void setXY(int x, int y) {
        _x = x;
        _y = y;
    }
    public Point getXY(){
        return new Point(_x, _y);
    }


    public int getWidth(){
        return _bm.getWidth();
    }
    public int getHeight(){
        return _bm.getHeight();
    }

    public int getRotation() {
        return _rotation;
    }

    public void setRotation(int rotation) {
        _rotation += rotation;
    }

    public Matrix get_matrix() {
        return _m;
    }

    public void set_matrix(Matrix m) {
        _m = m;
    }

    public Bitmap get_bm() {
        return _bm;
    }

    public void genRandomVelocity() {
        Random r = new Random();
        int min = 1;
        int max = 5;
        int x = r.nextInt(max-min+1)+min;
        int y = r.nextInt(max-min+1)+min;
        _velocity =  new Point (x,y);
    }

    public Point get_velocity() {
        return _velocity;
    }

    public void set_velocity(Point val) {
        _velocity = val;
    }
}
