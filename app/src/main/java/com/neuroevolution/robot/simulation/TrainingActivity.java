package com.neuroevolution.robot.simulation;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mukesh.tinydb.TinyDB;

public class TrainingActivity extends AppCompatActivity {

    public static final String BEST_ROBOT = "best_robot_in_current_generation";
    public static final String GENERATION = "generation_Stringified";
    private TinyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_activity);

        db = new TinyDB(this);

        WebView wv;
        wv = (WebView) findViewById(R.id.wv1);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.setWebChromeClient(new WebChromeClient());

        // Inspired from https://stackoverflow.com/questions/7422427/android-webview-slow
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            wv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            wv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        wv.addJavascriptInterface(this, "Android");
        wv.loadUrl("file:///android_asset/webview/web-view-content.html");
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
