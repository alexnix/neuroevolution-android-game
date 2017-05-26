package com.neuroevolution.robot.simulation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    public static String NEXT_ACTIVITY = "next_activity";
    public static int MANUAL_CONTROL_ACTIVITY = 0;
    public static int AUTO_CONTROL_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void antreneaza(View view) {
        Intent i = new Intent(this, TrainingActivity.class);
        startActivity(i);
    }

    public void control_automat(View view) {
        Intent i = new Intent(this, ManualControlActivity.class);
        i.putExtra(NEXT_ACTIVITY, MANUAL_CONTROL_ACTIVITY);
        startActivity(i);
    }

    public void control_manual(View view) {
        Intent i = new Intent(this, ManualControlActivity.class);
        i.putExtra(NEXT_ACTIVITY, MANUAL_CONTROL_ACTIVITY);
        startActivity(i);
    }
}
