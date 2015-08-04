package com.tsc.drawing;

/**
 * Created by Todd Cherry on 7/29/2015.
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
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
    private static final int NUM_OF_STARS = 25;
    public Sprite poop;
    public Sprite dude;

    synchronized public void resetStarField() {
        starField = null;
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

        dude.setXY(p1.x, p1.y);
        poop.setXY(p2.x, p2.y);

        poop.genRandomVelocity();
        dude.set_velocity(new Point(1,1));
    }

    public GameBoard(Context context, AttributeSet aSet) {
        super(context, aSet);
        //it's best not to create any new objects in the on draw
        //initialize them as class variables here
        p = new Paint();

        poop = new Sprite(-1,-1, BitmapFactory.decodeResource(getResources(), R.drawable.poop4));
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
        //poop4
        if(poop.getX() >= 0){
            Matrix m = poop.get_matrix();
            m.reset();
            m.postTranslate(poop.getX(), poop.getY());
            m.postRotate(poop.getRotation(),
                    (float) (poop.getX() + poop.getWidth() / 2.0),
                    (float) (poop.getY() + poop.getHeight() / 2.0));

            canvas.drawBitmap(poop.get_bm(), m, null);
            poop.setRotation(5);
            if(poop.getRotation() >= 360)
                poop.setRotation(0);
        }
        String msg = "x: " + poop.getX() + " y: " + poop.getY();

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(30);
        canvas.drawText(msg, 40,40, paint);
    }

    public void frameUpdate() {

            Point dudePoint = dude.getXY();
            Point poopPoint = poop.getXY();
            Point curVel = dude.get_velocity();

            //Now calc the new positions.
            //Note if we exceed a boundary the direction of the velocity gets reversed.
            dudePoint.x = dudePoint.x + curVel.x;
           /* if (dudePoint.x > sprite1MaxX || dudePoint.x < 5) {
                Point newLoc = new Point(curVel.x *= -1,curVel.y);
                dude.set_velocity(newLoc);
            }*/
            dudePoint.y = dudePoint.y + curVel.y;
           /* if (dudePoint.y > sprite1MaxY || dudePoint.y < 5) {
                Point newLoc = new Point(curVel.x, curVel.y *= -1);
                dude.set_velocity(newLoc);
            }*/

            curVel = ((GameBoard)findViewById(R.id.the_canvas)).poop.get_velocity();

            poopPoint.x = poopPoint.x + ((GameBoard)findViewById(R.id.the_canvas)).poop.get_velocity().x;
            /*if (poopPoint.x > sprite2MaxX || poopPoint.x < 5) {
                Point newLoc = new Point(curVel.x *= -1,curVel.y);
               poop.set_velocity(newLoc);
            }*/
            poopPoint.y = poopPoint.y + ((GameBoard)findViewById(R.id.the_canvas)).poop.get_velocity().y;
            /*if (poopPoint.y > sprite2MaxY || poopPoint.y < 5) {
                Point newLoc = new Point(curVel.x, curVel.y *= -1);
                poop.set_velocity(newLoc);
            }*/
            dude.setXY(dudePoint.x, dudePoint.y);
            poop.setXY(poopPoint.x, poopPoint.y);
    }
}
