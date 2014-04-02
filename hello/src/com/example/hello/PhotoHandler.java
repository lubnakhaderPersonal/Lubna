package com.example.hello;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PhotoHandler implements PictureCallback {

	  private final Context context;
	  private final String date;
	  private MainActivity activity;

	  public PhotoHandler(Context context,String pictureName, MainActivity act) {
	    this.context = context;
	    this.date=pictureName;
	    activity = act;
	  
	  }

	  @Override
	  public void onPictureTaken(byte[] data, Camera mCamera) {

	    File pictureFileDir = getDir();

	    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

	      Log.d("", "Can't create directory to save image.");
	      Toast.makeText(context, "Can't create directory to save image.",
	          Toast.LENGTH_LONG).show();
	      
	      return;

	    }

	    
	  
	   
	    String photoFile = date;

	    String filename = pictureFileDir.getPath() + File.separator + photoFile;

	    File pictureFile = new File(filename);

	    try {
	      FileOutputStream fos = new FileOutputStream(pictureFile);
	      fos.write(data);
	      fos.close();
	      //Toast.makeText(context, "New Image saved:" + filename,
	        //  Toast.LENGTH_LONG).show();
	      Log.d("",filename);
	      
	      Editor editor = 
	    		   context.getSharedPreferences("myState", Context.MODE_PRIVATE)
	    	        .edit();
	    	    editor.clear();
	    	    editor.putString("name", filename);
	    	    editor.commit();
	    	    //notify the main activity 
	    	    activity.pictureTaken();

	    } catch (Exception error) {
	      Log.d("", "File" + filename + "not saved: "
	          + error.getMessage());
	      Toast.makeText(context, "Image could not be saved.",
	          Toast.LENGTH_LONG).show();
	    }
	  }

	  private File getDir() {
	    File sdDir = Environment
	      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	    return new File(sdDir, context.getString(R.string.foldername));
	  }
	  
	  
	} 

