package com.example.m17336.bt_to_pc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.erz.joysticklibrary.JoyStick;
import com.example.m17336.bt_to_pc.bluetooth_specifics.CommunicationThread;

import java.io.IOException;

public class ControlsActivity extends AppCompatActivity implements JoyStick.JoyStickListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        MyApplication app = (MyApplication) getApplicationContext();
        com = app.getCom();
        if (com != null) com.start();

        JoyStick joyStick = (JoyStick) findViewById(R.id.joy1);
        joyStick.setListener(this);

        Button up = (Button) findViewById(R.id.up);
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

        Button down = (Button) findViewById(R.id.down);
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

        Button left = (Button) findViewById(R.id.left);
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

        Button right = (Button) findViewById(R.id.right);
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


    @Override
    public void onMove(JoyStick joyStick, double angle, double power, int direction) {
//        if(angle < 0)
//            angle = Math.PI + Math.abs(angle);
//        else
//            angle = Math.abs(Math.PI - angle);
//        double f[] = f(Math.cos(angle), Math.sin(angle));
//
//        String command = String.format("%c%02d %c%02d %c%02d", f[0]*power > 0 ? '+' : '-',
//                (int)Math.round(Math.abs(f[0]*power)),
//                f[1]*power > 0 ? '+' : '-', (int)Math.round(Math.abs(f[1]*power)),
//                f[2]*power > 0 ? '+' : '-', (int)Math.round(Math.abs(f[2]*power)));
//
//        if( com != null ) {
//            com.write(command);
//        }
//
//        Log.d("Moved", angle + " " + power+"=>" + f[0] + " " + f[1] + " " +f[2]);
//        Log.d("Command", command);
    }

    @Override
    public void onTap() {

    }

    @Override
    public void onDoubleTap() {

    }

    private String command(double[] f) {
        f[0] = 255/0.68 * f[0];
        f[1] = 255/0.68 * f[1];
        f[2] = 255/0.68 * f[2];

        String command = String.format("%c%03d %c%03d %c%03d",
                f[0] > 0 ? '+' : '-',
                (int)Math.round(Math.abs(f[0])),

                f[1] > 0 ? '+' : '-',
                (int)Math.round(Math.abs(f[1])),

                f[2] > 0 ? '+' : '-',
                (int)Math.round(Math.abs(f[2])));
        
        return  command;
    }



}
