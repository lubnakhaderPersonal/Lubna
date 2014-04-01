package com.example.hello;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewSwitcher.ViewFactory;

public class IntroductionActivity extends Activity implements OnClickListener{

private ImageButton img;
private ImageSwitcher imageSwitcher;
private Button nextButton;
private int slideCount = 0;
int imgs[] = 
{ 
	R.drawable.slide1, 
	R.drawable.slide2, 
	R.drawable.slide3, 
	R.drawable.slide4,
	R.drawable.slide5,
	R.drawable.slide6,
	R.drawable.slide7,
	
};
@Override
public void onCreate(Bundle icicle) {
super.onCreate(icicle);
getWindow().setFormat(PixelFormat.TRANSLUCENT);
setContentView(R.layout.introduction_layout);
nextButton = (Button)findViewById(R.id.nextbutton);
nextButton.setOnClickListener(this);

img = (ImageButton)findViewById(R.id.imageButton1);
imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher1);

imageSwitcher.setFactory(new ViewFactory() {

@Override
public View makeView() {
ImageView myView = new ImageView(getApplicationContext());
myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		return myView;
 }

});

imageSwitcher.setImageResource(imgs[slideCount]);
}
@Override
public void onClick(View view) {
	// TODO Auto-generated method stub
	switch(view.getId())
	{
	case R.id.nextbutton:
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
		break;
	}
}

public void next(View view){
    slideCount = (slideCount+1)%7;
    Animation in = AnimationUtils.loadAnimation(this,
    android.R.anim.slide_in_left);
    Animation out = AnimationUtils.loadAnimation(this,
    android.R.anim.slide_out_right);
    imageSwitcher.setInAnimation(in);
    imageSwitcher.setOutAnimation(out);
    imageSwitcher.setImageResource(imgs[slideCount]);
 }
 public void previous(View view){
    slideCount = (slideCount-1);
    if(slideCount < 0)
    	slideCount = 6;
    Animation in = AnimationUtils.loadAnimation(this,
    android.R.anim.slide_out_right);
    Animation out = AnimationUtils.loadAnimation(this,
    android.R.anim.slide_in_left);
    imageSwitcher.setInAnimation(out);
    imageSwitcher.setOutAnimation(in);
    imageSwitcher.setImageResource(imgs[slideCount]);
 }
}
