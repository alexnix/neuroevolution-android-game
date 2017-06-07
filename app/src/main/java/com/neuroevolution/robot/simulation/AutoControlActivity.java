package com.neuroevolution.robot.simulation;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.neuroevolution.robot.simulation.core.CameraPreview;

// Camera functions inspired from https://developer.android.com/guide/topics/media/camera.html
public class AutoControlActivity extends AppCompatActivity implements Camera.PreviewCallback {

    private Camera mCamera;
    private CameraPreview mPreview;
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_control);

        tv = (TextView) findViewById(R.id.display);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        Camera.Parameters camParams = mCamera.getParameters();
        camParams.set("iso", "100");
        mCamera.setParameters(camParams);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
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
        Camera.Parameters mParameters = camera.getParameters();
        Camera.Size mSize = mParameters.getPreviewSize();
        int mWidth = mSize.width;
        int mHeight = mSize.height;

        int rgb[] = new int[mWidth * mHeight];
        YUV_NV21_TO_RGB(rgb, bytes, mWidth, mHeight);

        int i, j, sum, sum_max = 0, max_i = 0, max_j =0;
        float max = 0;
        filtering:
        for( i = 1; i < mHeight - 1; i++) {
            for ( j = 1; j < mWidth - 1; j++) {
                int sumR = ((rgb[(i) * mWidth + (j)] & 0x00FF0000) >> 16) +
                        ((rgb[(i - 1) * mWidth + (j)] & 0x00FF0000) >> 16) +
                        ((rgb[(i + 1) * mWidth + (j)] & 0x00FF0000) >> 16) +
                        ((rgb[(i) * mWidth + (j - 1)] & 0x00FF0000) >> 16) +
                        ((rgb[(i) * mWidth + (j + 1)] & 0x00FF0000) >> 16) +
                        ((rgb[(i - 1) * mWidth + (j + 1)] & 0x00FF0000) >> 16) +
                        ((rgb[(i + 1) * mWidth + (j - 1)] & 0x00FF0000) >> 16) +
                        ((rgb[(i - 1) * mWidth + (j - 1)] & 0x00FF0000) >> 16) +
                        ((rgb[(i + 1) * mWidth + (j + 1)] & 0x00FF0000) >> 16);

                int sumG = ((rgb[(i) * mWidth + (j)] & 0x0000ff00) >> 8) +
                        ((rgb[(i - 1) * mWidth + (j)] & 0x0000ff00) >> 8) +
                        ((rgb[(i + 1) * mWidth + (j)] & 0x0000ff00) >> 8) +
                        ((rgb[(i) * mWidth + (j - 1)] & 0x0000ff00) >> 8) +
                        ((rgb[(i) * mWidth + (j + 1)] & 0x0000ff00) >> 8) +
                        ((rgb[(i - 1) * mWidth + (j + 1)] & 0x0000ff00) >> 8) +
                        ((rgb[(i + 1) * mWidth + (j - 1)] & 0x0000ff00) >> 8) +
                        ((rgb[(i - 1) * mWidth + (j - 1)] & 0x0000ff00) >> 8) +
                        ((rgb[(i + 1) * mWidth + (j + 1)] & 0x0000ff00) >> 8);

                int sumB = ((rgb[(i) * mWidth + (j)] & 0x000000ff)) +
                        ((rgb[(i - 1) * mWidth + (j)] & 0x000000ff)) +
                        ((rgb[(i + 1) * mWidth + (j)] & 0x000000ff)) +
                        ((rgb[(i) * mWidth + (j - 1)] & 0x000000ff)) +
                        ((rgb[(i) * mWidth + (j + 1)] & 0x000000ff)) +
                        ((rgb[(i - 1) * mWidth + (j + 1)] & 0x000000ff)) +
                        ((rgb[(i + 1) * mWidth + (j - 1)] & 0x000000ff)) +
                        ((rgb[(i - 1) * mWidth + (j - 1)] & 0x000000ff)) +
                        ((rgb[(i + 1) * mWidth + (j + 1)] & 0x000000ff));

                sum = sumR + sumB + sumG;
                if(sum > sum_max)
                {
                    sum_max = sum;
                    max_i = i;
                    max_j = j;
                }
//
            }
        }

        Log.d("WORKS", "max: " + max);
        tv.setText(String.valueOf(max_i + " : " + max_j));

    }

    // YUV_NV21 to RGB by Cheok Yan Cheng
    // https://stackoverflow.com/questions/12469730/confusion-on-yuv-nv21-conversion-to-rgb
    public static void YUV_NV21_TO_RGB(int[] argb, byte[] yuv, int width, int height) {
        final int frameSize = width * height;

        final int ii = 0;
        final int ij = 0;
        final int di = +1;
        final int dj = +1;

        int a = 0;
        for (int i = 0, ci = ii; i < height; ++i, ci += di) {
            for (int j = 0, cj = ij; j < width; ++j, cj += dj) {
                int y = (0xff & ((int) yuv[ci * width + cj]));
                int v = (0xff & ((int) yuv[frameSize + (ci >> 1) * width + (cj & ~1) + 0]));
                int u = (0xff & ((int) yuv[frameSize + (ci >> 1) * width + (cj & ~1) + 1]));
                y = y < 16 ? 16 : y;

                int r = (int) (1.164f * (y - 16) + 1.596f * (v - 128));
                int g = (int) (1.164f * (y - 16) - 0.813f * (v - 128) - 0.391f * (u - 128));
                int b = (int) (1.164f * (y - 16) + 2.018f * (u - 128));

                r = r < 0 ? 0 : (r > 255 ? 255 : r);
                g = g < 0 ? 0 : (g > 255 ? 255 : g);
                b = b < 0 ? 0 : (b > 255 ? 255 : b);

                argb[a++] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
    }
}
