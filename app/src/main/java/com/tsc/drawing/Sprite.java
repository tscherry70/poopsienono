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
    private Point _lastCollision = new Point(0,0);
    private Matrix _m;
    private Bitmap _bm;
    private int _x = 0;
    private int _y = 0;
    private int _rotationSpeed = 0;
    private int _id = 0;

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

    public int getWidth(){
        return _bm.getWidth();
    }
    public int getHeight(){
        return _bm.getHeight();
    }
    public int getRotation() {
        return _rotation;
    }
    public void incrementRotation(int rotation) {
        _rotation += rotation;
    }
    public Matrix get_matrix() { return _m; }
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

    public void setRotation(int val) {
        _rotation = val;
    }

    public int get_rotationSpeed() {
        return _rotationSpeed;
    }

    public void set_rotationSpeed(int _rotationSpeed) {
        this._rotationSpeed = _rotationSpeed;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Point get_lastCollision() {
        return _lastCollision;
    }

    public void set_lastCollision(Point val) {
        _lastCollision = val;
    }
}
