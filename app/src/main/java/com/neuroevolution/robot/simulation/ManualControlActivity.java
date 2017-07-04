package com.neuroevolution.robot.simulation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.neuroevolution.robot.simulation.bluetooth_specifics.CommunicationThread;
import com.neuroevolution.robot.simulation.core.MyApplication;

import java.io.IOException;

public class ManualControlActivity extends AppCompatActivity {

    private CommunicationThread com;

    private double[][] m = {
            { 0.58, -0.33, 0.33},
            {-0.58, -0.33, 0.33},
            { 0,     0.67, 0.33}
    };

    public double[] f(double ax, double ay) {
        double f[] = new double[3];
        for(int i = 0; i < 3; i++)
            f[i] = m[i][0]*ax + m[i][1]*ay;
        return f;
    }

    private String command(double[] f) {
        f[0] = 255/0.68 * f[0];
        f[1] = 255/0.68 * f[1];
        f[2] = 255/0.68 * f[2];

        String command = String.format("%c%03d %c%03d %c%03d",
                f[0] >= 0 ? '+' : '-',
                (int)Math.round(Math.abs(f[0])),

                f[1] >= 0 ? '+' : '-',
                (int)Math.round(Math.abs(f[1])),

                f[2] >= 0 ? '+' : '-',
                (int)Math.round(Math.abs(f[2])));

        return  command;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        MyApplication app = (MyApplication) getApplicationContext();
        com = app.getCom();
        if (com != null) com.start();

        ImageView up = (ImageView) findViewById(R.id.up);
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        double f[] = f(1, 0);
                        String command = command(f(1, 0));
                        Log.wtf("command", command + " : " + f[0] + " " + f[1] + " " +f[2]);
                        if( com != null )
                            com.write(command(f(1, 0)));
                        break;
                    case MotionEvent.ACTION_UP:
                        if( com != null )
                            com.write(command(f(0, 0)));
                        break;
                }
                return true;
            }
        });

        ImageView down = (ImageView) findViewById(R.id.down);
        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        double f[] = f(-1, 0);
                        String command = command(f(-1, 0));
                        Log.wtf("command", command + " : " + f[0] + " " + f[1] + " " +f[2]);
                        if( com != null )
                            com.write(command(f(-1, 0)));
                        break;
                    case MotionEvent.ACTION_UP:
                        if( com != null )
                            com.write(command(f(0, 0)));
                        break;
                }
                return true;
            }
        });

        ImageView left = (ImageView) findViewById(R.id.left);
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        double f[] = f(0, -1);
                        String command = command(f(0, -1));
                        Log.wtf("command", command + " : " + f[0] + " " + f[1] + " " +f[2]);
                        if( com != null )
                            com.write(command(f(0, -1)));
                        break;
                    case MotionEvent.ACTION_UP:
                        if( com != null )
                            com.write(command(f(0, 0)));
                        break;
                }
                return true;
            }
        });

        ImageView right = (ImageView) findViewById(R.id.right);
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        double f[] = f(0, 1);
                        String command = command(f(0, 1));
                        Log.wtf("command", command + " : " + f[0] + " " + f[1] + " " +f[2]);
                        if( com != null )
                            com.write(command(f(0, 1)));
                        break;
                    case MotionEvent.ACTION_UP:
                        if( com != null )
                            com.write(command(f(0, 0)));
                        break;
                }
                return true;
            }
        });

        ImageView rotate = (ImageView) findViewById(R.id.rotate);
        rotate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        if( com != null )
                            com.write("+200 +200 +200");
                        break;
                    case MotionEvent.ACTION_UP:
                        if( com != null )
                            com.write("+000 +000 +000");
                        break;
                }
                return true;
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if(com != null)
                com.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
