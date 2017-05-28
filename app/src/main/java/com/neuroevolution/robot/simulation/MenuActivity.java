package com.neuroevolution.robot.simulation;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    public static String NEXT_ACTIVITY = "next_activity";
    public static int MANUAL_CONTROL_ACTIVITY = 0;
    public static int AUTO_CONTROL_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView b1 = (TextView) findViewById(R.id.button1);
        TextView b2 = (TextView) findViewById(R.id.button2);
        TextView b3 = (TextView) findViewById(R.id.button3);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/display_font.otf");

        b1.setTypeface(tf);
        b2.setTypeface(tf);
        b3.setTypeface(tf);
    }

    public void antreneaza(View view) {
        Intent i = new Intent(this, TrainingActivity.class);
        startActivity(i);
    }

    public void control_automat(View view) {
        Intent i = new Intent(this, AutoControlActivity.class);
        i.putExtra(NEXT_ACTIVITY, AUTO_CONTROL_ACTIVITY);
        startActivity(i);
    }

    public void control_manual(View view) {
        Intent i = new Intent(this, DeviceList_Activity.class);
        i.putExtra(NEXT_ACTIVITY, MANUAL_CONTROL_ACTIVITY);
        startActivity(i);
    }
}
