package com.example.hello;



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.hardware.Camera;

import android.os.Bundle;
import android.os.Environment;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements OnClickListener {

	String normalPicName ="";
	String rightPicName ="";
	String leftPicName ="";
	
	float normalBlackRatio = 0;
	float leftBlackRatio = 0;
	float rightBlackRatio = 0;
//	
//	float ExperimentalData[][] = new float[][]{
//			  
//			  { -10, (float)0.206896551724138},
//			  { -9, (float)0.229885057471264},
//			  { -8, (float)0.258620689655172},
//			  { -7, (float)0.304597701149425},
//			  { -6, (float)0.344827586206897},
//			  { -5, (float)0.408045977011494},
//			  { -4, (float)0.477011494252874},
//			  { -3, (float)0.551724137931034},
//			  { -2, (float)0.666666666666667},
//			  { -1, (float)0.810344827586207},
//			  { 0, 1},
//			  { 1, (float)1.27011494252874},
//			  { 2, (float)1.59770114942529},
//			  { 3, (float)1.8448275862069},
//			  { 4, (float)2.29166666666667},
//			 
//			  
//			 
//			};
	


		//this.listener=listener;

	//instance of camera
	private Camera mCamera;
	//instance of the surfaceview
	private CameraPreview cameraPreview;
	// the framelayout to plug the surfacepreview
	private FrameLayout frameLayout;
	private ImageButton takePictureButton;
	//private TextView textBox;
	private int stepNumber = 1;
	int centerx,centery;
	DrawOnTop mDraw;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		if (!OpenCVLoader.initDebug()) {
	        // Handle initialization error
	    }
	    
		//get the button to take picture
		takePictureButton = (ImageButton)findViewById(R.id.take_picture_button);
		takePictureButton.setOnClickListener(this);
	        
	   //get the textview
		//textBox = (TextView)findViewById(R.id.textbox);
		
		//DrawOnTop mDraw = new DrawOnTop(this); 
		 Display dis1 = getWindowManager().getDefaultDisplay(); 
		 Point size = new Point();
		 dis1.getSize(size);
		 centerx = (int)size.x/2;
		 centery = (int)size.y/2;
//        addContentView(mDraw, new LayoutParams 
//(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
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
	protected void onResume()
	{
		super.onResume();
		if(mCamera == null)
		{
			//set the camera instance 
			if (checkCameraHardware(this)==false)
				this.finish();
			else if((mCamera=getCameraInstance())==null)
				this.finish();
			else
			{
				//requestWindowFeature(Window.FEATURE_NO_TITLE); 
				mDraw = new DrawOnTop(this); 
				cameraPreview = new CameraPreview(this, mCamera);
		        frameLayout = (FrameLayout) findViewById(R.id.camera_preview);
		        frameLayout.addView(cameraPreview);
		        frameLayout.addView(mDraw);
		         
			}
			
		}
	}
	
	
	 @Override
	    protected void onPause() {
	        super.onPause();
	        //releaseCamera();
	        Log.d("", "in pause");

	    }
	 
	 private void releaseCamera(){
	        if (mCamera != null){
	            mCamera.release();        
	            mCamera = null;
	        }
	    }
	 
	 @Override
	 public void onDestroy(){
		 releaseCamera(); 

		 Log.d("", "in ondestroy");
		
		 try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.exit(0);
		 
	 }
    
	@Override
	public void onClick(View clickedView) {
		// TODO Auto-generated method stub

		switch(clickedView.getId())
		{
		case R.id.take_picture_button:
			takePicture();
			break;
		}
	}
	
	private void takePicture()
	{
	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
   	  String date = dateFormat.format(new Date());
   	  
   	  //check if the left or right pic is being taken to name the 
   	  //image accordingly
   	  String preString = null;
   	  if(stepNumber == 1)
   	  {
   		  preString = "Normal";
   		  normalPicName = preString + date + ".jpg";
   	  }
   	  else if (stepNumber == 2)
   	  {
   		  preString = "Left";
   		leftPicName = preString + date + ".jpg";
   	  }
   	  else if (stepNumber == 3)
   	  {
   		  preString = "Right";
   		rightPicName = preString + date + ".jpg";
   	  }
   	  String file_name = preString + date + ".jpg";
   	 
   	  //take the picture
   	  mCamera.takePicture(null, null,
 		        new PhotoHandler(getApplicationContext(),file_name,(MainActivity)this));
  // 	mCamera.stopPreview(); 
 	  mCamera.startPreview();
 	  
	}
	
//call back received from photohandler 
// can prompt the user to proceed to next step
//or repeat the same step	
public void pictureTaken()
{
	FragmentManager manager = this.getSupportFragmentManager();
	 new PictureConfirmationDialogue().show(manager,"confirmation");
            
}
//called from the modal dailog on 
//the user selecting to proceed
public void proceedToNextStep()
{
	 DrawOnTop mDraw2 = new DrawOnTop(this);
 	 // mDraw2.clearCanvas();
 	 frameLayout.addView(mDraw2);
	switch(stepNumber)
	{
	
	case 1:
	//	textBox.setText(getString(R.string.left_lens_measurement));
		stepNumber++;
		break;
	case 2:
		//textBox.setText(getString(R.string.right_lens_measurement));
		stepNumber++;
		break;
	case 3:
		ProgressDialog Asycdialog;
		Asycdialog = ProgressDialog.show(MainActivity.this, "",
                "Calculating .. Please wait ...");
		normalBlackRatio = getBlackRatio(normalPicName);
		Log.d("normal ratio",Double.toString(normalBlackRatio));
		leftBlackRatio = getBlackRatio(leftPicName);
		Log.d("normal ratio",Double.toString(normalBlackRatio));
		rightBlackRatio = getBlackRatio(rightPicName);
		Log.d("normal ratio",Double.toString(normalBlackRatio));
		//new processImage().execute("");
		double[] results = {getPower(leftBlackRatio/normalBlackRatio),getPower(rightBlackRatio/normalBlackRatio)};//getResults();
		Intent i = new Intent(this, ResultsActivity.class);
		
		Bundle bundle = new Bundle();
		bundle.putDoubleArray("results",results);  
	    i.putExtras(bundle);
	    Asycdialog.hide();
	    //Fire the second activity
	    startActivity(i);
	   
		break;
	default :
		break;
	}
	
}
//called from the modal dialog on
//the user selecting to repeat the same step
public void repeatStep()
{
//do nothing	
}

private void getResults()
{

}





protected float getBlackRatio(String filename)
{
	 Log.d("",filename);
	// Log.d("","inside function");
	 BitmapFactory.Options options = new BitmapFactory.Options();
	 options.inSampleSize = 3;
    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    
    File pictureFileDir = getDir();


    filename = pictureFileDir.getPath() + File.separator + filename;

    
    Bitmap bm = BitmapFactory.decodeFile(filename, options);
 //Log.d("","after getting image");
    int width = bm.getWidth();
    int height = bm.getHeight();
    int scaleWidth =  width/4;
    int scaleHeight = height/4;
    // CREATE A MATRIX FOR THE MANIPULATION
    Matrix matrix = new Matrix();     
    matrix.postRotate(90);
    bm =Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

    int centerrow =(int)(bm.getHeight()/2),centercol=(int)(bm.getWidth()/2);
   // Log.d("centers",Integer.toString(centerrow)+"  "+Integer.toString(centerrow));
    Mat tmp=new Mat() ;
    tmp= new Mat (width, height, CvType.CV_8UC1);
  //  Log.d("after mat","");
   // tmp = new Mat (bm.getWidth(), bm.getHeight(), CvType.CV_8UC1);
    Utils.bitmapToMat(bm, tmp);
    Imgproc.cvtColor(tmp, tmp, Imgproc.COLOR_RGB2GRAY);
    
    Imgproc.GaussianBlur(tmp, tmp, new Size(11,11), 0);
 
    
    Imgproc.threshold(tmp,tmp,45,255,Imgproc.THRESH_BINARY_INV);
    Imgproc.dilate(tmp, tmp, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10,10))); 

    int width_left=0,width_right=0,h_down=0,h_up=0;
    
    double[] pixel = tmp.get(centerrow, centercol);
    int i=1;
    while(true)
    {
    	pixel = tmp.get(centerrow, centercol+i);
    	if((int)pixel[0]==0)
    		break;
    	i++;
    	width_right++;
    }
    
    i=0;
    while(true)
    {
    	pixel = tmp.get(centerrow, centercol-i);
    	if((int)pixel[0]==0)
    		break;
    	i++;
    	width_left++;
    }
    
    i=0;
    while(true)
    {
    	pixel = tmp.get(centerrow+i, centercol);
    	if((int)pixel[0]==0)
    		break;
    	i++;
    	h_down++;
    }
    
    i=0;
    while(true)
    {
    	pixel = tmp.get(centerrow-i, centercol);
    	if((int)pixel[0]==0)
    		break;
    	i++;
    	h_up++;
    }
    
    
    
    
 //   Rect roi = new Rect(width, height, width, height);
   Rect roi = new Rect(centercol-width_left-10, centerrow-h_up-10, width_left+width_right+20, h_up+h_down+20);
    Mat cropped = new Mat(tmp, roi);
    
    double max_sum = 0,sum=0;
    for (i = 0; (i < cropped.rows() ) ; i+=5)
    {sum=0;
    	for(int j=0;j<cropped.cols();j++){
    		
    		
          pixel = cropped.get(i, j);
          sum+=pixel[0];
          
          //  Log.d("pixel",Double.toString(pixel[0]));
    	}
    	if (sum>max_sum)
    		max_sum=sum;
    }
    max_sum/=255;
    double black_ratio = (max_sum*max_sum*297/210)/(tmp.rows()*tmp.cols());
    
    Log.d("black ratio ",Double.toString(black_ratio));
    Bitmap bitmap = Bitmap.createBitmap(cropped.cols(), cropped.rows(), Bitmap.Config.ARGB_8888);

    Utils.matToBitmap(cropped, bitmap);
    saveImage(bitmap,"cropped_"+Integer.toString(imagenum));
    imagenum++;
    return (float)black_ratio;
//    return 1;
    


}

int imagenum=1;

private File getDir() {
    File sdDir = Environment
      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    return new File(sdDir, getApplicationContext().getString(R.string.foldername));
  }


private float getPower(float ratio){
	
	Log.d("ratio received in get power: ",Float.toString(ratio));
	 double power =  (4.8032 * Math.pow(ratio,5) + -30.6888 * Math.pow(ratio,4) +   76.6593* Math.pow(ratio,3) +  -95.7733* Math.pow(ratio,2) +   65.0022*ratio +  -20.0359 )   ;//	float min_diff=10;
//	float min_power = 0;
//	float diff =0;
//	for(int i=0;i<ExperimentalData.length;i++)
//	{
//		diff = Math.abs(ExperimentalData[i][1] - ratio);
//		if(diff< min_diff)
//		{
//			min_diff = diff;
//			min_power = ExperimentalData[i][0];
//		}
//	}
//	
	return (float) (Math.round(power*2)/2.0);
	
}





public void saveImage(Bitmap data,String name) {

  File pictureFileDir = getDir();

  if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

    Log.d("", "Can't create directory to save image.");

    
    return;
    
    

  }
  OutputStream outStream = null;
  File file = new File(pictureFileDir.getPath() + File.separator + name+".png");
  try {
   outStream = new FileOutputStream(file);
   data.compress(Bitmap.CompressFormat.PNG, 100, outStream);
   outStream.flush();
   outStream.close();
  }
  catch(Exception e)
  {}

 
  //String photoFile = date;

//  String filename = pictureFileDir.getPath() + File.separator + name;
//
//  File pictureFile = new File(filename);

//  try {
//    FileOutputStream fos = new FileOutputStream(pictureFile);
//    fos.write(data);
//    fos.close();
//
//    Log.d("",filename);
//    
//
//  	    //notify the main activity 
//
//
//  } catch (Exception error) {
//    Log.d("", "File" + filename + "not saved: "
//        + error.getMessage());
//
//  }
}



	class DrawOnTop extends View { 
	
		 private Paint textPaint = new Paint();
		    private Canvas can;
			public DrawOnTop(Context context) {
				super(context);
				// Create out paint to use for drawing
		        textPaint.setARGB(255, 200, 0, 0);
		        textPaint.setTextSize(30);
		        /* This call is necessary, or else the 
		         * draw method will not be called. 
		         */
		        setWillNotDraw(false);
			}
			
			@Override
		    protected void onDraw(Canvas canvas){
				can = canvas;
				Log.d("centers",Integer.toString(centerx)+"   "+Integer.toString(centery));
				// A Simple Text Render to test the display
				String t="";
				
				
				switch(stepNumber)
				{
					
				case 1:
					t="Take a picture without lenses";
					break;
				case 2:
					t="Take a picture with the left lens";
					break;
				case 3:
					t="Take a picture with the right lens";
				}
		        canvas.drawText(t, 50, 50, textPaint);
		        canvas.drawCircle(centerx, centery, 10, textPaint);
			}
			
			public void clearCanvas()
			{
				String t="";
				switch(stepNumber)
				{
		
				case 1:
					t="Take a picture with the left lens";
					break;
				case 2:
					t="Take a picture with the right lens";
					break;
				default:
					t="Take a picture without lenses";
					break;
				}
				can.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
				can.drawText(t, 50, 50, textPaint);
		        can.drawCircle(centerx, centery, 10, textPaint);
			}
	
	} 



}

	




