package com.neuroevolution.robot.simulation;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.neuroevolution.robot.simulation.core.CameraPreview;

public class AutoControlActivity extends AppCompatActivity implements Camera.PreviewCallback {

    private Camera mCamera;
    private CameraPreview mPreview;
    private int width, height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_control);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        mCamera.setPreviewCallback(this);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        width = preview.getWidth();
        height = preview.getHeight();
        preview.addView(mPreview);
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCamera.release();
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Log.d("data", "have");
    }
}
