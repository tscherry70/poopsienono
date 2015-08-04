package com.tsc.poopsienono;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tsc.drawing.GameBoard;

import java.util.Random;

public class Main extends Activity implements OnClickListener {

    private Handler frame = new Handler();
    private static final int FRAME_RATE = 20; //50 frames per second
    //Velocity includes the speed and the direction of our sprite motion
    private Point sprite1Velocity;
    private Point sprite2Velocity;
    private int sprite1MaxX;
    private int sprite1MaxY;
    private int sprite2MaxX;
    private int sprite2MaxY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Handler h = new Handler();

        findViewById(R.id.the_button).setOnClickListener(this);
        
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                initGfx();
            }
        }, 1000);
    }

   /* private Point getRandomPoint() {
        Random r = new Random();
        int minX = 0;
        int maxX = findViewById(R.id.the_canvas).getWidth() - ((GameBoard)findViewById(R.id.the_canvas)).dude.getWidth();
        int x = 0;
        int minY = 0;
        int maxY = findViewById(R.id.the_canvas).getHeight() - ((GameBoard)findViewById(R.id.the_canvas)).dude.getHeight();
        int y = 0;
        x = r.nextInt(maxX - minX + 1) + minX;
        y = r.nextInt(maxY - minY +1 ) + minY;
        return new Point (x,y);
    }*/

   /* private Point getRandomVelocity() {
        Random r = new Random();
        int min = 1;
        int max = 5;
        int x = r.nextInt(max-min+1)+min;
        int y = r.nextInt(max-min+1)+min;
        return new Point (x,y);
    }*/

    synchronized public void initGfx() {
        ((GameBoard)findViewById(R.id.the_canvas)).resetStarField();
        findViewById(R.id.the_button).setEnabled(true);
        //It's a good idea to remove any existing callbacks to keep
        //them from inadvertently stacking up.
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, FRAME_RATE);
        ((GameBoard) findViewById(R.id.the_canvas)).initGfx();

       /* Point p1, p2;

        do {
            p1 = getRandomPoint();
            p2 = getRandomPoint();
        } while (Math.abs(p1.x - p2.x) < ((GameBoard)findViewById(R.id.the_canvas)).dude.getWidth());

        ((GameBoard)findViewById(R.id.the_canvas)).dude.setXY(p1.x, p1.y);
        ((GameBoard)findViewById(R.id.the_canvas)).poop.setXY(p2.x, p2.y);

        ((GameBoard)findViewById(R.id.the_canvas)).poop.genRandomVelocity();
        ((GameBoard)findViewById(R.id.the_canvas)).dude.set_velocity(new Point(1,1));


        sprite1MaxX = findViewById(R.id.the_canvas).getWidth() -
                ((GameBoard)findViewById(R.id.the_canvas)).dude.getWidth();
        sprite1MaxY = findViewById(R.id.the_canvas).getHeight() -
                ((GameBoard)findViewById(R.id.the_canvas)).dude.getHeight();
        sprite2MaxX = findViewById(R.id.the_canvas).getWidth() -
                ((GameBoard)findViewById(R.id.the_canvas)).poop.getWidth();
        sprite2MaxY = findViewById(R.id.the_canvas).getHeight() -
                ((GameBoard)findViewById(R.id.the_canvas)).poop.getHeight();*/

        findViewById(R.id.the_button).setEnabled(true);
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, FRAME_RATE);


    }

    @Override
    synchronized public void onClick(View v){
        initGfx();
    }

    private Runnable frameUpdate = new Runnable() {
        @Override
        synchronized public void run() {
            frame.removeCallbacks(frameUpdate);

            ((GameBoard)findViewById(R.id.the_canvas)).frameUpdate();

          /*  Point dudePoint = new Point
                    (((GameBoard)findViewById(R.id.the_canvas)).dude.getX(),
                            ((GameBoard)findViewById(R.id.the_canvas)).dude.getY());
            Point poopPoint = new Point
                    (((GameBoard)findViewById(R.id.the_canvas)).poop.getX(),
                            ((GameBoard)findViewById(R.id.the_canvas)).poop.getY());


            Point curVel = ((GameBoard)findViewById(R.id.the_canvas)).dude.get_velocity();

            //Now calc the new positions.
            //Note if we exceed a boundary the direction of the velocity gets reversed.
            dudePoint.x = dudePoint.x + curVel.x;
            if (dudePoint.x > sprite1MaxX || dudePoint.x < 5) {
                Point newLoc = new Point(curVel.x *= -1,curVel.y);
                ((GameBoard)findViewById(R.id.the_canvas)).dude.set_velocity(newLoc);
            }
            dudePoint.y = dudePoint.y + curVel.y;
            if (dudePoint.y > sprite1MaxY || dudePoint.y < 5) {
                Point newLoc = new Point(curVel.x, curVel.y *= -1);
                ((GameBoard)findViewById(R.id.the_canvas)).dude.set_velocity(newLoc);
            }

            curVel = ((GameBoard)findViewById(R.id.the_canvas)).poop.get_velocity();

            poopPoint.x = poopPoint.x + ((GameBoard)findViewById(R.id.the_canvas)).poop.get_velocity().x;
            if (poopPoint.x > sprite2MaxX || poopPoint.x < 5) {
                Point newLoc = new Point(curVel.x *= -1,curVel.y);
               ((GameBoard)findViewById(R.id.the_canvas)).poop.set_velocity(newLoc);
            }
            poopPoint.y = poopPoint.y + ((GameBoard)findViewById(R.id.the_canvas)).poop.get_velocity().y;
            if (poopPoint.y > sprite2MaxY || poopPoint.y < 5) {
                Point newLoc = new Point(curVel.x, curVel.y *= -1);
                ((GameBoard)findViewById(R.id.the_canvas)).poop.set_velocity(newLoc);
            }
            ((GameBoard)findViewById(R.id.the_canvas)).dude.setXY(dudePoint.x, dudePoint.y);
            ((GameBoard)findViewById(R.id.the_canvas)).poop.setXY(poopPoint.x, poopPoint.y);*/


            //make any updates to on screen objects here
            //then invoke the on draw by invalidating the canvas
            findViewById(R.id.the_canvas).invalidate();
            frame.postDelayed(frameUpdate, FRAME_RATE);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
