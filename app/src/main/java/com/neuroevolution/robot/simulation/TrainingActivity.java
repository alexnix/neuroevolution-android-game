package com.neuroevolution.robot.simulation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class TrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_activity);
        WebView wv;
        wv = (WebView) findViewById(R.id.wv1);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
//        wv.setWebChromeClient(new WebChromeClient());
        wv.loadUrl("file:///android_asset/index.html");
    }
}