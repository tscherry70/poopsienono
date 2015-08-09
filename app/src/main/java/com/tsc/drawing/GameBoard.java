package com.tsc.drawing;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.tsc.poopsienono.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends View{


    private Paint p;
    private List<Point> starField = null;
    private int starAlpha = 80;
    private int starFade = 2;
    private int MaxPoopSprites = 10;
    private static final int NUM_OF_STARS = 25;
    public List<Sprite> poops;
    public Sprite dude;


    synchronized public void resetStarField() {
        starField = null;
    }

    private int getRandomXLocation(){
        Random r = new Random();
        int maxX = findViewById(R.id.the_canvas).getWidth();
        return r.nextInt(maxX);
    }
    private int getRandomNum(int low, int high){
        Random r = new Random();
        return r.nextInt(high - low + 1) + low;
    }
    private Point getRandomPoint() {
        Random r = new Random();
        int minX = 0;
        int maxX = findViewById(R.id.the_canvas).getWidth() - dude.getWidth();
        int x = 0;
        int minY = 0;
        int maxY = findViewById(R.id.the_canvas).getHeight() - dude.getHeight();
        int y = 0;
        x = r.nextInt(maxX - minX + 1) + minX;
        y = r.nextInt(maxY - minY +1 ) + minY;
        return new Point (x,y);
    }

    synchronized public void initGfx(){

        Point p1, p2;

        do {
            p1 = getRandomPoint();
            p2 = getRandomPoint();

        } while (Math.abs(p1.x - p2.x) < dude.getWidth());

        dude.setXY(findViewById(R.id.the_canvas).getWidth() / 2,
                findViewById(R.id.the_canvas).getHeight() - 50);

        int id = 0;
        for(Sprite poop : poops){
            poop.setXY(getRandomXLocation(), 0);
            poop.genRandomVelocity();
            poop.set_rotationSpeed(getRandomNum(1, 20));
            poop.set_id(id++);
        }

        //poop.setXY(p2.x, p2.y);
        //poop.genRandomVelocity();
        dude.set_velocity(new Point(1,1));
    }

    public GameBoard(Context context, AttributeSet aSet) {
        super(context, aSet);
        //it's best not to create any new objects in the on draw
        //initialize them as class variables here
        p = new Paint();

        poops = new ArrayList<Sprite>();
        for (int x = 0; x < MaxPoopSprites; x++){
            poops.add(new Sprite(-1,-1, BitmapFactory.decodeResource(getResources(), R.drawable.poop1_trans)));
        }
        //poop = new Sprite(-1,-1, BitmapFactory.decodeResource(getResources(), R.drawable.poop1_trans));
        dude = new Sprite(-1,-1, BitmapFactory.decodeResource(getResources(), R.drawable.robot1));

    }

    private void initializeStars(int maxX, int maxY) {
        starField = new ArrayList<Point>();
        for (int i=0; i<NUM_OF_STARS; i++) {
            Random r = new Random();
            int x = r.nextInt(maxX-5+1)+5;
            int y = r.nextInt(maxY-5+1)+5;
            starField.add(new Point (x,y));
        }
    }
    private boolean checkForCollision(int id) {

        Sprite p = poops.get(id);
        Rect r1 = new Rect(p.getX(), p.getY(), p.getX() + p.getWidth(), p.getY() + p.getHeight());

        for(int x = 0; x < MaxPoopSprites; x++){

            Sprite other = poops.get(x);

            if(other.get_id() == id){
                continue;

            } else {

                Rect r2 = new Rect(other.getX(), other.getY(), other.getX() + other.getWidth(), other.getY() + other.getHeight());
                Rect r3 = new Rect(r1);
                if(r1.intersect(r2)){
                    for (int i = r1.left; i < r1.right; i++) {
                        for (int j = r1.top; j < r1.bottom; j++) {
                            if (p.get_bm().getPixel(i - r3.left, j - r3.top) != Color.TRANSPARENT) {
                                if (other.get_bm().getPixel(i - r2.left, j - r2.top) != Color.TRANSPARENT) {
                                    p.set_lastCollision(new Point(other.getX() + i - r2.left, other.getY() + j - r2.top));
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    synchronized public void onDraw(Canvas canvas) {
        //create a black canvas
        p.setColor(Color.BLACK);
        p.setAlpha(255);
        p.setStrokeWidth(1);
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        //initialize the starfield if needed
        if (starField == null) {
            initializeStars(canvas.getWidth(), canvas.getHeight());
        }
        //draw the stars
        p.setColor(Color.CYAN);
        p.setAlpha(starAlpha += starFade);
        //fade them in and out
        if (starAlpha >= 252 || starAlpha <= 80) starFade = starFade * -1;
        p.setStrokeWidth(5);

        for (int i = 0; i < NUM_OF_STARS; i++) {
            canvas.drawPoint(starField.get(i).x, starField.get(i).y, p);
        }

        //dude
        if (dude.getX() >= 0) {
            canvas.drawBitmap(dude.get_bm(), dude.getX(), dude.getY(), null);
        }
        for(Sprite poop : poops) {
            //poop4
            if (poop.getX() >= 0) {
                Matrix m = poop.get_matrix();
                m.reset();
                m.postTranslate(poop.getX(), poop.getY());
                m.postRotate(poop.getRotation(),
                        (float) (poop.getX() + poop.getWidth() / 2.0),
                        (float) (poop.getY() + poop.getHeight() / 2.0));

                canvas.drawBitmap(poop.get_bm(), m, null);
                poop.incrementRotation(poop.get_rotationSpeed());
                if (poop.getRotation() >= 360)
                    poop.incrementRotation(0);
            }


            boolean collisionDetected = checkForCollision(poop.get_id());
            if(collisionDetected) {
                if (poop.get_lastCollision().x > 0 || poop.get_lastCollision().y > 0) {
                    canvas.drawLine(poop.get_lastCollision().x - 5, poop.get_lastCollision().y - 5,
                            poop.get_lastCollision().x + 5, poop.get_lastCollision().y + 5, p);
                    canvas.drawLine(poop.get_lastCollision().x + 5, poop.get_lastCollision().y - 5,
                            poop.get_lastCollision().x - 5, poop.get_lastCollision().y + 5, p);
                }
            }
        }

        /*String msg = "poop x: " + poop.getX() + " y: " + poop.getY();
        msg += "\ndude x: " + dude.getX() + " y: " + dude.getY();

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(30);
        canvas.drawText(msg, 40,40, paint);*/
    }

    public void frameUpdate() {

            //Point dudePoint = dude.getXY();

        for(Sprite poop: poops){
            int poopY = poop.getY();
            int poopX = poop.getX();

            if(poopY > findViewById(R.id.the_canvas).getHeight() + 100){

                poopX = getRandomXLocation();
                poopY = -200;

            }else{

                poopY += poop.get_velocity().y;
            }

            poop.setXY(poopX, poopY);

        }
            //Point poopPoint = poop.getXY();
            //Point curVel = dude.get_velocity();

            //Now calc the new positions.
            //Note if we exceed a boundary the direction of the velocity gets reversed.
            //dudePoint.x = dudePoint.x + curVel.x;
           /* if (dudePoint.x > sprite1MaxX || dudePoint.x < 5) {
                Point newLoc = new Point(curVel.x *= -1,curVel.y);
                dude.set_velocity(newLoc);
            }*/
            //dudePoint.y = dudePoint.y + curVel.y;
           /* if (dudePoint.y > sprite1MaxY || dudePoint.y < 5) {
                Point newLoc = new Point(curVel.x, curVel.y *= -1);
                dude.set_velocity(newLoc);
            }*/

            //curVel = ((GameBoard)findViewById(R.id.the_canvas)).poop.get_velocity();

           /* if(poopPoint.y > findViewById(R.id.the_canvas).getHeight() + 100){

                poopPoint.x = getRandomXLocation();
                poopPoint.y = -200;
            }*/

            //poopPoint.x = poopPoint.x + ((GameBoard)findViewById(R.id.the_canvas)).poop.get_velocity().x;
            /*if (poopPoint.x > sprite2MaxX || poopPoint.x < 5) {
                Point newLoc = new Point(curVel.x *= -1,curVel.y);
               poop.set_velocity(newLoc);
            }*/
            //poopPoint.y += 5;
            //poopPoint.y = poopPoint.y + ((GameBoard)findViewById(R.id.the_canvas)).poop.get_velocity().y;
            /*if (poopPoint.y > sprite2MaxY || poopPoint.y < 5) {
                Point newLoc = new Point(curVel.x, curVel.y *= -1);
                poop.set_velocity(newLoc);
            }*/
            //dude.setXY(dudePoint.x, dudePoint.y);
            //poop.setXY(poopPoint.x, poopPoint.y);
    }
}
