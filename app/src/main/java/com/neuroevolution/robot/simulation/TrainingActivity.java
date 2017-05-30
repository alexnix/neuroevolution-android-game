package com.neuroevolution.robot.simulation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.mukesh.tinydb.TinyDB;

public class TrainingActivity extends AppCompatActivity {

    private static final String BEST_ROBOT = "best_robot_in_current_generation";
    private static final String GENERATION = "generation_Stringified";
    private TinyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_activity);

        WebView wv;
        wv = (WebView) findViewById(R.id.wv1);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(this, "Android");
        wv.loadUrl("file:///android_asset/webview/web-view-content.html");

        db = new TinyDB(this);
    }

    @JavascriptInterface
    public void setBest(int idx) {
        db.putInt(BEST_ROBOT, idx);
    }

    @JavascriptInterface
    public int getBest() {
        return db.getInt(BEST_ROBOT);
    }

    @JavascriptInterface
    public void saveGeneration(String gen) {
        db.putString(GENERATION, gen);
    }

    @JavascriptInterface
    public String restoreGeneration() {
        return db.getString(GENERATION);
    }
}
