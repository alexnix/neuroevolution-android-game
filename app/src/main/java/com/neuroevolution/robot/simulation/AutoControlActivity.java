package com.neuroevolution.robot.simulation;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AutoControlActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    private String tag = "AutoControll";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_control);

        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        start_camera();
    }

    private void start_camera()
    {
        try{
            camera = Camera.open();
        }catch(RuntimeException e){
            Log.e(tag, "init_camera: " + e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        //modify parameter
        param.setPreviewFrameRate(20);
        param.setPreviewSize(176, 144);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            Log.e(tag, "init_camera: " + e);
            return;
        }
    }

    private void stop_camera() {
        camera.stopPreview();
        camera.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop_camera();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
