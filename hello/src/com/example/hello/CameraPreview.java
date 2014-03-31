package com.example.hello;

import java.io.IOException;
import java.security.Policy.Parameters;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = null;
	private SurfaceHolder mHolder;
    private Camera mCamera;
   
    
    
    

   
    
    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        

        Log.d(TAG,"in camera preview constructor");
       
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            
            Log.d(TAG,"in surface created");
            
            mCamera.setPreviewDisplay(holder);
            //Camera.Parameters params = mCamera.getParameters();
            //params.setFocusMode("continuous-picture");
            //mCamera.setParameters(params);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();

           
	        Log.d(TAG,"after auto focus");
            
	        
            
            
            
            
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    	
    	mCamera.stopPreview();
    	
    	//mCamera.release();
    	
    	Log.d(TAG, "in surface destroyed");
    	
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
    //	mCamera.stopPreview();
    
        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        Camera.Parameters parameters= mCamera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        parameters.setPreviewSize(sizes.get(0).width,sizes.get(0).height);
        mCamera.setParameters(parameters);

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}
